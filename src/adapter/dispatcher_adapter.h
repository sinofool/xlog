#ifndef __DISPATCHER_ADAPTER_H__
#define __DISPATCHER_ADAPTER_H__

#include <Ice/Ice.h>
#include <IceUtil/RWRecMutex.h>

#include <xlog.h>
#include "src/common/common.h"

namespace xlog
{

class DispatcherAdapter: public Ice::Object
{
public:
    DispatcherAdapter(const Ice::CommunicatorPtr& ic, const DispatcherConfigPtr& config) :
            _ic(ic), _config_dispatcher(config)
    {
    }
    void init();
    bool sendNormal(const slice::LogDataSeq& data);
    bool sendFailed(const slice::LogDataSeq& data);

private:
    Ice::CommunicatorPtr _ic;
    DispatcherConfigPtr _config_dispatcher;

    IceUtil::RWRecMutex _prx_lock;
    VectorOfString _prx_address;
    std::vector<slice::DispatcherPrx> _prx;
    long _prx_version;
    void rebuild_prx();
};

}

#endif
