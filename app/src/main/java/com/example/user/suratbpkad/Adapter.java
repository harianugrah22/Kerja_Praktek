package com.example.user.suratbpkad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 09/01/2019.
 */

public class Adapter extends BaseAdapter {
    private static ArrayList<SearchResult> searchArrayList;
    private LayoutInflater mInflater;

    public Adapter(Context context, ArrayList<SearchResult> results){
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
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.content_disposisi_kabid, null);
            holder = new ViewHolder();
            holder.txtNomorSurat = (TextView) convertView.findViewById(R.id.nomor_surat);

        }
        return null;
    }

    static class ViewHolder{
        TextView txtNomorSurat;
        TextView txtTanggalSurat;
        TextView txtTanggalTerima;
        TextView txtPengirim;
        TextView txtPerihal;
        TextView txtMemo;
        TextView txtStatus;
        TextView txtYangDitugaskan;
    }
}
