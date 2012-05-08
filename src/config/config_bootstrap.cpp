#include "src/common/zk_conn.h"
#include "src/config/dispatcher_config.h"
#include "build/generated/xlog.pb.h"
#include <boost/lexical_cast.hpp>

using namespace xlog;
using namespace std;

int main(int argc, char **argv)
{   
    if(argc==1)
    {
      cout << "Usage:zk_host:zk_port/xlog_path slot_count"<<endl;
      return 0;
    }
    ZKConnectionPtr conn = ZKConnectionPtr(new ZKConnection);
    *argv++;
    if (!conn->init(*argv++))
    {
        cerr << "Can not init zk, exit" << endl;
        return 0;
    }
    
    int slotCount=boost::lexical_cast<int>(*argv);
    VectorOfChar empty;
    xlog::proto::ClusterInfo cluster;
    cluster.set_size(slotCount);
    VectorOfChar data(cluster.ByteSize());
    cluster.SerializeToArray(&data[0], data.size());
    bool dis_ret = conn->create(DISPATCHERS_PATH, data);
    if (!dis_ret)
    {
        std::cout << "Create " << DISPATCHERS_PATH << " error." << std::endl;
    }
    for (int i = 0; i < slotCount; ++i)
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
