/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import info.infomila.exceptions.GrupException;
import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 *
 * @author alber
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "grup")
@NamedQueries({
    @NamedQuery(
        name = "Grup.getClassificacio",
        query = "select s.id as soci, s.nom as nom, s.cognom1 as cognom1, s.cognom2 as cognom2, em.coeficientBase as coeficientSoci,\n" +
                "(select count(pp) from Partida pp where (pp.sociA.id = s.id or pp.sociB.id = s.id) ) as partidesTotals,\n" +
                "(select count(pp) from Partida pp where ((pp.guanyador = 0 and pp.sociA = s.id) or (pp.guanyador = 1 and pp.sociB = s.id)) ) as partidesGuanyades,\n" +
                "(select count(pp) from Partida pp where ((pp.guanyador = 1 and pp.sociA = s.id) or (pp.guanyador = 0 and pp.sociB = s.id)) ) as partidesPerdudes\n" +
                "from Soci s\n" +
                "left join Partida p on (s.id = p.sociA.id or s.id = p.sociB.id)\n" +
                "left join EstadisticaModalitat em on (s.id = em.emPK.soci.id and em.emPK.modalitat.id = :modalitat_id)\n" +
                "where p.grup.id=:grup_id\n" +
                "group by s.id\n" +
                "order by partidesGuanyades desc, coeficientSoci desc"
    ),
    @NamedQuery(
        name = "Grup.getGrupTorneigOfSoci",
        query = "select g from Grup g "
                + "left join Inscrit i on g.id = i.grup.id "
                + "where i.soci.id = :sociId and i.torneig.id = :torneigId"
    ),
})
public class Grup implements Serializable
{
    @Transient
    private static final long serialVersionUID = 5L;
    
    @Id
    @TableGenerator(name = "gen_grup",
            table = "comptadors",
            pkColumnName = "CLAU",
            valueColumnName = "COMPTADOR",
            pkColumnValue = "grup",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_grup")
    private int id;
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="torneig_id", nullable = false)
    private Torneig torneig;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String descripcio;
    
    @Column(name = "caramboles_victoria")
    private int carambolesVictoria;
    
    @Column(name = "entrades_limit")
    private int limitEntrades;
    
    @Column
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
    
    public Grup(Grup g) {
        this.id = g.id;
        this.torneig = new Torneig(g.torneig);
        this.descripcio = g.descripcio;
        this.carambolesVictoria = g.carambolesVictoria;
        this.limitEntrades = g.limitEntrades;
        this.actiu = g.actiu;
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
            throw new GrupException("La descripcío dun grup és obligatória i ha de ser una cadena major a 2 carácters");
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
            throw new GrupException("El límit dentrades ha de ser un número estrictament positiu");
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
