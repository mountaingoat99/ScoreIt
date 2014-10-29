package info.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rodriguez.divingscores.R;

import java.util.ArrayList;

import info.Helpers.RankingResults;


public class MyCustomBaseAdapter extends BaseAdapter {
    private static ArrayList<RankingResults> searchArrayList;

    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<RankingResults> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.list_rankings, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.txtScore = (TextView) convertView.findViewById(R.id.score);
            holder.txtDiveNumber = (TextView) convertView.findViewById(R.id.diveNumber);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.txtName.setText(searchArrayList.get(position).getName());
        holder.txtScore.setText(searchArrayList.get(position).getScore());
        holder.txtDiveNumber.setText(searchArrayList.get(position).getDiveNumber());

        return convertView;
    }
    static class ViewHolder {
        TextView txtName;
        TextView txtScore;
        TextView txtDiveNumber;
    }

}
