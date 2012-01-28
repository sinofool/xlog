#include <Ice/Ice.h>
#include "src/agent/AgentI.h"
#include "src/common/zk_manager.h"
#include "src/config/agent_config_manager.h"
#include "src/config/client_config_manager.h"
#include "src/config/dispatcher_config_manager.h"
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

        //TODO
        ZkManagerPtr zm = new ZkManager;
        //设置全局的zkmanager，要在调用init之前，因为ZkWatcher会获取全局的zkmanager
        setZkManager(zm);
        if (!zm->init("127.0.0.1:2222/xlog"))
        {
            cerr << appName() << ": can not init zk, exit" << endl;
            return 0;
        }

        ClientConfigManagerPtr clientCM = new ClientConfigManager(zm);
        if (!clientCM->init())
        {
            cerr << appName() << ": can not init client config, exit" << endl;
        }

        ClientAdapterPtr clientAdapter = new ClientAdapter(clientCM);

        AgentConfigManagerPtr agentCM = new AgentConfigManager("", zm, clientAdapter);
        if (!agentCM->init())
        {
            cerr << appName() << ": can not init agent config, exit" << endl;
        }

        DispatcherConfigManagerPtr dispatcherCM = new DispatcherConfigManager(zm);
        if (!dispatcherCM->init())
        {
            cerr << appName() << ": can not init dispatcher config, exit" << endl;
        }

        //初始化完成之后，添加到zkmanager的监听队列中
        zm->addListener(agentCM);
        zm->addListener(dispatcherCM);

        DispatcherAdapterPtr dispatcher = new DispatcherAdapter(dispatcherCM);

        Ice::ObjectAdapterPtr adapter = communicator()->createObjectAdapterWithEndpoints(
                "XlogAgent", "default -p 10000");
        AgentIPtr agent = new AgentI(agentCM, clientCM, dispatcherCM, dispatcher);
        Ice::ObjectPrx prx = adapter->add(agent, communicator()->stringToIdentity("A"));
        
        adapter->activate();

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
