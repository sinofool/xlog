#include "src/config/dispatcher_config.h"
#include "src/adapter/dispatcher_adapter.h"
#include "build/generated/xlog.h"

namespace xlog
{

void DispatcherAdapter::init()
{
    rebuild_prx();
}

bool DispatcherAdapter::sendNormal(const LogDataSeq& data)
{
    long version = _config_dispatcher->version();
    if (version != _prx_version)
    {
        rebuild_prx();
    }
    IceUtil::RWRecMutex::RLock lock(_prx_lock);
    for (size_t i = 0; i < _prx.size(); ++i)
    {
        _prx[i]->add(data);
    }
    return true;
}

bool DispatcherAdapter::sendFailed(const LogDataSeq& data)
{
    //TODO
    return true;
}

void DispatcherAdapter::rebuild_prx()
{
    long version = _config_dispatcher->version();
    VectorOfString new_address = _config_dispatcher->get();
    std::vector<DispatcherPrx> newprx;
    newprx.resize(new_address.size());
    for (size_t i = 0; i < new_address.size(); ++i)
    {
        std::cout << __FILE__ << ":" << __LINE__ << " constructing endpoint " << new_address[i]
                << std::endl;
        newprx[i] = DispatcherPrx::uncheckedCast(_ic->stringToProxy(new_address[i]));
    }

    IceUtil::RWRecMutex::WLock lock(_prx_lock);
    _prx_address.swap(new_address);
    _prx.swap(newprx);
    _prx_version = version;
}

}
