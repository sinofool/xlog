
#include "src/common/zk_manager.h"
#include "src/config/dispatcher_config_manager.h"

namespace xlog
{

DispatcherConfigManager::DispatcherConfigManager(const ZkManagerPtr& zm) : _zm(zm)
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
    ::IceUtil::RWRecMutex::RLock lock(_configMutex);
    return _config;
}

void DispatcherConfigManager::subscribe(const ::Ice::StringSeq& categories, const std::string& prxStr)
{
    //TODO
}

}
