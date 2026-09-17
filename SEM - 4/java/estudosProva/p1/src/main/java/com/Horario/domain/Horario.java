package com.Horario.domain;

public class Horario implements Comparable<Horario>, Cloneable {

    private byte hora;

    private byte minuto;

    private byte segundo;

    public byte getSegundo() {
        return segundo;
    }

    public byte getMinuto() {
        return minuto;
    }

    public byte getHora() {
        return hora;
    }

    public void setHora(byte hora) {
        if (hora < 0 || hora > 23) throw new IllegalArgumentException("Hora invalida");
        this.hora = hora;
    }

    public void setMinuto(byte minuto) {
        if (minuto < 0 || minuto > 59) throw new IllegalArgumentException("Minuto invalido");
        this.minuto = minuto;
    }

    public void setSegundo(byte segundo) {
        if (segundo < 0 || segundo > 59) throw new IllegalArgumentException("Segundo invalido");
        this.segundo = segundo;
    }

    public void adiante(int segundos){
        int newSegundos = toSeconds() + segundos;
        newSegundos %= 86400; // trazendo do futuro para o presente, estando no futuro

        alterarHorarioBaseadoEmSegundos(newSegundos);

    }

    public void retroceda(int segundos){
        int newSegundos = toSeconds() - segundos;
        int resto = newSegundos % 86400;

        if (resto < 0) resto = -resto;
        newSegundos = resto;

        alterarHorarioBaseadoEmSegundos(newSegundos);

    }

    private int toSeconds() {
        return hora * 3600
                + minuto * 60
                + segundo;
    }

    private void alterarHorarioBaseadoEmSegundos(int newSegundos ){
        this.hora = (byte) (newSegundos / 3600);
        newSegundos %= 3600;

        this.minuto = (byte) (newSegundos / 60);

        this.segundo = (byte) (newSegundos % 60);
    }


    public Horario(byte hora, byte minuto, byte segundo) {
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }

    public Horario(Horario h) {
        setHora(h.getHora());
        setMinuto(h.getMinuto());
        setSegundo(h.getSegundo());

    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o instanceof Horario oh) {
            return oh.getHora() == this.getHora() &&
                    oh.getMinuto() == this.getMinuto() &&
                    oh.getSegundo() == this.getSegundo();
        }

        return false;

    }

    @Override
    public int compareTo(Horario h) {
        return toSeconds() - h.toSeconds(); // esq - dir < 0, então, dir +, senão esq +, se for 0, é igual
    }

    @Override
    public Horario clone() throws CloneNotSupportedException {
        Horario horario = (Horario) super.clone();
        return new Horario(horario);
    }

    @Override
    public String toString() {
        return String.format(
                "%s:%s:%s",
                hora >= 10 ? hora : "0" + hora,
                minuto >= 10 ? minuto : "0" + minuto,
                segundo >= 10 ? segundo : "0" + segundo
        );
    }
}
