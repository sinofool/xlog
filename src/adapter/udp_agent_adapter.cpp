#include "src/common/util.hpp"
#include "src/adapter/udp_agent_adapter.h"

namespace xlog
{

void UdpAgentAdapter::setPrxs(const ::Ice::StringSeq&  config)
{
    std::vector<AgentPrx> res;
    for(::Ice::StringSeq::const_iterator it = config.begin(); it !=config.end(); ++it)
    {
        res.push_back(Util::getPrx<AgentPrx>(_ic, *it));
    }

    ::IceUtil::RWRecMutex::WLock lock(_rwMutex);

    _agents.swap(res);
}

}
