#ifndef __UDP_AGENT_ADAPTER_H__
#define __UDP_AGENT_ADAPTER_H__

#include "src/adapter/agent_adapter.h"

namespace xlog
{

class UdpAgentAdapter: public AgentAdapter
{
protected:
    virtual void setPrxs(const ::Ice::StringSeq& config);
};

}
#endif
