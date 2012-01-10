#include <src/agent/AgentI.h>

namespace xlog {

AgentI::AgentI() 
{
	normalSendWorker_ = new NormalSendWorker;
	normalSendWorker_->start().detach();
	failedSendWorker_ = new FailedSendWorker;
	failedSendWorker_->start().detach();
}

void AgentI::add(const LogDataSeq& datas, const ::Ice::Current& current)
{
	normalSendWorker_->add(datas);
}
    
void AgentI::addFailedLogDatas(const LogDataSeq& datas, const ::Ice::Current& current)
{
	failedSendWorker_->add(datas);
}

::Ice::StringSeq AgentI::getAgents(const ::Ice::Current& current)
{
  if(agentConfigCM_)
  {
      return agentConfigCM_->getConfig();
  }

  return ::Ice::StringSeq();
}

void AgentI::setAgentConfigManager(const AgentConfigManagerPtr& agentConfigCM)
{
    agentConfigCM_ = agentConfigCM;
}

void AgentI::setDispatcherAdapter(const DispatcherAdapterPtr& dispatcher)
{
    dispatcher_ = dispatcher;
}

void SendWorker::add(const LogDataSeq& datas)
{
	::IceUtil::Monitor< ::IceUtil::Mutex>::Lock lock(datasMutex_);
	datas_.insert(datas_.end(), datas.begin(), datas.end());
	datasMutex_.notify();
}

void SendWorker::run()
{
	while(true)
	{
		LogDataSeq datas;
		{
			::IceUtil::Monitor< ::IceUtil::Mutex>::Lock lock(datasMutex_);
			if(datas_.empty())
			{
				datasMutex_.wait();
			}
			datas.swap(datas_);
		}

    send(datas);
	}
}

bool NormalSendWorker::send(const LogDataSeq& datas)
{
  //TODO
  //dispatcher_->send(datas);
	return true;
}

bool FailedSendWorker::send(const LogDataSeq& datas)
{
  //TODO
  //dispatcher_->sendFailedLogDatas(datas);
	return true;
}

}
