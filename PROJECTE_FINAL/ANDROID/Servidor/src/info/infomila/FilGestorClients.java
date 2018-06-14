/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila;

import com.mysql.jdbc.Blob;
import info.infomila.ComponentSGBDException;
import info.infomila.IComponentSGBD;
import info.infomila.appbolos.models.Classificacio;
import info.infomila.appbolos.models.Grup;
import info.infomila.appbolos.models.Partida;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.rowset.serial.SerialBlob;
import sun.misc.BASE64Encoder;

/**
 *
 * @author alber
 */
public class FilGestorClients extends Thread{
 
    
    
    private Socket socket;
    private int numCLient;
    private String sessionID;
    private Socket socketClient;
    private IComponentSGBD dbConnection;
   // private ObjectInputStream dis;
   // private ObjectOutputStream dos;
    private static long codiSoci;
    
    public FilGestorClients(Socket s, int numCli, IComponentSGBD ib){        
        this.numCLient = numCli;
        this.socket = s;
        this.dbConnection = ib;       
    }
    
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    private String generateSessionID(){
        SecureRandom r = new SecureRandom();
        byte[] aesKey = new byte[16];
        r.nextBytes(aesKey);
        return bytesToHex(aesKey);
    }
    
    
    

    @Override
    public void run() {
        int request = 0;    
        Soci s = null;
        ObjectInputStream dis = null;
        ObjectOutputStream dos = null;
        
            
            try {
                dos = new ObjectOutputStream(socket.getOutputStream());
                dis = new ObjectInputStream(socket.getInputStream());        
                request = dis.readInt();            
                System.out.println("REQUEST: " +request);
                switch (request) {

                    case 1:

                        String user = (String) dis.readObject();
                        String password = (String) dis.readObject();

                    try {
                        String psswd = Encriptar(String.valueOf(password));
                        s = dbConnection.loginSoci(user, psswd);
                           
                        if (s == null){
                            System.out.println("Soci no vàlid!");
                            dos.writeInt(-1);
                        } else {
                            System.out.println("Soci vàlid!");
                            dos.writeInt(1);

                            sessionID = generateSessionID();
                            Servidor.addUser(sessionID, s);

                            dos.writeObject(sessionID);
                            dos.flush();

                            dos.writeObject(new info.infomila.appbolos.models.Soci(s));
                            dos.flush();
                        }            
                            
                    } catch (ComponentSGBDException ex) {
                        Logger.getLogger(FilGestorClients.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                        break;

                    case 2:  
                        sessionID = (String) dis.readObject();
                        System.out.println("Session id usuario: " + sessionID);
                        Soci soci = (Soci) dis.readObject();
                        s = Servidor.getSociBySessionID(sessionID);
                        if (s==null || soci==null) {
                            System.out.println("Sessio no valida");
                            dos.writeInt(-1);
                        } else {
                            System.out.println("Sessio vàlida");
                            dos.writeInt(1);

                            s.setNom(soci.getNom());
                            s.setCognom1(soci.getCognom1());
                            s.setCognom2(soci.getCognom2());
                            s.setPasswordHash(soci.getPasswordHash());

                            Boolean bOk;
                            try {
                                dbConnection.modificarSoci(s);
                                dbConnection.commit();
                                bOk = true;
                            } catch (ComponentSGBDException ex) {
                                dbConnection.rollback();
                                bOk = false;
                            }

                            dos.writeBoolean(bOk);
                            dos.flush();
                        }
                    break;


                    case 3: // LLISTA DE ON PARTICIPO
                    sessionID = (String)dis.readObject();
                    System.out.println("Session id usuario: " + sessionID);
                    s = Servidor.getSociBySessionID(sessionID);
                    if (s==null) {
                        System.out.println("Sessio no valida");
                        dos.writeInt(-1);
                    } else {
                        System.out.println("Sessio vàlida");
                        dos.writeInt(1);
                        
                        List<Torneig> llista = dbConnection.getTornejosActius(s.getId(), true);
                        dos.writeObject(llista);
                        dos.flush();
                    }
                    break;
                case 4: // LLISTA DE TORNEJOS OBERTS
                    sessionID = (String)dis.readObject();
                    System.out.println("Session id usuario: " + sessionID);
                    s = Servidor.getSociBySessionID(sessionID);
                    if (s==null) {
                        System.out.println("Sessio no valida");
                        dos.writeInt(-1);
                    } else {
                        System.out.println("Sessio vàlida");
                        dos.writeInt(1);
                        
                        List<Torneig> llista = dbConnection.getTornejosOberts(s.getId());
                        List<Torneig> llTorneig = new ArrayList();
                        for (Torneig t : llista) {
                            llTorneig.add(new Torneig(t));
                        }
                        dos.writeObject(llTorneig);
                        dos.flush();
                    }
                    break;
                case 5: // FER INSCRIPCIO
                    sessionID = (String)dis.readObject();
                    int torneigId = dis.readInt();
                    System.out.println("Session id usuario: " + sessionID);
                    s = Servidor.getSociBySessionID(sessionID);
                    if (s==null) {
                        System.out.println("Sessio no valida");
                        dos.writeInt(-1);
                        dos.flush();
                    } else {
                        System.out.println("Sessio vàlida");
                        dos.writeInt(1);
                        dos.flush();
                        
                        Torneig t = dbConnection.getTorneigById(torneigId);
                        
                        Boolean bOk;
                        try {
                            bOk = dbConnection.ferInscripcio(s, new Torneig(t));
                            if (bOk) dbConnection.commit(); else dbConnection.rollback();
                        } catch (ComponentSGBDException bpe) {
                            bOk = false;
                            dbConnection.rollback();
                        }
                        dos.writeBoolean(bOk);
                        dos.flush();
                    }
                    break;
                case 6: // GET PARTIDES
                    sessionID = (String) dis.readObject();
                    int torId = dis.readInt();
                    System.out.println("Session id usuario: " + sessionID);
                    s = Servidor.getSociBySessionID(sessionID);
                    if (s==null) {
                        System.out.println("Sessio no valida");
                        dos.writeInt(-1);
                        dos.flush();
                    } else {
                        System.out.println("Sessio vàlida");
                        
                        Grup grup = dbConnection.getGrupTorneigOfSoci(torId, s.getId());
                        if (grup!=null) {
                            dos.writeInt(1);
                            dos.flush();
                            
                            List<Partida> partides = dbConnection.getPartides(grup.getId(), torId, s.getId(), true);

                            dos.writeObject(partides);
                            dos.flush();
                        } else {
                            dos.writeInt(-1);
                            dos.flush();
                        }
                    }
                    break;
                case 7: // MODIFICAR PARTIDA
                    sessionID = (String)dis.readObject();
                    Partida pAux = (Partida)dis.readObject();
                    System.out.println("Session id usuario: " + sessionID);
                    s = Servidor.getSociBySessionID(sessionID);
                    if (s==null || pAux==null) {
                        System.out.println("Sessio no valida");
                        dos.writeBoolean(false);
                        dos.flush();
                    } else {
                        System.out.println("Sessio vàlida");     
                        Partida p = dbConnection.getPartidaById(pAux.getId());                   
                        if (p!=null) {
                            try {
                                // Establim dades
                                p.setEntradesA(pAux.getEntradesA());
                                p.setEntradesB(pAux.getEntradesB());
                                p.setEntradesTotals(pAux.getEntradesTotals());
                                p.setCarambolesA(pAux.getCarambolesA());
                                p.setCarambolesB(pAux.getCarambolesB());
                                p.setGuanyador(pAux.getGuanyador());
                                p.setModeVictoria(pAux.getModeVictoria());
                                p.setEstatPartida(pAux.getEstatPartida());
                                
                                // Fem update
                                dbConnection.modificarPartida(p);
                                dbConnection.commit();
                                dos.writeBoolean(true);
                                dos.flush();
                            } catch (ComponentSGBDException ex) {
                                dos.writeBoolean(false);
                                dos.flush();
                            }
                        } else {
                            dos.writeBoolean(false);
                            dos.flush();
                        }
                    }
                    break;
                case 8: // LLISTA CLASSIFICACIONS
                    sessionID = (String)dis.readObject();
                    System.out.println("Session id usuario: " + sessionID);
                    int sId = dis.readInt();
                    int tId = dis.readInt();
                    int mId = dis.readInt();
                    s = Servidor.getSociBySessionID(sessionID);
                    if (s==null) {
                        System.out.println("Sessio no valida");
                        dos.writeInt(-1);
                    } else {
                        System.out.println("Sessio vàlida");
                        
                        Grup g = dbConnection.getGrupTorneigOfSoci(tId, sId);
                        if (g!=null) {
                            dos.writeInt(1);
                            List<Object> llista = dbConnection.getClassificacio(mId, g.getId());
                            List<Classificacio> llistaClassifica = new ArrayList();
                            
                            for (Object object : llista) {
                                Object[] obj = (Object[])object;
                                String nomSoci = ((String)obj[1]!=null) ? (String)obj[1] : "";
                                nomSoci += ((String)obj[2]!=null) ? " "+(String)obj[2] : "";
                                nomSoci += ((String)obj[3]!=null) ? " "+(String)obj[3] : "";
                                Double coefBase = (obj[4]!=null) ? (Double)obj[4] : 0;
                                Long totals = (obj[5]!=null) ? (Long)obj[5] : 0;
                                Long guanyades = (obj[6]!=null) ? (Long)obj[6] : 0;
                                Long perdudes = (obj[7]!=null) ? (Long)obj[7] : 0;
                                
                                Classificacio c = new Classificacio(nomSoci, Math.toIntExact(totals), Math.toIntExact(guanyades), Math.toIntExact(perdudes), coefBase);
                                llistaClassifica.add(c);
                            }

                            dos.writeObject(llistaClassifica);
                            dos.flush();
                        } else {
                            dos.writeInt(-1);
                        }
                    }
                    break;
                }
            
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilGestorClients.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ComponentSGBDException ex) {
            Logger.getLogger(FilGestorClients.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}
    public static String Encriptar(String texto) {
 
        String secretKey = "claveEncriptar"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = org.apache.commons.codec.binary.Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
 
    public static String Desencriptar(String textoEncriptado) throws Exception {
 
        String secretKey = "claveEncriptar"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = org.apache.commons.codec.binary.Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

}
