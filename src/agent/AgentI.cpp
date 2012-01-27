#include "src/config/agent_config_manager.h"
#include "src/config/client_config_manager.h"
#include "src/config/dispatcher_config_manager.h"
#include "src/adapter/dispatcher_adapter.h"
#include "src/agent/AgentI.h"

namespace xlog
{

AgentI::AgentI(const AgentConfigManagerPtr& agentConfigCM,
        const ClientConfigManagerPtr& clientConfigCM,
        const DispatcherConfigManagerPtr& dispatcherConfigCM,
        const DispatcherAdapterPtr& dispatcherAdapter) :
        Agent(), _agentConfigCM(agentConfigCM), _clientConfigCM(clientConfigCM), _dispatcherConfigCM(
                dispatcherConfigCM), _dispatcherAdapter(dispatcherAdapter)
{
    _normalSendWorker = new NormalSendWorker;
    _normalSendWorker->start().detach();
    _failedSendWorker = new FailedSendWorker;
    _failedSendWorker->start().detach();
}

void AgentI::add(const LogDataSeq& data, const ::Ice::Current& current)
{
    _normalSendWorker->add(data);
}

void AgentI::addFailedLogData(const LogDataSeq& data, const ::Ice::Current& current)
{
    _failedSendWorker->add(data);
}

::Ice::StringSeq AgentI::subscribeClient(const std::string& prxStr, const ::Ice::Current& current)
{
    if (_clientConfigCM)
    {
        _clientConfigCM->subscribe(prxStr);
    }

    if (_agentConfigCM)
    {
        return _agentConfigCM->getConfig();
    }

    return ::Ice::StringSeq();
}

::Ice::StringSeq AgentI::subscribeSubscriber(const ::Ice::StringSeq& categories,
        const std::string& prxStr, const ::Ice::Current& current)
{
    if (_dispatcherConfigCM)
    {
        _dispatcherConfigCM->subscribe(categories, prxStr);
    }

    if (_agentConfigCM)
    {
        return _agentConfigCM->getConfig();
    }

    return ::Ice::StringSeq();
}

void SendWorker::add(const LogDataSeq& data)
{
    ::IceUtil::Monitor<IceUtil::Mutex>::Lock lock(_dataMutex);
    _data.insert(_data.end(), data.begin(), data.end());
    _dataMutex.notify();
}

void SendWorker::run()
{
    for (;;)
    {
        LogDataSeq data;
        {
            ::IceUtil::Monitor<IceUtil::Mutex>::Lock lock(_dataMutex);
            if (_data.empty())
            {
                _dataMutex.wait();
            }
            data.swap(_data);
        }

        send(data);
    }
}

bool NormalSendWorker::send(const LogDataSeq& data)
{
    //TODO
    return true;
}

bool FailedSendWorker::send(const LogDataSeq& data)
{
    //TODO
    return true;
}

}
