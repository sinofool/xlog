
ifndef ICE_HOME
	ICE_HOME=/opt/Ice-3.3
endif
ifndef ZOOKEEPER_HOME
	ZOOKEEPER_HOME=/opt/zookeeper
endif

ifndef BOOST_HOME
	BOOST_HOME=/opt/boost
endif

#=========== RULES below ===========

CC=g++ -g
INCLUDE_PATH=-I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -I$(BOOST_HOME)/include -Ibuild/generated
all: build/agent.binary# build/libClient.a

clean:
	rm -rf build

build/%.o: src/%.cpp src/%.h
	mkdir -p build/{common,config,agent,client,adapter}
	$(CC) -c -o $@ $(INCLUDE_PATH) $<
	
build/libXlogSlice.a: slice/xlog.ice
	mkdir -p build/generated
	$(ICE_HOME)/bin/slice2cpp -I$(ICE_HOME)/slice --output-dir build/generated slice/xlog.ice
	$(CC) -c -o build/generated/xlog.o -I$(ICE_HOME)/include -Ibuild/generated build/generated/xlog.cpp
	$(AR) cr build/libXlogSlice.a build/generated/xlog.o

build/agent.o: src/agent/agent.cpp build/libXlogSlice.a
	$(CC) -c -o $@ $(INCLUDE_PATH) src/agent/agent.cpp

build/agent.binary: build/agent.o build/agent/AgentI.o build/common/zk_manager.o build/config/agent_config_manager.o build/config/client_config_manager.o build/config/dispatcher_config.o build/adapter/client_adapter.o build/adapter/dispatcher_adapter.o build/common/zk_conn.o
	$(CC) -o $@ $^ -Lbuild -lXlogSlice -L$(ICE_HOME)/lib -L$(ZOOKEEPER_HOME)/lib -lpthread -lIce -lIceUtil -lzookeeper_mt -lpthread -Wl,--rpath=$(ICE_HOME)/lib -Wl,--rpath=$(ZOOKEEPER_HOME)/lib

#build/libClient.a: build/agent/agent_adapter.o build/client.o build/tcp_client.o build/udp_client.o
#	$(AR) cr $@ $^

