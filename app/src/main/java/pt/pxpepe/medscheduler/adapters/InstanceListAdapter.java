package pt.pxpepe.medscheduler.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.pxpepe.medscheduler.R;
import pt.pxpepe.medscheduler.data.JoinInstanceTreatment;
import pt.pxpepe.medscheduler.utilities.DateUtil;

public class InstanceListAdapter extends RecyclerView.Adapter<InstanceListAdapter.ViewHolder> {

    private int instanceItemLayout;
    private List<JoinInstanceTreatment> instanceList;
    private int position;
    private JoinInstanceTreatment currentInstance;

    public JoinInstanceTreatment getCurrentInstance() {
        return currentInstance;
    }

    // Constructor
    public InstanceListAdapter(int layoutId) {
        instanceItemLayout = layoutId;
    }

    public void setInstanceList(List<JoinInstanceTreatment> instances) {
        instanceList = instances;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return instanceList == null ? 0 : instanceList.size();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(instanceItemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int listPosition) {
        JoinInstanceTreatment instance = instanceList.get(listPosition);

        TextView instanceId= holder.instanceId;
        TextView instanceTreatmentName= holder.instanceTreatmentName;
        TextView instanceStartDate= holder.instanceStartDate;
        TextView instanceEndDate= holder.instanceEndDate;

        instanceId.setText(String.valueOf(instance.getInstanceId()));
        instanceTreatmentName.setText(instance.getName());
        instanceStartDate.setText(DateUtil.date2String(instance.getStartDate(), holder.itemView.getContext()));
        instanceEndDate.setText(DateUtil.date2String(instance.getEndDate(), holder.itemView.getContext()));

        holder.itemView.setOnLongClickListener(v -> {
            position = holder.getAdapterPosition();
            currentInstance = instanceList.get(position);
            return false;
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView instanceId;
        TextView instanceTreatmentName;
        TextView instanceStartDate;
        TextView instanceEndDate;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> menu.add(Menu.NONE, R.id.ctx_stop_instance, Menu.NONE, v.getResources().getString(R.string.stop)));
            instanceId = itemView.findViewById(R.id.itemInstanceId);
            instanceTreatmentName = itemView.findViewById(R.id.itemInstanceTreatmentName);
            instanceStartDate = itemView.findViewById(R.id.itemInstanceStartDate);
            instanceEndDate = itemView.findViewById(R.id.itemInstanceEndDate);
        }
    }


}