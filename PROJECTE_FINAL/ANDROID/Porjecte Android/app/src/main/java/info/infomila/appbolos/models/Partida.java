/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import info.infomila.appbolos.models.exceptions.PartidaException;

/**
 *
 * @author alber
 */
public class Partida implements Serializable {
    
    private static final long serialVersionUID = 7L;
    
    private int id;
    private Soci sociA;
    private Soci sociB;
    private Torneig torneig;
    private Grup grup;
    private int carambolesA;
    private int carambolesB;
    private int entradesA;
    private int entradesB;
    private Date dataPartida;
    private int entradesTotals;
    private EstatPartida estatPartida;
    private ModeVictoria modeVictoria;
    private Guanyador guanyador;

    protected Partida () {}

    public Partida(Partida partida) {
        this.id = partida.id;
        //this.sociA = partida.sociA;
        //this.sociB = partida.sociB;
        //this.torneig = partida.torneig;
        //this.grup = partida.grup;
        this.carambolesA = partida.carambolesA;
        this.carambolesB = partida.carambolesB;
        this.entradesA = partida.entradesA;
        this.entradesB = partida.entradesB;
        this.entradesTotals = partida.entradesTotals;
        this.dataPartida = partida.dataPartida;
        this.estatPartida = partida.estatPartida;
        this.modeVictoria = partida.modeVictoria;
        this.guanyador = partida.guanyador;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Soci getSociA() {
        return sociA;
    }

    protected void setSociA(Soci sociA) {
        this.sociA = sociA;
    }

    public Soci getSociB() {
        return sociB;
    }

    protected void setSociB(Soci sociB) {
        this.sociB = sociB;
    }

    public Torneig getTorneig() {
        return torneig;
    }

    protected void setTorneig(Torneig torneig) {
        this.torneig = torneig;
    }

    public Grup getGrup() {
        return grup;
    }

    protected void setGrup(Grup grup) {
        this.grup = grup;
    }

    public int getCarambolesA() {
        return carambolesA;
    }

    public void setCarambolesA(int carambolesA) {
        if (carambolesA<0) {
            throw new PartidaException("Les caramboles fetes per un soci han de ser un número estrictament positiu");
        }
        this.carambolesA = carambolesA;
    }

    public int getCarambolesB() {
        return carambolesB;
    }

    public void setCarambolesB(int carambolesB) {
        if (carambolesB<0) {
            throw new PartidaException("Les caramboles fetes per un soci han de ser un número estrictament positiu");
        }
        this.carambolesB = carambolesB;
    }

    public int getEntradesA() {
        return entradesA;
    }

    public void setEntradesA(int entradesA) {
        if (entradesA<0) {
            throw new PartidaException("Les entrades fetes per un soci han de ser un número estrictament positiu");
        }
        this.entradesA = entradesA;
    }

    public int getEntradesB() {
        return entradesB;
    }

    public void setEntradesB(int entradesB) {
        if (entradesB<0) {
            throw new PartidaException("Les entrades fetes per un soci han de ser un número estrictament positiu");
        }
        this.entradesB = entradesB;
    }

    public Date getDataPartida() {
        return dataPartida;
    }

    protected void setDataPartida(Date dataPartida) {
        this.dataPartida = dataPartida;
    }

    public int getEntradesTotals() {
        return entradesTotals;
    }

    public void setEntradesTotals(int entradesTotals) {
        if (entradesTotals<0) {
            throw new PartidaException("Les entrades totals han de ser un número estrictament positiu");
        }
        this.entradesTotals = entradesTotals;
    }

    public EstatPartida getEstatPartida() {
        return estatPartida;
    }

    public void setEstatPartida(EstatPartida estatPartida) {
        this.estatPartida = estatPartida;
    }

    public ModeVictoria getModeVictoria() {
        return modeVictoria;
    }

    public void setModeVictoria(ModeVictoria modeVictoria) {
        this.modeVictoria = modeVictoria;
    }

    public Guanyador getGuanyador() {
        return guanyador;
    }

    public void setGuanyador(Guanyador guanyador) {
        this.guanyador = guanyador;
    }
    
    public enum EstatPartida {
        PENDENT, JUGAT
    }
    
    public enum ModeVictoria {
        PER_CARAMBOLES, ENTRADES_ASSOLIDES, ABANDONAMENT
    }
    
    public enum Guanyador {
        A, B
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partida partida = (Partida) o;
        return id == partida.id;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
