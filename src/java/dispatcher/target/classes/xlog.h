// **********************************************************************
//
// Copyright (c) 2003-2009 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

// Ice version 3.3.1
// Generated from file `xlog.ice'

#ifndef _____build_generated_xlog_h__
#define _____build_generated_xlog_h__

#include <Ice/LocalObjectF.h>
#include <Ice/ProxyF.h>
#include <Ice/ObjectF.h>
#include <Ice/Exception.h>
#include <Ice/LocalObject.h>
#include <Ice/Proxy.h>
#include <Ice/Object.h>
#include <Ice/Outgoing.h>
#include <Ice/Incoming.h>
#include <Ice/Direct.h>
#include <Ice/StreamF.h>
#include <Ice/BuiltinSequences.h>
#include <Ice/UndefSysMacros.h>

#ifndef ICE_IGNORE_VERSION
#   if ICE_INT_VERSION / 100 != 303
#       error Ice version mismatch!
#   endif
#   if ICE_INT_VERSION % 100 > 50
#       error Beta header file detected
#   endif
#   if ICE_INT_VERSION % 100 < 1
#       error Ice patch level mismatch!
#   endif
#endif

namespace IceProxy
{

namespace xlog
{

namespace slice
{

class Subscriber;

class Agent;

class Logger;

class Dispatcher;

}

}

}

namespace xlog
{

namespace slice
{

class Subscriber;
bool operator==(const Subscriber&, const Subscriber&);
bool operator<(const Subscriber&, const Subscriber&);

class Agent;
bool operator==(const Agent&, const Agent&);
bool operator<(const Agent&, const Agent&);

class Logger;
bool operator==(const Logger&, const Logger&);
bool operator<(const Logger&, const Logger&);

class Dispatcher;
bool operator==(const Dispatcher&, const Dispatcher&);
bool operator<(const Dispatcher&, const Dispatcher&);

}

}

namespace IceInternal
{

::Ice::Object* upCast(::xlog::slice::Subscriber*);
::IceProxy::Ice::Object* upCast(::IceProxy::xlog::slice::Subscriber*);

::Ice::Object* upCast(::xlog::slice::Agent*);
::IceProxy::Ice::Object* upCast(::IceProxy::xlog::slice::Agent*);

::Ice::Object* upCast(::xlog::slice::Logger*);
::IceProxy::Ice::Object* upCast(::IceProxy::xlog::slice::Logger*);

::Ice::Object* upCast(::xlog::slice::Dispatcher*);
::IceProxy::Ice::Object* upCast(::IceProxy::xlog::slice::Dispatcher*);

}

namespace xlog
{

namespace slice
{

typedef ::IceInternal::Handle< ::xlog::slice::Subscriber> SubscriberPtr;
typedef ::IceInternal::ProxyHandle< ::IceProxy::xlog::slice::Subscriber> SubscriberPrx;

void __read(::IceInternal::BasicStream*, SubscriberPrx&);
void __patch__SubscriberPtr(void*, ::Ice::ObjectPtr&);

typedef ::IceInternal::Handle< ::xlog::slice::Agent> AgentPtr;
typedef ::IceInternal::ProxyHandle< ::IceProxy::xlog::slice::Agent> AgentPrx;

void __read(::IceInternal::BasicStream*, AgentPrx&);
void __patch__AgentPtr(void*, ::Ice::ObjectPtr&);

typedef ::IceInternal::Handle< ::xlog::slice::Logger> LoggerPtr;
typedef ::IceInternal::ProxyHandle< ::IceProxy::xlog::slice::Logger> LoggerPrx;

void __read(::IceInternal::BasicStream*, LoggerPrx&);
void __patch__LoggerPtr(void*, ::Ice::ObjectPtr&);

typedef ::IceInternal::Handle< ::xlog::slice::Dispatcher> DispatcherPtr;
typedef ::IceInternal::ProxyHandle< ::IceProxy::xlog::slice::Dispatcher> DispatcherPrx;

void __read(::IceInternal::BasicStream*, DispatcherPrx&);
void __patch__DispatcherPtr(void*, ::Ice::ObjectPtr&);

}

}

namespace xlog
{

namespace slice
{

struct LogData
{
    ::Ice::StringSeq categories;
    ::Ice::StringSeq logs;
    ::std::string checkSum;

    bool operator==(const LogData&) const;
    bool operator<(const LogData&) const;
    bool operator!=(const LogData& __rhs) const
    {
        return !operator==(__rhs);
    }
    bool operator<=(const LogData& __rhs) const
    {
        return operator<(__rhs) || operator==(__rhs);
    }
    bool operator>(const LogData& __rhs) const
    {
        return !operator<(__rhs) && !operator==(__rhs);
    }
    bool operator>=(const LogData& __rhs) const
    {
        return !operator<(__rhs);
    }

    void __write(::IceInternal::BasicStream*) const;
    void __read(::IceInternal::BasicStream*);
};

typedef ::std::vector< ::xlog::slice::LogData> LogDataSeq;
void __writeLogDataSeq(::IceInternal::BasicStream*, const ::xlog::slice::LogData*, const ::xlog::slice::LogData*);
void __readLogDataSeq(::IceInternal::BasicStream*, LogDataSeq&);

}

}

namespace IceProxy
{

namespace xlog
{

namespace slice
{

class Subscriber : virtual public ::IceProxy::Ice::Object
{
public:

    void notify(const ::Ice::StringSeq& config)
    {
        notify(config, 0);
    }
    void notify(const ::Ice::StringSeq& config, const ::Ice::Context& __ctx)
    {
        notify(config, &__ctx);
    }
    
private:

    void notify(const ::Ice::StringSeq&, const ::Ice::Context*);
    
public:
    
    ::IceInternal::ProxyHandle<Subscriber> ice_context(const ::Ice::Context& __context) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_context(__context).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_context(__context).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_adapterId(const std::string& __id) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_adapterId(__id).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_adapterId(__id).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_endpoints(const ::Ice::EndpointSeq& __endpoints) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_endpoints(__endpoints).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_endpoints(__endpoints).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_locatorCacheTimeout(int __timeout) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_locatorCacheTimeout(__timeout).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_locatorCacheTimeout(__timeout).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_connectionCached(bool __cached) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_connectionCached(__cached).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_connectionCached(__cached).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_endpointSelection(::Ice::EndpointSelectionType __est) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_endpointSelection(__est).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_endpointSelection(__est).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_secure(bool __secure) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_secure(__secure).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_secure(__secure).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_preferSecure(bool __preferSecure) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_preferSecure(__preferSecure).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_preferSecure(__preferSecure).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_router(const ::Ice::RouterPrx& __router) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_router(__router).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_router(__router).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_locator(const ::Ice::LocatorPrx& __locator) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_locator(__locator).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_locator(__locator).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_collocationOptimized(bool __co) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_collocationOptimized(__co).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_collocationOptimized(__co).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_twoway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_twoway().get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_twoway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_oneway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_oneway().get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_oneway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_batchOneway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_batchOneway().get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_batchOneway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_datagram() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_datagram().get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_datagram().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_batchDatagram() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_batchDatagram().get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_batchDatagram().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_compress(bool __compress) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_compress(__compress).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_compress(__compress).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_timeout(int __timeout) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_timeout(__timeout).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_timeout(__timeout).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Subscriber> ice_connectionId(const std::string& __id) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Subscriber*>(_Base::ice_connectionId(__id).get());
    #else
        return dynamic_cast<Subscriber*>(::IceProxy::Ice::Object::ice_connectionId(__id).get());
    #endif
    }
    
    static const ::std::string& ice_staticId();

private: 

    virtual ::IceInternal::Handle< ::IceDelegateM::Ice::Object> __createDelegateM();
    virtual ::IceInternal::Handle< ::IceDelegateD::Ice::Object> __createDelegateD();
    virtual ::IceProxy::Ice::Object* __newInstance() const;
};

class Agent : virtual public ::IceProxy::Ice::Object
{
public:

    void add(const ::xlog::slice::LogDataSeq& data)
    {
        add(data, 0);
    }
    void add(const ::xlog::slice::LogDataSeq& data, const ::Ice::Context& __ctx)
    {
        add(data, &__ctx);
    }
    
private:

    void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);
    
public:

    void addFailedLogData(const ::xlog::slice::LogDataSeq& data)
    {
        addFailedLogData(data, 0);
    }
    void addFailedLogData(const ::xlog::slice::LogDataSeq& data, const ::Ice::Context& __ctx)
    {
        addFailedLogData(data, &__ctx);
    }
    
private:

    void addFailedLogData(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);
    
public:

    ::Ice::StringSeq subscribeClient(const ::std::string& prxStr)
    {
        return subscribeClient(prxStr, 0);
    }
    ::Ice::StringSeq subscribeClient(const ::std::string& prxStr, const ::Ice::Context& __ctx)
    {
        return subscribeClient(prxStr, &__ctx);
    }
    
private:

    ::Ice::StringSeq subscribeClient(const ::std::string&, const ::Ice::Context*);
    
public:

    ::Ice::StringSeq subscribeSubscriber(const ::Ice::StringSeq& categories, const ::std::string& prxStr)
    {
        return subscribeSubscriber(categories, prxStr, 0);
    }
    ::Ice::StringSeq subscribeSubscriber(const ::Ice::StringSeq& categories, const ::std::string& prxStr, const ::Ice::Context& __ctx)
    {
        return subscribeSubscriber(categories, prxStr, &__ctx);
    }
    
private:

    ::Ice::StringSeq subscribeSubscriber(const ::Ice::StringSeq&, const ::std::string&, const ::Ice::Context*);
    
public:
    
    ::IceInternal::ProxyHandle<Agent> ice_context(const ::Ice::Context& __context) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_context(__context).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_context(__context).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_adapterId(const std::string& __id) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_adapterId(__id).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_adapterId(__id).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_endpoints(const ::Ice::EndpointSeq& __endpoints) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_endpoints(__endpoints).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_endpoints(__endpoints).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_locatorCacheTimeout(int __timeout) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_locatorCacheTimeout(__timeout).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_locatorCacheTimeout(__timeout).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_connectionCached(bool __cached) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_connectionCached(__cached).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_connectionCached(__cached).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_endpointSelection(::Ice::EndpointSelectionType __est) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_endpointSelection(__est).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_endpointSelection(__est).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_secure(bool __secure) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_secure(__secure).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_secure(__secure).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_preferSecure(bool __preferSecure) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_preferSecure(__preferSecure).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_preferSecure(__preferSecure).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_router(const ::Ice::RouterPrx& __router) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_router(__router).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_router(__router).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_locator(const ::Ice::LocatorPrx& __locator) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_locator(__locator).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_locator(__locator).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_collocationOptimized(bool __co) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_collocationOptimized(__co).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_collocationOptimized(__co).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_twoway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_twoway().get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_twoway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_oneway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_oneway().get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_oneway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_batchOneway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_batchOneway().get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_batchOneway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_datagram() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_datagram().get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_datagram().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_batchDatagram() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_batchDatagram().get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_batchDatagram().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_compress(bool __compress) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_compress(__compress).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_compress(__compress).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_timeout(int __timeout) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_timeout(__timeout).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_timeout(__timeout).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Agent> ice_connectionId(const std::string& __id) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Agent*>(_Base::ice_connectionId(__id).get());
    #else
        return dynamic_cast<Agent*>(::IceProxy::Ice::Object::ice_connectionId(__id).get());
    #endif
    }
    
    static const ::std::string& ice_staticId();

private: 

    virtual ::IceInternal::Handle< ::IceDelegateM::Ice::Object> __createDelegateM();
    virtual ::IceInternal::Handle< ::IceDelegateD::Ice::Object> __createDelegateD();
    virtual ::IceProxy::Ice::Object* __newInstance() const;
};

class Logger : virtual public ::IceProxy::Ice::Object
{
public:

    void add(const ::xlog::slice::LogDataSeq& data)
    {
        add(data, 0);
    }
    void add(const ::xlog::slice::LogDataSeq& data, const ::Ice::Context& __ctx)
    {
        add(data, &__ctx);
    }
    
private:

    void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);
    
public:

    void addLogData(const ::xlog::slice::LogData& data)
    {
        addLogData(data, 0);
    }
    void addLogData(const ::xlog::slice::LogData& data, const ::Ice::Context& __ctx)
    {
        addLogData(data, &__ctx);
    }
    
private:

    void addLogData(const ::xlog::slice::LogData&, const ::Ice::Context*);
    
public:
    
    ::IceInternal::ProxyHandle<Logger> ice_context(const ::Ice::Context& __context) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_context(__context).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_context(__context).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_adapterId(const std::string& __id) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_adapterId(__id).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_adapterId(__id).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_endpoints(const ::Ice::EndpointSeq& __endpoints) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_endpoints(__endpoints).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_endpoints(__endpoints).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_locatorCacheTimeout(int __timeout) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_locatorCacheTimeout(__timeout).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_locatorCacheTimeout(__timeout).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_connectionCached(bool __cached) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_connectionCached(__cached).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_connectionCached(__cached).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_endpointSelection(::Ice::EndpointSelectionType __est) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_endpointSelection(__est).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_endpointSelection(__est).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_secure(bool __secure) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_secure(__secure).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_secure(__secure).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_preferSecure(bool __preferSecure) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_preferSecure(__preferSecure).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_preferSecure(__preferSecure).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_router(const ::Ice::RouterPrx& __router) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_router(__router).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_router(__router).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_locator(const ::Ice::LocatorPrx& __locator) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_locator(__locator).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_locator(__locator).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_collocationOptimized(bool __co) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_collocationOptimized(__co).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_collocationOptimized(__co).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_twoway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_twoway().get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_twoway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_oneway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_oneway().get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_oneway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_batchOneway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_batchOneway().get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_batchOneway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_datagram() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_datagram().get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_datagram().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_batchDatagram() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_batchDatagram().get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_batchDatagram().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_compress(bool __compress) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_compress(__compress).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_compress(__compress).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_timeout(int __timeout) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_timeout(__timeout).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_timeout(__timeout).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Logger> ice_connectionId(const std::string& __id) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Logger*>(_Base::ice_connectionId(__id).get());
    #else
        return dynamic_cast<Logger*>(::IceProxy::Ice::Object::ice_connectionId(__id).get());
    #endif
    }
    
    static const ::std::string& ice_staticId();

private: 

    virtual ::IceInternal::Handle< ::IceDelegateM::Ice::Object> __createDelegateM();
    virtual ::IceInternal::Handle< ::IceDelegateD::Ice::Object> __createDelegateD();
    virtual ::IceProxy::Ice::Object* __newInstance() const;
};

class Dispatcher : virtual public ::IceProxy::Ice::Object
{
public:

    void add(const ::xlog::slice::LogDataSeq& data)
    {
        add(data, 0);
    }
    void add(const ::xlog::slice::LogDataSeq& data, const ::Ice::Context& __ctx)
    {
        add(data, &__ctx);
    }
    
private:

    void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);
    
public:

    void addLogData(const ::xlog::slice::LogData& data)
    {
        addLogData(data, 0);
    }
    void addLogData(const ::xlog::slice::LogData& data, const ::Ice::Context& __ctx)
    {
        addLogData(data, &__ctx);
    }
    
private:

    void addLogData(const ::xlog::slice::LogData&, const ::Ice::Context*);
    
public:

    void createZNode(::Ice::Int slot)
    {
        createZNode(slot, 0);
    }
    void createZNode(::Ice::Int slot, const ::Ice::Context& __ctx)
    {
        createZNode(slot, &__ctx);
    }
    
private:

    void createZNode(::Ice::Int, const ::Ice::Context*);
    
public:

    bool _cpp_register(const ::xlog::slice::LoggerPrx& subscriber, ::Ice::Int frequence)
    {
        return _cpp_register(subscriber, frequence, 0);
    }
    bool _cpp_register(const ::xlog::slice::LoggerPrx& subscriber, ::Ice::Int frequence, const ::Ice::Context& __ctx)
    {
        return _cpp_register(subscriber, frequence, &__ctx);
    }
    
private:

    bool _cpp_register(const ::xlog::slice::LoggerPrx&, ::Ice::Int, const ::Ice::Context*);
    
public:
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_context(const ::Ice::Context& __context) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_context(__context).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_context(__context).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_adapterId(const std::string& __id) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_adapterId(__id).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_adapterId(__id).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_endpoints(const ::Ice::EndpointSeq& __endpoints) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_endpoints(__endpoints).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_endpoints(__endpoints).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_locatorCacheTimeout(int __timeout) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_locatorCacheTimeout(__timeout).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_locatorCacheTimeout(__timeout).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_connectionCached(bool __cached) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_connectionCached(__cached).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_connectionCached(__cached).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_endpointSelection(::Ice::EndpointSelectionType __est) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_endpointSelection(__est).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_endpointSelection(__est).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_secure(bool __secure) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_secure(__secure).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_secure(__secure).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_preferSecure(bool __preferSecure) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_preferSecure(__preferSecure).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_preferSecure(__preferSecure).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_router(const ::Ice::RouterPrx& __router) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_router(__router).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_router(__router).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_locator(const ::Ice::LocatorPrx& __locator) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_locator(__locator).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_locator(__locator).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_collocationOptimized(bool __co) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_collocationOptimized(__co).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_collocationOptimized(__co).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_twoway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_twoway().get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_twoway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_oneway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_oneway().get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_oneway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_batchOneway() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_batchOneway().get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_batchOneway().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_datagram() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_datagram().get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_datagram().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_batchDatagram() const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_batchDatagram().get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_batchDatagram().get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_compress(bool __compress) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_compress(__compress).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_compress(__compress).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_timeout(int __timeout) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_timeout(__timeout).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_timeout(__timeout).get());
    #endif
    }
    
    ::IceInternal::ProxyHandle<Dispatcher> ice_connectionId(const std::string& __id) const
    {
    #if defined(_MSC_VER) && (_MSC_VER < 1300) // VC++ 6 compiler bug
        typedef ::IceProxy::Ice::Object _Base;
        return dynamic_cast<Dispatcher*>(_Base::ice_connectionId(__id).get());
    #else
        return dynamic_cast<Dispatcher*>(::IceProxy::Ice::Object::ice_connectionId(__id).get());
    #endif
    }
    
    static const ::std::string& ice_staticId();

private: 

    virtual ::IceInternal::Handle< ::IceDelegateM::Ice::Object> __createDelegateM();
    virtual ::IceInternal::Handle< ::IceDelegateD::Ice::Object> __createDelegateD();
    virtual ::IceProxy::Ice::Object* __newInstance() const;
};

}

}

}

namespace IceDelegate
{

namespace xlog
{

namespace slice
{

class Subscriber : virtual public ::IceDelegate::Ice::Object
{
public:

    virtual void notify(const ::Ice::StringSeq&, const ::Ice::Context*) = 0;
};

class Agent : virtual public ::IceDelegate::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*) = 0;

    virtual void addFailedLogData(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*) = 0;

    virtual ::Ice::StringSeq subscribeClient(const ::std::string&, const ::Ice::Context*) = 0;

    virtual ::Ice::StringSeq subscribeSubscriber(const ::Ice::StringSeq&, const ::std::string&, const ::Ice::Context*) = 0;
};

class Logger : virtual public ::IceDelegate::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*) = 0;

    virtual void addLogData(const ::xlog::slice::LogData&, const ::Ice::Context*) = 0;
};

class Dispatcher : virtual public ::IceDelegate::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*) = 0;

    virtual void addLogData(const ::xlog::slice::LogData&, const ::Ice::Context*) = 0;

    virtual void createZNode(::Ice::Int, const ::Ice::Context*) = 0;

    virtual bool _cpp_register(const ::xlog::slice::LoggerPrx&, ::Ice::Int, const ::Ice::Context*) = 0;
};

}

}

}

namespace IceDelegateM
{

namespace xlog
{

namespace slice
{

class Subscriber : virtual public ::IceDelegate::xlog::slice::Subscriber,
                   virtual public ::IceDelegateM::Ice::Object
{
public:

    virtual void notify(const ::Ice::StringSeq&, const ::Ice::Context*);
};

class Agent : virtual public ::IceDelegate::xlog::slice::Agent,
              virtual public ::IceDelegateM::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);

    virtual void addFailedLogData(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);

    virtual ::Ice::StringSeq subscribeClient(const ::std::string&, const ::Ice::Context*);

    virtual ::Ice::StringSeq subscribeSubscriber(const ::Ice::StringSeq&, const ::std::string&, const ::Ice::Context*);
};

class Logger : virtual public ::IceDelegate::xlog::slice::Logger,
               virtual public ::IceDelegateM::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);

    virtual void addLogData(const ::xlog::slice::LogData&, const ::Ice::Context*);
};

class Dispatcher : virtual public ::IceDelegate::xlog::slice::Dispatcher,
                   virtual public ::IceDelegateM::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);

    virtual void addLogData(const ::xlog::slice::LogData&, const ::Ice::Context*);

    virtual void createZNode(::Ice::Int, const ::Ice::Context*);

    virtual bool _cpp_register(const ::xlog::slice::LoggerPrx&, ::Ice::Int, const ::Ice::Context*);
};

}

}

}

namespace IceDelegateD
{

namespace xlog
{

namespace slice
{

class Subscriber : virtual public ::IceDelegate::xlog::slice::Subscriber,
                   virtual public ::IceDelegateD::Ice::Object
{
public:

    virtual void notify(const ::Ice::StringSeq&, const ::Ice::Context*);
};

class Agent : virtual public ::IceDelegate::xlog::slice::Agent,
              virtual public ::IceDelegateD::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);

    virtual void addFailedLogData(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);

    virtual ::Ice::StringSeq subscribeClient(const ::std::string&, const ::Ice::Context*);

    virtual ::Ice::StringSeq subscribeSubscriber(const ::Ice::StringSeq&, const ::std::string&, const ::Ice::Context*);
};

class Logger : virtual public ::IceDelegate::xlog::slice::Logger,
               virtual public ::IceDelegateD::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);

    virtual void addLogData(const ::xlog::slice::LogData&, const ::Ice::Context*);
};

class Dispatcher : virtual public ::IceDelegate::xlog::slice::Dispatcher,
                   virtual public ::IceDelegateD::Ice::Object
{
public:

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Context*);

    virtual void addLogData(const ::xlog::slice::LogData&, const ::Ice::Context*);

    virtual void createZNode(::Ice::Int, const ::Ice::Context*);

    virtual bool _cpp_register(const ::xlog::slice::LoggerPrx&, ::Ice::Int, const ::Ice::Context*);
};

}

}

}

namespace xlog
{

namespace slice
{

class Subscriber : virtual public ::Ice::Object
{
public:

    typedef SubscriberPrx ProxyType;
    typedef SubscriberPtr PointerType;
    
    virtual ::Ice::ObjectPtr ice_clone() const;

    virtual bool ice_isA(const ::std::string&, const ::Ice::Current& = ::Ice::Current()) const;
    virtual ::std::vector< ::std::string> ice_ids(const ::Ice::Current& = ::Ice::Current()) const;
    virtual const ::std::string& ice_id(const ::Ice::Current& = ::Ice::Current()) const;
    static const ::std::string& ice_staticId();

    virtual void notify(const ::Ice::StringSeq&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___notify(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual ::Ice::DispatchStatus __dispatch(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual void __write(::IceInternal::BasicStream*) const;
    virtual void __read(::IceInternal::BasicStream*, bool);
    virtual void __write(const ::Ice::OutputStreamPtr&) const;
    virtual void __read(const ::Ice::InputStreamPtr&, bool);
};

class Agent : virtual public ::Ice::Object
{
public:

    typedef AgentPrx ProxyType;
    typedef AgentPtr PointerType;
    
    virtual ::Ice::ObjectPtr ice_clone() const;

    virtual bool ice_isA(const ::std::string&, const ::Ice::Current& = ::Ice::Current()) const;
    virtual ::std::vector< ::std::string> ice_ids(const ::Ice::Current& = ::Ice::Current()) const;
    virtual const ::std::string& ice_id(const ::Ice::Current& = ::Ice::Current()) const;
    static const ::std::string& ice_staticId();

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___add(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual void addFailedLogData(const ::xlog::slice::LogDataSeq&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___addFailedLogData(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual ::Ice::StringSeq subscribeClient(const ::std::string&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___subscribeClient(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual ::Ice::StringSeq subscribeSubscriber(const ::Ice::StringSeq&, const ::std::string&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___subscribeSubscriber(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual ::Ice::DispatchStatus __dispatch(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual void __write(::IceInternal::BasicStream*) const;
    virtual void __read(::IceInternal::BasicStream*, bool);
    virtual void __write(const ::Ice::OutputStreamPtr&) const;
    virtual void __read(const ::Ice::InputStreamPtr&, bool);
};

class Logger : virtual public ::Ice::Object
{
public:

    typedef LoggerPrx ProxyType;
    typedef LoggerPtr PointerType;
    
    virtual ::Ice::ObjectPtr ice_clone() const;

    virtual bool ice_isA(const ::std::string&, const ::Ice::Current& = ::Ice::Current()) const;
    virtual ::std::vector< ::std::string> ice_ids(const ::Ice::Current& = ::Ice::Current()) const;
    virtual const ::std::string& ice_id(const ::Ice::Current& = ::Ice::Current()) const;
    static const ::std::string& ice_staticId();

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___add(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual void addLogData(const ::xlog::slice::LogData&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___addLogData(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual ::Ice::DispatchStatus __dispatch(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual void __write(::IceInternal::BasicStream*) const;
    virtual void __read(::IceInternal::BasicStream*, bool);
    virtual void __write(const ::Ice::OutputStreamPtr&) const;
    virtual void __read(const ::Ice::InputStreamPtr&, bool);
};

class Dispatcher : virtual public ::Ice::Object
{
public:

    typedef DispatcherPrx ProxyType;
    typedef DispatcherPtr PointerType;
    
    virtual ::Ice::ObjectPtr ice_clone() const;

    virtual bool ice_isA(const ::std::string&, const ::Ice::Current& = ::Ice::Current()) const;
    virtual ::std::vector< ::std::string> ice_ids(const ::Ice::Current& = ::Ice::Current()) const;
    virtual const ::std::string& ice_id(const ::Ice::Current& = ::Ice::Current()) const;
    static const ::std::string& ice_staticId();

    virtual void add(const ::xlog::slice::LogDataSeq&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___add(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual void addLogData(const ::xlog::slice::LogData&, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___addLogData(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual void createZNode(::Ice::Int, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___createZNode(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual bool _cpp_register(const ::xlog::slice::LoggerPrx&, ::Ice::Int, const ::Ice::Current& = ::Ice::Current()) = 0;
    ::Ice::DispatchStatus ___register(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual ::Ice::DispatchStatus __dispatch(::IceInternal::Incoming&, const ::Ice::Current&);

    virtual void __write(::IceInternal::BasicStream*) const;
    virtual void __read(::IceInternal::BasicStream*, bool);
    virtual void __write(const ::Ice::OutputStreamPtr&) const;
    virtual void __read(const ::Ice::InputStreamPtr&, bool);
};

}

}

#endif
