#ifndef __DISPATCHER_CONFIGMANAGER_H__
#define __DISPATCHER_CONFIGMANAGER_H__

#include <vector>
#include <map>

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
    std::map<int,std::vector<std::string> > get();
    //std::vector<std::string> get();
    long version(){
        return _version;
    }
    virtual void process(ZKWatchedEvent event);
private:
    void updateSlot(int slot_id);

    ZKConnectionPtr _conn;
    IceUtil::RWRecMutex _config_mutex;
    std::map<int,std::vector<std::string> > _config;
    long _version;
    int slots;
};

class DispatcherHashKey
{
  public :
    int hash(std::vector<std::string> key, int size);
};
}

#endif
