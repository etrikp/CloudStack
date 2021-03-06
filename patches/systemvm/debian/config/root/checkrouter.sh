#!/bin/bash

source /root/func.sh

lock="rrouter"
locked=$(getLockFile $lock)
if [ "$locked" != "1" ]
then
    exit 1
fi

bumped="Bumped: NO"
if [ -e /tmp/rrouter_bumped ]
then
    bumped="Bumped: YES"
fi

stat=`tail -n 1 /root/keepalived.log | grep "Status"`
if [ $? -eq 0 ]
then
    echo "$stat&$bumped"
fi

unlock_exit $? $lock $locked
