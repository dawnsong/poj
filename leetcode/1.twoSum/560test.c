/*
 * 560test.c
 * Copyright (C) 2017 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2017-07-07 11:29
 * Distributed under terms of the GPLv2 license.
 */

//#include "560test.h"

int main(int argc, char **argv){

    return 0;
}

int subarraySum(int* nums, int numsSize, int k) {
    int result=0;
    int i,j, cumsum;
    for(i=0;i<numsSize;i++){
        cumsum=nums[i];
        if(k == cumsum) result++;
        for(j=i+1;j<numsSize;j++){
            cumsum=cumsum+nums[j];
            if(k == cumsum) result++;
        }
    }
    return result;
}
