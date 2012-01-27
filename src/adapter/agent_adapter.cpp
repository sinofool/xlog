
#include <boost/lexical_cast.hpp>

#include "src/common/util.hpp"
#include "src/adapter/agent_adapter.h"

namespace xlog
{

bool AgentAdapter::init(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents)
{
    if(defaultAgents.empty())
    {
        std::cerr << "AgentAdapter::init defaultAgent is empty!" << std::endl;
        return false;
    }

    if(prxStr.length() == 0)
    {
        std::cerr << "AgentAdapter::init prxStr is empty!" << std::endl;
        return false;
    }
    
    srand(unsigned(time( NULL)));

    _prxStr = prxStr;

    _ic = ::Ice::initialize();
    
    srand((unsigned)time(NULL));
   
    ::Ice::ObjectAdapterPtr adapter = _ic->createObjectAdapter(_prxStr + boost::lexical_cast<std::string>(rand()));
    adapter->activate();
    adapter->addWithUUID(this);

    for(::Ice::StringSeq::const_iterator it = defaultAgents.begin(); it != defaultAgents.end(); ++it)
    {
        AgentPrx prx = Util::getPrx<AgentPrx>(_ic, *it, false, 300);

        ::Ice::StringSeq agents = subscribe(prx);

        if(!agents.empty())
        {
            setPrxs(agents);
            start().detach();
            return true;
        }
    }
    
    std::cerr << "AgentAdapter::init agents is empty!" << std::endl;

    return false;
}

void AgentAdapter::send(const LogDataSeq& data)
{
    std::vector<AgentPrx> prxs = getPrxs(2);

    AgentPrx normalPrx;
    AgentPrx backupPrx;
    
    if(prxs.size() == 0)
    {
        return;
    }else if(prxs.size() == 1)
    {
        normalPrx = prxs[0];
        backupPrx = normalPrx;
    }else
    {
        normalPrx = prxs[0];
        backupPrx = prxs[1];
    }
   
    try
    {
        normalPrx->add(data);
        return;
    }catch(::Ice::Exception& e)
    {
        std::cerr << "AgentAdapter::send send failed for first time, will send again!" << std::endl;
        try
        {
            backupPrx->addFailedLogData(data);
        }catch(::Ice::Exception& e1)
        {
            std::cerr << "AgentAdapter::send send failed for second time, will not send again!" << std::endl;
        }

        ::IceUtil::Monitor< ::IceUtil::Mutex>::Lock lock(_monitor);

        _monitor.notify();
    }
}

void AgentAdapter::notify(const ::Ice::StringSeq& agentConfig, const ::Ice::Current& current)
{
    setPrxs(agentConfig);
}

void AgentAdapter::run()
{
    for(;;)
    {
        ::Ice::StringSeq agents = subscribe();
        if(!agents.empty())
        {
            setPrxs(agents);
        }

        ::IceUtil::Monitor< ::IceUtil::Mutex>::Lock lock(_monitor);
        _monitor.timedWait(::IceUtil::Time::seconds(10));
    }
}

std::vector<AgentPrx> AgentAdapter::getPrxs(const int size)
{
    std::vector<AgentPrx> res;
    ::IceUtil::RWRecMutex::RLock lock(_rwMutex);
    if(_agents.empty())
    {
        return res;
    }

    int randNum = rand();

    int agentsNum = _agents.size();

    for(int i = 0; i< size; ++i)
    {
        res.push_back(_agents[(randNum +i) % agentsNum]);
    }

    return res;
}

::Ice::StringSeq AgentAdapter::subscribe(const AgentPrx& prx)
{
    AgentPrx subPrx;

    if(prx != 0)
    {
        subPrx = prx->ice_datagram();
    }else
    {
        std::vector<AgentPrx> prxs = getPrxs(1);

        if(prxs.empty())
        {
            std::cerr << "AgentAdapter::subscribe no prx found!" << std::endl;
            return ::Ice::StringSeq();
        }

        subPrx = prxs[0]->ice_datagram();
    }

    try
    {
        return subPrx->subscribeClient(_prxStr);
    }catch(::Ice::Exception& e)
    {
        std::cerr << "AgentAdapter::subscribe ::Ice::Exception " << e.what() << std::endl;
        return ::Ice::StringSeq();
    }
}

}
