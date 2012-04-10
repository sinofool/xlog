#include "src/config/dispatcher_config.h"
#include "src/adapter/dispatcher_adapter.h"
#include "build/generated/xlog.h"

namespace xlog
{

void DispatcherAdapter::init()
{
    nca = new LRUChooseAlgorithm;
    rebuild_prx();
}

bool DispatcherAdapter::sendNormal(const slice::LogDataSeq& data)
{
    long version = _config_dispatcher->version();
    if (version != _prx_version)
    {
        rebuild_prx();
    }
    IceUtil::RWRecMutex::RLock lock(_prx_lock);
    int size=data.size();
    for(int i=0;i<size;i++)
    { 
        slice::LogData ld=data.at(i);
        slice::DispatcherPrx dp=nca->doChoose(ld.categories,_prx);
        dp->addLogData(ld);
       
    }
    return true;
}

bool DispatcherAdapter::sendFailed(const slice::LogDataSeq& data)
{
    //TODO
    return true;
}

void DispatcherAdapter::rebuild_prx()
{
    long version = _config_dispatcher->version();
    std::map<int,std::vector<std::string> > new_address = _config_dispatcher->get();
    IceUtil::RWRecMutex::WLock lock(_prx_lock);
    std::map<int,std::vector<std::string> >::iterator iter;
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
           dn.call_count=0;
           dns.push_back(dn);
        }
        _prx[iter->first]=dns;
    }
    
    _prx_version = version;
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

}
