/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author alber
 */
@Embeddable
public class EstadisticaModalitatPK implements Serializable
{
    @Transient
    private static final long serialVersionUID = 8L;
    
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="soci_id", nullable = false)
    private Soci soci;
    
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="modalitat_id", nullable = false)
    private Modalitat modalitat;
    
    protected EstadisticaModalitatPK() { }

    public EstadisticaModalitatPK(Soci soci, Modalitat modalitat) {
        setSoci(soci);
        setModalitat(modalitat);
    }
    
    public EstadisticaModalitatPK(EstadisticaModalitatPK emPK)
    {
        modalitat = new Modalitat(emPK.modalitat);
    }
    
    public Soci getSoci()
    {
        return soci;
    }

    protected final void setSoci(Soci soci)
    {
        if (soci != null) 
        {
            this.soci = soci;
        }
    }

    public Modalitat getModalitat()
    {
        return modalitat;
    }

    protected final void setModalitat(Modalitat modalitat)
    {
        if (modalitat != null)
        {
            this.modalitat = modalitat;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.soci);
        hash = 61 * hash + Objects.hashCode(this.modalitat);
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
        final EstadisticaModalitatPK other = (EstadisticaModalitatPK) obj;
        if (!Objects.equals(this.soci, other.soci)) {
            return false;
        }
        return Objects.equals(this.modalitat, other.modalitat);
    }
    
    
    
}
