/*
 * src.cpp
 * Copyright (C) 2014 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2014-01-01 21:43
 * Distributed under terms of the GPLv2 license.
 */

//#include "src.h"
#include <iostream>
using namespace std;

int main(int argc, char **argv){

    int p,e,i,d;
    int nc=0,rd=0;
    while(cin>>p>>e>>i>>d){
        if(d<0)break;
        rd=d;
        while((rd-d)<=21252){
            rd++;
            //cout<<rd<<"\r";
            p=p%23;
            e=e%28;
            i=i%33;
            if( (0==(rd-p)%23) && (0==(rd-e)%28) && (0==(rd-i)%33) )break;
        }
        rd-=d;

        rd=rd%21252;
        if(rd<=0)rd=21252; //this is the key for brute-force solution
        nc++;
        cout << "Case "<<nc<<": the next triple peak occurs in "<< rd <<" days."<<endl;
    }
    return 0;
}

