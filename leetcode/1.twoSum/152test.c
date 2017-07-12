/*
 * 152test.c
 * Copyright (C) 2017 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2017-07-07 11:50
 * Distributed under terms of the GPLv2 license.
 */

//#include "152test.h"

int main(int argc, char **argv){

    return 0;
}
int maxProduct(int* nums, int numsSize) {
    int result=-2147483648; //maximal negative int
    int i,j, cumprod;
    for(i=0;i<numsSize;i++){
        cumprod=nums[i];
        if(result < cumprod) result=cumprod;
        for(j=i+1;j<numsSize;j++){
            cumprod=cumprod * nums[j];
            if(result < cumprod) result=cumprod;
        }
    }
    return result;
}
