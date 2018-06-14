package info.infomila.appbolos.menus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import info.infomila.appbolos.connexio.Connector;
import infomila.info.appbolos.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MenuFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        String ip = prefs.getString("ip", "127.0.0.1");
        Integer port = Integer.parseInt(prefs.getString("port", "1234"));
        Connector.SERVER_IP = ip;
        Connector.SERVER_PORT = port;
        super.onBackPressed();
    }

}
