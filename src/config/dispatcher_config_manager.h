#ifndef __DISPATCHER_CONFIGMANAGER_H__
#define __DISPATCHER_CONFIGMANAGER_H__

#include <vector>

#include <IceUtil/RWRecMutex.h>

#include "xlog.h"
#include "src/common/common.h"
#include "src/common/zookeeper_listener.h"

namespace xlog
{

class DispatcherConfigManager : public ZooKeeperListener
{
public:

    DispatcherConfigManager(const ZkManagerPtr& zm);

public:

    bool init();

    std::vector<DispatcherPrx> getConfig();

    void subscribe(const ::Ice::StringSeq& categories, const std::string& prxStr);

public:

    virtual bool handle();

private:

    ZkManagerPtr _zm;

    ::IceUtil::RWRecMutex _configMutex;
    
    std::vector<DispatcherPrx> _config;
};

}

#endif
