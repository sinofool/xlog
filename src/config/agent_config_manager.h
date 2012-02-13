#ifndef __AGENT_CONFIGMANAGER_H__
#define __AGENT_CONFIGMANAGER_H__

#include <vector>
#include <string>

#include <IceUtil/RWRecMutex.h>

#include "src/common/common.h"
#include "src/common/zookeeper_listener.h"

namespace xlog
{

class AgentConfig: public ZooKeeperListener
{
public:

    AgentConfig(const std::string& prx, const ZkManagerPtr& zm,
            const ClientAdapterPtr& clientAdapter);

    /**
     * 初始化
     * return true 成功 false 失败
     */
    bool init();

    /**
     * 获取所有agents
     * return 所有配置，如果失败则返回空的vector
     */
    std::vector<std::string> getConfig();

    /**
     * 实现ZooKeeperListener的纯虚方法
     * return true 成功 false 失败
     */
    virtual bool handle();

private:

    /**
     * 将prx_注册到zookeeper上
     * return true 成功 false 失败
     */
    bool subscribe();

    /**
     * 从zookeeper获取配置信息
     * return 配置信息，如果失败则返回空的vector
     */
    std::vector<std::string> update();

    /**
     * 设置配置
     * param 新的配置信息
     */
    void setConfig(const std::vector<std::string>& config);

    /**
     * 通知所有client新的agent配置信息
     */
    void notifyClients();

    ZkManagerPtr _zm; /*初始化设置的zkmanager*/

    std::string _prx; /*需要向zookeeper注册的prx字符串*/

    ClientAdapterPtr _clientAdapter;

    ::IceUtil::RWRecMutex _configMutex; /*config_的读写锁，使用读写锁是为了提高性能*/

    std::vector<std::string> _config; /*配置信息*/

};

}

#endif
