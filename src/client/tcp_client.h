#ifndef __TCP_CLIENT_H__
#define __TCP_CLIENT_H__

#include <IceUtil/Thread.h>

#include "src/common/common.h"
#include "src/client/client.h"

namespace xlog
{

class TcpClient: public Client
{
public:

    TcpClient(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
            const int maxQueueSize = 10000);

protected:

    virtual AgentAdapterPtr getAgentAdapter();
};

}

#endif
