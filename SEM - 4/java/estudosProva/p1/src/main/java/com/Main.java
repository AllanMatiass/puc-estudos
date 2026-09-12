package com;

import com.domain.Horario;

public class Main {
    public static void main(String[] args) {
        Horario h = new Horario((byte) 11, (byte) 30, (byte) 22);

        h.adiante(68);
        System.out.println(h);

        h.retroceda(3600 * 24);
        System.out.println(h);
    }
}