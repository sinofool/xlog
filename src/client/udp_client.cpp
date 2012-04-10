#include "src/adapter/agent_adapter.h"

#include "src/client/udp_client.h"

namespace xlog
{

UdpClient::UdpClient(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
        const int maxQueueSize) :
        Client(prxStr, defaultAgents, maxQueueSize)
{
    init(true);
}
/*
AgentAdapter UdpClient::getAgentAdapter()
{
    if (_agentAdapter == 0)
    {
        ::IceUtil::Mutex::Lock lock(_agentMutex);
        if (_agentAdapter == 0)
        {
            _agentAdapter = new AgentAdapter;

            if (!_agentAdapter->init(_prxStr, _defaultAgents,true))
            {
                _agentAdapter = 0;
            }
        }
    }
    return _agentAdapter;
}
*/
}
