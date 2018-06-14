/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import info.infomila.ComponentSGBD;
import info.infomila.ComponentSGBDException;
import info.infomila.IComponentSGBD;
import info.infomila.gestio.UserOnline;
import info.infomila.appbolos.models.Soci;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author alber
 */
public class Servidor{
    
    
    public static Map<String, Soci> usersMap = new HashMap<>();
    private static int usuarisConectats = 0;
    
    public static void main(String[] args) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("Configuracio.properties"));
        } catch (IOException ex) {
            System.out.println("Error al llegir el fitxer de propietats!");
            System.exit(1);
        }
        String nomClasse = p.getProperty("className");
        if (nomClasse==null || nomClasse.length()==0) {
            System.out.println("Es obligatóri indicar el nom de la classe [className] al fitxer de propietats 'Config.properties'!");
            System.exit(1);
        }
        String ipServer = p.getProperty("ipServer");
        if (ipServer==null || ipServer.length()==0) {
            System.out.println("Es obligatóri indicar el nom de la classe [ipServer] al fitxer de propietats 'Config.properties'!");
            System.exit(1);
        }
        int portServer = 0;
        String portServerStr = p.getProperty("portServer");
        if (portServerStr==null || portServerStr.length()==0) {
            System.out.println("Es obligatóri indicar el nom de la classe [portServer] al fitxer de propietats 'Config.properties'!");
            System.exit(1);
        } else {
            try {
                portServer = Integer.parseInt(portServerStr);
            } catch (NumberFormatException ex) {
                System.out.println("El port indicat no es vàlid!");
                System.exit(1);
            }
        }
        
        IComponentSGBD billar = null;
        try {
            String nomFitxerPropietats = p.getProperty("configHibernateFile");
            if (nomFitxerPropietats==null || nomFitxerPropietats.length()==0) {
                billar = ComponentSGBD.getInstance(nomClasse);
            } else {
                billar = ComponentSGBD.getInstance(nomClasse, nomFitxerPropietats);
            }
        } catch (ComponentSGBDException ex) {
            System.out.println("Error al crear la interfície billar!");
        }
        
        ServerSocket sockServer = null;
        try {
            InetAddress addr = InetAddress.getByName(ipServer);
            sockServer = new ServerSocket(portServer,50,addr);
           
            System.out.println("INICIANT SERVER...");
            
            while (true){
                Socket sockCli = sockServer.accept();
                System.out.println("CLIENT CONECTAT...");
                FilGestorClients t = new FilGestorClients(sockCli,usuarisConectats,billar);
                usuarisConectats++;
                t.start();
            }
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (sockServer!=null && !sockServer.isClosed()) {
                try {            
                      sockServer.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public static void addUser(String sessionID, Soci soci) {
        usersMap.put(sessionID, soci);
    }
    
    public static Soci getSociBySessionID(String sessionID)
    {
        return usersMap.get(sessionID);
    }    
    private UserOnline user;                                   
}
