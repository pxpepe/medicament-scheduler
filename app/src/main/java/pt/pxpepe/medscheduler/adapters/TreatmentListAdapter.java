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
import pt.pxpepe.medscheduler.data.Treatment;

public class TreatmentListAdapter extends RecyclerView.Adapter<TreatmentListAdapter.ViewHolder> {

    private int treatmentItemLayout;
    private List<Treatment> treatmentList;
    private int position;
    private Treatment currentTreatment;

    public Treatment getCurrentTreatment() {
        return currentTreatment;
    }

    // Constructor
    public TreatmentListAdapter(int layoutId) {
        treatmentItemLayout = layoutId;
    }

    public void setTreatmentList(List<Treatment> treatments) {
        treatmentList = treatments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return treatmentList == null ? 0 : treatmentList.size();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(treatmentItemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int listPosition) {
        Treatment treatment = treatmentList.get(listPosition);

        TextView treatmentId = holder.treatmentId;
        TextView treatmentName = holder.treatmentName;
        TextView treatmentDuration = holder.treatmentDuration;

        treatmentId.setText(String.valueOf(treatment.getId()));
        treatmentName.setText(treatment.getName());
        treatmentDuration.setText(String.valueOf(treatment.getDuration()));

        holder.itemView.setOnLongClickListener(v -> {
            position = holder.getAdapterPosition();
            currentTreatment = treatmentList.get(position);
            return false;
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView treatmentId;
        TextView treatmentName;
        TextView treatmentDuration;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                menu.setHeaderTitle(v.getResources().getString(R.string.select_an_option));
                menu.add(Menu.NONE, R.id.ctx_detail_treatment, Menu.NONE, v.getResources().getString(R.string.detail));
                menu.add(Menu.NONE, R.id.ctx_remove_treatment, Menu.NONE, v.getResources().getString(R.string.delete));
                menu.add(Menu.NONE, R.id.ctx_update_treatment, Menu.NONE, v.getResources().getString(R.string.update));
                menu.add(Menu.NONE, R.id.ctx_share_treatment, Menu.NONE, v.getResources().getString(R.string.share));

            });
            treatmentId = itemView.findViewById(R.id.itemTreatmentId);
            treatmentName = itemView.findViewById(R.id.itemTreatmentName);
            treatmentDuration = itemView.findViewById(R.id.itemTreatmentDuration);
        }
    }


}