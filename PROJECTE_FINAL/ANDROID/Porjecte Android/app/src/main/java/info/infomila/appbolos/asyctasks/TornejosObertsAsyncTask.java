package info.infomila.appbolos.asyctasks;

import android.os.AsyncTask;

import java.util.List;

import info.infomila.appbolos.connexio.Connector;
import info.infomila.appbolos.fragment.TornejosObertsFragment;
import info.infomila.appbolos.models.Torneig;

/**
 * Created by alber
 */

public class TornejosObertsAsyncTask extends AsyncTask<String, String, List<Torneig>> {

    private TornejosObertsFragment tornejosObertsFragment;

    public TornejosObertsAsyncTask(TornejosObertsFragment pFragment){ tornejosObertsFragment = pFragment; }

    @Override
    protected List<Torneig> doInBackground(String... params) {
        return Connector.getTornejosOberts(params[0]);
    }

    @Override
    protected void onPostExecute(List<Torneig> llistaTornejos) {
        super.onPostExecute(llistaTornejos);
        tornejosObertsFragment.setLlistaTornejosObertsInscripcio(llistaTornejos);
    }
}
