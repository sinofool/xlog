#ifndef __CLIENT_CONFIGMANAGER_H__
#define __CLIENT_CONFIGMANAGER_H__

#include <vector>
#include <string>

#include <IceUtil/RWRecMutex.h>

#include "src/common/common.h"
#include "src/common/zookeeper_listener.h"

namespace xlog
{

static const char* CLIENTS_PATH = "/clients/"; /*client在zookeeper中的父目录*/

class ClientConfig: public ZooKeeperListener
{
public:

    ClientConfig(const ZkManagerPtr& zm);

    /**
     * 初始化
     * return true 成功 false 失败
     */
    bool init();

    /**
     * 获取所有clients
     * return 所有配置，如果失败则返回空的vector
     */
    std::vector<std::string> getConfig();

    /**
     * 将prx注册到zookeeper上
     * return true 成功 false 失败
     */
    bool subscribe(const std::string& prx);

    /**
     * 将prx从zookeeper上删除
     * return true 成功 false 失败
     */
    bool remove(const std::string& prx);

    /**
     * 实现ZooKeeperListener的纯虚方法
     * return true 成功 false 失败
     */
    virtual bool handle();

private:

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

    ZkManagerPtr _zm; /*初始化设置的zkmanager*/

    ::IceUtil::RWRecMutex _configMutex; /*config_的读写锁，使用读写锁是为了提高性能*/

    std::vector<std::string> _config; /*配置信息*/

};

}

#endif
