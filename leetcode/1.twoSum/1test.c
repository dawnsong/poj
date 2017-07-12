/*
 * test.c
 * Copyright (C) 2017 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2017-07-07 10:43
 * Distributed under terms of the GPLv2 license.
 */

//#include "test.h"

int main(int argc, char **argv){

    return 0;
}

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* twoSum(int* nums, int numsSize, int target) {
    int *result=malloc(sizeof(int)*2);
    int i,j;
    for(i=0;i<numsSize-1;i++){
        result[0]=i;
        for(j=i+1;j<numsSize;j++){
            result[1]=j;
            if(target == (nums[result[0]]+nums[result[1]]) )
                return result;
        }
    }
    return result;
}
