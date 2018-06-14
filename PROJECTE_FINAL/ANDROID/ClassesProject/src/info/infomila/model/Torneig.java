/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import info.infomila.exceptions.TorneigException;
import info.infomila.model.Grup;
import info.infomila.model.Inscrit;
import info.infomila.model.Modalitat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author alber
 */
public class Torneig  implements Serializable
{

    private static final long serialVersionUID = 4L;
    private int id;
    private Modalitat modalitat;
    private String nom;
    private Date dataInici;
    private Date dataFi;
    private boolean preinscripcioOberta;
    private boolean actiu;
    private List<Grup> grups = new ArrayList<>();
    private List<Inscrit> inscripcions = new ArrayList<>();

    protected Torneig() { }

    public Torneig(Modalitat modalitat, String nom, Date dataInici, Date dataFi, boolean preinscripcioOberta, boolean actiu)
    {
        id = 0;
        setModalitat(modalitat);
        setNom(nom);
        setDataInici(dataInici);
        setDataFi(dataFi);
        setPreinscripcioOberta(preinscripcioOberta);
        setActiu(actiu);
    }
    
    public Torneig(Torneig torneig)
    {
        id = torneig.id;
        modalitat = torneig.modalitat;
        nom = torneig.nom;
        dataInici = torneig.dataInici;
        dataFi = torneig.dataFi;
        preinscripcioOberta = torneig.preinscripcioOberta;
        actiu = torneig.actiu;
    }

    public int getId()
    {
        return id;
    }

    protected void setId(int id)
    {
        this.id = id;
    }

    public Modalitat getModalitat()
    {
        return modalitat;
    }

    public void setModalitat(Modalitat modalitat)
    {
        if (modalitat==null) {
            throw new TorneigException("La modalitat d'un torneig és obligatória");
        }
        this.modalitat = modalitat;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        if (nom==null || nom.length()<2) {
            throw new TorneigException("El nom d'un torneig és obligatóri i ha de ser una cadena de 2 carácters mínim");
        }
        this.nom = nom;
    }

    public Date getDataInici()
    {
        return dataInici;
    }

    public void setDataInici(Date dataInici)
    {
        if (dataInici==null || dataInici.before(new Date())) {
            throw new TorneigException("La data d'inici és obligatória i ha de ser més gran que la data actual");
        }
        if (dataFi!=null && dataInici.after(dataFi)) {
            throw new TorneigException("La data d'inici no pot ser superior a la data fi");
        }
        this.dataInici = dataInici;
    }

    public Date getDataFi()
    {
        return dataFi;
    }

    public void setDataFi(Date dataFi)
    {
        if (dataFi==null || dataFi.before(new Date())) {
            throw new TorneigException("La data fi és obligatória i ha de ser més gran que la data actual");
        }
        if (dataInici!=null && dataFi.before(dataInici)) {
            throw new TorneigException("La data fi no pot ser inferior a la data d'inici");
        }
        this.dataFi = dataFi;
    }

    public boolean isPreinscripcioOberta()
    {
        return preinscripcioOberta;
    }

    public void setPreinscripcioOberta(boolean preinscripcioOberta)
    {
        this.preinscripcioOberta = preinscripcioOberta;
    }

    public boolean isActiu()
    {
        return actiu;
    }

    public void setActiu(boolean actiu)
    {
        this.actiu = actiu;
    }

    protected List<Grup> getGrups()
    {
        return grups;
    }
        
    public Iterator<Grup> iteGrups()
    {
        return grups.iterator();
    }

    protected void setGrups(List<Grup> grups)
    {
        this.grups = grups;
    }

    public Iterator<Inscrit> iteInscripcions()
    {
        return inscripcions.iterator();
    }

    protected void setInscripcions(List<Inscrit> inscripcions)
    {
        this.inscripcions = inscripcions;
    }
    
    public Inscrit getInscripcio(int idx)
    {
        return inscripcions.get(idx);
    }
    
    public void addInscripcio(Inscrit inscripcio)
    {
        if (inscripcio==null) {
            throw new TorneigException("No es pot afegir una inscripció amb valor null");
        }
        if (!this.inscripcions.contains(inscripcio)) {
            this.inscripcions.add(inscripcio);
            inscripcio.setTorneig(this);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
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
        final Torneig other = (Torneig) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Torneig{" + "id=" + id + ", nom=" + nom + ", dataInici=" + dataInici + ", preinscripcioOberta=" + preinscripcioOberta + ", actiu=" + actiu + '}';
    }
    
}
