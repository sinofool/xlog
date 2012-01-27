#ifndef __DISPATCHER_ADAPTER_H__
#define __DISPATCHER_ADAPTER_H__

#include <Ice/Ice.h>

#include <xlog.h>
#include "src/common/common.h"

namespace xlog
{

class DispatcherAdapter: public Ice::Object
{
public:
    DispatcherAdapter(const DispatcherConfigManagerPtr& dispatcherCM);
    bool send(const LogDataSeq& data);
    bool sendFailedLogData(const LogDataSeq& data);

private:
    DispatcherConfigManagerPtr _dispatcherCM;
};

}

#endif
