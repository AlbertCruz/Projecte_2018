/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import info.infomila.exceptions.InscripcioException;
import info.infomila.appbolos.models.InscritPK;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 *
 * @author alber
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "inscrit")
@IdClass(InscritPK.class)
public class Inscrit implements Serializable {
    
    @Transient
    private static final long serialVersionUID = 6L;
    
    @Id
    @TableGenerator(name = "gen_inscripcions",
            table = "comptadors",
            pkColumnName = "CLAU",
            valueColumnName = "COMPTADOR",
            pkColumnValue = "inscrit",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_inscripcions")
    private int id;
    
    @Id
    @ManyToOne(optional=false, fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="soci_id", nullable = false)
    private Soci soci;
    
    @Id
    @ManyToOne(optional=false, fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="torneig_id", nullable = false)
    private Torneig torneig;
    
    @Basic(optional = false)
    @Column(name = "data_creacio", nullable = false)
    private Date dataCreacio;
    
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="grup_id")
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
