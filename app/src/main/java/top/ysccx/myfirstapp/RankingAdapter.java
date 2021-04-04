package top.ysccx.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;


public class RankingAdapter extends BaseAdapter {
    private LinkedList<Ranking> mData;
    private Context mContext;

    public RankingAdapter(LinkedList<Ranking> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.ranking_item,null);
        TextView uid = convertView.findViewById(R.id.uid);
        TextView uname = convertView.findViewById(R.id.uname);
        TextView utime = convertView.findViewById(R.id.utime);
        TextView udate = convertView.findViewById(R.id.udate);
        uid.setText(mData.get(position).getId());
        uname.setText(mData.get(position).getName());
        utime.setText(mData.get(position).getTime());
        udate.setText(mData.get(position).getDate());
        return convertView;
    }
}
