#include <boost/lexical_cast.hpp>

#include "src/common/util.h"
#include "src/adapter/agent_adapter.h"

namespace xlog
{

bool AgentAdapter::init(const std::string& prxStr, const ::Ice::StringSeq& defaultAgents,bool is_udp_protocol)
{   
    if (defaultAgents.empty())
    {
        std::cerr << "AgentAdapter::init defaultAgent is empty!" << std::endl;
        return false;
    }

    if (prxStr.length() == 0)
    {
        std::cerr << "AgentAdapter::init prxStr is empty!" << std::endl;
        return false;
    }

    srand(unsigned(time(NULL)));

    _ic = ::Ice::initialize();

    srand((unsigned) time(NULL));
    current_agent_prx_number=0;

    std::vector<slice::AgentPrx> _prxs;
    for (::Ice::StringSeq::const_iterator it = defaultAgents.begin(); it != defaultAgents.end();
            ++it)
    {
        slice::AgentPrx prx = Util::getPrx<slice::AgentPrx>(_ic, *it, is_udp_protocol, 300);
	_prxs.push_back(prx);
    }
    agent_prxs.swap(_prxs);

    return true;
}

slice::AgentPrx AgentAdapter::getAgentPrx()
{
    int size=agent_prxs.size();
    if(current_agent_prx_number==size)
    {
        current_agent_prx_number=0;
    }
    return agent_prxs.at(current_agent_prx_number++);
    
}

void AgentAdapter::send(const slice::LogDataSeq& data)
{
    int size=agent_prxs.size();
    for(int i=0;i<size;i++)
    {
       try
       {
           getAgentPrx()->add(data);
           return;
       } catch (::Ice::Exception& e)
       {
           std::cerr << "AgentAdapter::send failed for " << i  << " time, will send again!" << std::endl;
       }
    }
    /*
    std::vector<slice::AgentPrx> prxs = getPrxs(2);

    slice::AgentPrx normalPrx;
    slice::AgentPrx backupPrx;

    if (prxs.size() == 0)
    {
        return;
    }
    else if (prxs.size() == 1)
    {
        normalPrx = prxs[0];
        backupPrx = normalPrx;
    }
    else
    {
        normalPrx = prxs[0];
        backupPrx = prxs[1];
    }

    try
    {
        std::cout << "Adapter add:"<< data.size() << std::endl;
        normalPrx->add(data);
        return;
    } catch (::Ice::Exception& e)
    {
        std::cerr << "AgentAdapter::send send failed for first time, will send again!" << std::endl;
        try
        {
            backupPrx->addFailedLogData(data);
        } catch (::Ice::Exception& e1)
        {
            std::cerr << "AgentAdapter::send send failed for second time, will not send again!"
                    << std::endl;
        }

        ::IceUtil::Monitor<IceUtil::Mutex>::Lock lock(_monitor);

        _monitor.notify();
    }
    */
}
/*
void AgentAdapter::notify(const ::Ice::StringSeq& agentConfig, const ::Ice::Current& current)
{
    setPrxs(agentConfig);
}

void AgentAdapter::run()
{
    for (;;)
    {
        ::Ice::StringSeq agents = subscribe();
        if (!agents.empty())
        {
            setPrxs(agents);
        }

        ::IceUtil::Monitor<IceUtil::Mutex>::Lock lock(_monitor);
        _monitor.timedWait(IceUtil::Time::seconds(10));
    }
}

std::vector<slice::AgentPrx> AgentAdapter::getPrxs(const int size)
{
    std::vector<slice::AgentPrx> res;
    ::IceUtil::RWRecMutex::RLock lock(_rwMutex);
    if (_agents.empty())
    {
        return res;
    }

    int randNum = rand();

    int agentsNum = _agents.size();

    for (int i = 0; i < size; ++i)
    {
        res.push_back(_agents[(randNum + i) % agentsNum]);
    }

    return res;
}

::Ice::StringSeq AgentAdapter::subscribe(const slice::AgentPrx& prx)
{
    slice::AgentPrx subPrx;

    if (prx != 0)
    {
        subPrx = prx->ice_datagram();
    }
    else
    {
        std::vector<slice::AgentPrx> prxs = getPrxs(1);

        if (prxs.empty())
        {
            std::cerr << "AgentAdapter::subscribe no prx found!" << std::endl;
            return ::Ice::StringSeq();
        }

        subPrx = prxs[0]->ice_datagram();
    }

    try
    {
        return subPrx->subscribeClient(_prxStr);
    } catch (::Ice::Exception& e)
    {
        std::cerr << "AgentAdapter::subscribe ::Ice::Exception " << e.what() << std::endl;
    }
    return ::Ice::StringSeq();
}

void TcpAgentAdapter::setPrxs(const Ice::StringSeq& config)
{
    std::vector<slice::AgentPrx> res;
    for (Ice::StringSeq::const_iterator it = config.begin(); it != config.end(); ++it)
    {
        res.push_back(Util::getPrx<slice::AgentPrx>(_ic, *it, false, 300));
    }

    ::IceUtil::RWRecMutex::WLock lock(_rwMutex);

    _agents.swap(res);
}

void UdpAgentAdapter::setPrxs(const Ice::StringSeq& config)
{
    std::vector<slice::AgentPrx> res;
    for (Ice::StringSeq::const_iterator it = config.begin(); it != config.end(); ++it)
    {
        res.push_back(Util::getPrx<slice::AgentPrx>(_ic, *it));
    }

    IceUtil::RWRecMutex::WLock lock(_rwMutex);

    _agents.swap(res);
}
*/
}
