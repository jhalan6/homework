#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
int main(void) {
    int i;
    int n = 4;
    pid_t childpid;
    for (i=1; i<n; i++)
        if ((childpid = fork()) == 0) break;
    // 子进程
    if (childpid > 0) sleep(1);
    //让父进程阻塞 1 秒
    fprintf(stderr, "This is process %ld with parent %ld\n", (long)getpid(), (long)getppid());
    return 0;
}
