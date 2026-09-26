package com.converge.filapilha;

public class Player {
    String name;
    double score;

    private static int counter = 1;
    public Player() {
        this.name = "Player " + counter++;
        this.score = Math.floor(Math.random() * 100);
    }

    @Override
    public String toString() {
        return name + " " + score + "\n";
    }
}
