#ifndef __AgentI_h__
#define __AgentI_h__

#include <xlog.h>
#include <IceUtil/Thread.h>
#include <IceUtil/Mutex.h>
#include <IceUtil/Monitor.h>

#include "src/common/common.h"

namespace xlog
{

class NormalSendWorker;
typedef IceUtil::Handle<NormalSendWorker> NormalSendWorkerPtr;

class FailedSendWorker;
typedef IceUtil::Handle<FailedSendWorker> FailedSendWorkerPtr;

class AgentI: virtual public Agent
{
public:
    AgentI(const AgentConfigManagerPtr& agentConfigCM, const ClientConfigManagerPtr& clientConfigCM,
            const DispatcherConfigManagerPtr& dispatcherConfigCM,
            const DispatcherAdapterPtr& dispatcher);

    /**
     * 收集client的log信息
     * @param data log信息
     */
    virtual void add(const LogDataSeq& data, const ::Ice::Current& current);

    /**
     * 收集client曾经发送失败的log信息
     * @param data log信息
     */
    virtual void addFailedLogData(const LogDataSeq& data, const ::Ice::Current& current);

    /**
     * 获取所有agent的字符串信息，并将client的信息注册到zookeeper上
     */
    virtual ::Ice::StringSeq subscribeClient(const std::string& prxStr,
            const ::Ice::Current& current);

    /**
     * 获取所有agent的字符串信息，并将subscriber的信息注册到zookeeper上
     */
    virtual ::Ice::StringSeq subscribeSubscriber(const ::Ice::StringSeq& categories,
            const std::string& prxStr, const ::Ice::Current& current);

private:
    NormalSendWorkerPtr _normalSendWorker; /**发送正常数据的worker*/
    FailedSendWorkerPtr _failedSendWorker; /**发送失败数据的worker*/
    AgentConfigManagerPtr _agentConfigCM;
    ClientConfigManagerPtr _clientConfigCM;
    DispatcherConfigManagerPtr _dispatcherConfigCM;
    DispatcherAdapterPtr _dispatcherAdapter;
};

typedef IceUtil::Handle<AgentI> AgentIPtr;

class SendWorker: public IceUtil::Thread
{
public:
    void add(const LogDataSeq& data);

protected:
    virtual void run();

    /**
     * 发送数据的接口
     * 不同的worker会调用dispatcher的不同接口
     */
    virtual bool send(const LogDataSeq& data) = 0;

private:
    LogDataSeq _data;
    ::IceUtil::Monitor<IceUtil::Mutex> _dataMutex;
};

class NormalSendWorker: public SendWorker
{
protected:
    virtual bool send(const LogDataSeq& data);
};

class FailedSendWorker: public SendWorker
{
protected:
    virtual bool send(const LogDataSeq& data);
};

}

#endif
