ICE_HOME=/opt/Ice-3.3
ZOOKEEPER_HOME=/usr/local/distcache-dev
CC=g++

all: build/agent build/libClient.a

clean:
	rm -rf build

build/libXlogSlice.a: slice/xlog.ice
	mkdir -p build/generated
	$(ICE_HOME)/bin/slice2cpp -I$(ICE_HOME)/slice --output-dir build/generated slice/xlog.ice
	$(CC) -c -o build/generated/xlog.o -I$(ICE_HOME)/include -Ibuild/generated build/generated/xlog.cpp
	$(AR) cr build/libXlogSlice.a build/generated/xlog.o

build/agent: build/libXlogSlice.a build/agent.o build/AgentI.o build/zk_manager.o build/agent_config_manager.o build/client_config_manager.o build/dispatcher_config_manager.o build/client_adapter.o build/dispatcher_adapter.o
	$(CC) -o $@ build/agent.o build/AgentI.o build/zk_manager.o build/agent_config_manager.o build/client_config_manager.o build/dispatcher_config_manager.o build/client_adapter.o build/dispatcher_adapter.o -Lbuild -lXlogSlice -L$(ICE_HOME)/lib -L$(ZOOKEEPER_HOME)/lib -lIce -lIceUtil -lzookeeper_mt

build/libClient.a: build/libXlogSlice.a build/agent_adapter.o build/client.o build/tcp_agent_adapter.o build/udp_agent_adapter.o build/tcp_client.o build/udp_client.o
	$(AR) cr $@ build/agent_adapter.o build/tcp_agent_adapter.o build/udp_agent_adapter.o build/tcp_client.o build/udp_client.o build/client.o 

build/zk_manager.o: src/common/zk_manager.cpp src/common/zk_manager.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/common/zk_manager.cpp

build/agent_config_manager.o: src/config/agent_config_manager.cpp src/config/agent_config_manager.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/config/agent_config_manager.cpp

build/client_config_manager.o: src/config/client_config_manager.cpp src/config/client_config_manager.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/config/client_config_manager.cpp

build/dispatcher_config_manager.o: src/config/dispatcher_config_manager.cpp src/config/dispatcher_config_manager.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/config/dispatcher_config_manager.cpp

build/agent_adapter.o: src/adapter/agent_adapter.cpp src/adapter/agent_adapter.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/adapter/agent_adapter.cpp

build/client_adapter.o: src/adapter/client_adapter.cpp src/adapter/client_adapter.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/adapter/client_adapter.cpp

build/dispatcher_adapter.o: src/adapter/dispatcher_adapter.cpp src/adapter/dispatcher_adapter.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/adapter/dispatcher_adapter.cpp

build/tcp_agent_adapter.o: src/adapter/tcp_agent_adapter.cpp src/adapter/tcp_agent_adapter.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/adapter/tcp_agent_adapter.cpp

build/udp_agent_adapter.o: src/adapter/udp_agent_adapter.cpp src/adapter/udp_agent_adapter.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/adapter/udp_agent_adapter.cpp

build/client.o: src/client/client.cpp src/client/client.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/client/client.cpp

build/tcp_client.o: src/client/tcp_client.cpp src/client/tcp_client.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/client/tcp_client.cpp

build/udp_client.o: src/client/udp_client.cpp src/client/udp_client.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/client/udp_client.cpp

build/AgentI.o: src/agent/AgentI.cpp src/agent/AgentI.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/agent/AgentI.cpp

build/agent.o: src/agent/agent.cpp
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/agent/agent.cpp

