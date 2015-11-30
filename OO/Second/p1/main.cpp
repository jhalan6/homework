#include <stdio.h>
#include "Rational.h" // Rational定义在该头文件中
int main () {
    Rational num1(1,3), num2(3,1), num3(2,6), result;
    result=num1+num2;
    printf("%s+%s=%s\n",num1.value(),num2.value(),result.value());
    result=num1*num2;
    printf("%s*%s=%s\n",num1.value(),num2.value(),result.value());
    printf("%s is%s equal to %s\n",num1.value(),(num1 == num2)?"":"n't",num2.value());
    printf("%s is%s equal to %s\n",num1.value(),(num1 == num3)?"":"n't",num3.value());
    return 0;
}
