package org.example.domain;

public class Data implements Cloneable, Comparable<Data>
{
    private byte  dia, mes;
    private short ano;

    private static int qtd=0;

    public static int getQtd ()
    {
        return Data.qtd;
    }

    public static boolean isBissexto (short ano)
    {
        // Calendario Juliano
        if (ano<1582)  return ano % 4 == 0;

        // Calendario Gregoriano
        if (ano%400==0) return true;

        return ano % 4 == 0 && ano % 100 != 0;
    }

    public static boolean isValida (byte dia, byte mes, short ano)
    {
        if (ano<-45) return false; // antes do Calendario Juliano
        if (ano== 0) return false; // nao existiu ano 0; do ano 1ac foi direto para o ano 1dc
        if (ano==1582 && mes==10 && dia>=5 && dia<=14) return false; // dias cortados dos calendario pelo Papa Gregorio

        if (dia<1 || dia>31 || mes<1 || mes>12) return false;

        if (dia>30 && (mes==4 || mes==6 || mes==9 || mes==11)) return false;
        if (dia>29 && mes==2) return false;
        if (dia>28 && mes==2 && !Data.isBissexto(ano)) return false;

        return true;
    }

    public /*void*/ Data (byte dia, byte mes, short ano) throws Exception
    {
        if (!Data.isValida(dia,mes,ano))
            throw new Exception ("Data invalida");

        this.dia=dia;
        this.mes=mes;
        this.ano=ano;

        Data.qtd++;
    }

    public void setDia (byte dia) throws Exception
    {
        if (!Data.isValida(dia,this.mes,this.ano))
            throw new Exception ("Dia invalido");

        this.dia=dia;
    }

    public byte getDia ()
    {
        return this.dia;
    }

    public void setMes (byte mes) throws Exception
    {
        if (!Data.isValida(this.dia,mes,this.ano))
            throw new Exception ("Mes invalido");

        this.mes=mes;
    }

    public byte getMes ()
    {
        return this.mes;
    }

    public void setAno (short ano) throws Exception
    {
        if (!Data.isValida(this.dia,this.mes,ano))
            throw new Exception ("Ano invalido");

        this.ano=ano;
    }

    public short getAno ()
    {
        return this.ano;
    }

    public void avanceUmDia () // altera o this
    {
        if (this.ano==1582 && this.mes==10 && this.dia==4)
        {
            this.dia=(byte)15;
        }
        else if (this.dia==31 && this.mes==12 && this.ano==-1)
        {
            this.dia=(byte)1;
            this.mes=(byte)1;
            this.ano=(short)1;
        }
        else if (Data.isValida((byte)(this.dia+1),this.mes,this.ano))
            this.dia++;
        else if (Data.isValida((byte)1,(byte)(this.mes+1),this.ano))
        {
            this.dia=(byte)1;
            this.mes++;
        }
        else // só sobrou estar no dia 31/12 de um ano que não é -1
        {
            this.dia=(byte)1;
            this.mes=(byte)1;
            this.ano++;
        }
    }

    public void avanceVariosDias (int qtd) throws Exception // altera o this
    {
        if (qtd<=0) throw new Exception ("Quantidade invalida");
        for (int i=0; i<qtd; i++) this.avanceUmDia();
    }

    public Data getDiaSeguinte () // não altera o this
    {
        Data retorno=null;
        try
        {
            retorno = new Data (this.dia,this.mes,this.ano);
        }
        catch (Exception erro)
        {} // sei que nao vai dar erro pq criei a nova data a partir duma velha que, quando foi criada, ja foi validada
        retorno.avanceUmDia();
        return retorno;
    }

    public Data getVariosDiasAdiante (int qtd) throws Exception // não altera o this
    {
        Data retorno=null;
        try
        {
            retorno = new Data (this.dia,this.mes,this.ano);
        }
        catch (Exception erro)
        {} // sei que nao vai dar erro pq criei a nova data a partir duma velha que, quando foi criada, ja foi validada
        retorno.avanceVariosDias(qtd);
        return retorno;
    }

    public void retrocedaUmDia () // altera o this
    {
        if (this.ano==1582 && this.mes==10 && this.dia==15)
        {
            this.dia=(byte)4;
        }
        else if (this.dia==1 && this.mes==1 && this.ano==1)
        {
            this.dia=(byte)31;
            this.mes=(byte)12;
            this.ano=(short)-1;
        }
        else if (Data.isValida((byte)(this.dia-1),this.mes,this.ano))
            this.dia--;
        else if (Data.isValida((byte)31,(byte)(this.mes-1),this.ano))
        {
            this.dia=(byte)31;
            this.mes--;
        }
        else if (Data.isValida((byte)30,(byte)(this.mes-1),this.ano))
        {
            this.dia=(byte)30;
            this.mes--;
        }
        else if (Data.isValida((byte)29,(byte)(this.mes-1),this.ano))
        {
            this.dia=(byte)29;
            this.mes--;
        }
        else if (Data.isValida((byte)28,(byte)(this.mes-1),this.ano))
        {
            this.dia=(byte)28;
            this.mes--;
        }
        else // só sobrou estar no dia 1/1 de um ano que não é 1
        {
            this.dia=(byte)31;
            this.mes=(byte)12;
            this.ano--;
        }
    }

    public void retrocedaVariosDias (int qtd) throws Exception // altera o this
    {
        if (qtd<=0) throw new Exception ("Quantidade invalida");
        for (int i=0; i<qtd; i++) this.retrocedaUmDia();
    }

    public Data getDiaAnterior () // não altera o this
    {

        Data retorno=null;
        try
        {
            retorno = new Data (this.dia,this.mes,this.ano);
        }
        catch (Exception ignored)
        {} // sei que nao vai dar erro pq criei a nova data a partir duma velha que, quando foi criada, ja foi validada
        retorno.retrocedaUmDia();
        return retorno;
    }

    public Data getVariosDiasAtras (int qtd) throws Exception // não altera o this
    {
        Data retorno = new Data(this.dia, this.mes, this.ano);

        retorno.retrocedaVariosDias(qtd);

        return retorno;
    }

    @Override
    public String toString ()
    {
        return (this.dia<10?"0":"")+ //0
                this.dia + "/" + //0X/ (se for menor que 10)
                (this.mes<10?"0":"")+ // 0x/0 se for menor que 10
                this.mes + "/" +
                (this.ano<0 ? (-this.ano) + "ac" : this.ano);
    }

    @Override
    public int hashCode(){
        int ret = 2;

        ret = 7 * ret + Byte.valueOf(this.mes).hashCode();
        ret = 7 * ret + Short.valueOf(this.ano).hashCode();
        ret = 7 * ret + Byte.valueOf(this.dia).hashCode();

        if (ret < 0) ret = -ret;

        return ret;

    }

    @Override
    public Data clone() {
        try {
            Data clone = (Data) super.clone();
            clone.ano = ano;
            clone.mes = mes;
            clone.dia = dia;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(Data o) {
        return this.toDias() - o.toDias();
    }

    public int toDias()
    {
        int dias = 0;

        for (short ano = -45; ano < this.ano; ano++)
        {
            dias += Data.isBissexto(ano) ? 366 : 365;
        }

        for (byte mes = 1; mes < this.mes; mes++)
        {
            if (mes == 2)
                dias += Data.isBissexto(this.ano) ? 29 : 28;
            else if (mes == 4 || mes == 6 || mes == 9 || mes == 11)
                dias += 30;
            else
                dias += 31;
        }

        dias += this.dia - 1;

        // Dias removidos na mudança do calendário Gregoriano
        if (this.ano > 1582 || (this.ano == 1582 && this.mes > 10) ||
                (this.ano == 1582 && this.mes == 10 && this.dia >= 15))
            dias -= 10;

        return dias;
    }
}



