#include "src/common/util.h"
#include "src/adapter/tcp_agent_adapter.h"

namespace xlog
{

void TcpAgentAdapter::setPrxs(const Ice::StringSeq& config)
{
    std::vector<AgentPrx> res;
    for (Ice::StringSeq::const_iterator it = config.begin(); it != config.end(); ++it)
    {
        res.push_back(Util::getPrx<AgentPrx>(_ic, *it, false, 300));
    }

    ::IceUtil::RWRecMutex::WLock lock(_rwMutex);

    _agents.swap(res);
}

}
