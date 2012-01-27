#include <algorithm>

#include "src/common/zk_manager.h"
#include "src/config/client_config_manager.h"

namespace xlog
{

ClientConfigManager::ClientConfigManager(const ZkManagerPtr& zm) :
                                         _zm(zm)
{
}

bool ClientConfigManager::init()
{
    return handle();
}

bool ClientConfigManager::handle()
{
    if(!_zm)
    {
        std::cerr<<"ClientConfigManager::handle failed, because zkmananager is null!"<<std::endl;
        return false;
    }
    
    std::vector<std::string> newConfig = update();

    if(newConfig.empty())
    {
        std::cerr<<"ClientConfigManager::handle can not get clients config from zk!"<<std::endl;
        return false;
    }

    setConfig(newConfig);

    return true;
}

bool ClientConfigManager::subscribe(const std::string& prx)
{
    if(!_zm)
    {
        std::cerr<<"ClientConfigManager::subscribe failed, because zkmanager is null!"<<std::endl;
    }
   
    if(prx == "")
    {
        std::cerr<<"ClientConfigManager::subscribe failed, because prx is null!"<<std::endl;
    }
    
    return _zm->createNormalNode(CLIENTS_PATH + prx);
}

bool ClientConfigManager::remove(const std::string& prx)
{
    if(!_zm)
    {
        std::cerr<<"ClientConfigManager::remove failed, because zkmanager is null!"<<std::endl;
    }
   
    if(prx == "")
    {
        std::cerr<<"ClientConfigManager::remove failed, because prx is null!"<<std::endl;
    }
    
    return _zm->deleteNormalNode(CLIENTS_PATH + prx);
}

std::vector<std::string> ClientConfigManager::update()
{
    if(!_zm)
    {
        std::cerr<<"ClientConfigManager::update failed, because zkmanager is null!"<<std::endl;
    }

    return _zm->getChildren(CLIENTS_PATH);
}

void ClientConfigManager::setConfig(const std::vector<std::string>& config)
{
    ::IceUtil::RWRecMutex::WLock lock(_configMutex);
    _config = config;
}

std::vector<std::string> ClientConfigManager::getConfig()
{
    ::IceUtil::RWRecMutex::RLock lock(_configMutex);
    return _config;
}

}

