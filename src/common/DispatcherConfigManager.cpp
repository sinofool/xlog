#include <src/common/ZkManager.h>
#include <src/common/DispatcherConfigManager.h>

namespace xlog
{

DispatcherConfigManager::DispatcherConfigManager(const ZkManagerPtr& zm) : zm_(zm)
{
}

bool DispatcherConfigManager::init()
{
    //TODO
    return true;
}

bool DispatcherConfigManager::handle()
{
    //TODO
    return true;
}

std::vector<DispatcherPrx> DispatcherConfigManager::getConfig()
{
    ::IceUtil::RWRecMutex::RLock lock(configMutex_);
    return config_;
}

}
