package info.infomila.appbolos.models;

import java.io.Serializable;

import info.infomila.appbolos.models.exceptions.GrupException;

public class Grup implements Serializable
{
    private static final long serialVersionUID = 5L;

    private int id;
    private Torneig torneig;
    private String descripcio;
    private int carambolesVictoria;
    private int limitEntrades;
    private boolean actiu;

    protected Grup() {}

    public Grup(Torneig torneig, String descripcio, int carambolesVictoria, int limitEntrades, boolean actiu) {
        id = 0;
        setTorneig(torneig);
        setDescripcio(descripcio);
        setCarambolesVictoria(carambolesVictoria);
        setLimitEntrades(limitEntrades);
        setActiu(actiu);
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Torneig getTorneig() {
        return torneig;
    }

    protected void setTorneig(Torneig torneig) {
        if (torneig==null) {
            throw new GrupException("No es pot establir un torneig null a un grup");
        }
        this.torneig = torneig;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        if (descripcio==null || descripcio.length()<2) {
            throw new GrupException("La descripcío d'un grup és obligatória i ha de ser una cadena major a 2 carácters");
        }
        this.descripcio = descripcio;
    }

    public int getCarambolesVictoria() {
        return carambolesVictoria;
    }

    public void setCarambolesVictoria(int carambolesVictoria) {
        if (carambolesVictoria<=0) {
            throw new GrupException("Les caramboles per victoria han de ser un número estrictament positiu");
        }
        this.carambolesVictoria = carambolesVictoria;
    }

    public int getLimitEntrades() {
        return limitEntrades;
    }

    public void setLimitEntrades(int limitEntrades) {
        if (limitEntrades<=0) {
            throw new GrupException("El límit d'entrades ha de ser un número estrictament positiu");
        }
        this.limitEntrades = limitEntrades;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

}
