package com.example.user.suratbpkad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 28/01/2019.
 */

public class KetRingkasanAdapter extends BaseAdapter {
    Context context;
    ArrayList<KetRingkasan> ringkasans;

    public KetRingkasanAdapter(Context c, ArrayList<KetRingkasan> ringkasans) {
        KetRingkasanAdapter.this.context = c;
        KetRingkasanAdapter.this.ringkasans = ringkasans;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.content_ketringkasan,parent,false);
        }

        TextView penomoranTxt = (TextView) convertView.findViewById(R.id.penomoran);
        TextView perihal_suratTxt = (TextView) convertView.findViewById(R.id.perihal);
        TextView nomor_suratTxt = (TextView) convertView.findViewById(R.id.nomor_surat);
        TextView pengirim_suratTxt = (TextView) convertView.findViewById(R.id.pengirim_surat);
        TextView tanggal_suratTxt = (TextView) convertView.findViewById(R.id.tanggal_surat);
        TextView status_suratTxt = (TextView) convertView.findViewById(R.id.status_surat);
        TextView sifat_suratTxt = (TextView) convertView.findViewById(R.id.sifat_surat);

        final KetRingkasan s = (KetRingkasan) this.getItem(i);

        penomoranTxt.setText(s.getPenomoran());
        perihal_suratTxt.setText(s.getPerihal_surat());
        nomor_suratTxt.setText(s.getNomor_surat());
        pengirim_suratTxt.setText(s.getPengirim_surat());
        tanggal_suratTxt.setText(s.getTanggal_surat());
        status_suratTxt.setText(s.getStatus_surat());
        sifat_suratTxt.setText(s.getSifat_surat());

        return convertView;
    }
}
