package com.example.user.suratbpkad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 10/01/2019.
 */

public class SuratAdapter extends BaseAdapter {

    Context context;
    ArrayList<Surat> surats;

    public SuratAdapter(Context c, ArrayList<Surat> surats) {
        SuratAdapter.this.context = c;
        SuratAdapter.this.surats = surats;
    }

    @Override
    public int getCount() {
        return surats.size();
    }

    @Override
    public Object getItem(int position) {
        return surats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.content_disposisi_kabid,parent,false);
        }

        TextView penomoranTxt = (TextView) convertView.findViewById(R.id.penomoran);
        TextView perihal_suratTxt = (TextView) convertView.findViewById(R.id.perihal);
        TextView nomor_suratTxt = (TextView) convertView.findViewById(R.id.nomor_surat);
        TextView pengirim_suratTxt = (TextView) convertView.findViewById(R.id.pengirim_surat);
        TextView tanggal_suratTxt = (TextView) convertView.findViewById(R.id.tanggal_surat);
        TextView tanggal_terimaTxt = (TextView) convertView.findViewById(R.id.tanggal_terima);
        TextView status_suratTxt = (TextView) convertView.findViewById(R.id.status_surat);
        TextView sifat_suratTxt = (TextView) convertView.findViewById(R.id.sifat_surat);
        TextView yang_ditugaskanTxt = (TextView) convertView.findViewById(R.id.yang_ditugaskan);

        final Surat s = (Surat) this.getItem(position);

        penomoranTxt.setText(s.getPenomoran());
        perihal_suratTxt.setText(s.getPerihal_surat());
        nomor_suratTxt.setText(s.getNomor_surat());
        pengirim_suratTxt.setText(s.getPengirim_surat());
        tanggal_suratTxt.setText(s.getTanggal_surat());
        tanggal_terimaTxt.setText(s.getTanggal_terima());
        status_suratTxt.setText(s.getStatus_surat());
        sifat_suratTxt.setText(s.getSifat_surat());
        yang_ditugaskanTxt.setText(s.getYang_ditugaskan());

        return convertView;
    }
}
