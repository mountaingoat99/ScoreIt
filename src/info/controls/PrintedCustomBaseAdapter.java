package info.controls;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import info.Helpers.PrintedResults;

import java.util.ArrayList;

public class PrintedCustomBaseAdapter extends BaseAdapter{

    private static ArrayList<PrintedResults> searchArrayList;

    private LayoutInflater mInflater;

    public PrintedCustomBaseAdapter(Context context, ArrayList<PrintedResults> results){
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
        return null;
    }
}
