package thiago.castilho.com.thiago_castilho_comp304lab4_ex1;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Fragments.AddTestFragment;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Fragments.PatientInfoFragment;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Fragments.ReadTestFragment;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.DataContext;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Doctor;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Nurse;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Test;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util.App;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util.DB;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AddTestFragment addTestFragment;
    ReadTestFragment readTestFragment;
    PatientInfoFragment patientInfoFragment;

    public Fragment currentFragment = null;

    public Test test = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (DB.database == null){
            DB.database = Room.databaseBuilder(getApplicationContext().getApplicationContext(),
                    DataContext.class, "App.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        setUserData(navigationView);
        displaySelectedScreen(R.id.nav_patient_info);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_add_test:
                fragment = new AddTestFragment();
                addTestFragment = (AddTestFragment)fragment;
                break;
            case R.id.nav_read_test:
                fragment = new ReadTestFragment();
                readTestFragment = (ReadTestFragment) fragment;
                break;
            case R.id.nav_patient_info:
                fragment = new PatientInfoFragment();
                patientInfoFragment = (PatientInfoFragment)fragment;
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(currentFragment != null){
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            ft.replace(R.id.content_frame, fragment);
            currentFragment = fragment;
            ft.commitAllowingStateLoss();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    void setUserData(NavigationView navigationView){
        View header = navigationView.getHeaderView(0);
        Integer userId = ((App)getApplication()).userId;
        TextView tvUserName = header.findViewById(R.id.tvNavUserName);
        TextView tvEmail = header.findViewById(R.id.tvNavEmail);



        Doctor doc = DB.database.doctorDao().getDoctorById(userId);
        if (doc != null){
            if(doc.firstName == null || doc.firstName.isEmpty()){
                tvUserName.setText("User");
            } else {
                tvUserName.setText(doc.firstName + " " + doc.lastName);
            }
            tvEmail.setText(doc.email);

        }

        Nurse nurse = DB.database.nurseDao().getNurseById(userId);
        if (nurse != null){
            if(nurse.firstName == null || nurse.firstName.isEmpty()){
                tvUserName.setText("User");
            } else {
                tvUserName.setText(nurse.firstName + " " + nurse.lastName);
            }
            tvEmail.setText(nurse.email);
        }
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.addTest:
                addTestFragment.onClick(view);
                break;
            case R.id.btnRegisterPatient:
                patientInfoFragment.onClick(view);
                break;
        }
    }
}
