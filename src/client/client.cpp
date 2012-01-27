#include "src/adapter/agent_adapter.h"
#include "src/client/client.h"

namespace xlog
{

Client::Client(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
        const int maxQueueSize) :
        _prxStr(prxStr), _defaultAgents(defaultAgents), _maxQueueSize(maxQueueSize)
{
    start().detach();
}

void Client::append(const LogDataSeq& data)
{
    ::IceUtil::Monitor<IceUtil::Mutex>::Lock lock(_dataMutex);

    if (_data.size() >= _maxQueueSize)
    {
        std::cerr << "Client::append queue is full, maxQueueSize is " << _maxQueueSize << std::endl;
        return;
    }

    _data.insert(_data.end(), data.begin(), data.end());

    _dataMutex.notify();
}

void Client::run()
{
    for (;;)
    {
        LogDataSeq data;
        {
            ::IceUtil::Monitor<IceUtil::Mutex>::Lock lock(_dataMutex);
            if (_data.empty())
            {
                _dataMutex.wait();
            }
            data.swap(_data);
        }

        AgentAdapterPtr agentAdapter = getAgentAdapter();

        if (agentAdapter == 0)
        {
            std::cerr << "Client::run no agentAdapter found!" << std::endl;
        }

        //TODO
        //发送的数据大小在ice中有限制，可以考虑在发送数据时进行数据分割，防止异常的发生
        agentAdapter->send(data);
    }
}

}
