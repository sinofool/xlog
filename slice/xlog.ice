#include <Ice/BuiltinSequences.ice>

module xlog {
  interface Agent {
    int ensureLog(Ice::StringSeq lines);
    void dobestLog(Ice::StringSeq lines);
  };

  interface Logger {
    int log(Ice::StringSeq lines);
  };

  interface Dispatcher {
    bool register(Logger* subscriber, int frequence);
  };
};

