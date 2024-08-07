package org.example.cuoiki_code_tutorial.Models;

public class BaiHoc {
    private String maBaiHoc;
    private String tenBaiHoc;
    private String noiDung;
    private String dinhDang;
    private int thoiLuong;
    private String maChuong;
    private int thuTu;
    private int trangThai;
    private int gioiHanKyTu;
    private String mucDo;
    private String codeMau;
    private String maKhoaHoc;

    public BaiHoc() {
    }

    public BaiHoc(String maBaiHoc, String tenBaiHoc, String noiDung, int thoiLuong, String maChuong, int thuTu, int trangThai) {
        this.maBaiHoc = maBaiHoc;
        this.tenBaiHoc = tenBaiHoc;
        this.noiDung = noiDung;
        this.maChuong = maChuong;
        this.thoiLuong = thoiLuong;

        this.thuTu = thuTu;
        this.trangThai = trangThai;
    }

    public BaiHoc(String maBaiHoc, String tenBaiHoc, String noiDung, String dinhDang, int thoiLuong, String maChuong, int thuTu, int trangThai, int gioiHanKyTu, String mucDo, String codeMau, String maKhoaHoc) {
        this.maBaiHoc = maBaiHoc;
        this.tenBaiHoc = tenBaiHoc;
        this.noiDung = noiDung;
        this.dinhDang = dinhDang;
        this.thoiLuong = thoiLuong;
        this.maChuong = maChuong;
        this.thuTu = thuTu;
        this.trangThai = trangThai;
        this.gioiHanKyTu = gioiHanKyTu;
        this.mucDo = mucDo;
        this.codeMau = codeMau;
        this.maKhoaHoc = maKhoaHoc;
    }

    public String getMaBaiHoc() {
        return maBaiHoc;
    }

    public void setMaBaiHoc(String maBaiHoc) {
        this.maBaiHoc = maBaiHoc;
    }

    public String getTenBaiHoc() {
        return tenBaiHoc;
    }

    public void setTenBaiHoc(String tenBaiHoc) {
        this.tenBaiHoc = tenBaiHoc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getDinhDang() {
        return dinhDang;
    }

    public void setDinhDang(String dinhDang) {
        this.dinhDang = dinhDang;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getMaChuong() {
        return maChuong;
    }

    public void setMaChuong(String maChuong) {
        this.maChuong = maChuong;
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

    public int getGioiHanKyTu() {
        return gioiHanKyTu;
    }

    public void setGioiHanKyTu(int gioiHanKyTu) {
        this.gioiHanKyTu = gioiHanKyTu;
    }

    public String getMucDo() {
        return mucDo;
    }

    public void setMucDo(String mucDo) {
        this.mucDo = mucDo;
    }

    public String getCodeMau() {
        return codeMau;
    }

    public void setCodeMau(String codeMau) {
        this.codeMau = codeMau;
    }

    public String getMaKhoaHoc() {
        return maKhoaHoc;
    }

    public void setMaKhoaHoc(String maKhoaHoc) {
        this.maKhoaHoc = maKhoaHoc;
    }
}
