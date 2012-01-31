#include <algorithm>

#include "src/common/zk_manager.h"
#include "src/adapter/client_adapter.h"
#include "src/config/agent_config_manager.h"

namespace xlog
{

AgentConfig::AgentConfig(const std::string& prx, const ZkManagerPtr& zm,
        const ClientAdapterPtr& clientAdapter) :
        _prx(prx), _zm(zm), _clientAdapter(clientAdapter)
{
}

bool AgentConfig::init()
{
    return handle();
}

bool AgentConfig::handle()
{
    if (!_zm)
    {
        std::cerr << "AgentConfig::handle failed, because zkmananager is null!" << std::endl;
        return false;
    }

    if (_prx == "")
    {
        std::cerr << "AgentConfig::handle failed, because _prx is null!" << std::endl;
        return false;
    }

    std::vector < std::string > newConfig = update();

    if (newConfig.empty())
    {
        std::cerr << "AgentConfig::handle can not get agents config from zk!" << std::endl;
        return false;
    }

    std::vector<std::string>::const_iterator findIt = find(newConfig.begin(), newConfig.end(),
            _prx);

    if (findIt == newConfig.end())
    {
        if (subscribe())
        {
            newConfig.push_back(_prx);
        }
        else
        {
            return false;
        }
    }

    setConfig(newConfig);

    notifyClients();

    return true;
}

bool AgentConfig::subscribe()
{
    if (!_zm)
    {
        std::cerr << "AgentConfig::subscribe failed, because zkmanager is null!"
                << std::endl;
    }

    if (_prx == "")
    {
        std::cerr << "AgentConfig::subscribe failed, because _prx is null!" << std::endl;
    }

    return _zm->createEphemeralNode(AGENTS_PATH + _prx);
}

std::vector<std::string> AgentConfig::update()
{
    if (!_zm)
    {
        std::cerr << "AgentConfig::update failed, because zkmanager is null!" << std::endl;
    }

    return _zm->getChildren(AGENTS_PATH);
}

void AgentConfig::setConfig(const std::vector<std::string>& config)
{
    ::IceUtil::RWRecMutex::WLock lock(_configMutex);
    _config = config;
}

//TODO
//如果判断配置信息是否有变化再决定是否通知会减少不必要的通知
void AgentConfig::notifyClients()
{
    std::vector < std::string > config = getConfig();

    _clientAdapter->notify(config);
}

std::vector<std::string> AgentConfig::getConfig()
{
    ::IceUtil::RWRecMutex::RLock lock(_configMutex);
    return _config;
}

}

