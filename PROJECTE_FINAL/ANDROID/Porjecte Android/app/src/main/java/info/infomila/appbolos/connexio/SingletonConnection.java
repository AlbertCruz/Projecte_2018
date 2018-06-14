package info.infomila.appbolos.connexio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import info.infomila.appbolos.models.Soci;

public class SingletonConnection {
    private static Socket socket=null;
    public static Soci soci = null;
    public static DataInputStream dis = null;
    public static DataOutputStream dos = null;

    public static boolean hasSocket(){
        return SingletonConnection.socket!=null;
    }
    public static void setSocket(Socket socketpass) {

        SingletonConnection.socket = socketpass;
        try {
            dis = new DataInputStream(socketpass.getInputStream());
            dos = new DataOutputStream(socketpass.getOutputStream());
        } catch (Exception ex){

        }
    }

    public static Socket getSocket(){
        return SingletonConnection.socket;
        //return socket;
    }

}
