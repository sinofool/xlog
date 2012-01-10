#ifndef __DISPATCHER_CONFIGMANAGER_H__
#define __DISPATCHER_CONFIGMANAGER_H__

#include <src/common/ConfigManager.h>

namespace xlog
{

class DispatcherConfigManager : public ConfigManager
{
public:

    virtual bool init();

protected:

    virtual bool subscribe();

    virtual std::vector<std::string> update();

    virtual void setConfig(const std::vector<std::string>& config);
};

}

#endif
