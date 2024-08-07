package org.example.cuoiki_code_tutorial.Dao.DucNhan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.cuoiki_code_tutorial.Models.TaiKhoan;
import org.example.cuoiki_code_tutorial.Utils.HandleException;
import org.example.cuoiki_code_tutorial.Utils.MySQLConnection;
public class TaiKhoanDAO {
    private static final String SELECT_ALL_TAIKHOAN = "SELECT * FROM taikhoan where VaiTro=1 and TrangThai=1";
    private static final String SELECT_ALL_TAIKHOAN_KhongHoatDong = "SELECT * FROM taikhoan where VaiTro=1 and TrangThai=0";

    public List<TaiKhoan> getAllTaiKhoan() {
        List<TaiKhoan> taiKhoanList = new ArrayList<>();
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_TAIKHOAN);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String maTK = resultSet.getString("MaTK");
                String tendangnhap= resultSet.getString("TenDangNhap");
                String matKhau = resultSet.getString("MatKhau");
                boolean trangThai = resultSet.getBoolean("TrangThai");

                TaiKhoan taiKhoan = new TaiKhoan(maTK, tendangnhap, matKhau, trangThai);
                taiKhoanList.add(taiKhoan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taiKhoanList;
    }
    public List<TaiKhoan> getAllTaiKhoanKhongHoatDong() {
        List<TaiKhoan> taiKhoanList = new ArrayList<>();
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_TAIKHOAN_KhongHoatDong);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String maTK = resultSet.getString("MaTK");
                String tendangnhap= resultSet.getString("TenDangNhap");
                String matKhau = resultSet.getString("MatKhau");
                boolean trangThai = resultSet.getBoolean("TrangThai");

                TaiKhoan taiKhoan = new TaiKhoan(maTK, tendangnhap, matKhau, trangThai);
                taiKhoanList.add(taiKhoan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taiKhoanList;
    }
    public boolean updateTaiKhoan(TaiKhoan taiKhoan) {
        String UPDATE_TAIKHOAN = "UPDATE taikhoan SET TenDangNhap=?, MatKhau=?, TrangThai=? WHERE MaTK=?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_TAIKHOAN)) {

            preparedStatement.setString(1, taiKhoan.getTenDangNhap());
            preparedStatement.setString(2, taiKhoan.getMatKhau());
            preparedStatement.setBoolean(3, taiKhoan.isTrangThai());
            preparedStatement.setString(4, taiKhoan.getMaTK());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
