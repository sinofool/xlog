/*
 * 管理zookeeper连接的类
 */

#ifndef __ZKMANAGER_H__
#define __ZKMANAGER_H__

#include <Ice/Ice.h>
#include <IceUtil/Mutex.h>
#include <IceUtil/Monitor.h>

#include <zookeeper/zookeeper.h>

#include <src/common/common.h>

namespace xlog 
{

class ZkManager : public ::Ice::Object
{
public:

    ZkManager();

public:

    /**
     * 初始化ZkManager
     * param zkAddress zookeeper的连接地址
     * return true 初始化成功，否则false
     */
    bool init(const std::string& zkAdress);

    /**
     * 为agentCM_赋值，需要在初始化时进行一次赋值
     * param agentCM 初始化好的AgentConfigManager实例
     */
    void setAgentConfigManager(const AgentConfigManagerPtr& agentCM);

    /**
     * 为dispatcherCM_赋值，需要在初始化时进行一次赋值
     * param dispatcherCM 初始化好的AgentConfigManager实例
     */
    void setDispatcherConfigManager(const DispatcherConfigManagerPtr& dispatcherCM);

    /**
     * 创建zookeeper临时节点，临时节点没有数据，只会创建目录
     * param path zkAddress_的相对地址
     * return true 创建成功，否则false
     */
    bool createEphemeralNode(const std::string& path);

    /**
     * 获取zookeeper中path对应路径的子节点
     * param path zkAddress_的相对地址
     * return path对应的子节点，如果获取失败则返回一个空的vector
     */
    std::vector<std::string> getChildren(const std::string& path);

    /**
     * zookeeper watcher 调用的接口，通知zookeeper连接已经建立成功
     */
    void notifyConnected();
    
    /**
     * 当zookeeper数据有变化时，通知已经注册的configmanager进行数据更新
     */
    void notifyChange();

    /**
     * 当zookeeper session expired时，重新建立zookeeper的连接
     */
    void reInit();

private:

    std::string zkAddress_; /*zookeeper连接的地址*/

    AgentConfigManagerPtr agentCM_; /*负责agent的配置管理*/
    
    DispatcherConfigManagerPtr dispatcherCM_; /*负责dispatcher的配置管理*/

    ::IceUtil::Monitor< ::IceUtil::Mutex> zhMonitor_; /*zh_的锁*/

    zhandle_t* zh_; /*zookeeper的连接*/
};

static ZkManagerPtr zm__; /*全局的ZkManager用于zookeeper watcher调用*/

}

#endif
