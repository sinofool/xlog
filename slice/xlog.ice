#include <Ice/BuiltinSequences.ice>

module xlog {
  struct LogData {
    Ice::StringSeq categories;
    Ice::StringSeq logs;
    string checkSum;
  };
  sequence<LogData> LogDataSeq;

  interface Subscriber {
    void notify(Ice::StringSeq config);
  };

  interface Agent {
    void add(LogDataSeq data);
    void addFailedLogData(LogDataSeq data);

    Ice::StringSeq subscribeClient(string prxStr);
    Ice::StringSeq subscribeSubscriber(Ice::StringSeq categories, string prxStr);
  };

  interface Logger {
    int log(Ice::StringSeq lines);
  };

  interface Dispatcher {
    bool register(Logger* subscriber, int frequence);
  };
};

