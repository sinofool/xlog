#include <src/common/ZkManager.h>
#include <src/common/ConfigManager.h>

namespace xlog
{

ConfigManager::ConfigManager()
{
    zm_ = NULL;
    
    prx_ = "";
}

void ConfigManager::setZkManager(const ZkManagerPtr& zm)
{
    zm_ = zm;
}

void ConfigManager::setPrx(const std::string& prx)
{
    prx_ = prx;
}

std::vector<std::string> ConfigManager::getConfig()
{
    ::IceUtil::RWRecMutex::RLock lock(configMutex_);
    return config_;
}

void ConfigManager::setConfig(const std::vector<std::string>& config)
{
    ::IceUtil::RWRecMutex::WLock lock(configMutex_);
    config_ = config;
}

}
