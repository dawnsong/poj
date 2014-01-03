/*
 * src.cpp
 * Copyright (C) 2014 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2014-01-03 15:01
 * Distributed under terms of the GPLv2 license.
 */

#include "string.h"
#include <iostream>
using namespace std;

int main(int argc, char **argv){

    const char haab[19][10]={"pop","no","zip","zotz","tzec","xul","yoxkin","mol","chen","yax","zac", "ceh","mac","kankin","muan","pax","koyab","cumhu","uayet" };
    const char tzolkin[20][10]={"imix","ik","akbal","kan","chicchan","cimi","manik","lamat","muluk","ok","chuen","eb","ben","ix","mem","cib","caban","eznab","canac","ahau"};

    int n=0;
    cin>>n;
    cout<<n<<endl;
    while(n){
        int d,y;
        char dot, m[10];
        int ad;//absolute day from day 0
        cin >> d>>dot>>m >>y ;
        int im=0; for(;im<20;im++){
            if(0==strcmp(m, haab[im]))break;
        }
        ad=d+im*20+y*365;
        int td,tyd,tm,ty;
        tyd=ad;//%260;
        ty=ad/260;
        td=1+tyd%13;
        tm=tyd%20;

        cout<< td <<" "<<tzolkin[tm] <<" "<<ty<<endl;
        n--;
    }

    return 0;
}

