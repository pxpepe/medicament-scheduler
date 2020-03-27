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
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import pt.pxpepe.medscheduler.Navigator;
import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.adapters.DosisListAdapter;
import pt.pxpepe.medscheduler.data.JoinDosisMedicamentTreatment;
import pt.pxpepe.medscheduler.data.Treatment;
import pt.pxpepe.medscheduler.viewmodels.DosisViewModel;
import pt.pxpepe.medscheduler.viewmodels.TreatmentViewModel;

public class DetailTreatmentFragment extends Fragment {
    private static final String ARG_TREATMENT_ID = "treatmentId";

    private Integer treatmentId;
    private DosisViewModel dosisViewModel;
    private DosisListAdapter adapter;
    private View myFragmentView;

    public static DetailTreatmentFragment newInstance(Integer id) {
        DetailTreatmentFragment fragment = new DetailTreatmentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TREATMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            treatmentId = getArguments().getInt(ARG_TREATMENT_ID);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TreatmentViewModel treatmentViewModel = ViewModelProviders.of(this).get(TreatmentViewModel.class);
        dosisViewModel = ViewModelProviders.of(this).get(DosisViewModel.class);

        myFragmentView = inflater.inflate(R.layout.fragment_detail_treatment, container, false);

        TextView txtName = myFragmentView.findViewById(R.id.lblDetailTreatmentName);
        TextView txtDuration = myFragmentView.findViewById(R.id.lblDetailTreatmentDuration);

        Button b = myFragmentView.findViewById(R.id.btnAddDosis);
        b.setOnClickListener(v1 -> onClick());

        if (treatmentId!=null) {
            Treatment treatment = treatmentViewModel.findTreatmentById(treatmentId);
            txtName.setText(treatment.getName());
            txtDuration.setText(String.valueOf(treatment.getDuration()));

            observerSetup();
            recyclerSetup();
        }

        return myFragmentView;
    }

    private void observerSetup(){

        dosisViewModel.getAllDosiss(treatmentId).observe(this, dosis -> {
            adapter.setDosisList(dosis);
            if (dosis!=null
                    && dosis.size()>0) {
                myFragmentView.findViewById(R.id.lblEmptyDosisSet).setVisibility(View.GONE);
            } else {
                myFragmentView.findViewById(R.id.lblEmptyDosisSet).setVisibility(View.VISIBLE);
            }

        });

    }


    private void recyclerSetup() {
        RecyclerView recyclerView;
        adapter = new DosisListAdapter(R.layout.list_dosis);
        recyclerView = myFragmentView.findViewById(R.id.dosis_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        JoinDosisMedicamentTreatment currentDosis = adapter.getCurrentDosis();
        if (currentDosis != null) {
            if (item.getItemId() == R.id.ctx_delete_dosis) {
                dosisViewModel.removeDosis(String.valueOf(currentDosis.getDosisId()));
            }
        }
        return super.onContextItemSelected(item);
    }

    private void onClick() {

        ((Navigator) Objects.requireNonNull(getActivity())).displayFragment(
                SaveDosisFragment
                        .newInstance(treatmentId),
                getString(R.string.save_dosis),
                R.layout.fragment_save_dosis, false);

    }
}
