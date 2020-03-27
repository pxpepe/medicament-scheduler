package pt.pxpepe.medscheduler.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pt.pxpepe.medscheduler.Navigator;
import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.adapters.TreatmentListAdapter;
import pt.pxpepe.medscheduler.data.JoinDosisMedicamentTreatment;
import pt.pxpepe.medscheduler.data.Treatment;
import pt.pxpepe.medscheduler.dto.MedicamentDosisDTO;
import pt.pxpepe.medscheduler.dto.TreatmentDTO;
import pt.pxpepe.medscheduler.utilities.SerializationHelper;
import pt.pxpepe.medscheduler.viewmodels.DosisViewModel;
import pt.pxpepe.medscheduler.viewmodels.TreatmentViewModel;

public class TreatmentFragment extends Fragment {

    private TreatmentViewModel treatmentViewModel;
    private DosisViewModel dosisViewModel;
    private TreatmentListAdapter adapter;
    private View myFragmentView;
    Message mMessage;

    public TreatmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        unPublishMessage();
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_treatment, container, false);

        treatmentViewModel = ViewModelProviders.of(this).get(TreatmentViewModel.class);
        dosisViewModel = ViewModelProviders.of(this).get(DosisViewModel.class);

        myFragmentView.findViewById(R.id.btnAddTreatment).setOnClickListener(v -> ((Navigator) Objects.requireNonNull(getActivity())).displayView(R.layout.fragment_save_treatment));

        observerSetup();
        recyclerSetup();

        // Inflate the layout for this fragment
        return myFragmentView;

    }

    private void observerSetup(){

        treatmentViewModel.getAllTreatments().observe(this, treatments -> {
            adapter.setTreatmentList(treatments);
            if (treatments!=null
                    && treatments.size()>0) {
                myFragmentView.findViewById(R.id.lblEmptyTreatmentSet).setVisibility(View.GONE);
            } else {
                myFragmentView.findViewById(R.id.lblEmptyTreatmentSet).setVisibility(View.VISIBLE);
            }

        });

    }


    private void recyclerSetup() {
        RecyclerView recyclerView;
        adapter = new TreatmentListAdapter(R.layout.list_treatment);
        recyclerView = myFragmentView.findViewById(R.id.treatment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Treatment currentTreatment = adapter.getCurrentTreatment();
        if (currentTreatment != null) {
            switch (item.getItemId()) {
                case R.id.ctx_detail_treatment:
                    ((Navigator) Objects.requireNonNull(getActivity())).displayFragment(
                            DetailTreatmentFragment
                                    .newInstance(currentTreatment.getId()),
                            getString(R.string.treatment_detail),
                            R.layout.fragment_detail_treatment, false);
                    break;
                case R.id.ctx_remove_treatment:
                    treatmentViewModel.removeTreatment(String.valueOf(currentTreatment.getId()));
                    break;
                case R.id.ctx_update_treatment:
                    ((Navigator) Objects.requireNonNull(getActivity())).displayFragment(
                            SaveTreatmentFragment
                                    .newInstance(currentTreatment.getId()),
                            getString(R.string.save_treatment),
                            R.layout.fragment_save_treatment, false);
                    break;

                case R.id.ctx_share_treatment:
                    shareWithOthers(currentTreatment);
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void shareWithOthers(Treatment currentTreatment) {
        LiveData<List<JoinDosisMedicamentTreatment>> liveData = dosisViewModel.getAllDosiss(currentTreatment.getId());
        liveData.observe(this, new Observer<List<JoinDosisMedicamentTreatment>>() {
            @Override
            public void onChanged(@Nullable List<JoinDosisMedicamentTreatment> joinDosisMedicamentTreatments) {
                if (joinDosisMedicamentTreatments!=null
                        && joinDosisMedicamentTreatments.size()>0) {
                    TreatmentDTO treatmentDto = getTreatmentDto(joinDosisMedicamentTreatments, currentTreatment);
                    unPublishMessage();
                    mMessage = new Message(SerializationHelper.serialize(treatmentDto),Navigator.MED_SCHEDULER_TYPE);
                    Nearby.getMessagesClient(Objects.requireNonNull(getContext())).publish(mMessage);
                } else {
                    Toast.makeText(getContext(), R.string.dosis_must_be_created_share, Toast.LENGTH_SHORT).show();
                }
                liveData.removeObserver(this);
            }
        });

    }

    private void unPublishMessage() {
        if (mMessage!=null) {
            Nearby.getMessagesClient(Objects.requireNonNull(getContext())).unpublish(mMessage);
            mMessage=null;
        }
    }

    private TreatmentDTO getTreatmentDto(@NotNull List<JoinDosisMedicamentTreatment> joinDosisMedicamentTreatments, Treatment currentTreatment) {
        List<MedicamentDosisDTO> medDosisDto = new ArrayList<>(joinDosisMedicamentTreatments.size());
        TreatmentDTO treatment = new TreatmentDTO(currentTreatment.getName(), currentTreatment.getDuration(), medDosisDto);
        for (JoinDosisMedicamentTreatment dosis : joinDosisMedicamentTreatments) {
            medDosisDto.add(new MedicamentDosisDTO(dosis.getMedicamentName(), dosis.getPeriod(), dosis.getPillNumber(), dosis.getDelayNumber()));
        }
        return treatment;
    }
}
