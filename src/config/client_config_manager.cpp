#include <algorithm>

#include "src/common/zk_manager.h"
#include "src/config/client_config_manager.h"

namespace xlog
{

ClientConfig::ClientConfig(const ZkManagerPtr& zm) :
        _zm(zm)
{
}

bool ClientConfig::init()
{
    return handle();
}

bool ClientConfig::handle()
{
    if (!_zm)
    {
        std::cerr << "ClientConfig::handle failed, because zkmananager is null!"
                << std::endl;
        return false;
    }

    std::vector < std::string > newConfig = update();

    if (newConfig.empty())
    {
        std::cerr << "ClientConfig::handle can not get clients config from zk!" << std::endl;
        return false;
    }

    setConfig(newConfig);

    return true;
}

bool ClientConfig::subscribe(const std::string& prx)
{
    if (!_zm)
    {
        std::cerr << "ClientConfig::subscribe failed, because zkmanager is null!"
                << std::endl;
    }

    if (prx == "")
    {
        std::cerr << "ClientConfig::subscribe failed, because prx is null!" << std::endl;
    }

    return _zm->createNormalNode(CLIENTS_PATH + prx);
}

bool ClientConfig::remove(const std::string& prx)
{
    if (!_zm)
    {
        std::cerr << "ClientConfig::remove failed, because zkmanager is null!" << std::endl;
    }

    if (prx == "")
    {
        std::cerr << "ClientConfig::remove failed, because prx is null!" << std::endl;
    }

    return _zm->deleteNormalNode(CLIENTS_PATH + prx);
}

std::vector<std::string> ClientConfig::update()
{
    if (!_zm)
    {
        std::cerr << "ClientConfig::update failed, because zkmanager is null!" << std::endl;
    }

    return _zm->getChildren(CLIENTS_PATH);
}

void ClientConfig::setConfig(const std::vector<std::string>& config)
{
    ::IceUtil::RWRecMutex::WLock lock(_configMutex);
    _config = config;
}

std::vector<std::string> ClientConfig::getConfig()
{
    ::IceUtil::RWRecMutex::RLock lock(_configMutex);
    return _config;
}

}

