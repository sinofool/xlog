#ifndef __AGENT_ADAPTER_H__
#define __AGENT_ADAPTER_H__

#include <string>
#include <vector>

#include <Ice/Ice.h>
#include <IceUtil/Thread.h>
#include <IceUtil/Monitor.h>
#include <IceUtil/RWRecMutex.h>

#include "xlog.h"

namespace xlog
{

//class AgentAdapter : public slice::Subscriber , public ::IceUtil::Thread
class AgentAdapter : public Ice::Object
{
public:
    bool init(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,bool is_udp_protocol);
    void send(const slice::LogDataSeq& data);
    //virtual void notify(const ::Ice::StringSeq& agentConfig, const ::Ice::Current& current);

private:
    int current_agent_prx_number;
    //virtual void run();
    //virtual void setPrxs(const ::Ice::StringSeq& config) = 0;

    //std::vector<slice::AgentPrx> getPrxs(const int size);
    std::vector<slice::AgentPrx> agent_prxs;
    slice::AgentPrx getAgentPrx();
    //::Ice::StringSeq subscribe(const slice::AgentPrx& prx = NULL);

    //std::string _prxStr;
    //std::vector<slice::AgentPrx> _agents;
    //::IceUtil::RWRecMutex _rwMutex;
    //::IceUtil::Monitor<IceUtil::Mutex> _monitor;
    ::Ice::CommunicatorPtr _ic;
};
/*
class TcpAgentAdapter: public AgentAdapter
{
protected:
    virtual void setPrxs(const ::Ice::StringSeq& config);
};

class UdpAgentAdapter: public AgentAdapter
{
protected:
    virtual void setPrxs(const ::Ice::StringSeq& config);
};
*/
}
#endif
