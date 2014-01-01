#!/bin/bash -
#
# cmp.sh
# Copyright (C) 2013 Xiaowei Song <dawnwei.song@gmail.com>
#

elog(){ echo "$@" 1>&2 ; }

if [ $# -eq 0 ]; then
  elog "Usage: $0 " 
  exit 1
fi 


paste $1 $2 |while read a b; do 
    echo "scale=200; $a-$b" |bc
done

