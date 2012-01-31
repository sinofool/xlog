#ifndef __CLIENT_ADAPTER_H__
#define __CLIENT_ADAPTER_H__

#include <vector>
#include <string>
#include <Ice/Ice.h>
#include "xlog.h"
#include "src/common/common.h"

namespace xlog
{

class ClientAdapter: public Ice::Object
{
public:
    ClientAdapter(const ClientConfigPtr& clientCM);
    void notify(const std::vector<std::string>& agentConfig);

private:
    ClientConfigPtr _clientCM;
    Ice::CommunicatorPtr _ic;
};

}

#endif
