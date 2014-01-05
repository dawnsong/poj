/*
 * src.cpp
 * Copyright (C) 2014 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2014-01-03 17:28
 * Distributed under terms of the GPLv2 license.
 */

#include <iostream>
using namespace std;

int main(int argc, char **argv){

    int width=0, height=0;
    while(1){
        cin>>width; if(width==0)break;
        cout<<width<<endl;

        int v, len,nl=0, value[1000], length[1000], cumLength[1000];
        cumLength[0]=0;//start from 1, not zero
        while(1){
            cin>>v>>len;
            if(v==0 && len==0)break;
            value[nl]=v; length[nl]=len;
            cumLength[1+nl]=cumLength[nl]+len;//trick to quickly map pixel value
            nl++;
        }
        //calc height of image
        height=0;
        for(int i=0;i<nl;i++){
            height+=length[i];
        }
        height/=width;
        //cout<<"height*width: "<<height<<" * "<<width<<endl;
        //

        //Improvement both in space and time
        //use RLE inside for memory
        //edge detection
        //and calculate and output RLE results
        int oldPV=-1;//record old pixel's value to see if need output
        int oldPN=0; //record old pixel's value's count for RLE

        int iRLE=0;  //track RLE array, cumcdf start from 1
        int po,oo;   //pixel position
        for(int i=0;i<height;i++)
        for(int j=0;j<width;j++){
            //map pixel value to RLE index
            po=i*width+j; while(po>=cumLength[iRLE] && iRLE<=nl)iRLE++;
            int maxdiff=0;
            int pv=value[iRLE-1];//image[i*width+j]
            //cerr<<po<<" "<<pv<<endl;
            //

            //Same RLE detection
            //if top-left and bottom-right both in same RLE
            //then no need to calclate any more

            for(int ki=i-1;ki<=i+1;ki++){
                if(ki<0 || ki>=height)continue;
                for(int kj=j-1;kj<=j+1;kj++){
                    if(ki==i && kj==j) continue;
                    if(kj<0 || kj>=width)continue;

                    oo=ki*width+kj;
                    int oRLE=iRLE-1;//assume not far away from iRLE
                    if(oo<po){
                        while(oo <cumLength[oRLE] && oRLE>=0)oRLE--;
                    }
                    else{
                        while(oo >=cumLength[oRLE] && oRLE<=nl)oRLE++;
                        oRLE-=1;
                    }
                    if(oRLE==iRLE-1)break;//same RLE

                    int ov=value[oRLE] ;//image[ki*width+kj]
                    int diff = pv>ov?(pv-ov):(ov-pv);
                    if(diff>maxdiff)maxdiff=diff;
                }
            }//rimage[i*width+j]=maxdiff;
            //detect if RLE output is needed
            if(oldPV!=maxdiff){
                if(oldPV>=0)//consider init condition
                    cout<<oldPV<<" "<<oldPN  <<endl;
                //start new count
                oldPV=maxdiff;
                oldPN=1;
            }else{ oldPN++; }
        }
        //for end condition
        cout<<oldPV<<" "<<oldPN  <<endl;


        ////decompress RLE
        //unsigned char image[height*width];//[height][width];
        //unsigned char *pimage=image;
        //for(int i=0;i<nl;i++){
            //for(int j=0;j<length[i];j++){
                //*pimage=value[i];
                //pimage++;
            //}
        //}

        ////edge detection
        //unsigned char rimage[height*width] ;//[height][width];
        //for(int i=0;i<height;i++)
        //for(int j=0;j<width;j++){
            //rimage[i*width+j]=0; int maxdiff=0;
            //for(int ki=i-1;ki<=i+1;ki++){
                //if(ki<0 || ki>=height)continue;
                //for(int kj=j-1;kj<=j+1;kj++){
                    //if(kj<0 || kj>=width)continue;
                    //int diff=abs(image[i*width+j]-image[ki*width+kj]);
                    //if(diff>maxdiff)maxdiff=diff;
                //}
            //}
            //rimage[i*width+j]=maxdiff;
        //}

        ////print RLE compressed result
        //pimage=rimage;
        //int n=1;
        //while(n<=height*width){
            //v=*pimage;
            //int nv=n, vv=0;
            //while(++nv<=height*width){
                //pimage++;
                //vv=*pimage;
                //if(vv-v)break;
            //}
            //cout<<v<<" "<<nv-n<<endl;
            //n=nv;
        //}
        cout<<"0 0"<<endl;
        //break;
    }
    cout<<"0"<<endl;

    return 0;
}

