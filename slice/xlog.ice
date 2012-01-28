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
    void add(LogDataSeq data);
  };

  interface Dispatcher {
    void add(LogDataSeq data);
    bool register(Logger* subscriber, int frequence);
  };
};

