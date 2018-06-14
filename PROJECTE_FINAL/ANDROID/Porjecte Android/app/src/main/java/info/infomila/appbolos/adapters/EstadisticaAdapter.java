package info.infomila.appbolos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.infomila.appbolos.models.EstadisticaModalitat;
import infomila.info.appbolos.R;

/**
 * Created by alber
 */

public class EstadisticaAdapter extends RecyclerView.Adapter<EstadisticaAdapter.Holder> {

    private List<EstadisticaModalitat> mLlistaEstadistiques;

    public EstadisticaAdapter(List<EstadisticaModalitat> pLlistaEstadistiques) {
        mLlistaEstadistiques = pLlistaEstadistiques;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.estadistica_modalitat_view, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        EstadisticaModalitat em = mLlistaEstadistiques.get(position);

        holder.mTxvModalitat.setText(em.getModalitat().getDescripcio());
        holder.mTxvCoeficientBase.setText(em.getCoeficientBase()+"");
        holder.mTxvCarambolesTotals.setText(em.getCarambolesTemporadaActual()+"");
        holder.mTxvEntradesTotals.setText(em.getEntradesTemporadaActual()+"");
    }

    @Override
    public int getItemCount() {
        return (mLlistaEstadistiques!=null) ? mLlistaEstadistiques.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTxvModalitat;
        private TextView mTxvEntradesTotals;
        private TextView mTxvCarambolesTotals;
        private TextView mTxvCoeficientBase;

        public Holder(View itemView) {
            super(itemView);
            mTxvModalitat = itemView.findViewById(R.id.txvModalitat);
            mTxvEntradesTotals = itemView.findViewById(R.id.txvEntradesTotals);
            mTxvCarambolesTotals = itemView.findViewById(R.id.txvCarambolesTotals);
            mTxvCoeficientBase = itemView.findViewById(R.id.txvCoeficientBase);
        }
    }

}
