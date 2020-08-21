package nanodevlab.imran.aqwalehazratalias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nanodevlab.imran.aqwalehazratalias.Fragments.Aqwal;
import nanodevlab.imran.aqwalehazratalias.Fragments.Duas;
import nanodevlab.imran.aqwalehazratalias.Fragments.Setting;
import nanodevlab.imran.aqwalehazratalias.Fragments.Videos;
import nanodevlab.imran.aqwalehazratalias.Fragments.Ziarats;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigatoin);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Aqwal(getApplicationContext())).commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {

                        case R.id.nav_aqwal:
                            selectedFragment = new Aqwal(MainActivity.this);
                            break;

                        case R.id.nav_videos:
                            selectedFragment = new Videos();
                            break;

                        case R.id.nav_dua:
                            selectedFragment = new Duas();
                            break;

                            case R.id.nav_Ziarats:
                            selectedFragment = new Ziarats();
                            break;

                        case R.id.nav_setting:
                            selectedFragment = new Setting();
                            break;

                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    }

                    return true;
                }
            };


}
