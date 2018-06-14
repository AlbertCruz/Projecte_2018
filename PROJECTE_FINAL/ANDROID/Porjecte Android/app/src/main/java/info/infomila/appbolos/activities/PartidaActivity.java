package info.infomila.appbolos.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.infomila.appbolos.adapters.DetallPartidaAdapter;
import info.infomila.appbolos.asyctasks.SendResultatPartidaAsyncTask;
import info.infomila.appbolos.db.HelperDB;
import info.infomila.appbolos.fragment.PartidesFragment;
import info.infomila.appbolos.models.DetallPartida;
import info.infomila.appbolos.models.Partida;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;
import infomila.info.appbolos.R;

public class PartidaActivity extends AppCompatActivity {

    public static final String SESSION_ID = "session_id";
    public static final String SOCI = "soci";
    public static final String TORNEIG = "torneig";
    public static final String PARTIDA = "partida";

    private String sessionId;
    private Torneig torneig;
    private Soci soci;
    private Soci contrincant;
    private Partida partida;

    private HelperDB helperDB;
    private DetallPartida mDetallPartidaA = null;
    private DetallPartida mDetallPartidaB = null;
    private int torn = 0;
    private List<DetallPartida> mLlistaEntradesPartida = new ArrayList<>();

    private DetallPartida mDadesPartidaSociA = null;
    private DetallPartida mDadesPartidaSociB = null;

    // Propietats de la vista
    private TextView txtSociEnCurs;
    private TextView txtSociEnCursTag;
    private TextView txtCarambolesEnCurs;
    private TextView txtEntradaActualSociA;
    private TextView txtNomSociA;
    private TextView txtCarambolesTotalsSociA;
    private TextView txtEntradaActualSociB;
    private TextView txtNomSociB;
    private TextView txtCarambolesTotalsSociB;
    private Button btnRestaCarambola;
    private Button bntSumaCarambola;
    private Button btnCanviarTorn;
    private Button btnCancelarTorn;
    private Button btnAbandonar;
    private RecyclerView mRcvDetallPartida;

    private DetallPartidaAdapter mDetallPartidaAdapter;

    private String guanyadorStr;
    private String modeVictoriaStr;

    private Partida pAux = null;
    private Partida.Guanyador guanyador;
    private Partida.ModeVictoria modeVictoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        if (getIntent().getExtras()!=null) {
            sessionId = (String) getIntent().getExtras().get(SESSION_ID);
            soci = (Soci) getIntent().getExtras().get(SOCI);
            partida = (Partida) getIntent().getExtras().get(PARTIDA);
            torneig = (Torneig) getIntent().getExtras().get(TORNEIG);

            if (partida.getSociA().equals(soci)) {
                contrincant = partida.getSociB();
                mDetallPartidaA = crearDetallPartida(soci,1,0);
                mDadesPartidaSociA = crearDetallPartida(soci,1,0);
                mDetallPartidaB = crearDetallPartida(contrincant,0,0);
                mDadesPartidaSociB = crearDetallPartida(contrincant,0,0);
            } else {
                contrincant = partida.getSociA();
                mDetallPartidaA = crearDetallPartida(contrincant,1,0);
                mDadesPartidaSociA = crearDetallPartida(contrincant,1,0);
                mDetallPartidaB = crearDetallPartida(soci,0,0);
                mDadesPartidaSociB = crearDetallPartida(soci,0,0);
            }
        }
        txtSociEnCurs = findViewById(R.id.txvSociEnCurs);
        txtSociEnCursTag = findViewById(R.id.txvSociEnCursTag);
        txtCarambolesEnCurs = findViewById(R.id.txvCarambolesEnCurs);
        txtEntradaActualSociA = findViewById(R.id.txvEntradaActualSociA);
        txtNomSociA = findViewById(R.id.txvNomSociA);
        txtCarambolesTotalsSociA = findViewById(R.id.txvCarambolesTotalsSociA);
        txtEntradaActualSociB = findViewById(R.id.txvEntradaActualSociB);
        txtNomSociB = findViewById(R.id.txvNomSociB);
        txtCarambolesTotalsSociB = findViewById(R.id.txvCarambolesTotalsSociB);
        btnCanviarTorn = findViewById(R.id.btCanviarTorn);
        btnCanviarTorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastId = helperDB.insertDetall( (torn%2==0) ? mDetallPartidaA : mDetallPartidaB );
                guanyador = null;
                modeVictoria = null;
                if (lastId>0) {
                    actualitzaTotals();
                    if (torn % 2 == 0) {
                        mDetallPartidaA.setPartidaId(lastId);
                        mLlistaEntradesPartida.add(new DetallPartida(mDetallPartidaA));
                        mDetallPartidaA.setCaramboles(0);
                        mDetallPartidaB.setEntrada(mDetallPartidaB.getEntrada() + 1);
                        if (mDadesPartidaSociA.getCaramboles()>=partida.getGrup().getCarambolesVictoria()) {
                            guanyador = Partida.Guanyador.A;
                            modeVictoria = Partida.ModeVictoria.PER_CARAMBOLES;
                        }
                    } else {
                        mDetallPartidaB.setPartidaId(lastId);
                        mLlistaEntradesPartida.add(new DetallPartida(mDetallPartidaB));
                        mDetallPartidaB.setCaramboles(0);
                        mDetallPartidaA.setEntrada(mDetallPartidaA.getEntrada() + 1);
                        if (mDadesPartidaSociB.getCaramboles()>=partida.getGrup().getCarambolesVictoria()) {
                            guanyador = Partida.Guanyador.B;
                            modeVictoria = Partida.ModeVictoria.PER_CARAMBOLES;
                        }
                    }
                    torn++;
                    btnCancelarTorn.setEnabled(true);
                    if (torn/2>=partida.getGrup().getLimitEntrades()) {
                        if (mDadesPartidaSociA.getCaramboles()!=mDadesPartidaSociB.getCaramboles()) {
                            guanyador = (mDadesPartidaSociA.getCaramboles()>mDadesPartidaSociB.getCaramboles()) ? Partida.Guanyador.A : Partida.Guanyador.B;
                            modeVictoria = Partida.ModeVictoria.ENTRADES_ASSOLIDES;
                            btnCanviarTorn.setEnabled(false);
                        }
                    }
                    populateTorn();
                    if (guanyador!=null) finalitzarPartida();
                }
            }
        });



        btnAbandonar = findViewById(R.id.btAbandonar);
        btnAbandonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guanyador = (torn%2==0) ? Partida.Guanyador.B : Partida.Guanyador.A;
                modeVictoria = Partida.ModeVictoria.ABANDONAMENT;
                finalitzarPartida();
            }
        });
        btnCancelarTorn = findViewById(R.id.btCancelarTorn);
        btnCancelarTorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperDB.deleteLastDetallPartida();
                btnCancelarTorn.setEnabled(false);
                torn--;
                DetallPartida dp = mLlistaEntradesPartida.get(mLlistaEntradesPartida.size()-1);
                if (torn%2==0) {
                    mDadesPartidaSociA.setCaramboles(mDadesPartidaSociA.getCaramboles()-dp.getCaramboles());
                    mDadesPartidaSociB.setEntrada(mDadesPartidaSociB.getEntrada()-1);
                } else {
                    mDadesPartidaSociB.setCaramboles(mDadesPartidaSociB.getCaramboles()-dp.getCaramboles());
                    mDadesPartidaSociA.setEntrada(mDadesPartidaSociA.getEntrada()-1);
                }
                mLlistaEntradesPartida.remove(dp);
                mDetallPartidaAdapter.refreshLlistaPartidas(mLlistaEntradesPartida);
                actualitzaTotals();
                populateTorn();
            }
        });
        btnCancelarTorn.setEnabled(false);



        bntSumaCarambola = findViewById(R.id.btSumaCarambola);
        bntSumaCarambola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValorCarambolesEnCurs(1);
            }
        });
        btnRestaCarambola = findViewById(R.id.btRestaCarambola);
        btnRestaCarambola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValorCarambolesEnCurs(-1);
            }
        });
        mRcvDetallPartida = findViewById(R.id.rcvDetallPartida);
        if (mRcvDetallPartida != null) {
            if(mRcvDetallPartida.getAdapter()==null) {
                mDetallPartidaAdapter = new DetallPartidaAdapter(mLlistaEntradesPartida);
                mRcvDetallPartida.setLayoutManager(new LinearLayoutManager(this));
                mRcvDetallPartida.setAdapter(mDetallPartidaAdapter);
            }
        }
        helperDB = new HelperDB(this);
        populateTorn();
    }



    private void actualitzaTotals() {
        mDadesPartidaSociA.setEntrada(mDetallPartidaA.getEntrada());
        mDadesPartidaSociB.setEntrada(mDetallPartidaB.getEntrada());
        mDadesPartidaSociA.setCaramboles(mDadesPartidaSociA.getCaramboles()+mDetallPartidaA.getCaramboles());
        mDadesPartidaSociB.setCaramboles(mDadesPartidaSociB.getCaramboles()+mDetallPartidaB.getCaramboles());
    }

    private void populateTorn() {
        if (torn%2==0) {
            txtSociEnCurs.setText(mDetallPartidaA.getSociNom());
            txtSociEnCursTag.setText(mDetallPartidaA.getSociTag());
        } else {
            txtSociEnCurs.setText(mDetallPartidaB.getSociNom());
            txtSociEnCursTag.setText(mDetallPartidaB.getSociTag());
        }
        txtCarambolesEnCurs.setText(0+"");
        mDetallPartidaAdapter.refreshLlistaPartidas(mLlistaEntradesPartida);
        txtEntradaActualSociA.setText(mDadesPartidaSociA.getEntrada()+"");
        txtNomSociA.setText(mDadesPartidaSociA.getSociNom());
        txtCarambolesTotalsSociA.setText(mDadesPartidaSociA.getCaramboles()+"");
        txtEntradaActualSociB.setText(mDadesPartidaSociB.getEntrada()+"");
        txtNomSociB.setText(mDadesPartidaSociB.getSociNom());
        txtCarambolesTotalsSociB.setText(mDadesPartidaSociB.getCaramboles()+"");
    }
    private void setValorCarambolesEnCurs(int i) {
        int caramboles = Integer.parseInt(txtCarambolesEnCurs.getText().toString());
        caramboles += i;
        if (caramboles<0) caramboles = 0;
        if (caramboles>partida.getGrup().getCarambolesVictoria()) caramboles = partida.getGrup().getCarambolesVictoria();

        if (torn%2==0) {
            mDetallPartidaA.setCaramboles(caramboles);
        } else {
            mDetallPartidaB.setCaramboles(caramboles);
        }

        txtCarambolesEnCurs.setText(caramboles+"");
    }

    private void finalitzarPartida() {
        if (guanyador!=null && modeVictoria!=null) {
            guanyadorStr = (guanyador == Partida.Guanyador.A) ? mDadesPartidaSociA.getSociNom() : mDadesPartidaSociB.getSociNom();
            switch (modeVictoria) {
                case PER_CARAMBOLES:
                    modeVictoriaStr = "per caramboles!";
                    break;
                case ENTRADES_ASSOLIDES:
                    modeVictoriaStr = "per entrades!";
                    break;
                case ABANDONAMENT:
                    modeVictoriaStr = "per abandonament del contrincant!";
                    break;
            }
            pAux = new Partida(partida);
            pAux.setEntradesA(mDadesPartidaSociA.getEntrada());
            pAux.setEntradesB(mDadesPartidaSociB.getEntrada());
            pAux.setEntradesTotals(mDadesPartidaSociA.getEntrada() + mDadesPartidaSociB.getEntrada());
            pAux.setCarambolesA(mDadesPartidaSociA.getCaramboles());
            pAux.setCarambolesB(mDadesPartidaSociB.getCaramboles());
            pAux.setGuanyador(guanyador);
            pAux.setModeVictoria(modeVictoria);
            pAux.setEstatPartida(Partida.EstatPartida.JUGAT);

            SendResultatPartidaAsyncTask connect = new SendResultatPartidaAsyncTask(this, pAux);
            connect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionId);
        }
    }

    private String getTagSoci(Soci s) {
        return (partida.getSociA().equals(s)) ? "A" : "B";
    }

    private DetallPartida crearDetallPartida(Soci s, int entrada, int caramboles) {
        return new DetallPartida(partida.getId(), entrada, null, s.getId(), s.getNomComplert(), getTagSoci(s), caramboles);
    }



    public void resultatPartidaRequest(Boolean bPartidaUpdated) {
        if (bPartidaUpdated && pAux!=null) {
            partida.setEntradesA(pAux.getEntradesA());
            partida.setEntradesB(pAux.getEntradesB());
            partida.setEntradesTotals(pAux.getEntradesTotals());
            partida.setCarambolesA(pAux.getCarambolesA());
            partida.setCarambolesB(pAux.getCarambolesB());
            partida.setGuanyador(pAux.getGuanyador());
            partida.setModeVictoria(pAux.getModeVictoria());
            partida.setEstatPartida(pAux.getEstatPartida());

            pAux = null;
            guanyador = null;
            modeVictoria = null;

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("S'ha acabat la partida")
                    .setMessage("La partida s'ha acabat. Guanyador --> '" + guanyadorStr + "' " + modeVictoriaStr)
                    .setPositiveButton("Tornar a la llista de partides", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            helperDB.deleteDetallPartida(partida.getId());
                            guanyadorStr = "";
                            modeVictoriaStr = "";

                            Intent intent = new Intent();
                            intent.putExtra(PartidesFragment.SESSION_ID, sessionId);
                            intent.putExtra(PartidesFragment.SOCI, soci);
                            intent.putExtra(PartidesFragment.TORNEIG, torneig);
                            intent.putExtra(PartidesFragment.PARTIDA, partida);
                            setResult(3, intent);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        } else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Error al enviar resultats")
                    .setMessage("Ha surgit un error...")
                    .setNegativeButton("Sortir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            helperDB.deleteDetallPartida(partida.getId());
                            guanyadorStr = "";
                            modeVictoriaStr = "";

                            Intent intent = new Intent();
                            intent.putExtra(PartidesFragment.SESSION_ID, sessionId);
                            intent.putExtra(PartidesFragment.SOCI, soci);
                            intent.putExtra(PartidesFragment.TORNEIG, torneig);
                            setResult(3, intent);
                            finish();
                        }
                    })
                    .setPositiveButton("Torna-hi", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finalitzarPartida();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Retirada")
                .setMessage("Realment vols retirar-te?")
                .setNegativeButton("No", null)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        helperDB.deleteDetallPartida(partida.getId());
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
