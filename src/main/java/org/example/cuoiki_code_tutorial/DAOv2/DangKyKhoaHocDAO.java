package org.example.cuoiki_code_tutorial.DAOv2;

import org.example.cuoiki_code_tutorial.Utils.ConnectJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DangKyKhoaHocDAO {


    public int getSoLuongBaiHocByMaKhoaHoc(String maKhoaHoc) throws SQLException {
        String query = "select max(ThuTu) from baihoc where MaKhoahoc = ?;";
        int SoLuongBaiHoc = 0;
        ResultSet rs = ConnectJDBC.query(query, maKhoaHoc);
        while (rs.next()) {
            SoLuongBaiHoc = rs.getInt(1);
        }
        return SoLuongBaiHoc;
    }
    public boolean isDangKyKhaohocByUssername(String username, String maKhoaHoc) throws SQLException {
        String query = "select TrangThai from dangky where MaHV = ? AND MaKH = ?;";

        ResultSet rs = ConnectJDBC.query(query,username, maKhoaHoc);
        if(rs.next())
        {
            int count = rs.getInt(1);
            if(count == 1 || count == 0)
            {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public Float isKiemtraDauVao(String maKhoaHoc, String username) throws SQLException {
        String query = "SELECT DiemBaiKiemTra FROM dangky WHERE MaHV = ? AND MaKH = ? ;";

        ResultSet rs = ConnectJDBC.query(query,username, maKhoaHoc);
        if(rs.next())
        {
            Float count = rs.getFloat(1);
            return  count;
        }

        return (float) 0;
    }

    public boolean dangKyKhoaHoc(String username, String maKhoaHoc, int soLuongBaiHoc) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        String query = "INSERT INTO `codelearn`.`khoahoc_baihoc` (`MaHV`, `MaKhoaHoc`, `ThuTu`) VALUES (?,?,?);";
        for(int i = 1; i <= soLuongBaiHoc; i++)
        {
            ConnectJDBC.update(query, username, maKhoaHoc, i);
        }
        String query1 = "Insert into codelearn.dangky (MaHV, MaKH, ThoiGianBatDau) VALUES (?,?,?);";
        ConnectJDBC.update(query1, username, maKhoaHoc, currentDate);
        return  isDangKyKhaohocByUssername(username, maKhoaHoc);

    }

    public String getMaHVByUsername(String username) throws SQLException {
        String maHV = "";
        String query = "SELECT hv.MaHV FROM hocvien hv INNER JOIN taikhoan tk ON hv.MaTK = tk.MaTK WHERE tk.TenDangNhap = ?;";
        ResultSet rs = ConnectJDBC.query(query, username);
        if(rs.next())
        {
            maHV = rs.getString("MaHV");
        }
        return maHV;
    }
    public String getMaADByUsername(String username) throws SQLException {
        String maHV = "";
        String query = "SELECT qtv.MaAD FROM quantrivien qtv INNER JOIN taikhoan tk ON qtv.MaTK = tk.MaTK WHERE tk.TenDangNhap = ?;";
        ResultSet rs = ConnectJDBC.query(query, username);
        if(rs.next())
        {
            maHV = rs.getString(1);
        }
        return maHV;
    }
    public String getTenByMaHV(String username) throws SQLException {
        String maHV = "";
        String query = "SELECT TenHv from hocvien where MaHV = ?;";
        ResultSet rs = ConnectJDBC.query(query, username);
        if(rs.next())
        {
            maHV = rs.getString(1);
        }
        return maHV;
    }

}
