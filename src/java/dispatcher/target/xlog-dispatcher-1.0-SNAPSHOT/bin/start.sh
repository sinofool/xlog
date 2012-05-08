#!/bin/bash

usage()
{
    printf "usage: ${0##*/} [webserver|dispatcher|all]\n"
    exit 1
}
[ $# -gt 0 ] || usage

#BASEDIR=`dirname $0`
cd ..
CLASSPATH=$CLASSPATH

LIBPATH="lib"

export LIBPATH

for f in `find $LIBPATH -name '*.jar'`
  do
    CLASSPATH=$CLASSPATH:$f
  done
# ******************************************************************
# ** Set java runtime options                                     **
# ** Change 256m to higher values in case you run out of memory.  **
# ******************************************************************

#export DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,address=3005,server=y,suspend=n"
OPT="-Xmx2g -Xms1g -Xmn512m -Xss128k -XX:+UseParallelGC -XX:ParallelGCThreads=20 -cp $CLASSPATH"

# ***************
# ** Run...    **
# ***************

java $OPT com.renren.dp.xlog.dispatcher.Bootstrap "${1+$@}"
