#include "src/common/zk_conn.h"
#include "src/config/dispatcher_config.h"
#include <boost/lexical_cast.hpp>

using namespace xlog;
using namespace std;

int main(int argc, char **argv)
{
    ZKConnectionPtr conn = ZKConnectionPtr(new ZKConnection);
    if (!conn->init("127.0.0.1:2222/xlog"))
    {
        cerr << "Can not init zk, exit" << endl;
        return 0;
    }
    VectorOfChar empty;
    VectorOfChar data;
    data.push_back(19);
    bool dis_ret = conn->create(DISPATCHERS_PATH, data);
    if (!dis_ret)
    {
        std::cout << "Create " << DISPATCHERS_PATH << " error." << std::endl;
    }
    for (int i = 0; i < 19; ++i)
    {
        string path = string(DISPATCHERS_PATH) + string("/") + boost::lexical_cast<std::string>(i);

        bool dir_ret = conn->create(path, empty);
        if (!dir_ret)
        {
            std::cout << "Create " << path << " error." << std::endl;
        }
    }
    bool agt_ret = conn->create(AGENTS_PATH, empty);
    if (!agt_ret)
    {
        std::cout << "Create " << AGENTS_PATH << " error." << std::endl;
    }

}
