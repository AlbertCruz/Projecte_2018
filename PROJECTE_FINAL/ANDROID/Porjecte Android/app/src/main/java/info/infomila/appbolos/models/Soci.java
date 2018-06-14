package info.infomila.appbolos.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

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

import info.infomila.appbolos.models.exceptions.SociException;

public class Soci implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nif;
    private String nom;
    private String cognom1;
    private String cognom2;
    private Blob foto;
    private Date dataAlta;
    private String passwordHash;
    protected String data;
    private boolean actiu;
    private List<EstadisticaModalitat> estadistiquesMod = new ArrayList();
    private List<Inscrit> inscrit = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

    protected Soci(){}

    public Soci(String nif, String nom, String cognom1, String cognom2, String password, Blob foto, boolean actiu)
    {
        id = 0;
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setPassword(password);
        setFoto(foto);
        setActiu(actiu);
        setDataAlta(new Date());
    }

    public int getId()
    {
        return id;
    }

    protected final void setId(int id)
    {
        this.id = id;
    }

    public String getNif()
    {
        return nif;
    }

    public final void setNif(String nif)
    {
        if (nif==null) {
            throw new SociException("El nif és obligatór i ha de ser un NIF vàlid!");
        }
        this.nif = nif;
    }

    public String getNom()
    {
        return nom;
    }

    public final void setNom(String nom)
    {
        if (nom==null || nom.length()<2) {
            throw new SociException("El nom del soci és obligatór i ha de ser una cadena major a 3 caràcters");
        }
        this.nom = nom;
    }

    public String getCognom1()
    {
        return cognom1;
    }

    public final void setCognom1(String cognom1)
    {
        this.cognom1 = cognom1;
    }

    public String getCognom2()
    {
        return cognom2;
    }

    public final void setCognom2(String cognom2)
    {
        this.cognom2 = cognom2;
    }

    public Date getDataAlta()
    {
        return dataAlta;
    }

    public String getDataAltaString()
    {
        return sdf.format(dataAlta);
    }

    protected final void setDataAlta(Date dataAlta)
    {
        this.dataAlta = dataAlta;
    }

    public String getPassword()
    {
        return passwordHash;
    }

    public final void setPassword(String passwordHash)
    {
        if (passwordHash==null || passwordHash.length()==0) {
            throw new SociException("El password del soci és obligatóri");
        }
        this.passwordHash = passwordHash;
    }

    public Blob getFoto()
    {
        return foto;
    }

    public final void setFoto(Blob foto)
    {
        this.foto = foto;
    }

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

    public List<EstadisticaModalitat> getEstadistiques()
    {
        return this.estadistiquesMod;
    }

    public EstadisticaModalitat getEstadistica(int idx)
    {
        return estadistiquesMod.get(idx);
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

    private boolean validarNIF(String nif)
    {
        boolean correcto;
        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher = pattern.matcher(nif);

        if (matcher.matches()) {

            String letra = matcher.group(2);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";

            int index = Integer.parseInt(matcher.group(1));
            index = index % 23;

            String reference = letras.substring(index, index + 1);
            correcto = reference.equalsIgnoreCase(letra);

        } else {
            correcto = false;
        }

        return correcto;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final Soci other = (Soci) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Soci{" + "id=" + id + ", nif=" + nif + ", nom=" + nom + ", cognom1=" + cognom1 + ", cognom2=" + cognom2 + ", dataAlta=" + sdf.format(dataAlta)+ ", actiu=" + actiu + '}';
    }

    public String getNomComplert() {
        String nom = this.nom;
        nom += (this.cognom1!=null) ? " " + this.cognom1 : "";
        nom += (this.cognom2!=null) ? " " + this.cognom2 : "";
        return nom;
    }
}