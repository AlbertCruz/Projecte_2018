/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import info.infomila.model.Modalitat;
import info.infomila.model.Soci;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mr. Robot
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
    public int commit();
    
    /**
     * Mètode per fer rollback.
     * @return, retorna 0 si s'ha pogut realitzar el commit, negatiu en cas contrari.
     */
    public int rollback();

    /**
     * Mètode per borrar un soci.
     * @return, retorna 0 si s'ha pogut realitzar el commit, negatiu en cas contrari.
     */
    public int borrarSoci(int dni);
    
    public List<Soci> getLlistatSocis();
    
    public List<Soci> getSociPerDni(String dni) throws IComponentSGBDException;
    
    public List<Soci> getSociPerNomCognoms(String nom, String cognom1, String cognom2) throws IComponentSGBDException;
    
    public Soci crearNouSoci(Soci s) throws IComponentSGBDException;
    
    public boolean existeixSoci(int idSoci);
    
    public Soci modificarSoci(Soci s) throws IComponentSGBDException;
    
    public List<Modalitat> getLlistaModalitats() throws IComponentSGBDException;
}
