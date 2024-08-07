package org.example.cuoiki_code_tutorial.Models;

import java.util.Date;

public class QuanTriVien {
    private  String MaAD;
    private String TenAD;
    private String Email;
    private String SoDienThoai;
    private String DiaChi;

    private Date NgaySinh;
    private boolean GioiTinh;
    private  String AnhDaiDien;
    private String TrangThai;

    public String getMaAD() {
        return MaAD;
    }

    public void setMaAD(String maAD) {
        MaAD = maAD;
    }

    public String getTenAD() {
        return TenAD;
    }

    public void setTenAD(String tenAD) {
        TenAD = tenAD;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getAnhDaiDien() {
        return AnhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        AnhDaiDien = anhDaiDien;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public QuanTriVien(String tenAD, String email, String soDienThoai, String diaChi, Date ngaySinh, boolean gioiTinh, String anhDaiDien, String trangThai) {
        TenAD = tenAD;
        Email = email;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        AnhDaiDien = anhDaiDien;
        TrangThai = trangThai;
    }
}
