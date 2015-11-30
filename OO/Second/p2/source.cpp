#include "source.h"
#include<stdio.h>
Sub1::Sub1(int i,char* s):Base(i){
    this->s=s;
}
void Sub1::printOn(){
    printf("%s's body is %d\n",s,iBody);
}
Sub2::Sub2(int i,short s):Base(i){
    this->s=s;
}
void Sub2::printOn(){
    printf("%d and %d\n",this->s,iBody);
}

