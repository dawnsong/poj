/*
 * src.cpp
 * Copyright (C) 2014 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2014-01-01 20:35
 * Distributed under terms of the GPLv2 license.
 */

#include <iomanip>
#include <iostream>
using namespace std;

int main(int argc, char **argv){

    double a,m[12];
    int i=0;
    while(cin>>a){
        m[i++]=a;
    }
    a=0;
    for(i=0;i<12;i++) a+=m[i];
    a=a/12.0;
    cout<<"$"<<fixed<<setprecision(2)<<a;

    return 0;
}

