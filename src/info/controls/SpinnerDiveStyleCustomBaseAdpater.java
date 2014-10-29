package info.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rodriguez.divingscores.R;

import java.util.ArrayList;

import info.Helpers.DiveStyleSpinner;

public class SpinnerDiveStyleCustomBaseAdpater extends BaseAdapter {

    private static ArrayList<DiveStyleSpinner> searchArrayList;

    private LayoutInflater mInflator;

    public SpinnerDiveStyleCustomBaseAdpater(Context context, ArrayList<DiveStyleSpinner> results){
        searchArrayList = results;
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return searchArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflator.inflate(R.layout.spinner_two_column, null);
            holder = new ViewHolder();
            holder.txtid = (TextView) convertView.findViewById(R.id.diveId);
            holder.txtDiveStyle = (TextView) convertView.findViewById(R.id.diveStyle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.txtid.setText(searchArrayList.get(position).getId());
        holder.txtDiveStyle.setText(searchArrayList.get(position).getDiveStyle());

        return convertView;
    }

    static class ViewHolder {
        TextView txtid;
        TextView txtDiveStyle;
    }
}
