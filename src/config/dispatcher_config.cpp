#include "src/common/zk_conn.h"
#include "src/config/dispatcher_config.h"

namespace xlog
{

DispatcherConfig::DispatcherConfig(const ZKConnectionPtr& conn) :
        _conn(conn)
{
}

bool DispatcherConfig::init()
{
    std::vector<std::string> dispatchers = _conn->getChildren(DISPATCHERS_PATH, this);
    VectorOfString newconfig;
    for (VectorOfString::iterator it = dispatchers.begin(); it != dispatchers.end(); ++it)
    {
        std::string dispatcher_name = *it;
        VectorOfChar data = _conn->getData(std::string(DISPATCHERS_PATH) + "/" + dispatcher_name,
                0);

        std::string endpoint;
        endpoint.assign(data.begin(), data.end());
        newconfig.push_back(endpoint);
    }
    IceUtil::RWRecMutex::WLock lock(_config_mutex);
    _config.swap(newconfig);
    _version++;
    return true;
}

void DispatcherConfig::process(ZKWatchedEvent event)
{
    std::cout << __FILE__ << ":" << __LINE__ << std::endl;
    if (event.keeperState != SyncConnected)
    {
        std::cerr << __FILE__ <<":" << __LINE__ << " keeperState=" << event.keeperState
                << " is not SyncConnected" << std::endl;
        return;
    }
    if (event.eventType != NodeChildrenChanged)
    {
        std::cerr << __FILE__ <<":" << __LINE__ << " eventType=" << event.eventType
                << " is not NodeChildrenChanged. It is only watching this event." << std::endl;
        //TODO re-watch
        return;
    }
    if (event.path != DISPATCHERS_PATH)
    {
        std::cerr << __FILE__ <<":" << __LINE__ << " eventPath=" << event.path << " is not "
                << DISPATCHERS_PATH << ". It is only watching this path." << std::endl;
    }
    init();
}

std::vector<std::string> DispatcherConfig::get()
{
    IceUtil::RWRecMutex::RLock lock(_config_mutex);
    return _config;
}

}
