package br.com.geo;

public class Ponto {
    private final int id;
    private final String nome;
    private final Double latitude;
    private final Double longitude;

    public Ponto(int id, String nome, double latitude, double longitude) {
        this.id = id;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return String.format("[ID %d] %s (Lat: %.6f, Lon: %.6f)", id, nome, latitude, longitude);
    }
}
