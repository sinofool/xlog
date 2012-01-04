#include <Ice/Ice.h>
#include "src/agent/AgentI.h"

using namespace std;
using namespace xlog;

class AgentApp: virtual public Ice::Application {
public:
	virtual int run(int, char*[]) {
		shutdownOnInterrupt();

		Ice::ObjectAdapterPtr adapter =
				communicator()->createObjectAdapterWithEndpoints("XlogAgent",
						"default -p 10000");
		AgentPtr agent = new AgentI;
		adapter->add(agent, communicator()->stringToIdentity("A"));
		adapter->activate();

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
