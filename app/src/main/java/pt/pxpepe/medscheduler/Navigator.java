package pt.pxpepe.medscheduler;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.pxpepe.medscheduler.data.Dosis;
import pt.pxpepe.medscheduler.data.Medicament;
import pt.pxpepe.medscheduler.data.Treatment;
import pt.pxpepe.medscheduler.dto.MedicamentDosisDTO;
import pt.pxpepe.medscheduler.dto.TreatmentDTO;
import pt.pxpepe.medscheduler.fragments.HomeFragment;
import pt.pxpepe.medscheduler.fragments.InstanceFragment;
import pt.pxpepe.medscheduler.fragments.MedicamentFragment;
import pt.pxpepe.medscheduler.fragments.SaveMedicamentFragment;
import pt.pxpepe.medscheduler.fragments.SaveTreatmentFragment;
import pt.pxpepe.medscheduler.fragments.StartInstanceFragment;
import pt.pxpepe.medscheduler.fragments.TreatmentFragment;
import pt.pxpepe.medscheduler.utilities.SerializationHelper;
import pt.pxpepe.medscheduler.viewmodels.DosisViewModel;
import pt.pxpepe.medscheduler.viewmodels.MedicamentViewModel;
import pt.pxpepe.medscheduler.viewmodels.TreatmentViewModel;

public class Navigator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MED_SCHEDULER_TYPE = "MED_SCHEDULER_TYPE";

    private List<Integer> fragStack = new ArrayList<>();
    @SuppressLint("UseSparseArrays")
    private Map<Integer, Fragment> instancesMap = new HashMap<>();

    MessageListener mMessageListener;

    private TreatmentViewModel treatmentViewModel;
    private DosisViewModel dosisViewModel;
    private MedicamentViewModel medicamentViewModel;


    public Integer fragStackPop() {
        Integer lastItem=null;
        if (!fragStack.isEmpty()) {
            lastItem = fragStack.remove(fragStack.size()-1);
        }
        return lastItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        displayView(R.id.nav_home);

        treatmentViewModel = ViewModelProviders.of(this).get(TreatmentViewModel.class);
        dosisViewModel = ViewModelProviders.of(this).get(DosisViewModel.class);
        medicamentViewModel = ViewModelProviders.of(this).get(MedicamentViewModel.class);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            go2PreviousFragment();

        }
    }

    public void go2PreviousFragment() {
        Integer currentView = fragStackPop();// pop current item
        Integer previousView = fragStackPop(); // pop last Item
        if (previousView!=null
                && currentView!=R.id.nav_home) {
            displayView(previousView);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {

        // Handle navigation view item clicks here.
        displayView(item.getItemId(), true);

        return true;
    }

    public void displayView(int viewId) {
        displayView(viewId, false);
    }

    public void displayView(int viewId, boolean restartBreadCrumbs) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {

            case R.id.nav_home:
                fragment = new HomeFragment();
                title  = getString(R.string.menu_home);
                break;

            case R.id.nav_treatment:
                fragment = new TreatmentFragment();
                title  = getString(R.string.menu_treatment);
                break;

            case R.layout.fragment_save_treatment:
                fragment = new SaveTreatmentFragment();
                title  = getString(R.string.add_treatment);
                break;

            case R.id.nav_medicament:
                fragment = new MedicamentFragment();
                title  = getString(R.string.menu_medicament);
                break;

            case R.layout.fragment_save_medicament:
                fragment = new SaveMedicamentFragment();
                title  = getString(R.string.add_medicament);
                break;

            case R.id.nav_instance:
                fragment = new InstanceFragment();
                title  = getString(R.string.menu_instance);
                break;

            case R.layout.fragment_start_instance:
                fragment = new StartInstanceFragment();
                title  = getString(R.string.start_instance);
                break;
            case R.layout.fragment_detail_treatment:
                if (instancesMap.containsKey(viewId)) {
                    fragment = instancesMap.get(viewId);
                    title  = getString(R.string.treatment_detail);
                }
                break;

        }

        displayFragment(fragment, title, viewId, restartBreadCrumbs);

    }

    public void displayFragment(Fragment fragment, String title, int viewId, boolean restartBreadCrumbs) {
        if (restartBreadCrumbs) {
            fragStack = new ArrayList<>();
            fragStack.add(R.id.nav_home);
        }
        instancesMap.put(viewId,fragment);
        fragStack.add(viewId);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupNearbyMessages();
    }

    private void setupNearbyMessages() {
        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                importTreatment(message);
            }

            @Override
            public void onLost(Message message) {
                Log.v("","unPublished message");
            }
        };
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .build();
        Nearby.getMessagesClient(this).subscribe(mMessageListener, options);
    }

    private void importTreatment(Message message) {
        TreatmentDTO treatmentDTO = (TreatmentDTO) SerializationHelper.deserialize(message.getContent());
        if (treatmentDTO!=null) {

            Long treatmentId = treatmentViewModel.insertTreatment(new Treatment(treatmentDTO.getName(), treatmentDTO.getDuration()));
            if (treatmentId!=null) {

                for (MedicamentDosisDTO medicamentDosis : treatmentDTO.getMedicamentDosisList()) {
                    try {
                        Long medicamentId = medicamentViewModel.insertMedicament(new Medicament(medicamentDosis.getName()));
                        if (medicamentId!=null) {
                            dosisViewModel.insertDosis(new Dosis(medicamentId.intValue(), treatmentId.intValue(), medicamentDosis.getPeriod(), medicamentDosis.getPillNumber(), medicamentDosis.getDelayNumber()));
                        }
                    } catch (Exception e) {
                        Log.e("", e.toString());
                    }
                }

            }

            Toast.makeText(getApplicationContext(), R.string.treatment_imported_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener);
        super.onStop();
    }

}
