/*
 * 167test.c
 * Copyright (C) 2017 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2017-07-07 11:11
 * Distributed under terms of the GPLv2 license.
 */

//#include "167test.h"

int main(int argc, char **argv){

    return 0;
}
/**
 * Return an array of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* twoSum(int* numbers, int numbersSize, int target, int* returnSize) {
    *returnSize = 2;
    int *result=malloc( *returnSize * sizeof(int));
    int i,j;
    for(i=0;i<numbersSize-1;i++){
        result[0]=i+1;
        for(j=i+1;j<numbersSize;j++){
            result[1]=j+1;
            if(target == (numbers[i]+numbers[j]) )
                return result;
        }
    }
    return result;
}
