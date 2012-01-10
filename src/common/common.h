
/*
 * 系统内使用的类的前置声明
 */

#ifndef __COMMON_H__
#define __COMMON_H__

#include <IceUtil/Handle.h>

namespace xlog
{

class ZooKeeperListener;
typedef ::IceUtil::Handle<ZooKeeperListener> ZooKeeperListenerPtr;

class AgentConfigManager;
typedef ::IceUtil::Handle<AgentConfigManager> AgentConfigManagerPtr;

class DispatcherConfigManager;
typedef ::IceUtil::Handle<DispatcherConfigManager> DispatcherConfigManagerPtr;

class ZkManager;
typedef ::IceUtil::Handle<ZkManager> ZkManagerPtr;

class DispatcherAdapter;
typedef ::IceUtil::Handle<DispatcherAdapter> DispatcherAdapterPtr;

}
#endif
