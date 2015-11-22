#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<unistd.h>
int main(){
    pid_t pid;
    int status;
    for(int i=0;i<4;++i){
        printf("This process PID:%d,PPID:%d\n",(int)getpid(),(int)getppid());
        pid=fork();
        if(pid){
            pid=wait(&status);
            return 0;
        }
    }
    return 0;
}
