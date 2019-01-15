package com.example.user.suratbpkad;

/**
 * Created by user on 10/01/2019.
 */

public class Surat {
    String key, penomoran, perihal_surat, nomor_surat, pengirim_surat, tanggal_surat, tanggal_terima, status_surat, sifat_surat, yang_ditugaskan, memo_surat;

    public Surat(){
    }

    public String getKey(){return key;}
    public void setKey(String key) {this.key = key;}
    public String getPenomoran(){return penomoran;}
    public void setPenomoran(String penomoran) {this.penomoran = penomoran;}
    public String getPerihal_surat(){return perihal_surat;}
    public void setPerihal_surat(String perihal_surat) {this.perihal_surat = perihal_surat;}
    public String getNomor_surat(){return nomor_surat;}
    public void setNomor_surat(String nomor_surat) {this.nomor_surat = nomor_surat;}
    public String getPengirim_surat(){return pengirim_surat;}
    public void setPengirim_surat(String pengirim_surat) {this.pengirim_surat = pengirim_surat;}
    public String getTanggal_surat(){return tanggal_surat;}
    public void setTanggal_surat(String tanggal_surat) {this.tanggal_surat = tanggal_surat;}
    public String getTanggal_terima(){return tanggal_terima;}
    public void setTanggal_terima(String tanggal_terima) {this.tanggal_terima = tanggal_terima;}
    public String getStatus_surat(){return status_surat;}
    public void setStatus_surat(String status_surat) {this.status_surat = status_surat;}
    public String getYang_ditugaskan(){return yang_ditugaskan;}
    public void setYang_ditugaskan(String yang_ditugaskan) {this.yang_ditugaskan = yang_ditugaskan;}
    public String getSifat_surat(){return sifat_surat;}
    public void setSifat_surat(String sifat_surat) {this.sifat_surat = sifat_surat;}
    public String getMemo_surat(){return memo_surat;}
    public void setMemo_surat(String memo_surat) {this.memo_surat = memo_surat;}
}
