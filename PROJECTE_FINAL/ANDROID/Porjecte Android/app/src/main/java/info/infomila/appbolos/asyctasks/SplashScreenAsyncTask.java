package info.infomila.appbolos.asyctasks;

import android.content.Context;
import android.os.AsyncTask;

import info.infomila.appbolos.activities.SplashScreenActivity;

public class SplashScreenAsyncTask extends AsyncTask<Void, Void, Integer> {


    private Context context;

    public SplashScreenAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        ((SplashScreenActivity)context).onAsyncEnded();
        try{
            finalize();
        }catch(Throwable ex){}

    }
}
