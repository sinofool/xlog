#ifndef __AgentI_h__
#define __AgentI_h__

#include <xlog.h>

namespace xlog
{

class AgentI : virtual public Agent
{
public:

    virtual ::Ice::Int ensureLog(const ::Ice::StringSeq&,
                                 const Ice::Current&);

    virtual void dobestLog(const ::Ice::StringSeq&,
                           const Ice::Current&);
};

}

#endif
