package org.example.cuoiki_code_tutorial.Dao.DucNhan;

import org.example.cuoiki_code_tutorial.Models.TaiKhoan;
import org.example.cuoiki_code_tutorial.Utils.HandleException;
import org.example.cuoiki_code_tutorial.Utils.MySQLConnection;


import java.sql.*;

public class LoginDAO {

   private static final String DANG_NHAP="SELECT * FROM taikhoan WHERE TrangThai = 1 AND TenDangNhap = ? AND MatKhau = ? AND VaiTro=?";

    public TaiKhoan onLogin(TaiKhoan taiKhoanDangNhap) throws ClassNotFoundException {
        TaiKhoan taiKhoan = null;

        try {
            Connection conn = MySQLConnection.getConnection();

            PreparedStatement preparedStatement = conn.prepareStatement(DANG_NHAP);
            preparedStatement.setString(1, taiKhoanDangNhap.getTenDangNhap());
            preparedStatement.setString(2, taiKhoanDangNhap.getMatKhau());
            preparedStatement.setBoolean(3, taiKhoanDangNhap.isVaiTro());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
            {
                taiKhoan = new TaiKhoan();
                taiKhoan.setTenDangNhap(rs.getString("TenDangNhap"));
                taiKhoan.setMatKhau(rs.getString("MatKhau"));
                taiKhoan.setVaiTro(rs.getBoolean("VaiTro"));
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return taiKhoan;
    }
}
