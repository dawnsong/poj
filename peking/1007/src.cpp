/*
 * qsort.ex.cpp
 * Copyright (C) 2014 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2014-01-03 10:55
 * Distributed under terms of the GPLv2 license.
 */

//#include <stdio.h>
#include <stdlib.h>

typedef struct {
    char *str;
    int len;
}SGENE;
int calcUnsortedness(char aDNA[]){
    char *p=aDNA;
    int result=0;
    while(*p){
        char *pp=p;
        while(*(++pp)){
            result +=( *p > *pp );
            //printf("%c>%c %d\n",*p, *pp, *p > *pp );
        }
        //printf("%d\n", result);
        p++;
    }
    return result;
}
int compareGeneValues(const void * a, const void * b){
    SGENE *ga=(SGENE *)a;
    SGENE *gb=(SGENE *)b;
    //return gb->value - ga->value;

    return calcUnsortedness(ga->str) - calcUnsortedness(gb->str);
}

#include <iostream>
using namespace std;

int main(int argc, char **argv){

    //char x[]="DAABEC";
    //printf("%d\n",x[1]>x[3]);
    //printf("%d", calcUnsortedness(x));

    int len, n;
    cin>>len>>n;
    SGENE *dna=new SGENE[n];

    for(int i=0;i<n;i++){
        dna[i].str=new char[len+1];
        cin>>dna[i].str;
        //cout<<dna[i].str<<" "<<calcUnsortedness(dna[i].str)<<endl;
    }
    qsort(dna, n, sizeof(SGENE), compareGeneValues);

    for(int i=0;i<n;i++){
        cout<<dna[i].str<<endl;
        delete [] dna[i].str;
    }
    delete[] dna;
    return 0;
}

