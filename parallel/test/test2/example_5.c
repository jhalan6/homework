#include<pthread.h>
#include<stdio.h>
#include<stdlib.h>
int a=1;
void func(int* arg){
    int temp=0;
  //  while(1){
   //     if(temp!=*arg){
     //       temp=*arg;
            printf("传进来的：%d，共享的：%d,线程id：%ld\n",*arg,a,(long)pthread_self());
            pthread_exit(0);
  //      }
  //  }
}
int main(){
    printf("线程id：%ld\n",(long)pthread_self());
    pthread_t t;
    int b=2333;
    pthread_create(&t,NULL,(void*)func,&b);
  //  while(1){
    //    a++;
  //      sleep(1);
    pthread_join(t,NULL);
    while(1){
        b++;
        pthread_create(&t,NULL,(void*)func,&b);
        pthread_join(t,NULL);
    }
    //  }
    exit(0);
}
