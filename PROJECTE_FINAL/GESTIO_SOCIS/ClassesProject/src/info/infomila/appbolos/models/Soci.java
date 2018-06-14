/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.appbolos.models;

import info.infomila.exceptions.SociException;
import java.io.Serializable;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Basic;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@NamedQueries({
    @NamedQuery(
        name = "Soci.loginSoci", query = "select s from Soci s where s.nif = :nif and s.passwordHash = :password"),
    @NamedQuery(name="Soci.login", 
           query="select count(s.nif) from Soci s where s.nif=?1 and s.passwordHash=?2"),
    @NamedQuery(name="Soci.getSociPerNif",
            query="select s.id from Soci s where s.nif=?1"),
    @NamedQuery(name="Soci.getLlistaSocis", query="select s from Soci s"),    
    @NamedQuery(name = "Soci.getSociPerDni", query = "select s from Soci s where s.nif like ?1"),    
    @NamedQuery(name = "Soci.getSociPerNomCognoms", query = "select s from Soci s where (?1='' or s.nom like ?1) and (?2='' or s.cognom1 like ?2) and (?3='' or s.cognom2 is null or s.cognom2 like ?3)")        
    })
@Entity
@Access(AccessType.FIELD)
@Table(name = "soci")
public class Soci implements Serializable {
    
    @Transient
    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name = "gen_soci", table = "comptadors",
            pkColumnName = "CLAU", pkColumnValue = "soci",
            valueColumnName = "COMPTADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gen_soci")
    private int id;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 9, unique = true)
    private String nif;
    @Column(length = 30, nullable = false)
    private String nom;
    @Column(length = 30, nullable = false)
    private String cognom1;
    @Basic(optional = true)
    @Column(length = 30, nullable = true)
    private String cognom2;

    //private byte[] bytesFoto;
    @Column(name = "data_alta")
    private Date dataAlta;
    @Column(length = 32, nullable = false, name = "password")
    private String passwordHash;
    @Column
    private boolean actiu;
    
    @Column
    @Lob
    private Blob foto;
   
    @OneToMany(mappedBy = "emPK.soci", fetch = FetchType.LAZY)
    private List<EstadisticaModalitat> estadistiquesMod = new ArrayList();    
    
    @OneToMany(mappedBy = "soci", fetch = FetchType.LAZY)
    private List<Inscrit> inscrit = new ArrayList<>();
    
    @Transient
    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
           
    protected Soci(){}

    public Soci(String nif, String nom, String cognom1, String cognom2, String passwordHash, Blob foto, boolean actiu)
    {
        id = 0;
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setPasswordHash(passwordHash);
        setFoto(foto);
        setActiu(actiu);
        setDataAlta(new Date());
    }
    
    public Soci(Soci soci)
    {
        id = soci.id;
        nif = soci.nif;
        nom = soci.nom;
        cognom1 = soci.cognom1;
        cognom2 = soci.cognom2;
        dataAlta = soci.dataAlta;
        passwordHash = soci.passwordHash;
        //foto = soci.foto;
        actiu = soci.actiu;
        for (EstadisticaModalitat em : soci.estadistiquesMod) {
            addEstadistica(new EstadisticaModalitat(em));
        }
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    //public byte[] getFoto(){
    //     return foto;
    //}
    
    public Date getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }
    
    public String getNif() {
        return nif;
    }

    public String getNom() {
        return nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /*public byte[] getBytesFoto() {
        return bytesFoto;
    }

    public void setBytesFoto(byte[] bytesFoto) {
        this.bytesFoto = bytesFoto;
    }*/
    
    public Blob getFoto()
    {
        return foto;
    }

    public final void setFoto(Blob foto)
    {
        this.foto = foto;
    }
    
    public void setNif(String nif) {
        if(nif != null && nif.length() == 9)
        this.nif = nif;
        else throw new SociException("nif invàlid (valor null o longitud no igual a 9 no permés)");
    }

    public void setNom(String nom) {
        if(nom != null && ! nom.isEmpty())
        this.nom = nom;
        else throw new SociException("nom invàlid (valor null o cadena buida no permés)");
    }

    public void setCognom1(String cognom1) {
        if(cognom1 != null && ! cognom1.isEmpty())
        this.cognom1 = cognom1;
        else throw new SociException("cognom1 invàlid (valor null o cadena buida no permés)");
    }

    public void setCognom2(String cognom2) {
        if(cognom2 != null && ! cognom2.isEmpty())
        this.cognom2 = cognom2;
        else throw new SociException("cognom2 invàlid (valor null o cadena buida no permés)");
    }
    
    //public void setFoto(byte[] bytesFoto){
    //    this.foto = bytesFoto;
    //}
    public boolean isActiu() {
        return actiu;
    }

    public final void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public Iterator<EstadisticaModalitat> iteEstadistiques()
    {
        return estadistiquesMod.iterator();
    }

    protected void setEstadistiques(List<EstadisticaModalitat> estadistiques)
    {
        this.estadistiquesMod = estadistiques;
    }
    
    public EstadisticaModalitat getEstadistica(int idx)
    {
        return estadistiquesMod.get(idx);
    }
    
    public final void addEstadistica(EstadisticaModalitat estadistica)
    {
        this.estadistiquesMod.add(estadistica);
    }

    public Iterator<Inscrit> iteInscripcions()
    {
        return inscrit.iterator();
    }

    protected void setInscripcions(List<Inscrit> inscripcions)
    {
        this.inscrit = inscripcions;
    }
    
    public Inscrit getInscripcio(int idx)
    {
        return inscrit.get(idx);
    }
    
    public void addInscripcio(Inscrit inscripcio)
    {
        if (inscripcio==null) {
            throw new SociException("No es pot afegir una inscripció amb valor null");
        }
        if (!this.inscrit.contains(inscripcio)) {
            this.inscrit.add(inscripcio);
            inscripcio.setSoci(this);
        }
    }

    private boolean validateNIF(String nif)
    {
        boolean ok;
        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher = pattern.matcher(nif);
        if (matcher.matches()) {
            String letra = matcher.group(2);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int index = Integer.parseInt(matcher.group(1));
            index = index % 23;
            String reference = letras.substring(index, index + 1);
            ok = reference.equalsIgnoreCase(letra);
        } else {
            ok = false;
        }
        return ok;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.nif);
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
        if (!(this instanceof Soci)) {
            return false;
        }
        final Soci other = (Soci) obj;
        if (!Objects.equals(this.nif, other.nif)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Soci{" + "nif=" + nif + ", nom=" + nom + ", cognom1=" + cognom1 + ", cognom2=" + cognom2 + "}";
    }

    
    
    
}

