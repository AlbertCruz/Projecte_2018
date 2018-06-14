package info.infomila.appbolos.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import info.infomila.appbolos.models.exceptions.ModalitatException;

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
