#ifndef __UDP_CLIENT_H__
#define __UDP_CLIENT_H__

#include "src/common/common.h"
#include "src/client/client.h"

namespace xlog
{

class UdpClient: public Client
{
public:

    UdpClient(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
            const int maxQueueSize = 10000);

};

}

#endif
