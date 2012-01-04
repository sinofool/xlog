#ifndef __LoggerI_h__
#define __LoggerI_h__

#include <xlog.h>

namespace xlog
{

class LoggerI : virtual public Logger
{
public:

    virtual ::Ice::Int log(const ::Ice::StringSeq&,
                           const Ice::Current&);
};

}

#endif
