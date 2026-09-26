package com.converge.filapilha;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    private static Queue<Player> players = new LinkedList<>();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            players.add(new Player());
        }

        System.out.printf("ANTES: %s\n ", players);
        System.out.println("-------------------");
        order();
        System.out.printf("DEPOIS: %s\n", players);

    }

    private static void order (){
        LinkedList<Player> list = new LinkedList<>(players);
        boolean switched;

        do {
            switched = false;
            for (int i = 0; i < players.size() - 1; i++) {
                if (list.get(i).score > list.get(i + 1).score) {
                    Player aux = list.get(i);

                    list.set(i, list.get(i + 1));
                    list.set(i + 1, aux);
                    switched = true;
                    break;
                }
            }
        } while (switched);

        players = list;
    }
}
