package pt.pxpepe.medscheduler.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import pt.pxpepe.medscheduler.Navigator;
import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.data.Treatment;
import pt.pxpepe.medscheduler.viewmodels.TreatmentViewModel;

public class SaveTreatmentFragment extends Fragment {
    private static final String ARG_TREATMENT_ID = "treatmentId";

    private Integer updateId;
    private TreatmentViewModel treatmentViewModel;

    private TextView txtName;
    private TextView txtDuration;



    public SaveTreatmentFragment() {
        // Required empty public constructor
    }

    public static SaveTreatmentFragment newInstance(Integer id) {
        SaveTreatmentFragment fragment = new SaveTreatmentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TREATMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            updateId = getArguments().getInt(ARG_TREATMENT_ID);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        treatmentViewModel = ViewModelProviders.of(this).get(TreatmentViewModel.class);

        View v = inflater.inflate(R.layout.fragment_save_treatment, container, false);

        txtName = v.findViewById(R.id.txtTreatmentName);
        txtDuration = v.findViewById(R.id.txtDuration);

        Button b = v.findViewById(R.id.btnAddDosis);
        b.setOnClickListener(v1 -> onClick());

        if (updateId!=null) {
            Treatment treatment = treatmentViewModel.findTreatmentById(updateId);
            txtName.setText(treatment.getName());
            txtDuration.setText(String.valueOf(treatment.getDuration()));
        }

        return v;
    }

    private void onClick() {
        String name = txtName.getText().toString();
        String duration = txtDuration.getText().toString();
        if (!name.equals("") && !duration.equals("")
                && TextUtils.isDigitsOnly(duration)) {

            if (updateId != null) {
                Treatment treatment = treatmentViewModel.findTreatmentById(updateId);
                treatment.setName(name);
                treatment.setDuration(Integer.valueOf(duration));
                treatmentViewModel.updateTreatment(treatment);
            } else {
                treatmentViewModel.insertTreatment(new Treatment(name, Integer.valueOf(duration)));
            }
            ((Navigator) Objects.requireNonNull(getActivity())).displayView(R.id.nav_treatment, true);
        } else {
            Toast.makeText(getContext(), R.string.all_the_fields_required, Toast.LENGTH_SHORT).show();
        }
    }
}
