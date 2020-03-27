package pt.pxpepe.medscheduler.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import pt.pxpepe.medscheduler.Navigator;
import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.adapters.MedicamentListAdapter;
import pt.pxpepe.medscheduler.data.Medicament;
import pt.pxpepe.medscheduler.viewmodels.MedicamentViewModel;

public class MedicamentFragment extends Fragment {

    private MedicamentViewModel medicamentViewModel;
    private MedicamentListAdapter adapter;
    private View myFragmentView;

    public MedicamentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_medicament, container, false);

        medicamentViewModel = ViewModelProviders.of(this).get(MedicamentViewModel.class);

        myFragmentView.findViewById(R.id.btnAddMedicament).setOnClickListener(v -> ((Navigator) Objects.requireNonNull(getActivity())).displayView(R.layout.fragment_save_medicament));

        observerSetup();
        recyclerSetup();

        // Inflate the layout for this fragment
        return myFragmentView;

    }

    private void observerSetup(){

        medicamentViewModel.getAllMedicaments().observe(this, medicaments -> {
            adapter.setMedicamentList(medicaments);
            if (medicaments!=null
                    && medicaments.size()>0) {
                myFragmentView.findViewById(R.id.lblEmptyMedicamentSet).setVisibility(View.GONE);
            } else {
                myFragmentView.findViewById(R.id.lblEmptyMedicamentSet).setVisibility(View.VISIBLE);
            }

        });

    }


    private void recyclerSetup() {
        RecyclerView recyclerView;
        adapter = new MedicamentListAdapter(R.layout.list_medicament);
        recyclerView = myFragmentView.findViewById(R.id.medicament_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Medicament currentMedicament = adapter.getCurrentMedicament();
        if (currentMedicament != null) {
            switch (item.getItemId()) {
                case R.id.ctx_remove_medicament:
                    medicamentViewModel.removeMedicament(String.valueOf(currentMedicament.getId()));
                    break;
                case R.id.ctx_update_medicament:
                    ((Navigator) Objects.requireNonNull(getActivity())).displayFragment(
                            SaveMedicamentFragment
                                    .newInstance(currentMedicament.getId()),
                            getString(R.string.save_medicament),
                            R.layout.fragment_save_medicament, false);
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }
}
