#include <src/common/AgentConfigManager.h>
#include <src/agent/DispatcherAdapter.h>
#include <src/agent/AgentI.h>

namespace xlog {

AgentI::AgentI(const AgentConfigManagerPtr& agentConfigCM, const DispatcherAdapterPtr& dispatcher) : Agent(), 
                agentConfigCM_(agentConfigCM), dispatcher_(dispatcher)
{
	normalSendWorker_ = new NormalSendWorker;
	normalSendWorker_->start().detach();
	failedSendWorker_ = new FailedSendWorker;
	failedSendWorker_->start().detach();
}

void AgentI::add(const LogDataSeq& data, const ::Ice::Current& current)
{
	normalSendWorker_->add(data);
}
    
void AgentI::addFailedLogData(const LogDataSeq& data, const ::Ice::Current& current)
{
	failedSendWorker_->add(data);
}

::Ice::StringSeq AgentI::getAgents(const ::Ice::Current& current)
{
  if(agentConfigCM_)
  {
      return agentConfigCM_->getConfig();
  }

  return ::Ice::StringSeq();
}

void SendWorker::add(const LogDataSeq& data)
{
	::IceUtil::Monitor< ::IceUtil::Mutex>::Lock lock(dataMutex_);
	data_.insert(data_.end(), data.begin(), data.end());
	dataMutex_.notify();
}

void SendWorker::run()
{
	while(true)
	{
		LogDataSeq data;
		{
			::IceUtil::Monitor< ::IceUtil::Mutex>::Lock lock(dataMutex_);
			if(data_.empty())
			{
				dataMutex_.wait();
			}
			data.swap(data_);
		}

    send(data);
	}
}

bool NormalSendWorker::send(const LogDataSeq& data)
{
  //TODO
  //dispatcher_->send(data);
	return true;
}

bool FailedSendWorker::send(const LogDataSeq& data)
{
  //TODO
  //dispatcher_->sendFailedLogDatas(data);
	return true;
}

}
