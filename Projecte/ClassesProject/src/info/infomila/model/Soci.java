/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.model;

import info.infomila.exceptions.SociException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
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
    @NamedQuery(name="Soci.getLlistaSocis", query="select s from Soci s"),    
    @NamedQuery(name = "Soci.getSociPerDni", query = "select s from Soci s where s.nif like ?1"),    
    @NamedQuery(name = "Soci.getSociPerNomCognoms", query = "select s from Soci s where (?1='' or s.nom like ?1) and (?2='' or s.cognom1 like ?2) and (?3='' or s.cognom2 is null or s.cognom2 like ?3)")        
    })
@Entity
@Table(name = "soci")
public class Soci implements Serializable {
    
    //readOnly
    @Id
    @TableGenerator(name = "comptadors_generator", table = "comptadors",
            pkColumnName = "TAULA", pkColumnValue = "soci",
            valueColumnName = "COMPTADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "comptadors_generator")
    private int id;
    @Column(length = 9)
    private String nif;
    @Column(length = 30, nullable = false)
    private String nom;
    @Column(length = 30, nullable = false)
    private String cognom1;
    @Basic(optional = true)
    @Column(length = 30, nullable = true)
    private String cognom2;
    @Basic(optional = true)
    @Column(nullable = true)
    private byte[] bytesFoto;
    @Column(name = "data_alta")
    private Date dataAlta;
    @Column(length = 32, nullable = false)
    private String passwordHash;
   
    @OneToMany(mappedBy = "soci")
    private List<EstadisticaModalitat> estadistiquesMod = new ArrayList();
    

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    

    
    
    

    public Soci() {
    }

    public Soci(String nif, String nom, String cognom1, String cognom2) {
        
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);     
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


    public byte[] getBytesFoto() {
        return bytesFoto;
    }

    public void setBytesFoto(byte[] bytesFoto) {
        this.bytesFoto = bytesFoto;
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
    public List<EstadisticaModalitat> getEstadistiquesMod2() {
        return estadistiquesMod;
    }
    
    public Iterator<EstadisticaModalitat> getEstadistiquesMod() {
        return estadistiquesMod.iterator();
    }

    public void addEstadistiquesMod(EstadisticaModalitat est) {
        if (est != null && !estadistiquesMod.contains(est)) {
            estadistiquesMod.add(est);
            if (!est.getSoci().equals(this)) {
                est.setSoci(this);
            }
        } else {
            throw new SociException("EstadisticaModalitat invàlid (valor null o repetit no permés)");
        }
    }

    public void removeEstadistiquesModByIndex(int index) {
        if (index > 0) {
            try {
                estadistiquesMod.get(index).setSoci(null);
                estadistiquesMod.remove(index);
            } catch (IndexOutOfBoundsException ex) {
                throw new SociException("index invàlid (fora de rang)", ex);
            }
        }
    }

    public void removeEstadistiquesMod(EstadisticaModalitat est) {
        if (est != null && estadistiquesMod.contains(est)) {
            estadistiquesMod.get(estadistiquesMod.indexOf(est)).setSoci(null);
            estadistiquesMod.remove(est);
        } else {
            throw new SociException("EstadisticaModalitat invàlid (valor null o no existent no permés)");
        }
    }

    public boolean existeixEstadisticaMod(EstadisticaModalitat est) {
        return estadistiquesMod.contains(est);
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

