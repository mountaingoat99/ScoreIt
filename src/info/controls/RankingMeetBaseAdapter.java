package info.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rodriguez.divingscores.R;
import com.rodriguez.divingscores.RankingsMeet;

import java.util.ArrayList;

public class RankingMeetBaseAdapter extends BaseAdapter {

    private static ArrayList<RankingsMeet> searchArrayList;

    private LayoutInflater mInflater;

    public RankingMeetBaseAdapter(Context context, ArrayList<RankingsMeet> results){
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
            convertView = mInflater.inflate(R.layout.list_rank_meters, null);
            holder = new ViewHolder();
            holder.txtMeetName = (TextView) convertView.findViewById(R.id.meetName);
            holder.txtBoard = (TextView) convertView.findViewById(R.id.board);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.txtMeetName.setText(searchArrayList.get(position).getMeetName());
        holder.txtBoard.setText(searchArrayList.get(position).getBoardSize());

        return convertView;
    }

    static class ViewHolder{
        TextView txtMeetName;
        TextView txtBoard;
    }
}
