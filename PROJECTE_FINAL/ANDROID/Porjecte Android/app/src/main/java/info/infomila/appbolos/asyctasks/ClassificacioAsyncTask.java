package info.infomila.appbolos.asyctasks;

import android.os.AsyncTask;

import java.util.List;

import info.infomila.appbolos.connexio.Connector;
import info.infomila.appbolos.fragment.ClassificacioFragment;
import info.infomila.appbolos.models.Classificacio;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.models.Torneig;

/**
 * Created by alber
 */

public class ClassificacioAsyncTask extends AsyncTask<String, String, List<Classificacio>> {

    private ClassificacioFragment classificacioFragment;
    private Soci soci;
    private Torneig torneig;

    public ClassificacioAsyncTask(ClassificacioFragment pFragment, Soci s, Torneig t){
        classificacioFragment = pFragment;
        soci = s;
        torneig = t;
    }

    @Override
    protected List<Classificacio> doInBackground(String... params) {
        return Connector.getClassificacio(params[0],soci.getId(), torneig.getId(), torneig.getModalitat().getId());
    }

    @Override
    protected void onPostExecute(List<Classificacio> llistaTornejos) {
        super.onPostExecute(llistaTornejos);
        classificacioFragment.setLlistaClassificacio(llistaTornejos);
    }
}
