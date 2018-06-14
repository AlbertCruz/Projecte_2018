package info.infomila.appbolos.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import infomila.info.appbolos.R;
import info.infomila.appbolos.asyctasks.SplashScreenAsyncTask;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SplashScreenAsyncTask task = new SplashScreenAsyncTask(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }

    public void onAsyncEnded() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
