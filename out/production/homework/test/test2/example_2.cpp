#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#define exit _exit
int helloworld2(char* name){
    printf("hello world, %s\n", name);
    return 0;
}
int main() {
    pthread_t t;
    char s[]="liuhui" ;
    pthread_create(&t, NULL,(void *(*)(void *))helloworld2, s);
    pthread_join(t, NULL);
    exit (0);
}
