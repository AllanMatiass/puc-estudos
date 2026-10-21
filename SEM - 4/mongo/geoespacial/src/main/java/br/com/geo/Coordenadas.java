package br.com.geo;

public record Coordenadas(
        double lat,
        double lon
) {
    public Coordenadas(Ponto p){
        this(p.getLatitude(), p.getLongitude());
    }
}