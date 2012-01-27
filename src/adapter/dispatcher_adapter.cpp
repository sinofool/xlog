#include "src/config/dispatcher_config_manager.h"
#include "src/adapter/dispatcher_adapter.h"

namespace xlog
{

DispatcherAdapter::DispatcherAdapter(const DispatcherConfigManagerPtr& dispatcherCM) :
        _dispatcherCM(dispatcherCM)
{
}

bool DispatcherAdapter::send(const LogDataSeq& data)
{
    //TODO
    return true;
}

bool DispatcherAdapter::sendFailedLogData(const LogDataSeq& data)
{
    //TODO
    return true;
}

}
