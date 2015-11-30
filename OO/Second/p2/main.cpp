#include"source.h"
int main(){
    char s[]="obj1";
    Sub1 s1(15,s);
    Sub2 s2(2015,11);
    s1.printOn();
    // 此时显示: <obj1’s body is 15>
    s2.printOn();
    // 此时显示: <11 and 2015>
    return 0;
}
