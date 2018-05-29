/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author alber
 */
@Entity
@Table(name = "estadistica_modalitat")
public class EstadisticaModalitat implements Serializable {
    
    @Id
    @TableGenerator(name = "comptadors_generator", table = "comptadors",
            pkColumnName = "TAULA", pkColumnValue = "estadistica_modalitat",
            valueColumnName = "COMPTADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "comptadors_generator")
    private int id;
        
    @Column(name = "coeficient_base", precision = 2)
    private BigDecimal coeficientBase;  
    
    @Column(name = "total_carambolas_temporada_actual", nullable = true)
    private int totalCarambolasTemporadaActual;
    
    @Column(name = "total_entrades_temporada_actual", nullable = true)
    private int totalEntradesTemporadaActual;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_soci")
    private Soci soci;   
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_modalitat")
    private Modalitat modalitat;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
     public BigDecimal getCoeficientBase() {
        return coeficientBase;
    }

    public void setCoeficientBase(BigDecimal coeficientBase) {
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
    public Soci getSoci() {
        return soci;
    }

    public void setSoci(Soci soci) {
        this.soci = soci;
        if (!soci.existeixEstadisticaMod(this)) {
            soci.addEstadistiquesMod(this);
        }       
    }        

    public Modalitat getModalitat() {
        return modalitat;
    }

    public void setModalitat(Modalitat modalitat) {
        this.modalitat = modalitat;
        if (!modalitat.existeixEstadisticaMod(this)) {
            modalitat.addEstadistiquesMod(this);
        }
    }
}
