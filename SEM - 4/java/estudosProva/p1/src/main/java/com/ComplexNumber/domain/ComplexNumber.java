package com.ComplexNumber.domain;

public class ComplexNumber {
    public int a;
    public int b;

    public ComplexNumber mais(ComplexNumber nc){
        return new ComplexNumber(this.a + nc.a, this.b + nc.b);
    }

    public ComplexNumber menos(ComplexNumber nc){
        return new ComplexNumber(this.a - nc.a, this.b - nc.b);
    }

    // a = a1.a2-b1.b2 e b = a1.b2+a2.b1;
    public ComplexNumber vezes(ComplexNumber nc){
        return new ComplexNumber(
                this.a * nc.a - this.b * nc.b,
                this.a * nc.b + nc.a * this.b
        );
    }
    // a = (a1.a2+b1.b2)/(a2^2+b2^2) e b = (a2.b1-a1.b2)/ (a2^2+b2^2);
    public ComplexNumber dividido(ComplexNumber nc){
        int a =(int) (
                (this.a * nc.a + this.b * nc.b) /
                        (Math.pow(nc.a, 2) + Math.pow(nc.b, 2))
        );

        int b = (int) (
                (nc.a * this.b - this.a * nc.b) /
                        ( Math.pow(nc.a, 2) + Math.pow(nc.b, 2))
                );

        return new ComplexNumber(a, b);
    }

    public ComplexNumber(int a, int b){
        this.a = a;
        this.b = b;
    }
}
