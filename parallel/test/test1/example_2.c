#include <sys/types.h> 
#include <sys/wait.h> 
#include <unistd.h> 
#include <stdio.h> 
#include <errno.h>
#include <stdlib.h>
int main(int argc, char *argv[]) {
    pid_t childpid;
    int status;
    if ((childpid = fork()) == -1) { //fork 出错
        perror("The fork failed");
        exit(-1); 
    }
    else if (childpid == 0) { //子进程
//        if (execl("./hello","hello",NULL) < 0) { //子进程执行另一个程序 hello 
        if (execl("/bin/ls","ls","-lh",NULL) < 0) { //子进程执行另一个程序 hello 
            perror("The exec of command failed");
            exit(-1);
        }
    }
    else if(childpid>0) { //父进程
        pid_t apid = wait(&status); //父进程调用 wait 等待子进程 
        printf("parent process has wait the %d child process exit\n",apid); 
        exit(0);
    } 
}
