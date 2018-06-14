package info.infomila.appbolos.asyctasks;

import android.os.AsyncTask;

import info.infomila.appbolos.activities.PartidaActivity;
import info.infomila.appbolos.connexio.Connector;
import info.infomila.appbolos.models.Partida;

/**
 * Created by alber
 */

public class SendResultatPartidaAsyncTask extends AsyncTask<String, String, Boolean> {

    private PartidaActivity mPartidaActivity;
    private Partida mPartida;

    public SendResultatPartidaAsyncTask(PartidaActivity pPartidaActivity, Partida pPartida) {
        mPartidaActivity = pPartidaActivity;
        mPartida = pPartida;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return Connector.SendResultatPartida(params[0],mPartida);
    }

    @Override
    protected void onPostExecute(Boolean bInscripcioOK) {
        super.onPostExecute(bInscripcioOK);
        mPartidaActivity.resultatPartidaRequest(bInscripcioOK);
    }
}
