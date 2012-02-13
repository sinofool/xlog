#ifndef __DISPATCHER_CONFIGMANAGER_H__
#define __DISPATCHER_CONFIGMANAGER_H__

#include <vector>

#include <IceUtil/RWRecMutex.h>

#include "xlog.h"
#include "src/common/common.h"
#include "src/common/zk_conn.h"

namespace xlog
{

class DispatcherConfig: public ZKWatcher
{
public:
    DispatcherConfig(const ZKConnectionPtr& zm);
    bool init();
    std::vector<std::string> get();
    long version(){
        return _version;
    }
    virtual void process(ZKWatchedEvent event);
private:
    ZKConnectionPtr _conn;
    IceUtil::RWRecMutex _config_mutex;
    VectorOfString _config;
    long _version;
};

}

#endif
