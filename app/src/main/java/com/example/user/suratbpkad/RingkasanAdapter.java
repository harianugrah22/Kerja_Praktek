package com.example.user.suratbpkad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 24/01/2019.
 */

public class RingkasanAdapter extends BaseAdapter {
    Context context;
    ArrayList<Ringkasan> ringkasans;

    public RingkasanAdapter(Context c, ArrayList<Ringkasan> ringkasans) {
        RingkasanAdapter.this.context = c;
        RingkasanAdapter.this.ringkasans = ringkasans;
    }
    @Override
    public int getCount() {
        return ringkasans.size();
    }

    @Override
    public Object getItem(int i) {
        return ringkasans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.content_ringkasan,parent,false);
        }

        TextView penomoranTxt = (TextView) convertView.findViewById(R.id.penomoran);
        TextView namaTxt = (TextView) convertView.findViewById(R.id.nama);
        TextView baruTxt = (TextView) convertView.findViewById(R.id.baru);
        TextView diprosesTxt = (TextView) convertView.findViewById(R.id.diproses);
        TextView direviewTxt = (TextView) convertView.findViewById(R.id.direview);
        TextView verifikasiTxt = (TextView) convertView.findViewById(R.id.verifikasi);
        TextView selesaiTxt = (TextView) convertView.findViewById(R.id.selesai);


        final Ringkasan r = (Ringkasan) this.getItem(i);

        penomoranTxt.setText(r.getPenomoran_ring());
        namaTxt.setText(r.getNama_ring());
        baruTxt.setText(r.getBaru_Diupload());
        diprosesTxt.setText(r.getSedang_Diproses());
        direviewTxt.setText(r.getSedang_Direview());
        verifikasiTxt.setText(r.getMenunggu_Verifikasi());
        selesaiTxt.setText(r.getSelesai());

        return convertView;
    }
}
