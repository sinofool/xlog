#include <src/agent/DispatcherAdapter.h>

namespace xlog
{

DispatcherAdapter::DispatcherAdapter(const DispatcherConfigManagerPtr& dispatcherCM) : dispatcherCM_(dispatcherCM)
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
