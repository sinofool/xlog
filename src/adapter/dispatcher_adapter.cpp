#include "src/config/dispatcher_config.h"
#include "src/adapter/dispatcher_adapter.h"
#include "build/generated/xlog.h"

namespace xlog
{

void DispatcherAdapter::init()
{
    nca = new RoundRobinAlgorithm;
    Ice::PropertiesPtr props=Ice::createProperties();
    props->setProperty("Ice.MessageSizeMax",ICE_MESSAGE_SIZE_MAX);
    Ice::InitializationData id;
    id.properties=props;
    _ic = Ice::initialize(id);
    //rebuild_prx();
}

bool DispatcherAdapter::sendNormal(const slice::LogData& data)
{
    long version = _config_dispatcher->version();
    if (version != _prx_version)
    {
      if(rebuild_prx())
      {
        _prx_version = version;
      }else
      {
        std::cout << __FILE__ << ":" << __LINE__ << " fail to rebuild prx! " << std::endl;
        return false;
      }
    }
    IceUtil::RWRecMutex::RLock lock(_prx_lock);
    
    slice::DispatcherPrx dp=nca->doChoose(data.categories,_prx);
    dp->addLogData(data);

    return true;
}

bool DispatcherAdapter::sendFailed(const slice::LogDataSeq& data)
{
    //TODO
    return true;
}

bool DispatcherAdapter::rebuild_prx()
{
    std::map<int,std::vector<std::string> > new_address = _config_dispatcher->get();
    IceUtil::RWRecMutex::WLock lock(_prx_lock);
    std::map<int,std::vector<std::string> >::iterator iter;
    bool _res = false;
    for(iter=new_address.begin();iter!=new_address.end();iter++)
    {
        int size=iter->second.size();
        if(size==0)
        {
           continue;
        }   
        std::vector<DispatcherNode> dns;
        for(int j=0;j<size;j++)
        {
           std::cout << __FILE__ << ":" << __LINE__ << " constructing endpoint : " << iter->second.at(j) << std::endl;
           DispatcherNode dn;
           dn.dispatcher_prx = slice::DispatcherPrx::uncheckedCast(_ic->stringToProxy(iter->second.at(j))); 
           dn.call_count = 0;
           dns.push_back(dn);
           _res = true;
        }
        _prx[iter->first] = dns;
    }
    return _res;
}

slice::DispatcherPrx LRUChooseAlgorithm::doChoose(const Ice::StringSeq& categories,std::map<int,std::vector<DispatcherNode> >& _prx )
{
    int slot=dispatcher_hash.hash(categories,_prx.size());
    std::vector<DispatcherNode> dns=_prx[slot];
    int least_call_count;
    int pos;
    int size=dns.size();
    for(int i=0;i<size;i++)
    {
        if(i==0)
        {
           least_call_count = dns.at(0).call_count;
           pos=0;
        }else
        {
           if(least_call_count > dns.at(i).call_count)
           {
              least_call_count = dns.at(i).call_count;
              pos=i;
           }
        }
    }
    DispatcherNode dn=dns.at(pos);
    dn.call_count=least_call_count+1;
    return dn.dispatcher_prx;
}

slice::DispatcherPrx RoundRobinAlgorithm::doChoose(const Ice::StringSeq& categories,std::map<int,std::vector<DispatcherNode> >& _prx_map )
{
    int slot=dispatcher_hash.hash(categories,_prx_map.size());
    std::vector<DispatcherNode> dns=_prx_map[slot];
    int size=dns.size();
    if(current_dn_num==size){
       current_dn_num=0;
    }
    DispatcherNode dn=dns.at(current_dn_num);
    current_dn_num++;
    return dn.dispatcher_prx;
}

}
