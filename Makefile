
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
INCLUDE_PATH=-I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -I$(PROTOBUF_HOME)/include -I$(BOOST_HOME)/include -Ibuild/generated
LINK_LIBRARY=-L$(ICE_HOME)/lib -L$(ZOOKEEPER_HOME)/lib -L$(PROTOBUF_HOME)/lib -lIce -lIceUtil -lzookeeper_mt -lprotobuf -Wl,--rpath=$(ICE_HOME)/lib -Wl,--rpath=$(ZOOKEEPER_HOME)/lib -Wl,--rpath=$(PROTOBUF_HOME)/lib
all: build/agent.binary build/config.bootstrap build/lib/libClient.a build/include/head #build/client.example

clean:
	rm -rf build

build/%.o: src/%.cpp src/%.h build/generated/xlog.cpp build/generated/xlog.h
	mkdir -p build/common
	mkdir -p build/config
	mkdir -p build/agent
	mkdir -p build/adapter
	mkdir -p build/client
	mkdir -p build/include
	mkdir -p build/lib
	$(CC) -c -o $@ $(INCLUDE_PATH) $<

build/generated/%.pb.o: build/generated/%.pb.cc build/generated/%.pb.h
	mkdir -p build/generated
	$(CC) -c -o $@ $(INCLUDE_PATH) $<

build/generated/%.o: build/generated/%.cpp build/generated/%.h
	mkdir -p build/generated
	$(CC) -c -o $@ $(INCLUDE_PATH) $<

build/generated/xlog.cpp build/generated/xlog.h: interface/xlog.ice interface/xlog.proto
	mkdir -p build/generated
	cd interface && $(ICE_HOME)/bin/slice2cpp -I$(ICE_HOME)/slice --output-dir ../build/generated xlog.ice
	cd interface && $(ICE_HOME)/bin/slice2java -I$(ICE_HOME)/slice --output-dir ../build/generated xlog.ice
	cd interface && $(PROTOBUF_HOME)/bin/protoc xlog.proto --cpp_out=../build/generated/
	cd interface && $(PROTOBUF_HOME)/bin/protoc xlog.proto --java_out=../build/generated/

build/include/head: build/generated/xlog.h src/client/client.h src/common/common.h
	mkdir -p build/include/src/client
	mkdir -p build/include/src/common
	cp build/generated/xlog.h build/include
	cp src/client/*.h build/include/src/client
	cp src/common/common.h build/include/src/common

interface: build/generated/xlog.h
	echo "PHONY object"

build/agent.binary: src/agent/agent.cpp build/generated/xlog.o build/agent/AgentI.o build/common/zk_manager.o build/config/agent_config_manager.o build/config/client_config_manager.o build/config/dispatcher_config.o build/adapter/client_adapter.o build/adapter/dispatcher_adapter.o build/common/zk_conn.o
	$(CC) -o $@ $(INCLUDE_PATH) $^ $(LINK_LIBRARY)

build/config.bootstrap: src/config/config_bootstrap.cpp build/generated/xlog.pb.o build/common/zk_conn.o
	$(CC) -o $@ $(INCLUDE_PATH) $^ $(LINK_LIBRARY)

#build/client.example: src/example/c++/client_example.cpp build/generated/xlog.o build/adapter/agent_adapter.o build/client/client.o
#	$(CC) -o $@ $(INCLUDE_PATH) $^ $(LINK_LIBRARY)

build/lib/libClient.a: build/generated/xlog.o build/adapter/agent_adapter.o build/client/client.o
	$(AR) cr $@ $^

