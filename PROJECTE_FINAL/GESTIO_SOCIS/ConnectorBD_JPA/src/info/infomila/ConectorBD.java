/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import info.infomila.gestio.UserOnline;
import info.infomila.appbolos.models.EstadisticaModalitat;
import info.infomila.appbolos.models.Grup;
import info.infomila.appbolos.models.Inscrit;
import info.infomila.appbolos.models.Modalitat;
import info.infomila.appbolos.models.Partida;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Javi
 */
public class ConectorBD implements IComponentSGBD {

    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    private EntityManager em;
    private int idSoci;
    private EntityManagerFactory emf;
    private Query q;

    public ConectorBD() throws ComponentSGBDException {
        this("Hibernate.properties");
    }

    public ConectorBD(String nomFitxerPropietats) throws ComponentSGBDException {
        Properties props = new Properties();       
        try {
            props.load(new FileReader(nomFitxerPropietats));
        } catch (FileNotFoundException ex) {
            throw new ComponentSGBDException("No es troba fitxer de propietats", ex);
        } catch (IOException ex) {
            throw new ComponentSGBDException("Error en carregar fitxer de propietats", ex);
        }        
        try {
            emf = Persistence.createEntityManagerFactory("UP-MySQL", new HashMap(props));            
        } catch (Exception ex) {
            throw new ComponentSGBDException("Problemes en crear EntityManagerFactory.", ex);
        }
        try {
            em = emf.createEntityManager();
        } catch (Exception ex) {
            throw new ComponentSGBDException("Problemes en crear EntityManager", ex);
        }

        System.out.println("Entity manager Creat");
    }
    
    @Override
    public long login(String user, String password) throws ComponentSGBDException {
        q = em.createNamedQuery("Soci.login", Long.class);
        q.setParameter(1, user);
        q.setParameter(2, password);

        long res = (long) q.getSingleResult();

        if (res == 1) {
            q = em.createNamedQuery("Soci.getSociPerNif", Integer.class);
            q.setParameter(1, user);
            idSoci = (int) q.getSingleResult();

            List<Long> connectionCodes = getIdsSessio();

            UserOnline usuariEntrant = new UserOnline(idSoci, user, connectionCodes);

            try {
                UserOnline usuari = em.find(UserOnline.class, user);

                if (usuari != null) {
                    usuari.setConnectonCode(usuariEntrant.getConnectionCode());
                    em.getTransaction().begin();
                    em.getTransaction().commit();
                } else {
                    em.persist(usuariEntrant);
                    em.getTransaction().begin();
                    em.getTransaction().commit();
                }

            } catch (Exception e) {

            }

            return usuariEntrant.getConnectionCode();
        }
        throw new ComponentSGBDException("Usuari invàlid, usuari o contrassenya errònis.");
    }       

    @Override
    public Soci loginSoci(String user, String pass) throws ComponentSGBDException {
        Soci s = null;
        try {
            Query q = em.createNamedQuery("Soci.loginSoci");
            q.setParameter("nif",user);
            q.setParameter("password",pass);
            try {
                s = (Soci)q.getSingleResult();
            } catch(NoResultException nre) { }
        } catch(IllegalArgumentException iae) {
            throw new ComponentSGBDException("Error al realitzar la consulta de loginSoci! \nError: " + iae.getMessage());
        }  
        if(s!=null)refresh(s);        
        return s;
    }
    
    @Override
    public void tancarConexio(long idSessio) {
        if (em != null) {
            UserOnline usuari = em.find(UserOnline.class, idSessio);
            em.getTransaction().begin();
            em.remove(usuari);
            em.getTransaction().commit();
            em.close();
        }
    }

    @Override
     public void commit() throws ComponentSGBDException {
        if (em!=null && em.isOpen() && em.getTransaction().isActive()) {
            try {
                em.getTransaction().commit();
            } catch(Exception ex) {
                throw new ComponentSGBDException("Hi ha hagut un error alhora de realitzar el commit!", ex);
            }
        }
    }

    @Override
    public void rollback() throws ComponentSGBDException {
        if (em!=null && em.isOpen() && em.getTransaction().isActive()) {
            try {
                em.getTransaction().rollback();
            } catch(Exception ex) {
                throw new ComponentSGBDException("Hi ha hagut un error alhora de realitzar el rollback!", ex);
            }
        }
    }

    //------- UTILS ---------
    public List<Long> getIdsSessio() {
        q = em.createNamedQuery("UserOnline.getConnectionCodes", Long.class);
        return q.getResultList();
    }

    public boolean validaIdSessio(long idSessio) {
        q = em.createNamedQuery("UserOnline.validaConnectionCode", Long.class);
        q.setParameter(1, idSessio);

        return (long) q.getSingleResult() == 1;
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
    public List<Soci> getLlistatSocis() {
        List<Soci> llistaSocis;
        q = em.createNamedQuery("Soci.getLlistaSocis",Soci.class);
        llistaSocis = q.getResultList();
        for (Soci s : llistaSocis) {
            Iterator<EstadisticaModalitat> it = s.iteEstadistiques();
        }
        return llistaSocis;
    }

    @Override
    public List<Soci> getSociPerDni(String dni) throws ComponentSGBDException {
        List<Soci> llistaSocis;
        q = em.createNamedQuery("Soci.getSociPerDni",Soci.class);
        q.setParameter(1,"%"+dni+"%" );
        llistaSocis = q.getResultList();
        for (Soci s : llistaSocis) {
            Iterator<EstadisticaModalitat> it = s.iteEstadistiques();
        }
        return llistaSocis;
    }

    @Override
    public List<Soci> getSociPerNomCognoms(String nom, String cognom1, String cognom2) throws ComponentSGBDException {
        List<Soci> llistaSocis;
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
        llistaSocis = q.getResultList();
        for (Soci s : llistaSocis) {
            Iterator<EstadisticaModalitat> it = s.iteEstadistiques();
        }
        return llistaSocis;
    }

    @Override
    public boolean existeixSoci(int idSoci) {
        if(idSoci>0){
            return em.find(Soci.class, idSoci) != null;
        }
       return false;
    }
    
    @Override
    public Soci crearNouSoci(Soci s) throws ComponentSGBDException {
        if(!existeixSoci(s.getId())){
            em.getTransaction().begin();
            em.persist(s);
            //em.flush();
            em.getTransaction().commit();            
            return s;
        }else throw new ComponentSGBDException("Soci ja existent");
    }

    @Override
    public Soci modificarSoci(Soci s) throws ComponentSGBDException {
        if(existeixSoci(s.getId())){            
            em.getTransaction().begin();
            em.merge(s);            
            //em.flush();
            em.getTransaction().commit();
            return s;
        }else throw new ComponentSGBDException("Aquest soci no existeix");
    }

    @Override
    public List<Modalitat> getLlistaModalitats() throws ComponentSGBDException{
        q = em.createNamedQuery("Modalitat.getLlistaModalitats",Modalitat.class);
        return q.getResultList();
    }     
    
    @Override
    public Torneig getTorneigById(int id) throws ComponentSGBDException {
        //EntityManager aux = null;
        try {
            //aux = getEntityManager();
            Torneig t = em.find(Torneig.class, id);
            
            return t;
        } catch(IllegalArgumentException iaex) {
            throw new ComponentSGBDException("Hi ha hagut un error al obtenir el torneig amb id: " + id, iaex);
        } finally {
            //closeEntityManager(aux);
        }
    }
    
    private EntityManager getEntityManager() throws ComponentSGBDException {
        try {
            return emf.createEntityManager();
        } catch (PersistenceException ex) {
            throw new ComponentSGBDException("Problemes al crear l'EntityManager", ex);
        }
    }
    
    private void closeEntityManager(EntityManager em) throws ComponentSGBDException {
        try {
            if (em!=null && em.isOpen()) em.close();
        } catch(Exception ex) {
            throw new ComponentSGBDException("Error al tancar la connexió!", ex);
        }
    }
    
    @Override
    public Grup getGrupTorneigOfSoci(int torneigId, int sociId) throws ComponentSGBDException {
        Grup g = null;
        EntityManager aux = null;
        try {
            aux = getEntityManager();
            Query q = aux.createNamedQuery("Grup.getGrupTorneigOfSoci");
            q.setParameter("torneigId",torneigId);
            q.setParameter("sociId",sociId);
            try {
                g = (Grup)q.getSingleResult();
            } catch(NoResultException nre) { }
        } catch(IllegalArgumentException iaex) {
            throw new ComponentSGBDException("Hi ha hagut un error al obtenir el grup del torneig " + torneigId + " i del soci " + sociId, iaex);
        } finally {
            closeEntityManager(aux);
        }
        return g;
    }

    @Override
    public List<Torneig> getTornejosActius(int sociId, boolean bLlistaCopia) throws ComponentSGBDException {
        EntityManager aux = null;
        try {
            aux = getEntityManager();
            Query q = aux.createNamedQuery("Torneig.getTornejosActius");
            q.setParameter("id",sociId);
            List<Torneig> llista = q.getResultList();
            if (bLlistaCopia) {
                List<Torneig> llTorneig = new ArrayList();
                llista.forEach((t) -> {
                    llTorneig.add(new Torneig(t));
                });
                return llTorneig;
            } else {
                return llista;
            }
        } catch(Exception e) {
            throw new ComponentSGBDException("Error al realitzar la consulta getTornejosActiusOnParticipo! \nError: " + e.getMessage());
        } finally {
            closeEntityManager(aux);
        }
    }
    
    @Override
    public List<Torneig> getTornejosOberts(int sociId) throws ComponentSGBDException {
        EntityManager aux = null;
        try {
            aux = getEntityManager();
            Query q = aux.createNamedQuery("Torneig.getTornejosOberts");
            q.setParameter("id",sociId);
            return q.getResultList();
        } catch(IllegalArgumentException iae) {
            throw new ComponentSGBDException("Error al realitzar la consulta getTornejosActiusOnParticipo! \nError: " + iae.getMessage());
        } finally {
            closeEntityManager(aux);
        }
    }

    @Override
    public Boolean ferInscripcio(Soci s, Torneig t) throws ComponentSGBDException {
        Boolean bOk = false;
        try {
            if (!em.getTransaction().isActive()) em.getTransaction().begin();
            Torneig tor = getTorneigById(t.getId());
            Inscrit i = new Inscrit(s,tor);
            em.persist(i);
            bOk = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bOk;
    }

    @Override
    public Partida getPartidaById(int id) throws ComponentSGBDException {
        EntityManager aux = null;
        try {
            aux = getEntityManager();
            return aux.find(Partida.class, id);
        } catch(IllegalArgumentException iaex) {
            throw new ComponentSGBDException("Hi ha hagut un error al obtenir la partida amb id: " + id, iaex);
        } finally {
            closeEntityManager(aux);
        }
    }
    
    @Override
    public void modificarPartida(Partida p) throws ComponentSGBDException {
        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        em.merge(p);
    }

    @Override
    public List<Partida> getPartides(int grupId, int torneigId, int sociId, boolean bLlistaCopia) throws ComponentSGBDException {
        EntityManager aux = null;
        List<Partida> llista = null;
        List<Partida> llPartides = new ArrayList();
        try {
            aux = getEntityManager();
            Query query = aux.createNamedQuery("Grup.getPartides");
            query.setParameter("grupId", grupId);
            query.setParameter("torneigId", torneigId);
            query.setParameter("sociId", sociId);
            
            llista = (List<Partida>) query.getResultList();
            if (bLlistaCopia) {
                llista.forEach((p) -> {
                    llPartides.add(new Partida(p));
                });
            }
        } catch (IllegalArgumentException iaex) {
            throw new ComponentSGBDException("Problemes al obtenir llista de partides...\nError: " + iaex.getMessage(), iaex);
        } finally {
            closeEntityManager(aux);
        }
        return (bLlistaCopia) ? llPartides : llista;
    }
    
    @Override
    public List<Object> getClassificacio(int modalitatId, int grupId) throws ComponentSGBDException {
        EntityManager aux = null;
        try {
            aux = getEntityManager();
            Query q = aux.createNamedQuery("Grup.getClassificacio");
            q.setParameter("modalitat_id",modalitatId);
            q.setParameter("grup_id",grupId);
            return q.getResultList();
        } catch(IllegalArgumentException iae) {
            throw new ComponentSGBDException("Error al realitzar la consulta getTornejosActiusOnParticipo! \nError: " + iae.getMessage());
        } finally {
            closeEntityManager(aux);
        }
    }
    
    @Override
    public void refresh(Object obj) throws ComponentSGBDException {
        em.refresh(obj);
    }       

    @Override
    public void close() throws ComponentSGBDException {
        try {
            if (em.isOpen()) closeEntityManager(em);
            if (emf.isOpen()) emf.close();
        } catch(Exception ex) {
            throw new ComponentSGBDException("Hi ha hagut un error al tancar la connexió!", ex);
        }
    }

    //@Override
    //public Soci getSociPerIDSessio(long idSessio) throws ComponentSGBDException {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
}
