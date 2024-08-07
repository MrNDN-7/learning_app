package org.example.cuoiki_code_tutorial.Models;

public class TaiKhoan {
    private String MaTK;
    private String TenDangNhap;
    private String MatKhau;
    private boolean VaiTro;
    private boolean TrangThai;

    public String getMaTK() {
        return MaTK;
    }

    public void setMaTK(String maTK) {
        MaTK = maTK;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        TenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public boolean isVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        VaiTro = vaiTro;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean trangThai) {
        TrangThai = trangThai;
    }



    public TaiKhoan(String tenDangNhap, String matKhau, boolean vaiTro) {
        TenDangNhap = tenDangNhap;
        MatKhau = matKhau;
        VaiTro = vaiTro;
    }
    public TaiKhoan(String maTK, String tenDangNhap, String matKhau, boolean trangThai) {
        MaTK=maTK;
        TenDangNhap = tenDangNhap;
        MatKhau = matKhau;
        TrangThai=trangThai;
    }
    public TaiKhoan() {
    }

}
