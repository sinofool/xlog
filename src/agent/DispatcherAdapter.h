#ifndef _DISPATCHER_ADAPTER_H__
#define _DISPATCHER_ADAPTER_H__

#include <Ice/Ice.h>

#include <xlog.h>
#include <src/common/common.h>
#include <src/common/DispatcherConfigManager.h>

namespace xlog
{

class DispatcherAdapter : public Ice::Object
{
public:

    void setDispatcherConfigManager(const DispatcherConfigManagerPtr& dispatcherCM);

    bool send(const LogDataSeq& datas);
    
    bool sendFailedLogDatas(const LogDataSeq& datas);

private:

    DispatcherConfigManagerPtr dispatcherCM_;

};

}

#endif
