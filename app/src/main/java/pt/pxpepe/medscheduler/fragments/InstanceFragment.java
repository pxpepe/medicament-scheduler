package pt.pxpepe.medscheduler.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import pt.pxpepe.medscheduler.Navigator;
import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.adapters.InstanceListAdapter;
import pt.pxpepe.medscheduler.data.JoinInstanceTreatment;
import pt.pxpepe.medscheduler.data.Schedule;
import pt.pxpepe.medscheduler.viewmodels.InstanceViewModel;
import pt.pxpepe.medscheduler.viewmodels.ScheduleViewModel;
import pt.pxpepe.medscheduler.workers.MyBroadcastReceiver;

public class InstanceFragment extends Fragment {

    private InstanceViewModel instanceViewModel;
    private ScheduleViewModel scheduleViewModel;
    private InstanceListAdapter adapter;
    private View myFragmentView;

    public InstanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_instance, container, false);

        instanceViewModel = ViewModelProviders.of(this).get(InstanceViewModel.class);
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);

        myFragmentView.findViewById(R.id.btnStartInstance).setOnClickListener(v -> ((Navigator) Objects.requireNonNull(getActivity())).displayView(R.layout.fragment_start_instance));

        observerSetup();
        recyclerSetup();

        // Inflate the layout for this fragment
        return myFragmentView;

    }

    private void observerSetup(){

        instanceViewModel.getAllInstances().observe(this, instances -> {
            adapter.setInstanceList(instances);
            if (instances!=null
                    && instances.size()>0) {
                myFragmentView.findViewById(R.id.lblEmptyInstanceSet).setVisibility(View.GONE);
            } else {
                myFragmentView.findViewById(R.id.lblEmptyInstanceSet).setVisibility(View.VISIBLE);
            }

        });

    }


    private void recyclerSetup() {
        RecyclerView recyclerView;
        adapter = new InstanceListAdapter(R.layout.list_instance);
        recyclerView = myFragmentView.findViewById(R.id.instance_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        JoinInstanceTreatment currentInstance = adapter.getCurrentInstance();
        if (currentInstance != null) {
            if (item.getItemId() == R.id.ctx_stop_instance) {
                LiveData<List<Schedule>> liveData = scheduleViewModel.getSchedulesByInstance(currentInstance.getInstanceId());
                liveData.observe(this, new Observer<List<Schedule>>() {
                    @Override
                    public void onChanged(@Nullable List<Schedule> scheduleList) {
                        if (scheduleList!=null) {
                            Context ctx = getContext();
                            for (Schedule schedule : scheduleList) {
                                MyBroadcastReceiver.cancelAlarmById(Objects.requireNonNull(ctx), schedule.getId());
                            }
                        }
                        liveData.removeObserver(this);
                    }
                });
                instanceViewModel.removeInstance(String.valueOf(currentInstance.getInstanceId()));
            }
        }
        return super.onContextItemSelected(item);
    }
}
