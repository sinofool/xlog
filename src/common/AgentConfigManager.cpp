#include <algorithm>

#include <src/common/ZkManager.h>
#include <src/common/AgentConfigManager.h>

namespace xlog
{

AgentConfigManager::AgentConfigManager(const std::string& prx, const ZkManagerPtr& zm) :
                                        prx_(prx), zm_(zm)
{
}

bool AgentConfigManager::init()
{
    return handle();
}

bool AgentConfigManager::handle()
{
    if(!zm_ )
    {
        std::cerr<<"AgentConfigManager::handle failed, because zkmananager is null!"<<std::endl;
        return false;
    }
    
    if(prx_ == "")
    {
        std::cerr<<"AgentConfigManager::handle failed, because prx_ is null!"<<std::endl;
        return false;
    }

    std::vector<std::string> newConfig = update();

    if(newConfig.empty())
    {
        std::cerr<<"AgentConfigManager::handle can not get agents config from zk!"<<std::endl;
        return false;
    }

    std::vector<std::string>::const_iterator findIt = find(newConfig.begin(), newConfig.end(), prx_);

    if(findIt == newConfig.end())
    {
        if(subscribe())
        {
            newConfig.push_back(prx_);
        }else
        {
            return false;
        }
    }

    setConfig(newConfig);

    return true;
}

bool AgentConfigManager::subscribe()
{
    if(!zm_)
    {
        std::cerr<<"AgentConfigManager::subscribe failed, because zkmanager is null!"<<std::endl;
    }
   
    if(prx_ == "")
    {
        std::cerr<<"AgentConfigManager::subscribe failed, because prx_ is null!"<<std::endl;
    }
    
    return zm_->createEphemeralNode(AGENTS_PATH+prx_);
}

std::vector<std::string> AgentConfigManager::update()
{
    if(!zm_)
    {
        std::cerr<<"AgentConfigManager::update failed, because zkmanager is null!"<<std::endl;
    }

    return zm_->getChildren(AGENTS_PATH);
}

void AgentConfigManager::setConfig(const std::vector<std::string>& config)
{
    ::IceUtil::RWRecMutex::WLock lock(configMutex_);
    config_ = config;
}

std::vector<std::string> AgentConfigManager::getConfig()
{
    ::IceUtil::RWRecMutex::RLock lock(configMutex_);
    return config_;
}

}

