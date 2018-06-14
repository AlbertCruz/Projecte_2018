/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 *
 * @author alber
 */
@NamedQueries({
    @NamedQuery(name = "Modalitat.getLlistaModalitats", query="select m from Modalitat m")    
    })
@Entity
@Access(AccessType.FIELD)
@Table(name = "modalitat")
public class Modalitat implements Serializable {
    
    @Transient
    private static final long serialVersionUID = 2L;
    
    @Id
    @TableGenerator(name = "gen_modalitat", table = "comptadors",
            pkColumnName = "CLAU", pkColumnValue = "modalitat",
            valueColumnName = "COMPTADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_modalitat")
    private int id;
    
    @Basic(optional = false)
    @Column(name = "desc_modalitat",length = 30, nullable = false)
    private String descripcio;   

    protected Modalitat() {}
    
    public Modalitat(int id, String descripcio)
    {
        setId(id);
        setDescripcio(descripcio);
    }

    public Modalitat(Modalitat modalitat) {
        id = modalitat.id;
        descripcio = modalitat.descripcio;
    }
    
    public int getId() {
        return id;
    }

    protected final void setId(int id) {
        this.id = id;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }   
    
     @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Modalitat other = (Modalitat) obj;
        return this.id == other.id;
    }
}
