package org.example.cuoiki_code_tutorial.Models;

import java.util.Date;

public class HocVien {
    private String MaHV;
    private  String TenHV;
    private String Email;
    private String SoDienThoai;
    private String DiaChi;

    private Date NgaySinh;
    private Boolean GioiTinh;
    private  String AnhDaiDien;
    private Boolean TrangThai;
    private String MaTK;

    public String getMaTK() {
        return MaTK;
    }

    public void setMaTK(String maTK) {
        MaTK = maTK;
    }

    public String getMaHV() {
        return MaHV;
    }

    public void setMaHV(String maHV) {
        MaHV = maHV;
    }

    public String getTenHV() {
        return TenHV;
    }

    public void setTenHV(String tenHV) {
        TenHV = tenHV;
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

    public Boolean getGioiTinh() {
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

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        TrangThai = trangThai;
    }

    public String getStringGioiTinh()
    {
        return this.GioiTinh?"Nam":"Nữ";
    }
    public String getStringTrangThai()
    {
        return this.TrangThai?"Hoạt Động":"Ngừng Hoạt Động";
    }

    public HocVien(String tenHV, String email, String soDienThoai, String diaChi, Date ngaySinh, Boolean gioiTinh, String anhDaiDien,boolean trangThai) {
        TenHV = tenHV;
        Email = email;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        AnhDaiDien = anhDaiDien;
        TrangThai = trangThai;
    }
    public HocVien(String maHV,String tenHV, String email, String soDienThoai, String diaChi, Date ngaySinh, Boolean gioiTinh, String anhDaiDien,boolean  trangThai,String maTK) {
        MaHV=maHV;
        TenHV = tenHV;
        Email = email;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        AnhDaiDien = anhDaiDien;
        TrangThai = trangThai;
        MaTK=maTK;
    }
}
