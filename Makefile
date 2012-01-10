ICE_HOME=/opt/Ice-3.3
ZOOKEEPER_HOME=/usr/local/distcache-dev
CC=g++

all: build/agent

clean:
	rm -rf build

build/libXlogSlice.a: slice/xlog.ice
	mkdir -p build/generated
	$(ICE_HOME)/bin/slice2cpp -I$(ICE_HOME)/slice --output-dir build/generated slice/xlog.ice
	$(CC) -c -o build/generated/xlog.o -I$(ICE_HOME)/include -Ibuild/generated build/generated/xlog.cpp
	$(AR) cr build/libXlogSlice.a build/generated/xlog.o

build/agent: build/libXlogSlice.a build/agent.o build/AgentI.o build/ZkManager.o build/ConfigManager.o build/AgentConfigManager.o build/DispatcherConfigManager.o build/DispatcherAdapter.o
	$(CC) -o $@ build/ZkManager.o build/ConfigManager.o build/AgentConfigManager.o build/DispatcherConfigManager.o build/DispatcherAdapter.o build/AgentI.o build/agent.o -Lbuild -lXlogSlice -L$(ICE_HOME)/lib -L$(ZOOKEEPER_HOME)/lib -lIce -lIceUtil -lzookeeper_mt

build/ZkManager.o: src/common/ZkManager.cpp src/common/ZkManager.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/common/ZkManager.cpp

build/ConfigManager.o: src/common/ConfigManager.cpp src/common/ConfigManager.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/common/ConfigManager.cpp

build/AgentConfigManager.o: src/common/AgentConfigManager.cpp src/common/AgentConfigManager.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/common/AgentConfigManager.cpp

build/DispatcherConfigManager.o: src/common/DispatcherConfigManager.cpp src/common/DispatcherConfigManager.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/common/DispatcherConfigManager.cpp

build/DispatcherAdapter.o: src/agent/DispatcherAdapter.cpp src/agent/DispatcherAdapter.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/agent/DispatcherAdapter.cpp

build/AgentI.o: src/agent/AgentI.cpp src/agent/AgentI.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/agent/AgentI.cpp

build/agent.o: src/agent/agent.cpp
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -I$(ZOOKEEPER_HOME)/include -Ibuild/generated src/agent/agent.cpp

