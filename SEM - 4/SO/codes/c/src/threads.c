#include <stdio.h>
#include <threads.h>

int thread_function(void *arg) {
    int *id = (int*)arg;
    printf("executando id %d\n", *id);
    return 0;
}

int main(){
    thrd_t thread;
    int id_thread = 2;

    if (thrd_create(&thread, thread_function, &id_thread) != thrd_success ) {
        printf("Erro ao criar thread");
        return 1;
    }

    int status_retorno;
    thrd_join(thread, &status_retorno);

    printf("Thread %d finalizada", id_thread);

    return 0;
}