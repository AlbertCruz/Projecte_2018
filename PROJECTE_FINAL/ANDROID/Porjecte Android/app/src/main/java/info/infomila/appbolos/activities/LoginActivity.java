package info.infomila.appbolos.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.infomila.appbolos.asyctasks.LoginAsyncTask;
import info.infomila.appbolos.connexio.Connector;
import info.infomila.appbolos.connexio.SingletonConnection;
import info.infomila.appbolos.menus.MenuActivity;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.utils.MD5Encrypter;
import infomila.info.appbolos.R;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_DATA = "data_user";
    public static SharedPreferences pref = null;
    public static SharedPreferences.Editor editor = null;
    private EditText etUser;
    private EditText etPassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getApplicationContext().getSharedPreferences(USER_DATA, 0);
        editor = pref.edit();

        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        String ip = prefs.getString("ip", "127.0.0.1");
        Integer port = Integer.parseInt(prefs.getString("port", "1234"));
        Connector.SERVER_IP = ip;
        Connector.SERVER_PORT = port;

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork().penaltyLog().build());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //editor.remove("nif");
        //editor.remove("password");
        //editor.remove("session_id");
        //editor.commit();
        etUser = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);

        context = this;

        Button btnEntra = (Button) findViewById(R.id.btnLogin);
        String nif = pref.getString("nif", null);
        String password = pref.getString("password", null);
        String pswd = "";
        try {
            pswd = MD5Encrypter.Desencriptar(password);
        } catch (Exception e) {
            password = "";
        }
        String sessionId = pref.getString("session_id", null);

        if (nif != null && pswd != null && sessionId != null) {
            //enviaLoginRequest(nif, pswd);
            etUser.setText(nif);
            etPassword.setText(pswd);
        }

        btnEntra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUser.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()){
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("Usuari o contrasenya necessaris")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    enviaLoginRequest(etUser.getText().toString(), etPassword.getText().toString());
                }
            }
        });

    }

    public void onClickMenu(View view){
        registerForContextMenu(view);
        openContextMenu(view);

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void enviaLoginRequest(String nif, String pass) {
        LoginAsyncTask connect = new LoginAsyncTask(this);
        connect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, nif, pass);
    }

    public void onClickEntrar(View view){




    }

    public void onLoginResult(Soci result){
        if(result == null){
            new AlertDialog.Builder(this)
                    .setTitle("Conexió Fallida!")
                    .setMessage("Usuari o contrasenya invàlids")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            //Enregistrem l'id d'usuari
            editor.putString("nif", result.getNif());
            editor.putString("password", result.getPassword());
            editor.commit();

            Toast.makeText(getBaseContext(), "Login success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), IniciActivity.class);
            intent.putExtra(IniciActivity.SOCI, result);
            intent.putExtra(IniciActivity.SESSION_ID, pref.getString("session_id", null));
            //SingletonConnection.soci = result;
            startActivity(intent);
        }
    }
}
