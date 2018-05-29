/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import info.infomila.model.Modalitat;
import info.infomila.model.Soci;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author alber
 */
public class ConectorBD implements IComponentSGBD {

    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    private EntityManager em;
    private EntityManagerFactory emf;
    private Query q;

    public ConectorBD() throws IComponentSGBDException {
        this("propietatsUnitatPersistencia.txt");
    }
    
    public ConectorBD(String nomFitxerPropietats) throws IComponentSGBDException {
        Properties props = new Properties();
        try {
            props.load(new FileReader(nomFitxerPropietats));
        } catch (FileNotFoundException ex) {
            throw new IComponentSGBDException("No es troba fitxer de propietats", ex);
        } catch (IOException ex) {
            throw new IComponentSGBDException("Error en carregar fitxer de propietats", ex);
        }
        String up = props.getProperty("up");
        if (up == null) {
            throw new IComponentSGBDException("Manca la propietat up en el fitxer de propietats");
        }
        try {
            emf = Persistence.createEntityManagerFactory(up);
        } catch (Exception ex) {
            throw new IComponentSGBDException("Problemes en crear EntityManagerFactory.", ex);
        }
        try {
            em = emf.createEntityManager();
        } catch (Exception ex) {
            throw new IComponentSGBDException("Problemes en crear EntityManager", ex);
        }

        System.out.println("Entity manager Creat");
    }
    
    @Override
    public void tancarConexio(long idSessio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int borrarSoci(int id){
        try {
            Soci soci = em.find(Soci.class, id);
            em.getTransaction().begin();
            em.remove(soci);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int commit() {
       try {
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int rollback() {
        try {
            em.getTransaction().rollback();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<Soci> getLlistatSocis() {
        q = em.createNamedQuery("Soci.getLlistaSocis",Soci.class);
        return q.getResultList();
    }

    @Override
    public List<Soci> getSociPerDni(String dni) throws IComponentSGBDException {
        q = em.createNamedQuery("Soci.getSociPerDni",Soci.class);
        q.setParameter(1,"%"+dni+"%" );
        return q.getResultList();
    }

    @Override
    public List<Soci> getSociPerNomCognoms(String nom, String cognom1, String cognom2) throws IComponentSGBDException {
        q = em.createNamedQuery("Soci.getSociPerNomCognoms",Soci.class);
        if(nom == null) nom = "";
        else nom = "%"+nom+"%";
        if(cognom1 == null) cognom1 = "";
        else cognom1 = "%"+cognom1+"%";
        if(cognom2 == null) cognom2 ="";
        else cognom2 = "%"+cognom2+"%";
        q.setParameter(1, nom);
        q.setParameter(2, cognom1);
        q.setParameter(3, cognom2);
        return q.getResultList();
    }

    @Override
    public boolean existeixSoci(int idSoci) {
        if(idSoci>0){
            return em.find(Soci.class, idSoci) != null;
        }
       return false;
    }
    
    @Override
    public Soci crearNouSoci(Soci s) throws IComponentSGBDException {
        if(!existeixSoci(s.getId())){
            em.getTransaction().begin();
            em.persist(s);
            em.flush();
            em.getTransaction().commit();            
            return s;
        }else throw new IComponentSGBDException("Soci ja existent");
    }

    @Override
    public Soci modificarSoci(Soci s) throws IComponentSGBDException {
        if(existeixSoci(s.getId())){            
            em.getTransaction().begin();
            em.merge(s);            
            em.flush();
            em.getTransaction().commit();
            return s;
        }else throw new IComponentSGBDException("Aquest soci no existeix");
    }

    @Override
    public List<Modalitat> getLlistaModalitats() throws IComponentSGBDException{
        q = em.createNamedQuery("Modalitat.getLlistaModalitats",Modalitat.class);
        return q.getResultList();
    }
    
}
