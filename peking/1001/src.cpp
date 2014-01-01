/*
 * src.cpp
 * Copyright (C) 2013 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2013-12-30 12:22
 * Distributed under terms of the GPLv2 license.
 */

//#include "src.h"
#define RESULTLEN 1024
#include <iostream>
using namespace std;


int main(int argc, char **argv){
    char s[7] = "Hello!"; s[6]=0;
    short n=0;

    char b[7] = "Hello!"; b[6]=0; //no dot
    int n10=0, nb=6;

    char *ps=0, *pb=0;
    while(cin >>s >>n){
        //cout <<s <<" "<<n << endl;

        //detect the power of 10
        ps=s; n10=0;
        while(*ps){
            n10++;
            if(*ps=='.') break;
            ps++;
        } //cout<<n10<<endl;
        if(!*ps){b[5]='x'; nb=6;}else{b[5]=0; nb=5;}
        n10=6-n10;
        n10=n10 * n;

        ps=s; pb=b;
        while(*ps && *pb){
            if(*ps != '.'){
                *pb=*ps;
                pb++;
            }
            ps++;
        }
        //pb=b; while(*pb){ cout<<*pb; pb++; } cout<<endl;
        //cout << b <<" " << n10 <<" " << nb << endl ;

        int r[RESULTLEN]; //for result
        for(int i=0;i<RESULTLEN;i++)r[i]=-1;//init

        //inverse order to save big integer
        for(int i=0;i<nb;i++){r[i]=b[nb-1-i]-'0';}
        for(int i=0;i<nb;i++){b[i]=r[i]+'0';}
        //for(int j=0;r[j]>=0;j++)cout<<r[j];  cout<<endl;

        //start power calculation
        for(int pw=n-1;pw>0;pw--){//power of n-1 since I saved power 1's result
            int t[nb][RESULTLEN];
            for(int i=0;i<nb;i++)
                for(int j=0;j<RESULTLEN;j++)
                    t[i][j]=-1;

            //multiply for each digit of multiplier b
            for(int i=0;i<nb;i++){
                for(int j=0;r[j]>=0;j++){
                    t[i][j]=r[j]*(b[i]-'0');
                }
            }
            //merge multi-layers into one
            for(int i=0;i<RESULTLEN;i++)r[i]=-1;//init
            for(int i=0;t[0][i]>=0;i++){
                r[i]=t[0][i];
                //cout<<t[0][j]<<" ";
            }//cout<<endl;
            for(int j=1;j<nb;j++){
                for(int k=0;t[j][k]>=0;k++){
                    if(r[j+k]<0)r[j+k]=0;
                    r[j+k] += t[j][k];
                    //cout << t[j][k]<<","<<r[j+k]<<"| ";
                }//cout<<endl;
            }

            //process to be 10-based numbers
            int u=0; //up to higher digit
            for(int j=0;r[j]>=0;j++){
                //cout<<r[j]<<" ";
                u= r[j]/10;
                //cout<<u<<" "<<r[j]<<endl;
                r[j]= r[j]%10;

                if(u>0 && r[j+1]<0)r[j+1]=0;
                r[j+1]+=u;
            }
        }//end for power calculation

        //get the length of big multiply result
        int rlen=0;
        while(r[rlen]>=0)rlen++;
        while(r[rlen-1]==0)rlen--;//remove head zero since it doesn't mean anything
        //for(int i=rlen-1;i>=0;i--)cout<<r[i]; cout<<" "<<rlen<<endl;
        if(rlen==0)cout<<0<<endl;
        else{
            int len=rlen;
            if(len<n10)len=n10;

            char result[1+len+1];//dot need one char, null need one char
            result[len+1]=0;
            int i=len,j=0, to=0;
            while(i>=0){
                if(i==(len-n10)){result[i]='.'; to=1; }
                if(j<rlen){
                    result[i-to]=r[j]+'0';
                    j++;
                }
                else result[i-to]='0';

                i--;
            }
            //cout<<result <<endl;// return 0;
            //clean extra zeros greater than 0
            int si=0;
            while(si<=len+1){
                if(result[si]>'0' || result[si]=='.')break;
                si++;
            }

            //clean extra zeros less than 0
            i=len;
            while(i>=0){
                if(result[i]=='.'){result[i]=0; break;}
                if(result[i]!='0'&& result[i]!='.')break;
                if(result[i]=='0')result[i]=0;
                i--;
            }

            char fresult[1+len+1];
            fresult[len+1]=0;
            for(i=si;i<=len+1;i++){
                fresult[i]=result[i];
            }

            cout<<fresult <<endl; // return 0;
        }
        //init for next line
        for(int i=0;i<6;i++)s[i]='0';
        for(int i=0;i<5;i++)b[i]='0';

        //return 0;
    }//end for while cin
    return 0;
}

