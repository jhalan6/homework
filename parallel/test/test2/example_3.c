#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
int helloworld3(int * no) {
    printf("hello world, %d\n", *no);
    return 0;
}
int main() {
    pthread_t t;
    int no = 9;
    pthread_create(&t, NULL, (void*)helloworld3, &no);
    pthread_join(t, NULL);
    exit (0);
}
