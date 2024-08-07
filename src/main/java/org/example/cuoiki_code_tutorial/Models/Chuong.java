package org.example.cuoiki_code_tutorial.Models;

public class Chuong {
    private String maChuong;
    private String tenChuong;
    private int thuTu;
    private String maKH;

    public Chuong() {
    }

    public Chuong(String maChuong, String tenChuong, int thuTu, String maKH) {
        this.maChuong = maChuong;
        this.tenChuong = tenChuong;
        this.thuTu = thuTu;
        this.maKH = maKH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public int getThuTu() {
        return thuTu;
    }

    public void setThuTu(int thuTu) {
        this.thuTu = thuTu;
    }

    public String getTenChuong() {
        return tenChuong;
    }

    public void setTenChuong(String tenChuong) {
        this.tenChuong = tenChuong;
    }

    public String getMaChuong() {
        return maChuong;
    }

    public void setMaChuong(String maChuong) {
        this.maChuong = maChuong;
    }
}
