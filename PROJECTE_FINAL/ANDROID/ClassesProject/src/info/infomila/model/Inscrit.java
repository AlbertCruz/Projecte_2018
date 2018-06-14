/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import info.infomila.exceptions.InscripcioException;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author alber
 */
public class Inscrit implements Serializable {

    private static final long serialVersionUID = 6L;
    private int id;
    private Soci soci;
    private Torneig torneig;
    private Date dataCreacio;
    private Grup grup;

    public Inscrit(Soci soci, Torneig torneig) {
        id = 0;
        setSoci(soci);
        setTorneig(torneig);
        setDataCreacio(new Date());
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Soci getSoci() {
        return soci;
    }

    public boolean setSoci(Soci soci) {
        if (soci==null) {
            throw new InscripcioException("El soci de la inscripció no pot ser null");
        }
        if (this.soci==null || (this.soci != null && !this.soci.equals(soci))) {
            this.soci = soci;
            return true;
        }
        return false;
    }

    public Torneig getTorneig() {
        return torneig;
    }

    public void setTorneig(Torneig torneig) {
        if (torneig==null) {
            throw new InscripcioException("El torneig de la inscripció no pot ser null");
        }
        this.torneig = torneig;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    protected void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public Grup getGrup() {
        return grup;
    }

    protected void setGrup(Grup grup) {
        this.grup = grup;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.soci);
        hash = 89 * hash + Objects.hashCode(this.torneig);
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
        final Inscrit other = (Inscrit) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.soci, other.soci)) {
            return false;
        }
        if (!Objects.equals(this.torneig, other.torneig)) {
            return false;
        }
        return true;
    }
    
}
