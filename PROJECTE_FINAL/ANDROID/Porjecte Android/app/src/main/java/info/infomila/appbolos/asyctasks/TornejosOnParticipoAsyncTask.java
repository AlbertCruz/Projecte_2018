package info.infomila.appbolos.asyctasks;

import android.os.AsyncTask;

import java.util.List;

import info.infomila.appbolos.connexio.Connector;
import info.infomila.appbolos.fragment.TornejosOnParticipoFragment;
import info.infomila.appbolos.models.Torneig;

/**
 * Created by alber
 */

public class TornejosOnParticipoAsyncTask extends AsyncTask<String, String, List<Torneig>> {

    private TornejosOnParticipoFragment tornejosOnParticipoFragment;

    public TornejosOnParticipoAsyncTask(TornejosOnParticipoFragment pFragment) { tornejosOnParticipoFragment = pFragment; }

    @Override
    protected List<Torneig> doInBackground(String... params) {
        return Connector.getTornejosOnParticipo(params[0]);
    }

    @Override
    protected void onPostExecute(List<Torneig> llista) {
        super.onPostExecute(llista);
        tornejosOnParticipoFragment.setLlistaTornejosOnParticipo(llista);
    }
}
