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
import pt.pxpepe.medscheduler.data.JoinDosisMedicamentTreatment;

public class DosisListAdapter extends RecyclerView.Adapter<DosisListAdapter.ViewHolder> {

    private int dosisItemLayout;
    private List<JoinDosisMedicamentTreatment> dosisList;
    private int position;
    private JoinDosisMedicamentTreatment currentDosis;

    public JoinDosisMedicamentTreatment getCurrentDosis() {
        return currentDosis;
    }

    // Constructor
    public DosisListAdapter(int layoutId) {
        dosisItemLayout = layoutId;
    }

    public void setDosisList(List<JoinDosisMedicamentTreatment> dosiss) {
        dosisList = dosiss;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dosisList == null ? 0 : dosisList.size();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(dosisItemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int listPosition) {
        JoinDosisMedicamentTreatment dosis = dosisList.get(listPosition);

        TextView dosisId = holder.dosisId;
        TextView dosisMedicamentName= holder.dosisMedicamentName;
        TextView dosisPeriod= holder.dosisPeriod;
        TextView dosisPills= holder.dosisPills;

        dosisId.setText(String.valueOf(dosis.getDosisId()));
        dosisMedicamentName.setText(dosis.getMedicamentName());
        dosisPeriod.setText(String.valueOf(dosis.getPeriod()));
        dosisPills.setText(String.valueOf(dosis.getPillNumber()));

        holder.itemView.setOnLongClickListener(v -> {
            position = holder.getAdapterPosition();
            currentDosis = dosisList.get(position);
            return false;
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dosisId;
        TextView dosisMedicamentName;
        TextView dosisPeriod;
        TextView dosisPills;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> menu.add(Menu.NONE, R.id.ctx_delete_dosis, Menu.NONE, v.getResources().getString(R.string.delete)));
            dosisId = itemView.findViewById(R.id.itemDosisId);
            dosisMedicamentName = itemView.findViewById(R.id.itemDosisMedicamentName);
            dosisPeriod = itemView.findViewById(R.id.itemDosisPeriod);
            dosisPills = itemView.findViewById(R.id.itemPills);
        }
    }


}