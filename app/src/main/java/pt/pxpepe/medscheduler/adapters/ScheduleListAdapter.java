package pt.pxpepe.medscheduler.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.data.JoinScheduleInstanceMedicament;
import pt.pxpepe.medscheduler.utilities.DateUtil;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    private int scheduleItemLayout;
    private List<JoinScheduleInstanceMedicament> scheduleList;

    // Constructor
    public ScheduleListAdapter(int layoutId) {
        scheduleItemLayout = layoutId;
    }

    public void setScheduleList(List<JoinScheduleInstanceMedicament> schedules) {
        scheduleList = schedules;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return scheduleList == null ? 0 : scheduleList.size();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(scheduleItemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int listPosition) {
        JoinScheduleInstanceMedicament schedule = scheduleList.get(listPosition);

        TextView itemMedicamentName = holder.itemMedicamentName;
        TextView itemScheduleDate = holder.itemScheduleDate;

        itemMedicamentName.setText(schedule.getMedicamentName());
        itemScheduleDate.setText(DateUtil.date2String(schedule.getInstant(), holder.itemView.getContext()));

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemMedicamentName;
        TextView itemScheduleDate;
        ViewHolder(View itemView) {
            super(itemView);
            itemMedicamentName = itemView.findViewById(R.id.itemMedicamentName);
            itemScheduleDate = itemView.findViewById(R.id.itemScheduleDate);
        }
    }


}