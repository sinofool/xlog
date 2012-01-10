#ifndef __AGENT_CONFIGMANAGER_H__
#define __AGENT_CONFIGMANAGER_H__

#include <string>

#include <IceUtil/RWRecMutex.h>

#include <src/common/common.h>
#include <src/common/ZooKeeperListener.h>

namespace xlog
{

const std::string AGENTS_PATH = "/agents/"; /*agent在zookeeper中的父目录*/

class AgentConfigManager : public ZooKeeperListener
{
public:

    AgentConfigManager(const std::string& prx, const ZkManagerPtr& zm);

public:

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

public:
   
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

private:

    ZkManagerPtr zm_; /*初始化设置的zkmanager*/

    std::string prx_; /*需要向zookeeper注册的prx字符串*/

    ::IceUtil::RWRecMutex configMutex_; /*config_的读写锁，使用读写锁是为了提高性能*/

    std::vector<std::string> config_; /*配置信息*/

};

}

#endif
