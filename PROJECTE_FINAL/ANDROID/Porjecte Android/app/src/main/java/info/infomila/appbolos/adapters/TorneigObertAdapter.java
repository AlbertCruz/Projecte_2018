package info.infomila.appbolos.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import info.infomila.appbolos.asyctasks.FerInscripcioAsyncTask;
import info.infomila.appbolos.models.Torneig;
import infomila.info.appbolos.R;

/**
 * Created by alber
        */

public class TorneigObertAdapter extends RecyclerView.Adapter<TorneigObertAdapter.Holder> {

    private Torneig mTorneigSel = null;
    private String mSessionId;
    private List<Torneig> mLlistaTorneig;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
    private View v = null;

    public TorneigObertAdapter(String pSessionId, List<Torneig> pLlistaTorneig) {
        mSessionId = pSessionId;
        mLlistaTorneig = pLlistaTorneig;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.torneig_obert_view, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Torneig t = mLlistaTorneig.get(position);

        holder.mTxvNom.setText(t.getNom());
        holder.mTxvModalitat.setText(t.getModalitat().getDescripcio());
        holder.mTxvData.setText(sdf.format(t.getDataInici()) + " / " + sdf.format(t.getDataFi()));

    }

    @Override
    public int getItemCount() {
        return (mLlistaTorneig!=null) ? mLlistaTorneig.size() : 0;
    }

    public void ferInscripcioRequest(Boolean bInscripcioOK) {
        if (bInscripcioOK && mTorneigSel != null) {
            mLlistaTorneig.remove(mTorneigSel);
            this.notifyDataSetChanged();
            Toast.makeText(v.getContext(), "Inscripció realitzada amb éxit!", Toast.LENGTH_SHORT).show();
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTxvNom;
        private TextView mTxvModalitat;
        private TextView mTxvData;
        private Button mBtInscriu;

        public Holder(View itemView) {
            super(itemView);
            mTxvNom = itemView.findViewById(R.id.txvNom);
            mTxvModalitat = itemView.findViewById(R.id.txvModalitat);
            mTxvData = itemView.findViewById(R.id.txvData);
            mBtInscriu = itemView.findViewById(R.id.btInscriu);

            mBtInscriu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTorneigSel = mLlistaTorneig.get(getLayoutPosition());
                    v = view;

                    FerInscripcioAsyncTask connect = new FerInscripcioAsyncTask(TorneigObertAdapter.this);
                    connect.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, TorneigObertAdapter.this.mSessionId, mTorneigSel.getId()+"");
                }
            });
        }
    }

}
