package info.infomila.appbolos.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.infomila.appbolos.activities.PartidaActivity;
import info.infomila.appbolos.adapters.PartidaAdapter;
import info.infomila.appbolos.adapters.listeners.PartidaClickListener;
import info.infomila.appbolos.asyctasks.PartidesAsyncTask;
import info.infomila.appbolos.models.Partida;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;
import infomila.info.appbolos.R;

public class PartidesFragment extends Fragment implements PartidaClickListener {

    public static final String SESSION_ID = "session_id";
    public static final String SOCI = "soci";
    public static final String TORNEIG = "torneig";
    public static final String PARTIDA = "partida";

    private String sessionId;
    private Soci soci;
    private Torneig torneig;
    private Partida partida;
    private RecyclerView mRcvPartides;
    private PartidaAdapter mPartidaAdapter;
    private List<Partida> mLlistaPartides = new ArrayList<>();

    public PartidesFragment() {
        // Required empty public constructor
    }

    public static PartidesFragment newInstance(String session, Soci s, Torneig t) {
        PartidesFragment fragment = new PartidesFragment();
        Bundle args = new Bundle();
        args.putString(SESSION_ID, session);
        args.putSerializable(SOCI, s);
        args.putSerializable(TORNEIG, t);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sessionId = getArguments().getString(SESSION_ID);
            soci = (Soci)getArguments().getSerializable(SOCI);
            torneig = (Torneig) getArguments().getSerializable(TORNEIG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View containerFrame = inflater.inflate(R.layout.fragment_partides, container, false);

        mRcvPartides = containerFrame.findViewById(R.id.mRcvPartides);
        getLlistaPartides();

        return containerFrame;
    }

    private void getLlistaPartides() {
        PartidesAsyncTask connect = new PartidesAsyncTask(this);
        connect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionId, torneig.getId()+"");
    }

    public void setLlistaPartides(List<Partida> llista) {
        mLlistaPartides = llista;

        if (mRcvPartides != null) {
            if(mRcvPartides.getAdapter()==null) {
                mPartidaAdapter = new PartidaAdapter(mLlistaPartides, soci, this);
                mRcvPartides.setLayoutManager(new LinearLayoutManager(getContext()));
                mRcvPartides.setAdapter(mPartidaAdapter);
            } else {
                mPartidaAdapter.refreshLlistaPartidas(llista);
            }
        }

        /*
        if (mLlistaPartides!=null && partida!=null) {
            int idx = mLlistaPartides.indexOf(partida);
            if (idx>=0 && partida.getEstatPartida()==Partida.EstatPartida.JUGAT) {
                mLlistaPartides.remove(idx);
            }
        }
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void OnItemClick(Partida pSelected) {
        if (pSelected != null) {
            Intent intent = new Intent(this.getActivity(), PartidaActivity.class);
            intent.putExtra(PartidaActivity.SESSION_ID, sessionId);
            intent.putExtra(PartidaActivity.SOCI, soci);
            intent.putExtra(PartidaActivity.TORNEIG, torneig);
            intent.putExtra(PartidaActivity.PARTIDA, pSelected);
            this.startActivityForResult(intent, 3);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 3: // PerfilActivity
                soci = (Soci) data.getExtras().get(SOCI);
                sessionId = (String) data.getExtras().get(SESSION_ID);
                torneig = (Torneig) data.getExtras().get(TORNEIG);
                partida = (Partida) data.getExtras().get(PARTIDA);

                if (sessionId!=null && soci!=null && torneig!=null) {
                    getLlistaPartides();
                }
                break;
        }
    }

}
