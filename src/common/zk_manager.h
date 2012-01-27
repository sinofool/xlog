/*
 * 管理zookeeper连接的类
 */

#ifndef __ZKMANAGER_H__
#define __ZKMANAGER_H__

#include <vector>
#include <Ice/Ice.h>
#include <IceUtil/Mutex.h>
#include <IceUtil/Monitor.h>

#include <zookeeper/zookeeper.h>

#include "src/common/common.h"

namespace xlog
{

/**
 * 用来设置全局的ZkManager
 */
void setZkManager(const ZkManagerPtr& zm);

class ZkManager: public ::Ice::Object
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
     * 添加listener赋值，需要在初始化时调用，因为调用没有加锁，不是线程安全的
     * param listener 初始化好的ZooKeeperListener实例
     */
    void addListener(const ZooKeeperListenerPtr& listener);

    /**
     * 创建zookeeper临时节点，临时节点没有数据，只会创建目录
     * param path zkAddress_的相对地址
     * return true 创建成功，否则false
     */
    bool createEphemeralNode(const std::string& path);

    /**
     * 创建zookeeper普通节点，节点没有数据，只会创建目录
     * param path zkAddress_的相对地址
     * return true 创建成功，否则false
     */
    bool createNormalNode(const std::string& path);

    /**
     * 删除zookeeper普通节点
     * param path zkAddress_的相对地址
     * return true 删除成功，否则false
     */
    bool deleteNormalNode(const std::string& path);

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

    std::string _zkAddress; /*zookeeper连接的地址*/

    std::vector<ZooKeeperListenerPtr> _listeners; /*所有listeneragent的配置管理*/

    ::IceUtil::Monitor<IceUtil::Mutex> _zhMonitor; /*zh_的锁*/

    zhandle_t* _zh; /*zookeeper的连接*/
};

static ZkManagerPtr __zm; /*全局的ZkManager用于zookeeper watcher调用*/

}

#endif
