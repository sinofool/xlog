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

class AgentAdapter: public Subscriber, public ::IceUtil::Thread
{
public:
    bool init(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents);
    void send(const LogDataSeq& data);
    virtual void notify(const ::Ice::StringSeq& agentConfig, const ::Ice::Current& current);

protected:
    virtual void run();
    virtual void setPrxs(const ::Ice::StringSeq& config) = 0;

    std::vector<AgentPrx> getPrxs(const int size);
    ::Ice::StringSeq subscribe(const AgentPrx& prx = NULL);

    std::string _prxStr;
    std::vector<AgentPrx> _agents;
    ::IceUtil::RWRecMutex _rwMutex;
    ::IceUtil::Monitor<IceUtil::Mutex> _monitor;
    ::Ice::CommunicatorPtr _ic;
};

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

}
#endif
