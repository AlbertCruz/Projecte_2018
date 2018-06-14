package info.infomila.appbolos.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.infomila.appbolos.adapters.EstadisticaAdapter;
import info.infomila.appbolos.models.EstadisticaModalitat;
import info.infomila.appbolos.models.Soci;
import infomila.info.appbolos.R;

public class EstadistiquesFragment extends Fragment {

    private static final String SESSION_ID = "session_id";
    private static final String SOCI = "soci";

    private String sessionId;
    private Soci soci;
    private RecyclerView mRcvEstadistiques;
    private EstadisticaAdapter mEstadisticaAdapter;

    public EstadistiquesFragment() {
        // Required empty public constructor
    }

    public static EstadistiquesFragment newInstance(String session, Soci s) {
        EstadistiquesFragment fragment = new EstadistiquesFragment();
        Bundle args = new Bundle();
        args.putString(SESSION_ID, session);
        args.putSerializable(SOCI, s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sessionId = getArguments().getString(SESSION_ID);
            soci = (Soci)getArguments().getSerializable(SOCI);
        }
        Log.d("DADES_DEBUG","Estadístiques carregades, qtat = " + soci.iteEstadistiques());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View containerFrame = inflater.inflate(R.layout.fragment_estadistiques, container, false);

        mRcvEstadistiques = containerFrame.findViewById(R.id.mRcvEstadistiques);

        if (mRcvEstadistiques != null) {
            if(mRcvEstadistiques.getAdapter()==null) {
                List<EstadisticaModalitat> llistaEstadistiques = soci.getEstadistiques();
                mEstadisticaAdapter = new EstadisticaAdapter(llistaEstadistiques);
                mRcvEstadistiques.setLayoutManager(new LinearLayoutManager(getContext()));
                mRcvEstadistiques.setAdapter(mEstadisticaAdapter);
            }
        }

        return containerFrame;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
