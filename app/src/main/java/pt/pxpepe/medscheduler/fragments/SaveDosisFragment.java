package pt.pxpepe.medscheduler.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import pt.pxpepe.medscheduler.Navigator;
import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.adapters.MedicamentSpinnerAdapter;
import pt.pxpepe.medscheduler.data.Dosis;
import pt.pxpepe.medscheduler.data.Medicament;
import pt.pxpepe.medscheduler.viewmodels.DosisViewModel;
import pt.pxpepe.medscheduler.viewmodels.MedicamentViewModel;

public class SaveDosisFragment extends Fragment {
    private static final String ARG_TREATMENT_ID = "treatmentId";
    private Integer treatmentId;

    Spinner medicamentSpinner;
    EditText txtNumPeriod;
    EditText txtNumPills;
    EditText txtNumDelay;

    private DosisViewModel dosisViewModel;
    private MedicamentViewModel medicamentViewModel;

    public static SaveDosisFragment newInstance(Integer id) {
        SaveDosisFragment fragment = new SaveDosisFragment();
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

        dosisViewModel = ViewModelProviders.of(this).get(DosisViewModel.class);
        medicamentViewModel = ViewModelProviders.of(this).get(MedicamentViewModel.class);

        View v = inflater.inflate(R.layout.fragment_save_dosis, container, false);

        medicamentSpinner = v.findViewById(R.id.spnMedicaments);
        txtNumPeriod = v.findViewById(R.id.txtNumPeriod);
        txtNumPills = v.findViewById(R.id.txtNumPills);
        txtNumDelay = v.findViewById(R.id.txtNumDelay);

        fillSpinner();

        Button b = v.findViewById(R.id.btnSaveDosis);
        b.setOnClickListener(v1 -> onClick());

        return v;
    }

    private void fillSpinner() {

        medicamentViewModel.getAllMedicaments().observe(this, medicaments -> {
            MedicamentSpinnerAdapter medicamentSpinnerAdapter = new MedicamentSpinnerAdapter(getContext(),
                    android.R.layout.simple_spinner_item,
                    medicaments);
            medicamentSpinner.setAdapter(medicamentSpinnerAdapter);
        });

    }

    private void onClick() {

        String strPeriod = txtNumPeriod.getText().toString();
        String strPills = txtNumPills.getText().toString();
        String strDelay = txtNumDelay.getText().toString();
        Medicament medicament = (Medicament) medicamentSpinner.getSelectedItem();

        if (!strPeriod.equals("")
                && TextUtils.isDigitsOnly(strPeriod)
                && !strPills.equals("")
                && TextUtils.isDigitsOnly(strPills)
                && !strDelay.equals("")
                && TextUtils.isDigitsOnly(strDelay)
                && medicament!=null) {

            Integer period = Integer.valueOf(strPeriod);
            Integer pills = Integer.valueOf(strPills);
            Integer delay = Integer.valueOf(strDelay);
            if (isValidPeriod(period)
                    && isValidPills(pills)
                    && isValidDelay(delay)) {
                if (dosisViewModel.insertDosis(new Dosis(medicament.getId(),
                            treatmentId, period, pills, delay))) {
                    ((Navigator) Objects.requireNonNull(getActivity())).go2PreviousFragment();
                } else {
                    Toast.makeText(getContext(), R.string.medicament_dosis_exist, Toast.LENGTH_SHORT).show();
                }

            }

        } else {
            Toast.makeText(getContext(), R.string.all_the_fields_required, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidDelay(Integer delay) {
        boolean isValid;
        if (!(isValid = delay >= 1 && delay <= 30)) {
            Toast.makeText(getContext(), R.string.invalid_delay, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private boolean isValidPills(Integer pills) {
        boolean isValid;
        if (!(isValid = pills >= 1 && pills <= 5)) {
            Toast.makeText(getContext(), R.string.invalid_pills, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private boolean isValidPeriod(Integer period) {
        boolean isValid;
        if (!(isValid = period >= 1 && period <= 24)) {
            Toast.makeText(getContext(), R.string.invalid_period, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }
}
