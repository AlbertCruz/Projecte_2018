package info.infomila.appbolos.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.sql.Blob;

import info.infomila.appbolos.asyctasks.LoginAsyncTask;
import info.infomila.appbolos.asyctasks.PerfilAsyncTask;
import info.infomila.appbolos.connexio.SingletonConnection;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.utils.MD5Encrypter;
import infomila.info.appbolos.R;

public class PerfilActivity extends AppCompatActivity {

    public static final String SOCI = "soci";
    public static final String SESSION_ID = "session_id";

    private static Soci soci;
    private static String session_id;

    private EditText etNif;
    private EditText etNom;
    private EditText etCognom;
    private EditText etCognom2;
    private EditText etPaswd;
    private ImageView imgFoto;
    private Context context;
    private Soci oSoci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork().penaltyLog().build());

        setContentView(R.layout.activity_perfil);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        soci = (Soci)getIntent().getExtras().get(SOCI);
        session_id = (String)getIntent().getExtras().get(SESSION_ID);

        etNif = (EditText) findViewById(R.id.txtNif);
        etNom = (EditText) findViewById(R.id.txtNom);
        etCognom = (EditText) findViewById(R.id.txtCog1);
        etCognom2 = (EditText) findViewById(R.id.txtCog2);
        etPaswd = (EditText) findViewById(R.id.txtPswd);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);

        context = this;

        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNif.getText().toString().isEmpty() || etNom.getText().toString().isEmpty() || etCognom.getText().toString().isEmpty() ||
                        etPaswd.getText().toString().isEmpty()){
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
                    String nif = etNif.getText().toString();
                    String nom = etNom.getText().toString();
                    String cognom1 = etCognom.getText().toString();
                    String cognom2 = etCognom2.getText().toString();
                    String password = MD5Encrypter.Encriptar(etPaswd.getText().toString());
                    //soci.setNom(nom);
                    //soci.setCognom1(cognom1);
                    //soci.setCognom2(cognom2);
                    //soci.setPassword(password);

                    Soci ss = new Soci(nif,nom,cognom1,cognom2,password,null,true);
                      //Falta foto
                  SingletonConnection.soci = ss;
                  PerfilAsyncTask task = new PerfilAsyncTask(context,ss);
                  task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,session_id);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    finish();
                }catch (Throwable ex){

                }

            }
        });
        getSoci(soci);
    }

    private void getSoci(Soci soci) {
        try{
            etNif.setText(soci.getNif());
            etNom.setText(soci.getNom());
            etCognom.setText(soci.getCognom1());
            etCognom2.setText(soci.getCognom2());
            etPaswd.setText(MD5Encrypter.Desencriptar(soci.getPassword()));
            /*if(soci.getBytesFoto()!=null) {
                /*Blob blob=soci.getFoto();
                int blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                imgFoto.setImageBitmap(BitmapFactory.decodeByteArray( soci.getBytesFoto(), 0,
                        soci.getBytesFoto().length));
            }*/
        }catch(Exception ex) {

        }
    }

    public void onPerfilResult(Boolean result){
        if(!result){
            new AlertDialog.Builder(this)
                    .setTitle("Conexió Fallida!")
                    .setMessage("No s'ha pogut desar les dades del soci")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            //Enregistrem l'id d'usuari
            new AlertDialog.Builder(this)
                    .setTitle("OK")
                    .setMessage("Les dades s'han guardat correctament")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
    }
}
