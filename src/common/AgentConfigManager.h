#ifndef __AGENT_CONFIGMANAGER_H__
#define __AGENT_CONFIGMANAGER_H__

#include <string>

#include <src/common/ConfigManager.h>

namespace xlog
{

const std::string AGENTS_PATH = "/agents/";

class AgentConfigManager : public ConfigManager
{
public:

    virtual bool init();

protected:

    virtual bool subscribe();

    virtual std::vector<std::string> update();

};

}

#endif
