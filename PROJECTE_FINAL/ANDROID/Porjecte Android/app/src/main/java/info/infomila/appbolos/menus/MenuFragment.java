package info.infomila.appbolos.menus;


import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import infomila.info.appbolos.R;

public class MenuFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork(
                ).detectAll().penaltyLog().build());


        addPreferencesFromResource(R.xml.preferences);
    }
}
