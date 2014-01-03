/*
 * qsort.ex.cpp
 * Copyright (C) 2014 Xiaowei Song <dawnwei.song@gmail.com>
 * Version: 2014-01-03 10:55
 * Distributed under terms of the GPLv2 license.
 */

#include <stdio.h>
#include <stdlib.h>

int values[]={40, 10, 100, 90, 20, 25};
int compareValues(const void * a, const void * b){
    return (*(int *)a - *(int *)b);
    //return (int(*a)-int(*b));
}

typedef struct {
    char str[10];
    double value;
}SGENE;
SGENE x[6];
int compareGeneValues(const void * a, const void * b){
    SGENE *ga=(SGENE *)a;
    SGENE *gb=(SGENE *)b;
    return gb->value - ga->value;
}


int main(int argc, char **argv){
    int n;
    qsort(values, 6, sizeof(int), compareValues);
    for(n=0;n<6;n++){
        printf("%d ", values[n]);
    }
    printf("\n");

    for(n=0;n<6;n++){
        x[n].value=6-n;
    }
    qsort(x, 6, sizeof(SGENE), compareGeneValues);
    for(n=0;n<6;n++){
        printf("%f ", x[n].value);
    }
    printf("\n");

    return 0;
}

