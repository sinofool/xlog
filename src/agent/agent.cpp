#include <Ice/Ice.h>
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
    virtual int run(int, char*[])
    {
        shutdownOnInterrupt();

        ZKConnectionPtr conn = ZKConnectionPtr(new ZKConnection);
        if (!conn->init("127.0.0.1:2222/xlog"))
        {
            cerr << appName() << ": can not init zk, exit" << endl;
            return 0;
        }
        std::cout << "ZooKeeper inited. Now initializing ICE. " << std::endl;
        Ice::ObjectAdapterPtr adapter = communicator()->createObjectAdapterWithEndpoints(appName(),
                "default -h 127.0.0.1 -p 10000");
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
