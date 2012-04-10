#include "src/common/zk_conn.h"
#include "src/config/dispatcher_config.h"
#include <boost/lexical_cast.hpp>
#include <iostream>

namespace xlog
{

DispatcherConfig::DispatcherConfig(const ZKConnectionPtr& conn) :
        _conn(conn)
{
}

bool DispatcherConfig::init()
{
    IceUtil::RWRecMutex::WLock lock(_config_mutex);
    if(slots==0){
      std::vector<std::string> slot_nodes = _conn->getChildren(DISPATCHERS_PATH, 0);
      slots=slot_nodes.size();
      for(std::vector<std::string>::iterator it=slot_nodes.begin();it!=slot_nodes.end();it++){
        if(std::string("master").compare(*it)==0){
          slots=slots-1;
          break;
        }
      }
    }

    for(int i=0;i<slots;i++)
    {
       updateSlot(i);
    }
    
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
    std::string event_path=event.path;
    std::cout << __FILE__ <<":" << __LINE__ << " eventPath=" << event_path 
                 << ".It is watching this path." << std::endl;
    
    int pos=event_path.find_last_of("/\\");
    int slot=boost::lexical_cast<int>(event_path.substr(pos+1));
    updateSlot(slot);
}

void DispatcherConfig::updateSlot(int slot_id)
{
    std::string path(DISPATCHERS_PATH);
    path.append("/");
    path.append(boost::lexical_cast<std::string>(slot_id));

    std::vector<std::string> znodes = _conn->getChildren(path, this);
    std::vector<std::string> dispatcher_nodes;
    for (VectorOfString::iterator it = znodes.begin(); it != znodes.end(); ++it)
    {
        std::string znode = *it;
        VectorOfChar znode_data = _conn->getData(path + "/" + znode, 0);

        std::string endpoint;
        endpoint.assign(znode_data.begin()+4, znode_data.end());
        dispatcher_nodes.push_back(endpoint);
        znode_data.clear();
    }
    _config[slot_id]=dispatcher_nodes;
    _version++;

    for(int i=0;i<slots;i++){
        std::vector<std::string> vect=_config[i];
        for(std::vector<std::string>::iterator it=vect.begin();it!=vect.end();it++){
           std::cout << "address:" << *it <<std::endl;
        }
    }
}
/*
std::vector<std::string> DispatcherConfig::get(){
  IceUtil::RWRecMutex::RLock lock(_config_mutex);
  return std::vector<std::string>();
}
*/
std::map<int,std::vector<std::string> > DispatcherConfig::get()
{
    IceUtil::RWRecMutex::RLock lock(_config_mutex);
    std::map<int,std::vector<std::string> > address_map;
    address_map.swap(_config);
    return address_map;
}

int DispatcherHashKey::hash(std::vector<std::string> key, int size)
{
    int count=0;
    for(std::vector<std::string>::iterator it=key.begin();it!=key.end();it++)
    {
        std::string category=*it;
        const char *p=category.c_str();
        int len =category.size();
        for(int i=0;i<len;i++)
        {
            count+=(int)*p++;
        }
    }
    return count % size;
}

}
