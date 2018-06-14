package info.infomila.appbolos.models;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import info.infomila.appbolos.models.exceptions.InscritException;

public class Inscrit implements Serializable {

    private static final long serialVersionUID = 6L;

    private int id;
    private Date dataCreacio;
    private Soci soci;
    private Torneig torneig;
    private Grup grup;

    public Inscrit(Date dataCreacio, Soci soci, Torneig torneig) {
        setDataCreacio(new Date());
        setSoci(soci);
        setTorneig(torneig);
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    protected void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public Soci getSoci() {
        return soci;
    }

    public void setSoci(Soci soci) {
        if (soci==null) {
            throw new InscritException("El soci de la inscripció no pot ser null");
        }
        if (!this.soci.equals(soci)) {
            this.soci = soci;
            soci.addInscripcio(this);
        }
    }

    public Torneig getTorneig() {
        return torneig;
    }

    public void setTorneig(Torneig torneig) {
        if (torneig==null) {
            throw new InscritException("El torneig de la inscripció no pot ser null");
        }
        this.torneig = torneig;
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
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.soci);
        hash = 17 * hash + Objects.hashCode(this.torneig);
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
