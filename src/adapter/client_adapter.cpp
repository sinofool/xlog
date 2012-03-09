#include <boost/algorithm/string/split.hpp>

#include "src/common/util.h"
#include "src/config/client_config_manager.h"

#include "src/adapter/client_adapter.h"

namespace xlog
{

ClientAdapter::ClientAdapter(const ClientConfigPtr& clientCM) :
        _clientCM(clientCM)
{
    _ic = Ice::initialize();
}

void ClientAdapter::notify(const std::vector<std::string>& agentConfig)
{
    std::vector < std::string > prxVec = _clientCM->getConfig();

    for (std::vector<std::string>::const_iterator it = prxVec.begin(); it != prxVec.end(); ++it)
    {
        slice::SubscriberPrx prx = Util::getPrx<slice::SubscriberPrx>(_ic, *it);

        try
        {
            prx->notify(agentConfig);
            continue;
        } catch (Ice::Exception& e)
        {
            std::cerr << "ClientAdapter::notify Ice::Exception of prx " << prx->ice_toString()
                    << std::endl;
        } catch (...)
        {
            std::cerr << "ClientAdapter::notify UnknownException of prx " << prx->ice_toString()
                    << std::endl;
        }

        _clientCM->remove(*it);
    }
}

}
