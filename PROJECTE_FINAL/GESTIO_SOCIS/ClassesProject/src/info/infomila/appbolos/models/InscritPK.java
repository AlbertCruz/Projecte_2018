/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author alber
 */
public class InscritPK implements Serializable
{
    private static final long serialVersionUID = 8L;
    
    private int id;
    private Soci soci;
    private Torneig torneig;
    
    protected InscritPK() {}

    public InscritPK(int id, Soci soci, Torneig torneig) {
        setId(id);
        setSoci(soci);
        setTorneig(torneig);
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

    public void setSoci(Soci soci) {
        this.soci = soci;
    }

    public Torneig getTorneig() {
        return torneig;
    }

    public void setTorneig(Torneig torneig) {
        this.torneig = torneig;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
        hash = 11 * hash + Objects.hashCode(this.soci);
        hash = 11 * hash + Objects.hashCode(this.torneig);
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
        final InscritPK other = (InscritPK) obj;
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
