#include "Rational.h"
Rational::Rational(int left,int right){
    l=left;
    r=right;
}
Rational Rational::operator+(const Rational& operand2) const{
    if(operand2.r==r){
        return Rational(operand2.l+l,r);
    }else{
        return Rational(l*operand2.r+operand2.l*r,r*operand2.r);
    }
}
Rational Rational::operator*(const Rational& operand2) const{
    return Rational(l*operand2.l,r*operand2.r);
}
Rational &Rational::operator=(const Rational& operand2){
    l=operand2.l;
    r=operand2.r;
    return *this;
}

bool Rational::operator==(const Rational& operand2) const{
    return (l==operand2.l&&r==operand2.r)||(l/operand2.l==r/operand2.r);
}
char* Rational::value(){
    int temp=0;
    int R=r,L=l;
    char stack[20];
    int stackIndex=0;
    while(L){
        stack[stackIndex++]='0'+L%10;
        L/=10;
    }
    while (stackIndex) {
        val[temp++]=stack[--stackIndex];
    }
    stackIndex=0;
    val[temp++]='/';
    while(R){
        stack[stackIndex++]='0'+R%10;
        R/=10;
    }
    while (stackIndex) {
        val[temp++]=stack[--stackIndex];
    }
    stackIndex=0;
    val[temp++]='\0';
    return val;
}
