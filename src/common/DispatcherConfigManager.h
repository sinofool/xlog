#ifndef __DISPATCHER_CONFIGMANAGER_H__
#define __DISPATCHER_CONFIGMANAGER_H__

#include <xlog.h>
#include <IceUtil/RWRecMutex.h>

#include <src/common/common.h>
#include <src/common/ZooKeeperListener.h>

namespace xlog
{

class DispatcherConfigManager : public ZooKeeperListener
{
public:

    DispatcherConfigManager(const ZkManagerPtr& zm);

public:

    bool init();

    std::vector<DispatcherPrx> getConfig();

public:

    virtual bool handle();

private:

    ZkManagerPtr zm_;

    ::IceUtil::RWRecMutex configMutex_;
    
    std::vector<DispatcherPrx> config_;
};

}

#endif
