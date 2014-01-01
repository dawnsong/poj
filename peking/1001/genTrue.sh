#!/bin/bash -
#
# genTrue.sh
# Copyright (C) 2013 Xiaowei Song <dawnwei.song@gmail.com>
#

elog(){ echo "$@" 1>&2 ; }

if [ $# -eq 0 ]; then
  elog "Usage: $0 " 
  exit 1
fi 

cat $1|
while read n p; do 
    echo "scale=200; $n^$p"|bc ;
done |
sed -e ':a;N;$!ba;s/\\\n//g' #> ${1}.true

