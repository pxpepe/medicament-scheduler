package pt.pxpepe.medscheduler.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pt.pxpepe.medscheduler.data.Treatment;

public class TreatmentSpinnerAdapter extends ArrayAdapter<Treatment> {

    private List<Treatment>  values;

    public TreatmentSpinnerAdapter(Context context, int textViewResourceId,
                                   List<Treatment> values) {
        super(context, textViewResourceId, values);
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public Treatment getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                @NotNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getName());

        return label;
    }

}
