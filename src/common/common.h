/*
 * 系统内使用的类的前置声明
 */

#ifndef __COMMON_H__
#define __COMMON_H__

#include <IceUtil/Handle.h>
#include <boost/shared_ptr.hpp>

namespace xlog
{

class ZooKeeperListener;
typedef IceUtil::Handle<ZooKeeperListener> ZooKeeperListenerPtr;

class AgentConfig;
typedef IceUtil::Handle<AgentConfig> AgentConfigPtr;

class DispatcherConfig;
typedef boost::shared_ptr<DispatcherConfig> DispatcherConfigPtr;

class ClientConfig;
typedef ::IceUtil::Handle<ClientConfig> ClientConfigPtr;

class ZkManager;
typedef IceUtil::Handle<ZkManager> ZkManagerPtr;

class DispatcherAdapter;
typedef IceUtil::Handle<DispatcherAdapter> DispatcherAdapterPtr;

class ClientAdapter;
typedef ::IceUtil::Handle<ClientAdapter> ClientAdapterPtr;

class AgentAdapter;
typedef ::IceUtil::Handle<AgentAdapter> AgentAdapterPtr;

typedef std::vector<std::string> VectorOfString;
typedef std::vector<char> VectorOfChar;
}
#endif
