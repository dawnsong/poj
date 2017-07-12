/*
 * 523test.c
 * Copyright (C) 2017 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2017-07-07 11:43
 * Distributed under terms of the GPLv2 license.
 */

//#include "523test.h"

int main(int argc, char **argv){

    return 0;
}
bool checkSubarraySum(int* nums, int numsSize, int k) {
    int result=0;
    int i,j, cumsum;
    if(k<0)k=k*-1;

    if(k==0) {
/*
[0,0]
0
should return 1, this is a very special case, i.e., marginal condition!
otherwise, as long as k=0, return 0
*/
        result=0;
        for(i=0;i<numsSize;i++){
            cumsum=nums[i];
            if(cumsum!=0) continue;
            for(j=i+1;j<numsSize;j++){
                cumsum=cumsum+nums[j];
                if(0 == cumsum) return 1;
            }
        }
        return result;
    }
    else{
        for(i=0;i<numsSize;i++){
            cumsum=nums[i];
            for(j=i+1;j<numsSize;j++){
                cumsum=cumsum+nums[j];
                if(0 == cumsum%k) return 1;
            }
        }
    }
    return result;

}
