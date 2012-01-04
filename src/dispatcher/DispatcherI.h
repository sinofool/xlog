#ifndef __DispatcherI_h__
#define __DispatcherI_h__

#include <xlog.h>

namespace xlog
{

class DispatcherI : virtual public Dispatcher
{
public:

    virtual bool _cpp_register(const ::xlog::LoggerPrx&,
                               ::Ice::Int,
                               const Ice::Current&);
};

}

#endif
