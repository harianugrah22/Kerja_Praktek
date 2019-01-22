package com.example.user.suratbpkad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 22/01/2019.
 */

public class AkunAdapter extends BaseAdapter {

    private Context context;
    public static ArrayList<Akun> peranArrayList;


    public AkunAdapter(Context context, ArrayList<Akun> akunArrayList) {
        this.context = context;
        this.peranArrayList = akunArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return peranArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return peranArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_akun, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.nama);
            holder.tvPeran = (TextView) convertView.findViewById(R.id.peran);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        holder.checkBox.setText(peranArrayList.get(position).getNamaAd());
        holder.tvPeran.setText(peranArrayList.get(position).getPeranAd());

        holder.checkBox.setChecked(peranArrayList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.peran);
                Integer pos = (Integer)  holder.checkBox.getTag();
                Toast.makeText(context, "Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();

                if(peranArrayList.get(pos).getSelected()){
                    peranArrayList.get(pos).setSelected(false);
                }else {
                    peranArrayList.get(pos).setSelected(true);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvPeran;

    }

}
