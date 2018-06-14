package info.infomila.appbolos.asyctasks;

import android.os.AsyncTask;

import java.util.List;

import info.infomila.appbolos.connexio.Connector;
import info.infomila.appbolos.fragment.PartidesFragment;
import info.infomila.appbolos.models.Partida;

/**
 * Created by alber
 */

public class PartidesAsyncTask extends AsyncTask<String, String, List<Partida>> {

    private PartidesFragment mPartidesFragment;

    public PartidesAsyncTask(PartidesFragment pFragment) { mPartidesFragment = pFragment; }

    @Override
    protected List<Partida> doInBackground(String... params) {
        return Connector.getPartides(params[0], Integer.parseInt(params[1]));
    }

    @Override
    protected void onPostExecute(List<Partida> llista) {
        super.onPostExecute(llista);
        mPartidesFragment.setLlistaPartides(llista);
    }
}
