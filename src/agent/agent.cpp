#include <Ice/Ice.h>
#include <src/agent/AgentI.h>
#include <src/common/ZkManager.h>
#include <src/common/AgentConfigManager.h>
#include <src/common/DispatcherConfigManager.h>

using namespace std;
using namespace xlog;

class AgentApp: virtual public Ice::Application {
public:
	virtual int run(int, char*[]) {
		shutdownOnInterrupt();

    //TODO
    ZkManagerPtr zm = new ZkManager;
    if(!zm->init(""))
    {
			cerr << appName() << ": can not init zk, exit" << endl;
      return 0;
    }
    
    AgentConfigManagerPtr agentCM = new AgentConfigManager;
    agentCM->setZkManager(zm);
    if(!agentCM->init())
    {
			cerr << appName() << ": can not init agent config, exit" << endl;
    }
	
    Ice::ObjectAdapterPtr adapter =
				communicator()->createObjectAdapterWithEndpoints("XlogAgent",
						"default -p 10000");
		AgentIPtr agent = new AgentI;
		adapter->add(agent, communicator()->stringToIdentity("A"));
		adapter->activate();

    agent->setAgentConfigManager(agentCM);

		communicator()->waitForShutdown();
		if (interrupted()) {
			cerr << appName() << ": received signal, shutting down" << endl;
		}

		return 0;
	}
	;
};

int main(int argc, char* argv[]) {
	AgentApp app;
	return app.main(argc, argv);
}
