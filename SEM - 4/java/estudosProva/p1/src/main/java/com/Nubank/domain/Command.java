package com.Nubank.domain;

import java.util.Optional;

public class Command implements Comparable<Command> {
    public final int value;
    private final int priority;

    public Command(Optional<Integer> value, String priority) {
        this.value = value.orElse(-1);


        this.priority = priority.equals("URGENT") ? 1 : 0;
    }


    @Override
    public int compareTo(Command o) {
        return o.priority - this.priority;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
