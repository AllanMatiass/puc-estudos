public class Arvore <T extends Comparable<T>> {
    private class Node{
        Node esq;
        T info;
        Node dir;

        // getters
    }

    Node raiz;

    public boolean has(T i){
        Node curr = this.raiz;

        while (curr != null){
            int cmp = i.compareTo(curr.info);

            if (cmp == 0) return true;

            if (cmp < 0) curr = curr.esq;
            if (cmp > 0) curr = curr.dir;
        }

        return false;

    }

}