#ifndef __TCP_AGENT_ADAPTER_H__
#define __TCP_AGENT_ADAPTER_H__

#include "src/adapter/agent_adapter.h"

namespace xlog
{

class TcpAgentAdapter: public AgentAdapter
{
protected:
    virtual void setPrxs(const ::Ice::StringSeq& config);
};

}
#endif
