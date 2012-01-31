#include "src/common/zk_conn.h"
#include "src/common/common.h"
#include <boost/bind.hpp>

namespace xlog
{

void ZKWatcher::watcher_callback(zhandle_t *zh, int type, int state, const char *path, void *watcherCtx)
{
    ZKWatchedEvent event(EventType(type), KeeperState(state), path);
    ((ZKWatcher*) watcherCtx)->process(event);
}

bool ZKConnection::init(const std::string& address)
{
    _zk = zookeeper_init(address.c_str(), 0, 2000, 0, 0, 0);
    if (!_zk)
    {
        return false;
    }
    return true;
}
void ZKConnection::create(const std::string& path, const std::vector<char>& data)
{
    ;
}
void ZKConnection::remove(const std::string& path, int version)
{
    ;
}
void ZKConnection::exists(const std::string& path, ZKWatcher* watcher)
{
    ;
}
std::vector<char> ZKConnection::getData(const std::string& path, ZKWatcher* watcher)
{
    std::cout << "Getting data: " << path << std::endl;
    int size = 1024;
    VectorOfChar buffer;
    buffer.reserve(size);
    int rc = zoo_get(_zk, path.c_str(), 0, buffer.data(), &size, NULL);
    if (rc != ZOK)
    {
        std::cerr << __FILE__<<":" << __LINE__<<" zoo_(w)get returns not ZOK. (" << rc << ")"
                << std::endl;
        return std::vector<char>();
    }
    if (size == -1)
    {
        return std::vector<char>();
    }
    VectorOfChar ret;
    ret.assign(buffer.begin(), buffer.begin() + size);
    return ret;
}

bool ZKConnection::setData(const std::string& path, const std::vector<char>& data, int version)
{
    return false;
}

std::vector<std::string> ZKConnection::getChildren(const std::string& path, ZKWatcher* watcher)
{
    std::cout << "Getting children: " << path << std::endl;
    struct String_vector nodes;
    void * ctx;
    int rc =
            watcher ?
                    zoo_wget_children(_zk, path.c_str(), ZKWatcher::watcher_callback, watcher, &nodes) :
                    zoo_get_children(_zk, path.c_str(), 0, &nodes);

    if (rc != ZOK)
    {
        std::cerr << __FILE__<<":" << __LINE__<<" zoo_(w)get_children returns not ZOK. (" << rc
                << ")" << std::endl;
        return std::vector<std::string>();
    }

    VectorOfString ret;
    for (int i = 0; i < nodes.count; ++i)
    {
        std::cout << "Got child: " << nodes.data[i] << std::endl;
        ret.push_back(nodes.data[i]);
    }
    return ret;
}

ZKConnection::~ZKConnection()
{
    zookeeper_close(_zk);
}

}
