package pt.pxpepe.medscheduler.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import pt.pxpepe.medscheduler.data.Medicament;
import pt.pxpepe.medscheduler.viewmodels.MedicamentViewModel;

public class SaveMedicamentFragment extends Fragment {
    private static final String ARG_TREATMENT_ID = "medicamentId";

    private Integer updateId;
    private MedicamentViewModel medicamentViewModel;

    private TextView txtName;



    public SaveMedicamentFragment() {
        // Required empty public constructor
    }

    public static SaveMedicamentFragment newInstance(Integer id) {
        SaveMedicamentFragment fragment = new SaveMedicamentFragment();
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

        medicamentViewModel = ViewModelProviders.of(this).get(MedicamentViewModel.class);

        View v = inflater.inflate(R.layout.fragment_save_medicament, container, false);

        txtName = v.findViewById(R.id.txtMedicamentName);

        Button b = v.findViewById(R.id.btnSaveMedicament);
        b.setOnClickListener(v1 -> onClick());

        if (updateId!=null) {
            Medicament medicament = medicamentViewModel.findMedicamentById(updateId);
            txtName.setText(medicament.getName());
        }

        return v;
    }

    private void onClick() {
        String name = txtName.getText().toString();
        if (!name.equals("")) {

            if (updateId != null) {
                Medicament medicament = medicamentViewModel.findMedicamentById(updateId);
                medicament.setName(name);
                medicamentViewModel.updateMedicament(medicament);
            } else {
                medicamentViewModel.insertMedicament(new Medicament(name));
            }
            ((Navigator) Objects.requireNonNull(getActivity())).displayView(R.id.nav_medicament, true);
        } else {
            Toast.makeText(getContext(), R.string.all_the_fields_required, Toast.LENGTH_SHORT).show();
        }
    }
}
