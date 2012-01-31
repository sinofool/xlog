#include "src/config/agent_config_manager.h"
#include "src/config/client_config_manager.h"
#include "src/config/dispatcher_config.h"
#include "src/adapter/dispatcher_adapter.h"
#include "src/agent/AgentI.h"

namespace xlog
{

AgentI::AgentI()
{
}

void AgentI::init(const Ice::CommunicatorPtr& ic, const ZKConnectionPtr& conn)
{
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
    _config_dispatcher = DispatcherConfigPtr(new DispatcherConfig(conn));
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
    _config_dispatcher->init();
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
    _adapter_dispatcher = new DispatcherAdapter(ic, _config_dispatcher);
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
    _adapter_dispatcher->init();
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
    _normalSendWorker = new NormalSendWorker(_adapter_dispatcher);
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
    _normalSendWorker->start().detach();
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
    _failedSendWorker = new FailedSendWorker;
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
    _failedSendWorker->start().detach();
    std::cout << "AgentI::init step " << __LINE__ << std::endl;
}

void AgentI::add(const LogDataSeq& data, const ::Ice::Current& current)
{
    std::cout << "AgentI::add " << data.size() << std::endl;
    _normalSendWorker->add(data);
}

void AgentI::addFailedLogData(const LogDataSeq& data, const ::Ice::Current& current)
{
    _failedSendWorker->add(data);
}

::Ice::StringSeq AgentI::subscribeClient(const std::string& prxStr, const ::Ice::Current& current)
{
//    if (_clientConfigCM)
//    {
//        _clientConfigCM->subscribe(prxStr);
//    }
//
//    if (_agentConfigCM)
//    {
//        return _agentConfigCM->getConfig();
//    }

    return ::Ice::StringSeq();
}

::Ice::StringSeq AgentI::subscribeSubscriber(const ::Ice::StringSeq& categories,
        const std::string& prxStr, const ::Ice::Current& current)
{
//    if (_dispatcherConfigCM)
//    {
//        _dispatcherConfigCM->subscribe(categories, prxStr);
//    }

//    if (_agentConfigCM)
//    {
//        return _agentConfigCM->getConfig();
//    }

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
    _adapter_dispatcher->sendNormal(data);
    return true;
}

bool FailedSendWorker::send(const LogDataSeq& data)
{
    //TODO
    return true;
}

}
