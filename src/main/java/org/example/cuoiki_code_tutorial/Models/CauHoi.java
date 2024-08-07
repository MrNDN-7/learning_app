package org.example.cuoiki_code_tutorial.Models;

public class CauHoi {

    private String maKHoaHoc;
    private int stt;
    private String cauHoi;
    private String cauTraLoi1;
    private String cauTraLoi2;
    private String cauTraLoi3;
    private int dapAn;

    public CauHoi() {
    }

    public CauHoi(String maKHoaHoc, int stt, String cauHoi, String cauTraLoi1, String cauTraLoi2, String cauTraLoi3, int dapAn) {
        this.maKHoaHoc = maKHoaHoc;
        this.stt = stt;
        this.cauHoi = cauHoi;
        this.cauTraLoi1 = cauTraLoi1;
        this.cauTraLoi2 = cauTraLoi2;
        this.cauTraLoi3 = cauTraLoi3;
        this.dapAn = dapAn;
    }

    public String getMaKHoaHoc() {
        return maKHoaHoc;
    }

    public void setMaKHoaHoc(String maKHoaHoc) {
        this.maKHoaHoc = maKHoaHoc;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(String cauHoi) {
        this.cauHoi = cauHoi;
    }

    public String getCauTraLoi1() {
        return cauTraLoi1;
    }

    public void setCauTraLoi1(String cauTraLoi1) {
        this.cauTraLoi1 = cauTraLoi1;
    }

    public String getCauTraLoi2() {
        return cauTraLoi2;
    }

    public void setCauTraLoi2(String cauTraLoi2) {
        this.cauTraLoi2 = cauTraLoi2;
    }

    public String getCauTraLoi3() {
        return cauTraLoi3;
    }

    public void setCauTraLoi3(String cauTraLoi3) {
        this.cauTraLoi3 = cauTraLoi3;
    }

    public int getDapAn() {
        return dapAn;
    }

    public void setDapAn(int dapAn) {
        this.dapAn = dapAn;
    }
}
