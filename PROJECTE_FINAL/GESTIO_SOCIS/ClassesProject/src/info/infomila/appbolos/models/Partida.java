/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import info.infomila.exceptions.PartidaException;
import info.infomila.appbolos.models.Grup;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
@Table(name = "partida")

@NamedQueries({
    @NamedQuery(
        name = "Grup.getPartides",
        query = "select p from Partida p where p.grup.id = :grupId and p.torneig.id = :torneigId and (p.sociA.id = :sociId or p.sociB.id = :sociId) and p.estatPartida = 0"
    )
})
public class Partida implements Serializable {
    
    @Transient
    private static final long serialVersionUID = 7L;
    
    @Id
    @TableGenerator(name = "gen_partides",
            table = "comptadors",
            pkColumnName = "CLAU",
            valueColumnName = "COMPTADOR",
            pkColumnValue = "partida",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_partides")
    private int id;
    
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="soci_a", nullable = false)
    private Soci sociA;
    
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="soci_b", nullable = false)
    private Soci sociB;
    
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="torneig_id", nullable = false)
    private Torneig torneig;
    
    @ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name="grup_id", nullable = false)
    private Grup grup;
    
    @Column(name = "carambolesA")
    private int carambolesA;
    
    @Column(name = "carambolesB")
    private int carambolesB;
    
    @Column(name = "entradesA")
    private int entradesA;
    
    @Column(name = "entradesB")
    private int entradesB;
    
    @Column(name = "data_partida")
    private Date dataPartida;
    
    @Column(name = "num_entrades")
    private int entradesTotals;
    
    @Column(name = "estat_partida")
    private EstatPartida estatPartida;
    
    @Column(name = "mode_victoria")
    private ModeVictoria modeVictoria;
    
    @Column
    private Guanyador guanyador;
    
    protected Partida () {}
    
    public Partida(Partida partida) {
        this.id = partida.id;
        this.sociA = new Soci(partida.sociA);
        this.sociB = new Soci(partida.sociB);
        this.torneig = new Torneig(partida.torneig);
        this.grup = new Grup(partida.grup);
        this.carambolesA = partida.carambolesA;
        this.carambolesB = partida.carambolesB;
        this.entradesA = partida.entradesA;
        this.entradesB = partida.entradesB;
        this.dataPartida = partida.dataPartida;
        this.entradesTotals = partida.entradesTotals;
        this.estatPartida = partida.estatPartida;
        this.modeVictoria = partida.modeVictoria;
        this.guanyador = partida.guanyador;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public Soci getSociA() {
        return sociA;
    }

    protected void setSociA(Soci sociA) {
        this.sociA = sociA;
    }

    public Soci getSociB() {
        return sociB;
    }

    protected void setSociB(Soci sociB) {
        this.sociB = sociB;
    }

    public Torneig getTorneig() {
        return torneig;
    }

    protected void setTorneig(Torneig torneig) {
        this.torneig = torneig;
    }

    public Grup getGrup() {
        return grup;
    }

    protected void setGrup(Grup grup) {
        this.grup = grup;
    }

    public int getCarambolesA() {
        return carambolesA;
    }

    public void setCarambolesA(int carambolesA) {
        if (carambolesA<0) {
            throw new PartidaException("Les caramboles fetes per un soci han de ser un número estrictament positiu");
        }
        this.carambolesA = carambolesA;
    }

    public int getCarambolesB() {
        return carambolesB;
    }

    public void setCarambolesB(int carambolesB) {
        if (carambolesB<0) {
            throw new PartidaException("Les caramboles fetes per un soci han de ser un número estrictament positiu");
        }
        this.carambolesB = carambolesB;
    }

    public int getEntradesA() {
        return entradesA;
    }

    public void setEntradesA(int entradesA) {
        if (entradesA<0) {
            throw new PartidaException("Les entrades fetes per un soci han de ser un número estrictament positiu");
        }
        this.entradesA = entradesA;
    }

    public int getEntradesB() {
        return entradesB;
    }

    public void setEntradesB(int entradesB) {
        if (entradesB<0) {
            throw new PartidaException("Les entrades fetes per un soci han de ser un número estrictament positiu");
        }
        this.entradesB = entradesB;
    }

    public Date getDataPartida() {
        return dataPartida;
    }

    protected void setDataPartida(Date dataPartida) {
        this.dataPartida = dataPartida;
    }

    public int getEntradesTotals() {
        return entradesTotals;
    }

    public void setEntradesTotals(int entradesTotals) {
        if (entradesTotals<0) {
            throw new PartidaException("Les entrades totals han de ser un número estrictament positiu");
        }
        this.entradesTotals = entradesTotals;
    }

    public EstatPartida getEstatPartida() {
        return estatPartida;
    }

    public void setEstatPartida(EstatPartida estatPartida) {
        this.estatPartida = estatPartida;
    }

    public ModeVictoria getModeVictoria() {
        return modeVictoria;
    }

    public void setModeVictoria(ModeVictoria modeVictoria) {
        this.modeVictoria = modeVictoria;
    }

    public Guanyador getGuanyador() {
        return guanyador;
    }

    public void setGuanyador(Guanyador guanyador) {
        this.guanyador = guanyador;
    }
    
    public enum EstatPartida {
        PENDENT, JUGAT
    }
    
    public enum ModeVictoria {
        PER_CARAMBOLES, ENTRADES_ASSOLIDES, ABANDONAMENT
    }
    
    public enum Guanyador {
        A, B
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Partida other = (Partida) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        String data = (dataPartida!=null) ? sdf.format(dataPartida) : "";
        return "Partida{" + "id=" + id + ", sociA=" + sociA.getNif() + ", sociB=" + sociB.getNif() + ", torneig=" + torneig.getNom() + ", grup=" + grup.getDescripcio() + ", carambolesA=" + carambolesA + ", carambolesB=" + carambolesB + ", entradesA=" + entradesA + ", entradesB=" + entradesB + ", dataPartida=" + data + ", entradesTotals=" + entradesTotals + ", estatPartida=" + estatPartida + ", modeVictoria=" + modeVictoria + ", guanyador=" + guanyador + '}';
    }
    
    
    
}
