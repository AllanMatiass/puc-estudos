#include <stdio.h>
#include <stdlib.h>

typedef struct Grafo {
    int arestanula;
    int maxvertices;
    int n;
    int *v;
    int **M;
} Grafo;

int contarGrau(Grafo *g, int vertice) {
    int grau = 0;
    for (int i = 0; i < g->n; i++) {
        // M[linha][coluna]
        if (g->M[vertice][i] != g->arestanula) {
            grau++;
        }
    }
    return grau;
}

void dfsAux(Grafo g, int verticeAtual, int * visitados, int target){
    if(visitados[verticeAtual]) return;
    visitados[verticeAtual] = 1;

    for (int i = 0; i < g.n; i++){
        for (int j = 0; j < g.n; j++){
            int curr = g.M[i][j];
            if (
                curr != g.arestanula &&
                curr != target
            ){
                dfsAux(g, curr, visitados, target);
            }
        }
    }
}

void dfs (Grafo g, int target){
    int *visitados = calloc(g.n, sizeof(int));
    dfsAux(g, 0, visitados, target);

    for (int i = 0; i < sizeof(g.n); i++){
        printf("%d", visitados[i]);
    }

}


int main() {

    Grafo g = {-1, 7, 0, NULL, NULL};

    g.v = malloc(g.maxvertices * sizeof(int));

    g.M = malloc(g.maxvertices * sizeof(int*));

    for (int i = 0; i < g.maxvertices; i++) {
        g.M[i] = malloc(g.maxvertices * sizeof(int));

        for (int j = 0; j < g.maxvertices; j++) {
            g.M[i][j] = 0;
        }
    }

    // vértices
    for (int i = 0; i < g.maxvertices; i++) {
        g.v[g.n++] = i;
    }

    // arestas
    g.M[0][1] = 1; // A -> B

    g.M[0][2] = 1; // A -> C

    g.M[1][3] = 1; // B -> D

    g.M[1][4] = 1; // B -> E

    g.M[3][4] = 1; // D -> E

    g.M[2][5] = 1; // C -> F

    g.M[4][6] = 1; // E -> G

    g.M[5][6] = 1; // F -> G

    printf("Grafo inicializado.");

    printf("Iniciando DFS a partir do vértice 0.\n");

    dfs(g, 6);

    return 0;
}
