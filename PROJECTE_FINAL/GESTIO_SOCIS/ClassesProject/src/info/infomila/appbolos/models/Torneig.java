/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import info.infomila.exceptions.TorneigException;
import info.infomila.appbolos.models.Grup;
import info.infomila.appbolos.models.Inscrit;
import info.infomila.appbolos.models.Modalitat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 *
 * @author alber
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "torneig")
@NamedQueries({
    @NamedQuery(
        name = "Torneig.getTornejosActius",
        query = "select t from Torneig t where t.actiu = 1"
                + "and t.id in (select p.torneig.id from Partida p where p.torneig.id = t.id and (p.sociA.id = :id or p.sociB.id = :id))"
    ),
    @NamedQuery(
        name = "Torneig.getTornejosOberts",
        query = "select t from Torneig t "
                    + "where preinscripcio_open = 1 "
                    + "and t.actiu = 1 "
                    + "and t.id not in (select tt.id from Torneig tt left join Inscrit i on i.torneig.id = tt.id where i.soci.id = :id and tt.actiu = 1)"
    ),
})
public class Torneig  implements Serializable
{
    @Transient
    private static final long serialVersionUID = 4L;

    @Id
    @TableGenerator(name = "gen_tornejos",
            table = "comptadors",
            pkColumnName = "CLAU",
            valueColumnName = "COMPTADOR",
            pkColumnValue = "torneig",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_tornejos")
    private int id;
    
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="modalitat_id", nullable = false)
    private Modalitat modalitat;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String nom;
    
    @Basic(optional = false)
    @Column(name = "data_inici", nullable = false)
    private Date dataInici;
    
    @Basic(optional = false)
    @Column(name = "data_final", nullable = false)
    private Date dataFi;
    
    @Column(name = "preinscripcio_open")
    private boolean preinscripcioOberta;
    
    @Column
    private boolean actiu;
    
    @OneToMany(mappedBy = "torneig", fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    private List<Grup> grups = new ArrayList<>();
    
    @OneToMany(mappedBy = "torneig", fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
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
