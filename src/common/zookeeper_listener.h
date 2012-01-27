/*
 * 获取ZkManager通知的类
 */
#ifndef __ZOOKEEPER_LISTENER_H__
#define __ZOOKEEPER_LISTENER_H__

#include <Ice/Ice.h>

#include "src/common/common.h"

namespace xlog
{

class ZooKeeperListener: public Ice::Object
{
public:
    virtual bool handle() = 0;
protected:
    virtual ~ZooKeeperListener()
    {
    }
};

}

#endif
