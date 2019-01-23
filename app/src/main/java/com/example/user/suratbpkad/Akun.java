package com.example.user.suratbpkad;

/**
 * Created by user on 22/01/2019.
 */

public class Akun {
    private boolean isSelected;
    private String namaAd;
    private String peranAd;
    private String userAd;

    public String getUserAd() { return userAd; }

    public void setUserAd(String userAd) { this.userAd = userAd; }

    public String getNamaAd() {
        return namaAd;
    }

    public void setNamaAd(String namaAd) {
        this.namaAd = namaAd;
    }

    public String getPeranAd(){
        return peranAd;
    }

    public void setPeranAd (String peranAd){
        this.peranAd = peranAd;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
