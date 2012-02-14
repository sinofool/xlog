
ifndef ICE_HOME
	ICE_HOME=/opt/Ice-3.3
endif
ifndef ZOOKEEPER_HOME
	ZOOKEEPER_HOME=/opt/zookeeper
endif
ifndef PROTOBUF_HOME
	PROTOBUF_HOME=/opt/protobuf
endif

ifndef BOOST_HOME
	BOOST_HOME=/opt/boost
endif

#=========== RULES below ===========

CC=g++ -g
INCLUDE_PATH=-I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -I$(BOOST_HOME)/include -Ibuild/generated
all: build/agent.binary build/config.bootstrap# build/libClient.a

clean:
	rm -rf build

build/%.o: src/%.cpp src/%.h build/generated/xlog.cpp build/generated/xlog.h
	mkdir -p build/{common,config,agent,client,adapter}
	$(CC) -c -o $@ $(INCLUDE_PATH) $<

build/generated/%.o: build/generated/%.cpp build/generated/%.h
	mkdir -p build/generated
	$(CC) -c -o $@ $(INCLUDE_PATH) $<

build/generated/xlog.cpp build/generated/xlog.h: interface/xlog.ice interface/xlog.proto
	mkdir -p build/generated
	$(ICE_HOME)/bin/slice2cpp -I$(ICE_HOME)/slice --output-dir build/generated interface/xlog.ice
	$(ICE_HOME)/bin/slice2java -I$(ICE_HOME)/slice --output-dir build/generated interface/xlog.ice
	$(PROTOBUF_HOME)/bin/protoc interface/xlog.proto --cpp_out=build/generated/
	$(PROTOBUF_HOME)/bin/protoc interface/xlog.proto --java_out=build/generated/

interface: build/generated/xlog.h
	echo "PHONY object"

build/agent.binary: src/agent/agent.cpp build/generated/xlog.o build/agent/AgentI.o build/common/zk_manager.o build/config/agent_config_manager.o build/config/client_config_manager.o build/config/dispatcher_config.o build/adapter/client_adapter.o build/adapter/dispatcher_adapter.o build/common/zk_conn.o
	$(CC) -o $@ $(INCLUDE_PATH) $^ -L$(ICE_HOME)/lib -L$(ZOOKEEPER_HOME)/lib -lIce -lIceUtil -lzookeeper_mt -Wl,--rpath=$(ICE_HOME)/lib -Wl,--rpath=$(ZOOKEEPER_HOME)/lib

build/config.bootstrap: src/config/config_bootstrap.cpp build/common/zk_conn.o
	$(CC) -o $@ $(INCLUDE_PATH) $^ -L$(ZOOKEEPER_HOME)/lib -lzookeeper_mt  -Wl,--rpath=$(ZOOKEEPER_HOME)/lib
#build/libClient.a: build/agent/agent_adapter.o build/client.o build/tcp_client.o build/udp_client.o
#	$(AR) cr $@ $^

