package pt.pxpepe.medscheduler.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.adapters.ScheduleListAdapter;
import pt.pxpepe.medscheduler.viewmodels.ScheduleViewModel;

public class HomeFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;
    private ScheduleListAdapter adapter;
    private View myFragmentView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);

        observerSetup();
        recyclerSetup();

        // Inflate the layout for this fragment
        return myFragmentView;
    }



    private void observerSetup(){

        scheduleViewModel.getAllSchedules(new Date().getTime()).observe(this, schedules -> {
            adapter.setScheduleList(schedules);
            if (schedules!=null
                    && schedules.size()>0) {
                myFragmentView.findViewById(R.id.lblEmptyScheduleSet).setVisibility(View.GONE);
            } else {
                myFragmentView.findViewById(R.id.lblEmptyScheduleSet).setVisibility(View.VISIBLE);
            }

        });

    }


    private void recyclerSetup() {
        RecyclerView recyclerView;
        adapter = new ScheduleListAdapter(R.layout.list_schedule);
        recyclerView = myFragmentView.findViewById(R.id.schedule_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

}
