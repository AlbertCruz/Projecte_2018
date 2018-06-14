/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author alber
 */
public class ComponentSGBD {
    private ComponentSGBD() {}
    
    public static IComponentSGBD getInstance(String nomClasse) throws ComponentSGBDException {
        return getInstance(nomClasse,null);
    }
    
    public static IComponentSGBD getInstance(String nomClasse, String nomFitxerPropietats) throws ComponentSGBDException {
        IComponentSGBD obj;
        if (nomClasse == null) {
            throw new ComponentSGBDException("Nom de la classe erroni.");
        }
        try {
            if (nomFitxerPropietats == null) {
                // S'invoca constructor sense paràmetres
                obj = (IComponentSGBD) Class.forName(nomClasse).newInstance();
            } else {
                // S'invoca constructor amb 1 paràmetre
                Class c = Class.forName(nomClasse);
                Constructor co = c.getConstructor(String.class);
                obj = (IComponentSGBD) co.newInstance(nomFitxerPropietats);
            }
            return obj;
        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ComponentSGBDException("No es pot crear l'objecte de la classe " + nomClasse + "\n" + ex.getMessage(), ex);
        }
    }
}
