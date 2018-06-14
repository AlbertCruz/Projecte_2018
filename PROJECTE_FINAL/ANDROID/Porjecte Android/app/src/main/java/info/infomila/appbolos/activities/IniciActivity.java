package info.infomila.appbolos.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import info.infomila.appbolos.asyctasks.PerfilAsyncTask;
import info.infomila.appbolos.connexio.SingletonConnection;
import info.infomila.appbolos.fragment.EstadistiquesFragment;
import info.infomila.appbolos.fragment.TornejosObertsFragment;
import info.infomila.appbolos.fragment.TornejosOnParticipoFragment;
import info.infomila.appbolos.models.Soci;
import info.infomila.appbolos.utils.MD5Encrypter;
import infomila.info.appbolos.R;

public class IniciActivity extends AppCompatActivity {

    private static Soci soci;
    private static String session_id;

    public static final String SOCI = "soci";
    public static final String SESSION_ID = "session_id";
    public static final String TORNEIG = "torneig";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Inici");

        soci = (Soci)getIntent().getExtras().get(SOCI);
        session_id = (String)getIntent().getExtras().get(SESSION_ID);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(EstadistiquesFragment.newInstance(session_id,soci), "ESTADÍSTIQUES");
        adapter.addFragment(TornejosObertsFragment.newInstance(session_id,soci), "T. OBERTS");
        adapter.addFragment(TornejosOnParticipoFragment.newInstance(session_id,soci), "T. ON PARTICIPO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1: // PerfilActivity
                Soci sociUpdated = (Soci) data.getExtras().get(SOCI);
                String session = (String) data.getExtras().get(SESSION_ID);
                if (sociUpdated!=null) {
                    soci.setNif(sociUpdated.getNif());
                    soci.setNom(sociUpdated.getNom());
                    soci.setCognom1(sociUpdated.getCognom1());
                    soci.setCognom2(sociUpdated.getCognom2());
                    soci.setPassword(sociUpdated.getPassword());
                }
                if (session!=null && session.length()>0) session_id = session;
                break;
            case 2: // TorneigActivity
                break;
        }
        toolbar.setTitle("Inici");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,PerfilActivity.class);
            intent.putExtra(PerfilActivity.SOCI, soci);
            intent.putExtra(PerfilActivity.SESSION_ID, session_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
