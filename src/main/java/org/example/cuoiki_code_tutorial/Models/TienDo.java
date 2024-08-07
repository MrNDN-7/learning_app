package org.example.cuoiki_code_tutorial.Models;

public class TienDo {
    private String maTK;
    private String maKhoaHoc;
    private int thuTu;
    private int trangThai;

    public TienDo() {
    }

    public TienDo(String maTK, String maKhoaHoc, int thuTu, int trangThai) {
        this.maTK = maTK;
        this.maKhoaHoc = maKhoaHoc;
        this.thuTu = thuTu;
        this.trangThai = trangThai;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public String getMaKhoaHoc() {
        return maKhoaHoc;
    }

    public void setMaKhoaHoc(String maKhoaHoc) {
        this.maKhoaHoc = maKhoaHoc;
    }

    public int getThuTu() {
        return thuTu;
    }

    public void setThuTu(int thuTu) {
        this.thuTu = thuTu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
