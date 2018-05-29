/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import info.infomila.exceptions.ModalitatException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author alber
 */
@NamedQueries({
    @NamedQuery(name = "Modalitat.getLlistaModalitats", query="select m from Modalitat m")    
    })
@Entity
@Table(name = "modalitat")
public class Modalitat implements Serializable {
    @Id
    @TableGenerator(name = "comptadors_generator", table = "comptadors",
            pkColumnName = "TAULA", pkColumnValue = "modalitat",
            valueColumnName = "COMPTADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "comptadors_generator")
    private int id;
    @Column(name = "desc_modalitat",length = 30, nullable = false)
    private String descripcio;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "modalitat")
    private List<EstadisticaModalitat> estadistiquesMod = new ArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
    public Iterator<EstadisticaModalitat> getEstadistiquesMod() {
        return estadistiquesMod.iterator();
    }

    public void addEstadistiquesMod(EstadisticaModalitat est) {
        if (est != null && !estadistiquesMod.contains(est)) {
            estadistiquesMod.add(est);
            if (!est.getModalitat().equals(this)) {
                est.setModalitat(this);
            }
        } else {
            throw new ModalitatException("EstadisticaModalitat invàlid (valor null o repetit no permés)");
        }
    }

    public void removeEstadistiquesModByIndex(int index) {
        if (index > 0) {
            try {
                estadistiquesMod.get(index).setModalitat(null);
                estadistiquesMod.remove(index);
            } catch (IndexOutOfBoundsException ex) {
                throw new ModalitatException("index invàlid (fora de rang)", ex);
            }
        }
    }

    public void removeEstadistiquesMod(EstadisticaModalitat est) {
        if (est != null && estadistiquesMod.contains(est)) {
            estadistiquesMod.get(estadistiquesMod.indexOf(est)).setSoci(null);
            estadistiquesMod.remove(est);
        } else {
            throw new ModalitatException("EstadisticaModalitat invàlid (valor null o no existent no permés)");
        }
    }

    public boolean existeixEstadisticaMod(EstadisticaModalitat est) {
        return estadistiquesMod.contains(est);
    }
    
}
