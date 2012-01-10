#ifndef __CONFIGMANAGER_H__
#define __CONFIGMANAGER_H__

#include <vector>
#include <string>

#include <Ice/Ice.h>
#include <IceUtil/RWRecMutex.h>

#include <xlog.h>
#include <src/common/common.h>

namespace xlog
{

class ConfigManager : public Ice::Object
{
public:

    ConfigManager();

public:

    /**
     * 设置ZkManager
     * param zm ZkManager的智能指针
     */
    void setZkManager(const ZkManagerPtr& zm);

    /**
     * 设置需要注册的prx字符串
     * param prx 需要注册的字符串
     */
    void setPrx(const std::string& prx);
   
    /**
     * 获取配置管理的信息
     * return 配置信息，如果没有则返回空的vector
     */
    std::vector<std::string> getConfig();

protected:

    /**
     * 设置配置信息，提供默认的方法，子类可以重载
     * param config 新的配置信息
     */
    virtual void setConfig(const std::vector<std::string>& config);

public:

    /**
     * 初始化
     * return true 成功，否则false
     */
    virtual bool init() = 0;
    
protected:
   
    /**
     * 将prx_信息注册到zookeeper上，如果prx_为空，则不注册
     * return true 注册成功，或者prx_为空
     */
    virtual bool subscribe() = 0;

    /**
     * 从zookeeper获取最新的配置信息
     * return 最新的配置信息，如果失败则返回空的vector
     */
    virtual std::vector<std::string> update() = 0;

protected:

    ZkManagerPtr zm_;
    
    std::string prx_; /*需要注册的字符串*/

    ::IceUtil::RWRecMutex configMutex_; /*config_的读写锁，使用读写锁是为了提高性能*/

    std::vector<std::string> config_; /*配置信息*/
};

}
#endif
