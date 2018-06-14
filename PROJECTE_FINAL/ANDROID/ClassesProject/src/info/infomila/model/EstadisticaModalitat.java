/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author alber
 */

public class EstadisticaModalitat implements Serializable {  

    private static final long serialVersionUID = 3L;
    private Soci soci;
    private Modalitat modalitat;
    private double coeficientBase;  
    private int totalCarambolasTemporadaActual;
    private int totalEntradesTemporadaActual;        
    
    protected EstadisticaModalitat() {}
    
    public EstadisticaModalitat(Soci soci, Modalitat modalitat, double coeficientBase, int carambolesTemporadaActual, int entradesTemporadaActual)
    {
        setCoeficientBase(coeficientBase);
        setTotalCarambolasTemporadaActual(carambolesTemporadaActual);
        setTotalEntradesTemporadaActual(entradesTemporadaActual);
    }
    
    public EstadisticaModalitat(EstadisticaModalitat em)
    {
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
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.soci);
        hash = 89 * hash + Objects.hashCode(this.modalitat);
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
        if (!Objects.equals(this.soci, other.soci)) {
            return false;
        }
        if (!Objects.equals(this.modalitat, other.modalitat)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EstadisticaModalitat{" + "soci=" + soci + ", modalitat=" + modalitat + ", coeficientBase=" + coeficientBase + ", carambolesTemporadaActual=" + totalCarambolasTemporadaActual + ", entradesTemporadaActual=" + totalEntradesTemporadaActual + '}';
    }
}
