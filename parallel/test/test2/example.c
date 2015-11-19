#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#define exit _exit
void helloworld1() { //线程体代码
    printf("hello world\n");
}
int main() {
    pthread_t t; //创建一个新的线程,参数传递 NULL
    pthread_create(&t,NULL,(void*)helloworld1,NULL); 
 //   pthread_create(&t,NULL,(void*(*)(void*))helloworld1,NULL); 
    pthread_join(t,NULL); //等待线程执行完毕
    exit(0);
}
