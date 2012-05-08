
ifndef ICE_HOME
        ICE_HOME=/opt/Ice-3.3
endif
ifndef XLOG_HOME 
	XLOG_HOME=/home/xianquanzhang/xlog/build
endif

all:build/client.example

clean:
	rm -rf build

build/client.example: client_example.cpp
	mkdir -p build
	g++ -o $@ -I$(XLOG_HOME)/include -L$(XLOG_HOME)/lib $^ -lIce -lClient
