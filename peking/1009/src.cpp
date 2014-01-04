/*
 * src.cpp
 * Copyright (C) 2014 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2014-01-03 17:28
 * Distributed under terms of the GPLv2 license.
 */

#include <stdlib.h>
#include <iostream>
using namespace std;

int main(int argc, char **argv){

    int width=0, height=0;
    while(1){
        cin>>width; if(width==0)break;
        cout<<width<<endl;

        int v, len,nl=0, value[1000], length[1000];
        while(1){
            cin>>v>>len;
            if(v==0 && len==0)break;
            value[nl]=v; length[nl]=len;
            nl++;
        }
        //calc height of image
        height=0;
        for(int i=0;i<nl;i++){
            height+=length[i];
        }
        height/=width;
        //cout<<"height*width: "<<height<<" * "<<width<<endl;

        //decompress RLE
        unsigned char image[height*width];//[height][width];
        unsigned char *pimage=image;
        for(int i=0;i<nl;i++){
            for(int j=0;j<length[i];j++){
                *pimage=value[i];
                pimage++;
            }
        }

        //edge detection
        unsigned char rimage[height*width] ;//[height][width];
        for(int i=0;i<height;i++)
        for(int j=0;j<width;j++){
            rimage[i*width+j]=0; int maxdiff=0;
            for(int ki=i-1;ki<=i+1;ki++){
                if(ki<0 || ki>=height)continue;
                for(int kj=j-1;kj<=j+1;kj++){
                    if(kj<0 || kj>=width)continue;
                    int diff=abs(image[i*width+j]-image[ki*width+kj]);
                    if(diff>maxdiff)maxdiff=diff;
                }
            }
            rimage[i*width+j]=maxdiff;
        }

        //print RLE compressed result
        pimage=rimage;
        int n=1;
        while(n<=height*width){
            v=*pimage;
            int nv=n, vv=0;
            while(++nv<=height*width){
                pimage++;
                vv=*pimage;
                if(vv-v)break;
            }
            cout<<v<<" "<<nv-n<<endl;
            n=nv;
        }
        cout<<"0 0"<<endl;
    }
    cout<<"0"<<endl;

    return 0;
}

