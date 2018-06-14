package info.infomila.appbolos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import info.infomila.appbolos.models.DetallPartida;
import infomila.info.appbolos.R;

/**
 * Created by alber
 */

public class DetallPartidaAdapter extends RecyclerView.Adapter<DetallPartidaAdapter.Holder> {

    private List<DetallPartida> llDetallPartida;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public DetallPartidaAdapter(List<DetallPartida> pLlDetallPartida) {
        llDetallPartida = pLlDetallPartida;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detall_partida_view, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        DetallPartida detall = llDetallPartida.get(position);

        holder.tvEntrada.setText(detall.getEntrada()+"");
        holder.tvNomSoci.setText("["+ detall.getSociTag() +"] - " + detall.getSociNom());
        holder.tvDataEntrada.setText(sdf.format(detall.getDataEntrada()));
        holder.tvCaramboles.setText(detall.getCaramboles()+"");
    }

    @Override
    public int getItemCount() {
        return (llDetallPartida!=null && llDetallPartida.size()>0) ? llDetallPartida.size() : 0;
    }

    public void refreshLlistaPartidas(List<DetallPartida> llistaEntrades) {
        llDetallPartida = llistaEntrades;
        this.notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvEntrada;
        private TextView tvNomSoci;
        private TextView tvDataEntrada;
        private TextView tvCaramboles;

        public Holder(View itemView) {
            super(itemView);

            tvEntrada = itemView.findViewById(R.id.txvEntrada);
            tvNomSoci = itemView.findViewById(R.id.txvNomSoci);
            tvDataEntrada = itemView.findViewById(R.id.txvDataEntrada);
            tvCaramboles = itemView.findViewById(R.id.txvCaramboles);
        }
    }
}