package info.infomila.appbolos.asyctasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import info.infomila.appbolos.activities.LoginActivity;
import info.infomila.appbolos.connexio.Connector;
import info.infomila.appbolos.connexio.SingletonConnection;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.utils.MD5Encrypter;


public class LoginAsyncTask extends AsyncTask <String, String, Soci>{


    private LoginActivity loginActivity;

    public LoginAsyncTask(LoginActivity pActivity){ loginActivity = pActivity; }

    @Override
    protected Soci doInBackground(String... params) {
        Soci soci = Connector.Login(params[0],params[1]);
        return soci;
    }

    @Override
    protected void onPostExecute(Soci s) {
            super.onPostExecute(s);
            loginActivity.onLoginResult(s);
    }
}
