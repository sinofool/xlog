#include <Ice/Ice.h>
#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>

#include "src/agent/AgentI.h"
#include "src/common/zk_manager.h"
#include "src/config/agent_config_manager.h"
#include "src/config/client_config_manager.h"
#include "src/config/dispatcher_config.h"
#include "src/adapter/client_adapter.h"
#include "src/adapter/dispatcher_adapter.h"

using namespace std;
using namespace xlog;
class AgentApp: virtual public Ice::Application
{
public:
    virtual int run(int argc , char* argv[])
    {
        if(argc==1)
        {
            cout << "Usage:zk_host:zk_port/rootpath agent_host:agent_port"<<endl;
            return 0;
        }
        *argv++;

        shutdownOnInterrupt();

        ZKConnectionPtr conn = ZKConnectionPtr(new ZKConnection);
        if (!conn->init(*argv++))
        {
            cerr << appName() << ": can not init zk, exit" << endl;
            return 0;
        }
        std::cout << "ZooKeeper inited. Now initializing ICE. " << std::endl;

        std::vector < std::string > parts;
        boost::algorithm::split(parts, *argv, boost::algorithm::is_any_of(":"));
        if (parts.size() != 2)
        {
            std::cerr << "agent host:port is " << *argv 
                    << ",does not match the format : <host>:<port>!" << std::endl;
            return NULL;
        }
        std::string host = parts[0];
        std::string port = parts[1];
        std::ostringstream os;
        os << "default -h " << host << " -p " << port;

        Ice::ObjectAdapterPtr adapter = communicator()->createObjectAdapterWithEndpoints(appName(),
                os.str());
        std::cout << "new AgentI. ";
        AgentIPtr agent = new AgentI;
        agent->init(communicator(), conn);
        std::cout << "done." << endl;
        Ice::ObjectPrx prx = adapter->add(agent, communicator()->stringToIdentity("A"));

        std::cout << "Activating ";
        adapter->activate();
        std::cout << "done." << endl;

        communicator()->waitForShutdown();
        if (interrupted())
        {
            cerr << appName() << ": received signal, shutting down" << endl;
        }

        return 0;
    }
    ;
};

int main(int argc, char* argv[])
{
    AgentApp app;
    return app.main(argc, argv);
}
