#include <Ice/BuiltinSequences.ice>

module xlog {
  struct LogData {
	Ice::StringSeq categories;
	Ice::StringSeq logs;
	string checkSum;
  };
  sequence<LogData> LogDataSeq;

  interface Agent {
    void add(LogDataSeq data);
    void addFailedLogData(LogDataSeq data);
	Ice::StringSeq getAgents();
  };

  interface Logger {
    int log(Ice::StringSeq lines);
  };

  interface Dispatcher {
    bool register(Logger* subscriber, int frequence);
  };
};

