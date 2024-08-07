package org.example.cuoiki_code_tutorial.Dao.DucNhan;

import org.example.cuoiki_code_tutorial.Utils.MySQLConnection;
import org.example.cuoiki_code_tutorial.Utils.HandleException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDAO {
    private static final String INSERT_HOCVIEN_QUERY = "INSERT INTO hocvien (TenHV, Email, SoDienThoai, DiaChi, NgaySinh, GioiTinh, AnhDaiDien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static boolean addHocVien(String tenHV, String email, String soDienThoai, String diaChi, String ngaySinh, int gioiTinh, String anhDaiDien, int trangThai) {
        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_HOCVIEN_QUERY);
            preparedStatement.setString(1, tenHV);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, soDienThoai);
            preparedStatement.setString(4, diaChi);
            preparedStatement.setString(5, ngaySinh);
            preparedStatement.setInt(6, gioiTinh);
            preparedStatement.setString(7, anhDaiDien);
            preparedStatement.setInt(8, trangThai);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            HandleException.printSQLException(e);
            return false;
        }
    }
}
