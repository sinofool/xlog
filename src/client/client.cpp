#include "src/adapter/agent_adapter.h"
#include "src/client/client.h"

namespace xlog
{

Client::Client(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
        const bool is_udp_protocol, const int maxQueueSize) :
        _prxStr(prxStr), _defaultAgents(defaultAgents), _is_udp_protocol(is_udp_protocol), _maxQueueSize(maxQueueSize)
{
   _agentAdapter = new AgentAdapter;

   bool flag=_agentAdapter->init(_prxStr, _defaultAgents,_is_udp_protocol);
   if (flag)
   { 
      std::cout << "success to init agent adapter!" << std::endl;
      start().detach(); 
   } else
   {
      std::cerr << "failt to init agent adapter!" << std::endl;
   }
}
/*
void Client::init(bool is_udp_protocol)
{
     _agentAdapter = new AgentAdapter;

     bool flag=_agentAdapter->init(_prxStr, _defaultAgents,is_udp_protocol);
     if (flag)
     { 
        std::cout << "success to init agent adapter!" << std::endl;
        start().detach(); 
     } else
     {
        std::cerr << "failt to init agent adapter!" << std::endl;
     }
}
*/
bool Client::append(const slice::LogDataSeq& data)
{
    ::IceUtil::Monitor<IceUtil::Mutex>::Lock lock(_dataMutex);
    if (_data.size() >= _maxQueueSize)
    {
        std::cerr << "Client::append queue is full, maxQueueSize is " << _maxQueueSize << std::endl;
        return false;
    }

    _data.insert(_data.end(), data.begin(), data.end());

    _dataMutex.notify();

    return true;
}

void Client::run()
{
    for (;;)
    {
        slice::LogDataSeq data;
        {
            ::IceUtil::Monitor<IceUtil::Mutex>::Lock lock(_dataMutex);
            if (_data.empty())
            {    
                _dataMutex.wait();
            }
            data.swap(_data);
        }
        //TODO
        //发送的数据大小在ice中有限制，可以考虑在发送数据时进行数据分割，防止异常的发生
        _agentAdapter->send(data);  
    }
}

}
