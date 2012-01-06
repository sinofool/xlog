
#include "src/agent/AgentI.h"

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
	::IceUtil::Mutex::Lock lock(agentsMutex_);
	return agents_;
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
		if(!send(datas))
		{
			add(datas);
		}
	}
}

bool NormalSendWorker::send(const LogDataSeq& datas)
{
	//TODO
	return true;
}

bool FailedSendWorker::send(const LogDataSeq& datas)
{
	//TODO
	return true;
}

}
