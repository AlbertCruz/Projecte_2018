package info.infomila.appbolos.asyctasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import info.infomila.appbolos.activities.PerfilActivity;
import info.infomila.appbolos.connexio.Connector;
import info.infomila.appbolos.connexio.SingletonConnection;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.utils.MD5Encrypter;

public class PerfilAsyncTask extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private Socket socket;
    private String ip;
    private  int port;
    private Soci oSoci;

    public PerfilAsyncTask(Context context, Soci soci) {
        this.context = context;

        try {
            if(!SingletonConnection.hasSocket()){
                SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(context);
                ip = prefs.getString("ip", "127.0.0.1");
                port = Integer.parseInt(prefs.getString("port", "1234"));
                SingletonConnection.setSocket(new Socket(ip, port));
            }
            socket = SingletonConnection.getSocket();
            oSoci = soci;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Boolean doInBackground(String... params) {
        return Connector.updateSoci(params[0], oSoci);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        //super.onPostExecute(result);
        ((PerfilActivity) context).onPerfilResult(result);
    }

}