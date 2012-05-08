#ifndef __UTIL_HPP__
#define __UTIL_HPP__

#include <Ice/Ice.h>

#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>

namespace xlog
{
class Util
{
public:
    template<class T>
    static T getPrx(const ::Ice::CommunicatorPtr& ic, const std::string& prxStr, bool udp = true,
            const int timeout = 300)
    {
        std::vector < std::string > parts;
        boost::algorithm::split(parts, prxStr, boost::algorithm::is_any_of(":"));
        if (parts.size() != 2)
        {
            std::cerr << "Util::getPrx prx string " << prxStr
                    << " does not match the format : <host>:<port>!" << std::endl;
            return NULL;
        }
        std::string host = parts[0];
        std::string port = parts[1];
        std::ostringstream os;
        if(udp)
        {
	   os << "A:udp -h " << host << " -p " << port;
           return T::uncheckedCast(ic->stringToProxy(os.str())->ice_locatorCacheTimeout(60)->ice_compress(true)->ice_datagram());
	}else
        {
	   os << "A:tcp -h " << host << " -p " << port;
           return T::uncheckedCast(ic->stringToProxy(os.str())->ice_twoway()->ice_timeout(timeout));
	}	
    }
};

}

#endif
