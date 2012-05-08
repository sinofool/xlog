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

class Client : public ::IceUtil::Thread
{
public:

    Client(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,
            const bool is_udp_protocol, const int maxQueueSize = 10000);
    bool append(const slice::LogDataSeq& data);

protected:
    //void init(bool is_udp_protocol);  
    void run();

    slice::LogDataSeq _data;

    ::IceUtil::Monitor<IceUtil::Mutex> _dataMutex;

    std::string _prxStr;

    ::Ice::StringSeq _defaultAgents;

    int _maxQueueSize;
     
    bool _is_udp_protocol;

    ::IceUtil::Mutex _agentMutex;

    AgentAdapter *_agentAdapter;
};

}

#endif
