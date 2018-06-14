package info.infomila.appbolos.models;

import java.io.Serializable;

/**
 * Created by alber
 */

public class Classificacio implements Serializable {

    private static final long serialVersionUID = 9L;

    private String soci;
    private int pJugades;
    private int pGuanyades;
    private int pPerdudes;
    private double coeficientBase;

    public Classificacio(String soci, int pJugades, int pGuanyades, int pPerdudes, double coeficientBase) {
        this.soci = soci;
        this.pJugades = pJugades;
        this.pGuanyades = pGuanyades;
        this.pPerdudes = pPerdudes;
        this.coeficientBase = coeficientBase;
    }

    public String getSoci() {
        return soci;
    }

    public void setSoci(String soci) {
        this.soci = soci;
    }

    public int getpJugades() {
        return pJugades;
    }

    public void setpJugades(int pJugades) {
        this.pJugades = pJugades;
    }

    public int getpGuanyades() {
        return pGuanyades;
    }

    public void setpGuanyades(int pGuanyades) {
        this.pGuanyades = pGuanyades;
    }

    public int getpPerdudes() {
        return pPerdudes;
    }

    public void setpPerdudes(int pPerdudes) {
        this.pPerdudes = pPerdudes;
    }

    public double getCoeficientBase() {
        return coeficientBase;
    }

    public void setCoeficientBase(double coeficientBase) {
        this.coeficientBase = coeficientBase;
    }

}