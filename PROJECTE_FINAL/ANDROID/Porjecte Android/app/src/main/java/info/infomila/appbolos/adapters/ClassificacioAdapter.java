package info.infomila.appbolos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.infomila.appbolos.models.Classificacio;
import infomila.info.appbolos.R;

/**
 * Created by alber
 */

public class ClassificacioAdapter extends RecyclerView.Adapter<ClassificacioAdapter.Holder> {

    private List<Classificacio> mLlistaClassificacions;

    public ClassificacioAdapter(List<Classificacio> pLlistaClassificacions) {
        mLlistaClassificacions = pLlistaClassificacions;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.classificacio_view, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Classificacio c = mLlistaClassificacions.get(position);

        holder.mTxvPosicio.setText(position+1+"");
        holder.mTxvSoci.setText(c.getSoci());
        holder.mTxvPartidesGuanyades.setText(c.getpGuanyades()+"");
        holder.mTxvPartidesPerdudes.setText(c.getpPerdudes()+"");
        holder.mTxvCoeficientBase.setText(c.getCoeficientBase()+"");
    }

    @Override
    public int getItemCount() {
        return (mLlistaClassificacions!=null) ? mLlistaClassificacions.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTxvPosicio;
        private TextView mTxvSoci;
        private TextView mTxvPartidesGuanyades;
        private TextView mTxvPartidesPerdudes;
        private TextView mTxvCoeficientBase;

        public Holder(View itemView) {
            super(itemView);
            mTxvPosicio = itemView.findViewById(R.id.txvPosicio);
            mTxvSoci = itemView.findViewById(R.id.txvSoci);
            mTxvPartidesGuanyades = itemView.findViewById(R.id.txvPartidesGuanyades);
            mTxvPartidesPerdudes = itemView.findViewById(R.id.txvPartidesPerdudes);
            mTxvCoeficientBase = itemView.findViewById(R.id.txvCoeficientBase);
        }
    }

}
