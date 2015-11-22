#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
int helloworld1(){ //线程体代码 
    printf("hello world\n"); 
    return 0;
}
int main() {
    pthread_t t; //创建一个新的线程,参数传递 NULL
    pthread_create(&t, NULL, (void*)helloworld1, NULL); 
    pthread_join(t, NULL); //等待线程执行完毕
    exit(0);
}
