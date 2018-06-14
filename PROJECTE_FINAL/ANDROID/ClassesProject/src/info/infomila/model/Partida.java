/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import info.infomila.exceptions.PartidaException;
import info.infomila.model.Grup;
import info.infomila.model.Soci;
import info.infomila.model.Torneig;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        this.sociA = new Soci(partida.sociA);
        this.sociB = new Soci(partida.sociB);
        this.torneig = new Torneig(partida.torneig);
        this.grup = new Grup(partida.grup);
        this.carambolesA = partida.carambolesA;
        this.carambolesB = partida.carambolesB;
        this.entradesA = partida.entradesA;
        this.entradesB = partida.entradesB;
        this.dataPartida = partida.dataPartida;
        this.entradesTotals = partida.entradesTotals;
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
        PENDENT, JUGADA
    }
    
    public enum ModeVictoria {
        PER_CARAMBOLES, ENTRADES_ASSOLIDES, ABANDONAMENT
    }
    
    public enum Guanyador {
        A, B
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Partida other = (Partida) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        String data = (dataPartida!=null) ? sdf.format(dataPartida) : "";
        return "Partida{" + "id=" + id + ", sociA=" + sociA.getNif() + ", sociB=" + sociB.getNif() + ", torneig=" + torneig.getNom() + ", grup=" + grup.getDescripcio() + ", carambolesA=" + carambolesA + ", carambolesB=" + carambolesB + ", entradesA=" + entradesA + ", entradesB=" + entradesB + ", dataPartida=" + data + ", entradesTotals=" + entradesTotals + ", estatPartida=" + estatPartida + ", modeVictoria=" + modeVictoria + ", guanyador=" + guanyador + '}';
    }
    
    
    
}
