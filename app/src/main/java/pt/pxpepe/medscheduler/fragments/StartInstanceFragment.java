package pt.pxpepe.medscheduler.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import pt.pxpepe.medscheduler.Navigator;
import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.adapters.TreatmentSpinnerAdapter;
import pt.pxpepe.medscheduler.data.Instance;
import pt.pxpepe.medscheduler.data.JoinDosisMedicamentTreatment;
import pt.pxpepe.medscheduler.data.Schedule;
import pt.pxpepe.medscheduler.data.Treatment;
import pt.pxpepe.medscheduler.utilities.DateUtil;
import pt.pxpepe.medscheduler.viewmodels.DosisViewModel;
import pt.pxpepe.medscheduler.viewmodels.InstanceViewModel;
import pt.pxpepe.medscheduler.viewmodels.ScheduleViewModel;
import pt.pxpepe.medscheduler.viewmodels.TreatmentViewModel;
import pt.pxpepe.medscheduler.workers.MyBroadcastReceiver;

public class StartInstanceFragment extends Fragment {

    private InstanceViewModel instanceViewModel;
    private TreatmentViewModel treatmentViewModel;
    private DosisViewModel dosisViewModel;
    private ScheduleViewModel scheduleViewModel;

    Spinner treatmentSpinner;
    EditText txtStartDate;

    public StartInstanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        instanceViewModel = ViewModelProviders.of(this).get(InstanceViewModel.class);
        treatmentViewModel = ViewModelProviders.of(this).get(TreatmentViewModel.class);
        dosisViewModel = ViewModelProviders.of(this).get(DosisViewModel.class);
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);

        View v = inflater.inflate(R.layout.fragment_start_instance, container, false);

        treatmentSpinner = v.findViewById(R.id.spnTreatments);
        txtStartDate = v.findViewById(R.id.txtStartDate);
        txtStartDate.setText(DateUtil.date2String(new Date(), getContext()));

        fillSpinner();

        Button b = v.findViewById(R.id.btnSaveInstance);
        b.setOnClickListener(v1 -> onClick());

        return v;
    }

    private void fillSpinner() {

        treatmentViewModel.getAllTreatments().observe(this, treatments -> {
            TreatmentSpinnerAdapter treatmentSpinnerAdapter = new TreatmentSpinnerAdapter(getContext(),
                    android.R.layout.simple_spinner_item,
                    treatments);
            treatmentSpinner.setAdapter(treatmentSpinnerAdapter);
        });

    }

    private void onClick() {
        Treatment treatment = (Treatment) treatmentSpinner.getSelectedItem();
        Date startDate = DateUtil.string2Date(txtStartDate.getText().toString(), getContext());
        if (treatment!=null
            && startDate!=null) {
            Date endDate = DateUtil.sumDays(startDate,treatment.getDuration());

            LiveData<List<JoinDosisMedicamentTreatment>> liveData = dosisViewModel.getAllDosiss(treatment.getId());
            liveData.observe(this, new Observer<List<JoinDosisMedicamentTreatment>>() {
                @Override
                public void onChanged(@Nullable List<JoinDosisMedicamentTreatment> joinDosisMedicamentTreatments) {
                    if (joinDosisMedicamentTreatments!=null
                        && joinDosisMedicamentTreatments.size()>0) {
                        Long insertdId = instanceViewModel.insertInstance(new Instance(treatment.getId(), startDate, endDate));
                        if (insertdId!=null && insertdId>0) {
                            scheduleDosis(joinDosisMedicamentTreatments, insertdId, startDate, endDate);
                            ((Navigator) Objects.requireNonNull(getActivity())).displayView(R.id.nav_instance, true);
                        } else {
                            Toast.makeText(getContext(), R.string.treatment_instance_exist, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.dosis_must_be_created, Toast.LENGTH_SHORT).show();
                    }
                    liveData.removeObserver(this);
                }
            });


        } else {
            Toast.makeText(getContext(), R.string.all_the_fields_required, Toast.LENGTH_SHORT).show();
        }
    }

    private void scheduleDosis(@NotNull List<JoinDosisMedicamentTreatment> joinDosisMedicamentTreatments, Long instanceId, Date startDate, Date endDate) {
        for (JoinDosisMedicamentTreatment dosis : joinDosisMedicamentTreatments) {
            Date scheduleDate = new Date(startDate.getTime());
            while (scheduleDate.getTime()<=endDate.getTime()) {
                Long scheduleId = scheduleViewModel.insertSchedule(new Schedule(instanceId.intValue(), dosis.getMedicamentId(), scheduleDate));
                if (scheduleId!=null && scheduleId>0) {
                    String strNotification = getString(R.string.take_medicament,dosis.getMedicamentName(), DateUtil.date2String(scheduleDate, getContext()));
                    MyBroadcastReceiver.scheduleNotification(getContext(), scheduleDate.getTime(), scheduleId.intValue(), strNotification);
                }
                long hoursInMilis = dosis.getDuration()*60*60*1000;
                scheduleDate=new Date(scheduleDate.getTime()+hoursInMilis);
            }



        }
    }
}
