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
import pt.pxpepe.medscheduler.data.Medicament;

public class MedicamentListAdapter extends RecyclerView.Adapter<MedicamentListAdapter.ViewHolder> {

    private int medicamentItemLayout;
    private List<Medicament> medicamentList;
    private int position;
    private Medicament currentMedicament;

    public Medicament getCurrentMedicament() {
        return currentMedicament;
    }

    // Constructor
    public MedicamentListAdapter(int layoutId) {
        medicamentItemLayout = layoutId;
    }

    public void setMedicamentList(List<Medicament> medicaments) {
        medicamentList = medicaments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return medicamentList == null ? 0 : medicamentList.size();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(medicamentItemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final ViewHolder holder, final int listPosition) {
        Medicament medicament = medicamentList.get(listPosition);

        TextView medicamentId = holder.medicamentId;
        TextView medicamentName = holder.medicamentName;

        medicamentId.setText(String.valueOf(medicament.getId()));
        medicamentName.setText(medicament.getName());

        holder.itemView.setOnLongClickListener(v -> {
            position = holder.getAdapterPosition();
            currentMedicament = medicamentList.get(position);
            return false;
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicamentId;
        TextView medicamentName;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                menu.setHeaderTitle(v.getResources().getString(R.string.select_an_option));
                menu.add(Menu.NONE, R.id.ctx_remove_medicament, Menu.NONE, v.getResources().getString(R.string.delete));
                menu.add(Menu.NONE, R.id.ctx_update_medicament, Menu.NONE, v.getResources().getString(R.string.update));

            });
            medicamentId = itemView.findViewById(R.id.itemMedicamentId);
            medicamentName = itemView.findViewById(R.id.itemMedicamentName);
        }
    }


}