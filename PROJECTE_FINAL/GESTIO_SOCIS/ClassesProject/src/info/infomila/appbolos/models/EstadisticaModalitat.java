/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import info.infomila.model.EstadisticaModalitatException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "estadistica_modalitat")
public class EstadisticaModalitat implements Serializable {
    
    @Transient
    private static final long serialVersionUID = 3L;
    
    @EmbeddedId
    private EstadisticaModalitatPK emPK;        
        
    @Column(name = "coeficient_base")
    private double coeficientBase;  
    
    @Column(name = "total_caramboles_temp_actual", nullable = true)
    private int totalCarambolasTemporadaActual;
    
    @Column(name = "total_entrades_temp_actual", nullable = true)
    private int totalEntradesTemporadaActual;        
    
    protected EstadisticaModalitat() {}
    
    public EstadisticaModalitat(Soci soci, Modalitat modalitat, double coeficientBase, int carambolesTemporadaActual, int entradesTemporadaActual)
    {
        emPK = new EstadisticaModalitatPK(soci, modalitat);
        setCoeficientBase(coeficientBase);
        setTotalCarambolasTemporadaActual(carambolesTemporadaActual);
        setTotalEntradesTemporadaActual(entradesTemporadaActual);
    }
    
    public EstadisticaModalitat(EstadisticaModalitat em)
    {
        emPK = new EstadisticaModalitatPK(em.emPK);
        coeficientBase = em.coeficientBase;
        totalCarambolasTemporadaActual = em.totalCarambolasTemporadaActual;
        totalEntradesTemporadaActual = em.totalEntradesTemporadaActual;
    }    
    
     public double getCoeficientBase() {
        return coeficientBase;
    }

    public void setCoeficientBase(double coeficientBase) {
        if (coeficientBase<0 || coeficientBase>1) {
            throw new EstadisticaModalitatException("El coeficient de la estadística no pot ser superior de 1 ni inferior de 0");
        }
        this.coeficientBase = coeficientBase;
    }

    public int getTotalCarambolasTemporadaActual() {
        return totalCarambolasTemporadaActual;
    }

    public void setTotalCarambolasTemporadaActual(int totalCarambolasTemporadaActual) {
        this.totalCarambolasTemporadaActual = totalCarambolasTemporadaActual;
    }

    public int getTotalEntradesTemporadaActual() {
        return totalEntradesTemporadaActual;
    }

    public void setTotalEntradesTemporadaActual(int totalEntradesTemporadaActual) {
        this.totalEntradesTemporadaActual = totalEntradesTemporadaActual;
    }       

    public Soci getSoci()
    {
        return emPK.getSoci();
    }

    protected final void setSoci(Soci soci)
    {
        emPK.setSoci(soci);
    }

    public Modalitat getModalitat()
    {
        return emPK.getModalitat();
    }

    protected final void setModalitat(Modalitat modalitat)
    {
        emPK.setModalitat(modalitat);
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.emPK);
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
        final EstadisticaModalitat other = (EstadisticaModalitat) obj;
        return Objects.equals(this.emPK, other.emPK);
    }

    @Override
    public String toString() {
        return emPK.getModalitat().getDescripcio();
    }
}
