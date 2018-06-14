package info.infomila.appbolos.connexio;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import info.infomila.appbolos.activities.LoginActivity;
import info.infomila.appbolos.models.Classificacio;
import info.infomila.appbolos.models.Partida;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;

/**
 * Created by alber
 */

public class Connector {

    //private static String SERVER_IP = "192.168.0.164";
    public static String SERVER_IP;
    public static int SERVER_PORT;

    public static void run() {
        try {

            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Soci Login(String nif, String pass) {
        Soci user = null;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            ObjectInputStream dataEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream dataSalida = new ObjectOutputStream(socket.getOutputStream());
            dataSalida.writeInt(1);
            dataSalida.flush();
            dataSalida.writeObject(nif);
            dataSalida.flush();
            dataSalida.writeObject(pass);
            dataSalida.flush();

            try {
                switch (dataEntrada.readInt()) {
                    case 1:
                        String sessionId = (String)dataEntrada.readObject();
                        LoginActivity.editor.putString("session_id", sessionId);
                        user = (Soci)dataEntrada.readObject();
                        break;
                }
            } catch (ClassNotFoundException e) {
                socket.close();
            }

            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static List<Torneig> getTornejosOberts(String sessionId) {
        List<Torneig> llTorneig = null;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            ObjectInputStream dataEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream dataSalida = new ObjectOutputStream(socket.getOutputStream());

            dataSalida.writeInt(4);
            dataSalida.flush();
            dataSalida.writeObject(sessionId);
            dataSalida.flush();

            try {
                switch (dataEntrada.readInt()) {
                    case 1:
                        llTorneig = (List<Torneig>) dataEntrada.readObject();
                        break;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return llTorneig;
    }

    public static List<Torneig> getTornejosOnParticipo(String sessionId) {
        List<Torneig> llTorneig = null;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            ObjectInputStream dataEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream dataSalida = new ObjectOutputStream(socket.getOutputStream());

            dataSalida.writeInt(3);
            dataSalida.flush();
            dataSalida.writeObject(sessionId);
            dataSalida.flush();

            try {
                switch (dataEntrada.readInt()) {
                    case 1:
                        llTorneig = (List<Torneig>) dataEntrada.readObject();
                        break;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return llTorneig;
    }

    public static Boolean ferInscripcio(String sessionId, int torneigId) {
        Boolean bOk = false;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            ObjectInputStream dataEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream dataSalida = new ObjectOutputStream(socket.getOutputStream());

            dataSalida.writeInt(5);
            dataSalida.flush();
            dataSalida.writeObject(sessionId);
            dataSalida.flush();
            dataSalida.writeInt(torneigId);
            dataSalida.flush();

            int res = dataEntrada.readInt();

            switch (res) {
                case 1:
                    bOk = dataEntrada.readBoolean();
                    break;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  bOk;
    }

    public static Boolean updateSoci(String sessionId, Soci mSoci) {
        Boolean bOk = false;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            ObjectInputStream dataEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream dataSalida = new ObjectOutputStream(socket.getOutputStream());

            dataSalida.writeInt(2);
            dataSalida.flush();
            dataSalida.writeObject(sessionId);
            dataSalida.flush();
            dataSalida.writeObject(mSoci);
            dataSalida.flush();

            if (dataEntrada.readInt()==1) bOk = dataEntrada.readBoolean();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  bOk;
    }

    public static List<Classificacio> getClassificacio(String sessionId, int sociId, int torneigId, int modalitatId) {
        List<Classificacio> llClassificacio = null;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            ObjectInputStream dataEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream dataSalida = new ObjectOutputStream(socket.getOutputStream());

            dataSalida.writeInt(8);
            dataSalida.flush();
            dataSalida.writeObject(sessionId);
            dataSalida.flush();
            dataSalida.writeInt(sociId);
            dataSalida.flush();
            dataSalida.writeInt(torneigId);
            dataSalida.flush();
            dataSalida.writeInt(modalitatId);
            dataSalida.flush();

            try {
                if (dataEntrada.readInt()==1) {
                    llClassificacio = (List<Classificacio>) dataEntrada.readObject();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return llClassificacio;
    }

    public static List<Partida> getPartides(String sessionId, int torneigId) {
        List<Partida> llPartides = null;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            ObjectInputStream dataEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream dataSalida = new ObjectOutputStream(socket.getOutputStream());

            dataSalida.writeInt(6);
            dataSalida.flush();
            dataSalida.writeObject(sessionId);
            dataSalida.flush();
            dataSalida.writeInt(torneigId);
            dataSalida.flush();

            try {
                if (dataEntrada.readInt()==1) {
                    llPartides = (List<Partida>) dataEntrada.readObject();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return llPartides;
    }

    public static Boolean SendResultatPartida(String sessionId, Partida mPartida) {
        Boolean bOk = false;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            ObjectInputStream dataEntrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream dataSalida = new ObjectOutputStream(socket.getOutputStream());

            dataSalida.writeInt(7);
            dataSalida.flush();
            dataSalida.writeObject(sessionId);
            dataSalida.flush();
            dataSalida.writeObject(mPartida);
            dataSalida.flush();

            bOk = dataEntrada.readBoolean();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  bOk;
    }

}
