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

        for(int i=0;i<height;i++){
            if(i){//speedup
            //cerr<<i<<endl;
            //Assumption: RLE is far more larger than image's width
            //Same RLE 2 lines ignorance if 4 lines are in same RLE
            int pon0, pon1, pon2;
            int n0rle=iRLE;
            pon0=(i-1)*width;
            while(pon0<cumLength[n0rle] && n0rle>=0)n0rle--;
            //cerr<< "pon0: "<<pon0<<"|"<<n0rle <<" "<< cumLength[n0rle]<<" "<<n0rle+1<<","<<nl<<" "<<cumLength[n0rle+1]<<endl;
            //if(n0rle==iRLE){
            if(pon0>=cumLength[n0rle] && (n0rle+1)<=nl &&pon0<cumLength[n0rle+1]){
                //int n1rle=n0rle;
                pon1=(i+1)*width-1;
                //while(pon1>=cumLength[n1rle] && n1rle<=nl)n1rle++; n1rle-=1;
                //cerr<< "pon1: "<<pon1<<"|"<<n0rle <<" "<< cumLength[n0rle]<<" "<<n0rle+1<<","<<nl<<" "<<cumLength[n0rle+1]<<endl;
                if(pon1>=cumLength[n0rle] && (n0rle+1)<=nl &&pon1<cumLength[n0rle+1]){
                    //int n2rle=n1rle;
                    pon2=(i+2)*width-1;
                    //while(pon2>=cumLength[n2rle] && n2rle<=nl)n2rle++; n2rle-=1;
                    //if(n2rle==n1rle){
                    //cerr<< "pon2: "<<pon2<<"|"<<n0rle <<" "<< cumLength[n0rle]<<" "<<n0rle+1<<","<<nl<<" "<<cumLength[n0rle+1]<<endl;
                    if(pon2>=cumLength[n0rle] && (n0rle+1)<=nl &&pon2<cumLength[n0rle+1]){
                        if(0==oldPV){
                            oldPN+=width;
                            //ignore 1 lines from now
                            //i+=1;//consider i++ in for loop, compensation
                            //calculate directly the result from the cumLength edge point
                            int speedo=1;
                            pon2+=width*speedo;
                            while(pon2>=cumLength[n0rle] && (n0rle+1)<=nl &&pon2<cumLength[n0rle+1]){
                            oldPN+=width*speedo;
                            i+=1*speedo;
                            pon2+=width*speedo;
                            speedo++;
                            }
                            speedo--;
                            oldPN-=width*speedo;
                            i-=1*speedo;
                            continue;
                        }
                    }
                }
            }//n0rle==iRLE
            }//i>=1

            for(int j=0;j<width;j++){
                //map pixel value to RLE index
                po=i*width+j; while(po>=cumLength[iRLE] && iRLE<=nl)iRLE++; iRLE-=1;

                int maxdiff=0;
                int pv=value[iRLE];//image[i*width+j]
                //cerr<<po<<" "<<pv<<endl;
                //

                for(int ki=i-1; ki<=i+1; ki++){
                    if(ki<0 || ki>=height)continue;
                    for(int kj=j-1;kj<=j+1;kj++){
                        if(ki==i && kj==j) continue;
                        if(kj<0 || kj>=width)continue;

                        oo=ki*width+kj;
                        int oRLE=iRLE;//assume not far away from iRLE
                        if(oo<po){
                            while(oo <cumLength[oRLE] && oRLE>=0)oRLE--;
                        }
                        else{
                            while(oo >=cumLength[oRLE] && oRLE<=nl)oRLE++;
                            oRLE-=1;
                        }

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

