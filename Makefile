ICE_HOME=/opt/Ice-3.3
CC=g++

all: build/agent

clean:
	rm -rf build

build/libXlogSlice.a: slice/xlog.ice
	mkdir -p build/generated
	$(ICE_HOME)/bin/slice2cpp -I$(ICE_HOME)/slice --output-dir build/generated slice/xlog.ice
	$(CC) -c -o build/generated/xlog.o -I$(ICE_HOME)/include -Ibuild/generated build/generated/xlog.cpp
	$(AR) cr build/libXlogSlice.a build/generated/xlog.o

build/agent: build/libXlogSlice.a build/agent.o build/AgentI.o
	$(CC) -o $@ build/AgentI.o build/agent.o -Lbuild -lXlogSlice -L$(ICE_HOME)/lib -lIce -lIceUtil 

build/AgentI.o: src/agent/AgentI.cpp src/agent/AgentI.h
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -Ibuild/generated src/agent/AgentI.cpp

build/agent.o: src/agent/agent.cpp
	$(CC) -c -o $@ -I. -I$(ICE_HOME)/include -Ibuild/generated src/agent/agent.cpp

