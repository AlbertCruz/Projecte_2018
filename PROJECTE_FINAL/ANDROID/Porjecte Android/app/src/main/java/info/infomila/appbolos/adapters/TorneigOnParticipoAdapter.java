package info.infomila.appbolos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import info.infomila.appbolos.adapters.listeners.TorneigOnParticipoClickListener;
import info.infomila.appbolos.models.Modalitat;
import info.infomila.appbolos.models.Torneig;
import infomila.info.appbolos.R;

/**
 * Created by alber
 */

public class TorneigOnParticipoAdapter extends RecyclerView.Adapter<TorneigOnParticipoAdapter.Holder> {

    private List<Torneig> mLlistaTorneig;
    private TorneigOnParticipoClickListener mTorneigOnParticipoClickListener;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

    public TorneigOnParticipoAdapter(List<Torneig> pLlistaTorneig, TorneigOnParticipoClickListener pTorneigOnParticipoClickListener) {
        mLlistaTorneig = pLlistaTorneig;
        mTorneigOnParticipoClickListener = pTorneigOnParticipoClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.torneig_on_participo_view, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Torneig t = mLlistaTorneig.get(position);

        if (t!=null) {
            Modalitat m = t.getModalitat();
            holder.mTxvNom.setText((t.getNom() != null) ? t.getNom() : "");
            holder.mTxvModalitat.setText((m != null) ? m.getDescripcio() : "");
            holder.mTxvDataIni.setText((t.getDataInici() != null) ? sdf.format(t.getDataInici()) : "-");
            holder.mTxvDataFin.setText((t.getDataFi() != null) ? sdf.format(t.getDataFi()) : "-");
        }
    }

    @Override
    public int getItemCount() {
        return (mLlistaTorneig!=null) ? mLlistaTorneig.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTxvNom;
        private TextView mTxvModalitat;
        private TextView mTxvDataIni;
        private TextView mTxvDataFin;

        public Holder(View itemView) {
            super(itemView);
            mTxvNom = itemView.findViewById(R.id.txvNom);
            mTxvModalitat = itemView.findViewById(R.id.txvModalitat);
            mTxvDataIni = itemView.findViewById(R.id.txvDataIni);
            mTxvDataFin = itemView.findViewById(R.id.txvDataFin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                if (TorneigOnParticipoAdapter.this.mTorneigOnParticipoClickListener != null) {
                    TorneigOnParticipoAdapter.this.mTorneigOnParticipoClickListener.OnItemClick(mLlistaTorneig.get(getLayoutPosition()));
                }
                }
            });
        }
    }

}
