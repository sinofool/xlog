#ifndef __DISPATCHER_ADAPTER_H__
#define __DISPATCHER_ADAPTER_H__

#include <Ice/Ice.h>
#include <IceUtil/RWRecMutex.h>
#include <map>

#include <xlog.h>
#include "src/common/common.h"

namespace xlog
{
struct DispatcherNode
{
public :
    int call_count;
    slice::DispatcherPrx dispatcher_prx;
};

class NodeChooseAlgorithm
{
public :
    virtual slice::DispatcherPrx doChoose(const Ice::StringSeq& categories , std::map< int , std::vector<DispatcherNode> >& _prx ) = 0;
};

class DispatcherAdapter: public Ice::Object
{
public:
    DispatcherAdapter(const Ice::CommunicatorPtr& ic, const DispatcherConfigPtr& config) :
            _ic(ic), _config_dispatcher(config)
    {
    }
    void init();
    bool sendNormal(const slice::LogData& data);
    bool sendFailed(const slice::LogDataSeq& data);
    /* 
    struct DispatcherNode
    {
      public :
        int call_count;
	slice::DispatcherPrx *dispatcher_prx;
    };
    */
private:
    Ice::CommunicatorPtr _ic;
    DispatcherConfigPtr _config_dispatcher;

    IceUtil::RWRecMutex _prx_lock;
    VectorOfString _prx_address;
    //std::vector<slice::DispatcherPrx> _prx;
    std::map< int , std::vector<DispatcherNode> > _prx;
    NodeChooseAlgorithm *nca;
    long _prx_version;
    bool rebuild_prx();
};

class LRUChooseAlgorithm : public NodeChooseAlgorithm
{
public :
   slice::DispatcherPrx doChoose(const Ice::StringSeq& categories , std::map< int , std::vector<DispatcherNode> >& _prx );

private:
    DispatcherHashKey dispatcher_hash;
};

class RoundRobinAlgorithm : public NodeChooseAlgorithm
{
public :
   RoundRobinAlgorithm(){ current_dn_num = 0;};
   slice::DispatcherPrx doChoose(const Ice::StringSeq& categories , std::map< int , std::vector<DispatcherNode> >& _prx );

private:
    int current_dn_num;
    DispatcherHashKey dispatcher_hash;
};

}

#endif
