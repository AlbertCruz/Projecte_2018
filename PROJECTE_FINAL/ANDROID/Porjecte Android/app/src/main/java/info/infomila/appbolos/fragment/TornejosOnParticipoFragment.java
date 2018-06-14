package info.infomila.appbolos.fragment;

import android.content.Context;
import android.content.Intent;
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

import info.infomila.appbolos.activities.TorneigActivity;
import info.infomila.appbolos.adapters.TorneigOnParticipoAdapter;
import info.infomila.appbolos.adapters.listeners.TorneigOnParticipoClickListener;
import info.infomila.appbolos.asyctasks.TornejosOnParticipoAsyncTask;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;
import infomila.info.appbolos.R;

public class TornejosOnParticipoFragment extends Fragment implements TorneigOnParticipoClickListener {

    private static final String SESSION_ID = "session_id";
    private static final String SOCI = "soci";

    private String sessionId;
    private Soci soci;
    private List<Torneig> mLlistaTornejos;

    private RecyclerView mRcvTornejosOnParticipo;
    private TorneigOnParticipoAdapter mTorneigOnParticipoAdapter;

    public TornejosOnParticipoFragment() {
        // Required empty public constructor
    }

    public static TornejosOnParticipoFragment newInstance(String session, Soci s) {
        TornejosOnParticipoFragment fragment = new TornejosOnParticipoFragment();
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

        getLlistaTornejosOnParticipo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View containerFrame = inflater.inflate(R.layout.fragment_tornejos_on_participo, container, false);

        mRcvTornejosOnParticipo = containerFrame.findViewById(R.id.mRcvTornejosOnParticipo);
        getLlistaTornejosOnParticipo();

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

    public void getLlistaTornejosOnParticipo() {
        TornejosOnParticipoAsyncTask connect = new TornejosOnParticipoAsyncTask(this);
        connect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionId);
    }

    public void setLlistaTornejosOnParticipo(List<Torneig> llistaTornejos) {
        if (llistaTornejos!=null) mLlistaTornejos = llistaTornejos;
        if (mRcvTornejosOnParticipo != null) {
            if(mRcvTornejosOnParticipo.getAdapter()==null) {
                mTorneigOnParticipoAdapter = new TorneigOnParticipoAdapter(mLlistaTornejos, this);
                mRcvTornejosOnParticipo.setLayoutManager(new LinearLayoutManager(getContext()));
                mRcvTornejosOnParticipo.setAdapter(mTorneigOnParticipoAdapter);
            }
        }
    }

    @Override
    public void OnItemClick(Torneig tSelected) {
        if (tSelected!=null) {
            Intent intent = new Intent(this.getActivity(), TorneigActivity.class);
            intent.putExtra(TorneigActivity.SOCI, soci);
            intent.putExtra(TorneigActivity.SESSION_ID, sessionId);
            intent.putExtra(TorneigActivity.TORNEIG, tSelected);
            this.startActivityForResult(intent, 2);
        }
    }
}
