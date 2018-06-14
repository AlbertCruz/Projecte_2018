/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import java.io.Serializable;

/**
 *
 * @author alber
 */
public class Modalitat implements Serializable {

    private static final long serialVersionUID = 2L;
    private int id;
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
