/*
 * 628test.c
 * Copyright (C) 2017 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2017-07-12 09:48
 * Distributed under terms of the GPLv2 license.
 */

//#include "628test.h"

int main(int argc, char **argv){

    return 0;
}
// 22ms, can beat 52.83%
int maximumProduct(int* nums, int numsSize) {
    //int result=(1<<31) ; //-2147483648; //maximal negative int
    int i;
    //if all positive, easy, just 3 biggest num
    //if two negative, but its absolute is biggest, another one should be biggest positive num
    //if all negative, needs 3 largest negative too
    int max1, max2, max3, nm1, nm2;

    max1=max2=max3=(1<<31);
    nm1=nm2=~(1<<31);
    for(i=0;i<numsSize;i++){
        if(max1<nums[i]){
            max3=max2;
            max2=max1;
            max1 = nums[i];
        }
        else if(max2<nums[i]){
            max3=max2;
            max2=nums[i];
        }
        else if(max3<nums[i]){
            max3=nums[i];
        }

        if(nm1>nums[i]) {
            nm2=nm1;
            nm1=nums[i];
        }
        else if(nm2>nums[i])
            nm2=nums[i];
    }
    int r1=max1*max2*max3;
    int r2=nm1*nm2*max1;
    if(r1>r2) return r1;
    else return r2;
}
