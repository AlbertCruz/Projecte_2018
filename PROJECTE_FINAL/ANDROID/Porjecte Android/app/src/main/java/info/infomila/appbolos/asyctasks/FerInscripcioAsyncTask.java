package info.infomila.appbolos.asyctasks;

import android.os.AsyncTask;

import java.util.List;

import info.infomila.appbolos.adapters.TorneigObertAdapter;
import info.infomila.appbolos.connexio.Connector;

/**
 * Created by alber
 */

public class FerInscripcioAsyncTask extends AsyncTask<String, String, Boolean> {

    private TorneigObertAdapter torneigObertAdapter;

    public FerInscripcioAsyncTask(TorneigObertAdapter pAdapter) { torneigObertAdapter = pAdapter; }

    @Override
    protected Boolean doInBackground(String... params) {
        return Connector.ferInscripcio(params[0],Integer.parseInt(params[1]));
    }

    @Override
    protected void onPostExecute(Boolean bInscripcioOK) {
        super.onPostExecute(bInscripcioOK);
        torneigObertAdapter.ferInscripcioRequest(bInscripcioOK);
    }
}
