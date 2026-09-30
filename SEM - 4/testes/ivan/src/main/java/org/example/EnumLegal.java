package org.example;

public enum EnumLegal {

    CACHORRO("doguinho"),
    PAPAGAIO("papagaio"),
    GATO("gato"),
    PATO("pato"),
    PARAGUAIO("paraguaio");

    private final String value;

    // value = o que vai ser armaazenado no banco

    EnumLegal(String value){
        this.value = value;
    }
}
