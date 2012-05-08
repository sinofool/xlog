#include "xlog.h"
#include "src/client/client.h"

#include <string>
#include <boost/lexical_cast.hpp>
#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>

int main(int argc , char** argv)
{
    if(argc==1)
    {
        std::cout << "Usage: host:port , ... " << std::endl;
        return 0;
    }
    *argv++;
    std::string str_prx("my_client");
    Ice::StringSeq agent_seq;
    std::vector < std::string > parts;
    boost::algorithm::split(agent_seq , *argv, boost::algorithm::is_any_of(","));

    xlog::Client my_client(str_prx,agent_seq,false);
    //xlog::Client my_client(str_prx,agent_seq,true); 
 
    std::cout << " please input count : " << std::endl; 
    std::string str;
    getline(std::cin,str);
    while(str.compare("quit")!=0){
        int count =boost::lexical_cast<int>(str); 
        for(int i=0;i<count;i++){
            std::string tmp=boost::lexical_cast<std::string>(i);

            xlog::slice::LogDataSeq log_data_seq;
            xlog::slice::LogData log_data;
            Ice::StringSeq categories;
            categories.push_back("www");
            categories.push_back("renren");
            categories.push_back("com");

            log_data.categories=categories;

            Ice::StringSeq logs;
            logs.push_back("Welcome to renren! your number is : "+tmp);
            logs.push_back("Wello,everyone! your number is : "+tmp);

            log_data.logs=logs;
            log_data.checkSum=tmp;

            log_data_seq.push_back(log_data);
            my_client.append(log_data_seq);
        }
        std::cout << " please input count : " << std::endl;
        getline(std::cin,str);
    } 
    std::cout << " client exit !" << std::endl;    
    return 0;
}
