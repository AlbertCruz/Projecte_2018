/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import info.infomila.appbolos.models.Grup;
import info.infomila.appbolos.models.Modalitat;
import info.infomila.appbolos.models.Partida;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alber
 */
public interface IComponentSGBD {    
                                    
    /**
     * Mètode per tancar la conexió amb l'origen de dades.
     */
    public void tancarConexio(long idSessio);
    /**
     * Mètode per fer commit.
     * @return, retorna 0 si s'ha pogut realitzar el commit, negatiu en cas contrari.
     */
    public void commit() throws ComponentSGBDException;;
    void close() throws ComponentSGBDException;    
    /**
     * Mètode per fer rollback.
     * @return, retorna 0 si s'ha pogut realitzar el commit, negatiu en cas contrari.
     */
    public void rollback() throws ComponentSGBDException;;
    /**
     * Mètode per borrar un soci.
     * @return, retorna 0 si s'ha pogut realitzar el commit, negatiu en cas contrari.
     */
    public int borrarSoci(int dni);    
    public List<Soci> getLlistatSocis();    
    public List<Soci> getSociPerDni(String dni) throws ComponentSGBDException;    
    public List<Soci> getSociPerNomCognoms(String nom, String cognom1, String cognom2) throws ComponentSGBDException;    
    public Soci crearNouSoci(Soci s) throws ComponentSGBDException;    
    public boolean existeixSoci(int idSoci);    
    public Soci modificarSoci(Soci s) throws ComponentSGBDException;  
    public List<Modalitat> getLlistaModalitats() throws ComponentSGBDException;     
    public long login(String user, String password) throws ComponentSGBDException; 
    public Soci loginSoci(String user, String password) throws ComponentSGBDException; 
    //public Soci getSociPerIDSessio (long idSessio) throws ComponentSGBDException;
    //public List<Torneig> getTornejosActiusPerSoci(int id);    
    //List<Soci> getSocis(int mode) throws BillarPersistenceException;            
    //Soci loginSoci(String user, String pass) throws BillarPersistenceException;    
    public Torneig getTorneigById(int id) throws ComponentSGBDException;    
    public List<Torneig> getTornejosActius(int sociId, boolean bLlistaCopia) throws ComponentSGBDException;    
    public List<Torneig> getTornejosOberts(int sociId) throws ComponentSGBDException;    
    public Boolean ferInscripcio(Soci s, Torneig t) throws ComponentSGBDException;    
    public Partida getPartidaById(int id) throws ComponentSGBDException;
    public void modificarPartida(Partida p) throws ComponentSGBDException;    
    public List<Partida> getPartides(int grupId, int torneigId, int sociId, boolean bLlistaCopia) throws ComponentSGBDException;    
    public Grup getGrupTorneigOfSoci(int torneigId, int sociId) throws ComponentSGBDException;    
    public List<Object> getClassificacio(int modalitatId, int grupId) throws ComponentSGBDException;    
    public void refresh(Object obj) throws ComponentSGBDException;           
}
