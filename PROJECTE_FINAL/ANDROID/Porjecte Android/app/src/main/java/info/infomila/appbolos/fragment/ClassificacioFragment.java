package info.infomila.appbolos.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.infomila.appbolos.adapters.ClassificacioAdapter;
import info.infomila.appbolos.asyctasks.ClassificacioAsyncTask;
import info.infomila.appbolos.models.Classificacio;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;
import infomila.info.appbolos.R;

public class ClassificacioFragment extends Fragment {

    private static final String SESSION_ID = "session_id";
    private static final String SOCI = "soci";
    private static final String TORNEIG = "torneig";

    private String sessionId;
    private Soci soci;
    private Torneig torneig;
    private RecyclerView mRcvClassificacio;
    private ClassificacioAdapter mClassificacioAdapter;

    public ClassificacioFragment() {
    }

    public static ClassificacioFragment newInstance(String session, Soci s, Torneig t) {
        ClassificacioFragment fragment = new ClassificacioFragment();
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
        View containerFrame = inflater.inflate(R.layout.fragment_classificacio, container, false);

        mRcvClassificacio = containerFrame.findViewById(R.id.mRcvClassificacio);
        getLlistaClassificacio();

        return containerFrame;
    }

    private void getLlistaClassificacio() {
        ClassificacioAsyncTask connect = new ClassificacioAsyncTask(this, soci, torneig);
        connect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionId);
    }

    public void setLlistaClassificacio(List<Classificacio> llistaClassificacio) {
        System.out.println((llistaClassificacio!=null) ? llistaClassificacio.size() + "" : "EMPTY");

        if (mRcvClassificacio != null) {
            if(mRcvClassificacio.getAdapter()==null) {
                mClassificacioAdapter = new ClassificacioAdapter(llistaClassificacio);
                mRcvClassificacio.setLayoutManager(new LinearLayoutManager(getContext()));
                mRcvClassificacio.setAdapter(mClassificacioAdapter);
            }
        }

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
