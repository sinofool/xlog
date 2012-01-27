#include "src/adapter/tcp_agent_adapter.h"

#include "src/client/tcp_client.h"

namespace xlog
{

TcpClient::TcpClient(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
        const int maxQueueSize) :
        Client(prxStr, defaultAgents, maxQueueSize)
{
}

AgentAdapterPtr TcpClient::getAgentAdapter()
{
    if (_agentAdapter == 0)
    {
        ::IceUtil::Mutex::Lock lock(_agentMutex);
        if (_agentAdapter == 0)
        {
            _agentAdapter = new TcpAgentAdapter;

            if (_agentAdapter->init(_prxStr, _defaultAgents) == false)
            {
                _agentAdapter = 0;
            }
        }
    }
    return _agentAdapter;
}

}
