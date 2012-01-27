#ifndef __CLIENT_H__
#define __CLIENT_H__

#include <string>

#include <IceUtil/Monitor.h>
#include <IceUtil/Mutex.h>
#include <IceUtil/Thread.h>

#include "xlog.h"
#include "src/common/common.h"

namespace xlog
{

class Client: public ::IceUtil::Thread
{
public:

    Client(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
            const int maxQueueSize = 10000);

public:

    void append(const LogDataSeq& data);

protected:

    virtual void run();

protected:

    virtual AgentAdapterPtr getAgentAdapter() = 0;

protected:

    LogDataSeq _data;

    ::IceUtil::Monitor<IceUtil::Mutex> _dataMutex;

    std::string _prxStr;

    ::Ice::StringSeq _defaultAgents;

    int _maxQueueSize;

    ::IceUtil::Mutex _agentMutex;

    AgentAdapterPtr _agentAdapter;
};

}

#endif
