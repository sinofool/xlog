#include <Ice/BuiltinSequences.ice>

module xlog {
  struct LogData {
	Ice::StringSeq categories;
	Ice::StringSeq logs;
	string checkSum;
  };
  sequence<LogData> LogDataSeq;

  interface Agent {
    void add(LogDataSeq datas);
    void addFailedLogDatas(LogDataSeq datas);
	Ice::StringSeq getAgents();
  };

  interface Logger {
    int log(Ice::StringSeq lines);
  };

  interface Dispatcher {
    bool register(Logger* subscriber, int frequence);
  };
};

