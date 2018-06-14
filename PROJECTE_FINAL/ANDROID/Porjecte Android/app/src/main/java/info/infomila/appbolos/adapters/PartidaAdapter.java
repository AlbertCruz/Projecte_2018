package info.infomila.appbolos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import info.infomila.appbolos.adapters.listeners.PartidaClickListener;
import info.infomila.appbolos.models.Partida;
import info.infomila.appbolos.models.Soci;
import infomila.info.appbolos.R;

/**
 * Created by alber
 */

public class PartidaAdapter extends RecyclerView.Adapter<PartidaAdapter.Holder> {

    private List<Partida> mLlistaPartides;
    private Soci mSoci;
    private PartidaClickListener mPartidaClickListener;

    public PartidaAdapter(List<Partida> pLlistaPartides, Soci s, PartidaClickListener pPartidaClickListener) {
        mLlistaPartides = pLlistaPartides;
        mSoci = s;
        mPartidaClickListener = pPartidaClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partida_view, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Partida p = mLlistaPartides.get(position);
        Soci contrincant = (mSoci.equals(p.getSociA())) ? p.getSociB() : p.getSociA();
        String nom = contrincant.getNom();
        nom += (contrincant.getCognom1()!=null) ? " " + contrincant.getCognom1() : "";
        nom += (contrincant.getCognom2()!=null) ? " " + contrincant.getCognom2() : "";

        holder.mTxvContrincant.setText(nom);
    }

    public void refreshLlistaPartidas(List<Partida> pLlistaEntrades) {
        mLlistaPartides = pLlistaEntrades;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (mLlistaPartides!=null) ? mLlistaPartides.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTxvContrincant;
        private Button mBtJugarPartida;

        public Holder(View itemView) {
            super(itemView);
            mTxvContrincant = itemView.findViewById(R.id.txvContrincant);
            mBtJugarPartida = itemView.findViewById(R.id.btJugarPartida);

            mBtJugarPartida.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Partida partida = mLlistaPartides.get(getLayoutPosition());
                    if (PartidaAdapter.this.mPartidaClickListener != null) {
                        PartidaAdapter.this.mPartidaClickListener.OnItemClick(partida);
                    }
                }
            });
        }
    }
    
}
