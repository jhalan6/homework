#include<stdio.h>
#include"AType.hpp"
int main() {
    AType<int> i1(10),i2;
    AType<double> d1(1.5), d2(1), d3(0.5);
    printf("i1+i2=%d\n",i1+i2);
    printf("d1-d2=%0.1f\n",d1-d2);
    printf("i1>i1=%d\n",i1>i1);
    printf("d1>d2=%d\n",d1>d2); // d1>d2=1
    printf("d3>d2=%d\n",d3>d2); // d3>d2=-1
    return 0;
}
