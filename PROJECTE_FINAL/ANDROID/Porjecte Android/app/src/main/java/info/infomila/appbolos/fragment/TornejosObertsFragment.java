package info.infomila.appbolos.fragment;

import android.content.Context;
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

import info.infomila.appbolos.adapters.TorneigObertAdapter;
import info.infomila.appbolos.asyctasks.TornejosObertsAsyncTask;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;
import infomila.info.appbolos.R;

public class TornejosObertsFragment extends Fragment {

    private static final String SESSION_ID = "session_id";
    private static final String SOCI = "soci";

    private String sessionId;
    private Soci soci;
    private List<Torneig> mLlistaTornejos;

    private RecyclerView mRcvTornejosOberts;
    private TorneigObertAdapter mTorneigObertAdapter;

    public TornejosObertsFragment() {
        // Required empty public constructor
    }

    public static TornejosObertsFragment newInstance(String session, Soci s) {
        TornejosObertsFragment fragment = new TornejosObertsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View containerFrame = inflater.inflate(R.layout.fragment_tornejos_oberts, container, false);

        mRcvTornejosOberts = containerFrame.findViewById(R.id.mRcvTornejosOberts);
        getLlistaTornejosObertsInscripcio();

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

    public void getLlistaTornejosObertsInscripcio() {
        TornejosObertsAsyncTask connect = new TornejosObertsAsyncTask(this);
        connect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionId);
    }

    public void setLlistaTornejosObertsInscripcio(List<Torneig> llistaTornejos) {
        if (llistaTornejos!=null) mLlistaTornejos = llistaTornejos;
        if (mRcvTornejosOberts != null) {
            if(mRcvTornejosOberts.getAdapter()==null) {
                mTorneigObertAdapter = new TorneigObertAdapter(sessionId, mLlistaTornejos);
                mRcvTornejosOberts.setLayoutManager(new LinearLayoutManager(getContext()));
                mRcvTornejosOberts.setAdapter(mTorneigObertAdapter);
            }
        }
        Log.d("DADES_DEBUG","Llista tornejos oberts carregada, qtat = " + llistaTornejos.size());
    }

}
