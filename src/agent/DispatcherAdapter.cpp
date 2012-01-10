#include <src/agent/DispatcherAdapter.h>

namespace xlog
{

void DispatcherAdapter::setDispatcherConfigManager(const DispatcherConfigManagerPtr& dispatcherCM)
{
    dispatcherCM_ = dispatcherCM;
}

bool DispatcherAdapter::send(const LogDataSeq& datas)
{
    //TODO
    return true;
}

bool DispatcherAdapter::sendFailedLogDatas(const LogDataSeq& datas)
{
    //TODO
    return true;
}

}
