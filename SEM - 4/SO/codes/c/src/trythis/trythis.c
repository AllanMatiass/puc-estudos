#include <stdio.h>
#include <pthread.h>


pthread_t tid[2];
pthread_mutex_t mutex;

int counter = 0;

void* tryThis(void *arg) {
    pthread_mutex_lock(&mutex);

    counter++;
    printf("thread %d started\n", counter);
    printf("thread %d finished\n", counter);
    pthread_mutex_unlock(&mutex);
    return NULL;
}


int main() {
    pthread_mutex_init(&mutex, NULL);
    pthread_create(&tid[0], NULL, tryThis, NULL);
    pthread_create(&tid[1], NULL, tryThis, NULL);

    pthread_join(tid[0], NULL);
    pthread_join(tid[1], NULL);

    pthread_mutex_destroy(&mutex);



    printf("finalizado todas as threads\n");
    return 0;
}