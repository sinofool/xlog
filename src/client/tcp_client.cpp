#include "src/adapter/agent_adapter.h"
#include <iostream>
#include "src/client/tcp_client.h"

namespace xlog
{

TcpClient::TcpClient(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
        const int maxQueueSize) :
        Client(prxStr, defaultAgents, maxQueueSize)
{
   init(false);
}
/*
bool TcpClient::initAgentAdapter()
{
    bool res=false;
    if (_agentAdapter == 0)
    {
        ::IceUtil::Mutex::Lock lock(_agentMutex);
        if (_agentAdapter == 0)
        {
            _agentAdapter = new AgentAdapter;

            bool flag=_agentAdapter->init(_prxStr, _defaultAgents,false);
	    if (flag)
            {
                res=true;
            }
        }
    }
    return res;
}
*/
}
