package org.example.cuoiki_code_tutorial.Dao.DucNhan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.cuoiki_code_tutorial.Models.HocVien;
import org.example.cuoiki_code_tutorial.Utils.HandleException;
import org.example.cuoiki_code_tutorial.Utils.MySQLConnection;

public class HocVienDAO {
    private static final String SELECT_ALL_HOCVIEN_HOATDONG = "SELECT * FROM hocvien where TrangThai=1";
    private static final String SELECT_ALL_HOCVIEN = "SELECT * FROM hocvien where TrangThai=0";
    private static final String UPDATE_HOCVIEN_BY_TENDANGNHAP = "UPDATE hocvien SET TenHV=?, Email=?, SoDienThoai=?, DiaChi=?, NgaySinh=?, GioiTinh=?, AnhDaiDien=?, TrangThai=? WHERE MaTK IN (SELECT MaTK FROM TaiKhoan WHERE TenDangNhap=?)";
    private static final String UPDATE_TRANGTHAI_HOCVIEN = "UPDATE hocvien SET TrangThai=0 WHERE MaHV=?";


    public static List<HocVien> selectAllHocVienHoatDong() {
        List<HocVien> hocViens = new ArrayList<>();
        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_HOCVIEN_HOATDONG);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String maHV = rs.getString("MaHV");
                String tenHV = rs.getString("TenHV");
                String email = rs.getString("Email");
                String soDienThoai = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                java.sql.Date ngaySinh = rs.getDate("NgaySinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                String anhDaiDien = rs.getString("AnhDaiDien");
                boolean trangThai = rs.getBoolean("TrangThai");
                String maTK=rs.getString("MaTK");

                HocVien hocVien = new HocVien(maHV, tenHV, email, soDienThoai, diaChi, ngaySinh, gioiTinh, anhDaiDien, trangThai,maTK);
                hocViens.add(hocVien);
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return hocViens;
    }
    public static List<HocVien> selectAllHocVien() {
        List<HocVien> hocViens = new ArrayList<>();
        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_HOCVIEN);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String maHV = rs.getString("MaHV");
                String tenHV = rs.getString("TenHV");
                String email = rs.getString("Email");
                String soDienThoai = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                java.sql.Date ngaySinh = rs.getDate("NgaySinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                String anhDaiDien = rs.getString("AnhDaiDien");
                boolean trangThai = rs.getBoolean("TrangThai");
                String maTK=rs.getString("MaTK");

                HocVien hocVien = new HocVien(maHV, tenHV, email, soDienThoai, diaChi, ngaySinh, gioiTinh, anhDaiDien, trangThai,maTK);
                hocViens.add(hocVien);
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return hocViens;
    }

    // Phương thức insert
    private static final String INSERT_HOCVIEN = "INSERT INTO hocvien (TenHV, Email, SoDienThoai, DiaChi, NgaySinh, GioiTinh, AnhDaiDien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public boolean insertHocVien(HocVien hocVien) {
        boolean inserted = false;
        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_HOCVIEN);
            preparedStatement.setString(1, hocVien.getTenHV());
            preparedStatement.setString(2, hocVien.getEmail());
            preparedStatement.setString(3, hocVien.getSoDienThoai());
            preparedStatement.setString(4, hocVien.getDiaChi());
            preparedStatement.setDate(5, java.sql.Date.valueOf(String.valueOf(hocVien.getNgaySinh())));
            preparedStatement.setBoolean(6, hocVien.getGioiTinh());
            preparedStatement.setString(7, hocVien.getAnhDaiDien());
            preparedStatement.setBoolean(8, hocVien.getTrangThai());
            // Nếu cần thì thêm các tham số phù hợp với cấu trúc của database
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                inserted = true;
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return inserted;
    }

    // Phương thức update
    public boolean updateHocVien(String tenDangNhap, HocVien hocVien) {
        boolean updated = false;
        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_HOCVIEN_BY_TENDANGNHAP);
            preparedStatement.setString(1, hocVien.getTenHV());
            preparedStatement.setString(2, hocVien.getEmail());
            preparedStatement.setString(3, hocVien.getSoDienThoai());
            preparedStatement.setString(4, hocVien.getDiaChi());
            preparedStatement.setDate(5, java.sql.Date.valueOf(String.valueOf(hocVien.getNgaySinh())));
            preparedStatement.setBoolean(6, hocVien.getGioiTinh());
            preparedStatement.setString(7, hocVien.getAnhDaiDien());
            preparedStatement.setBoolean(8, hocVien.getTrangThai());
            preparedStatement.setString(9, tenDangNhap);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                updated = true;
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return updated;
    }
    private static final String UPDATE_HOCVIEN_BYMAHV = "UPDATE hocvien SET TenHV=?, Email=?, SoDienThoai=?, DiaChi=?, NgaySinh=?, GioiTinh=?, AnhDaiDien=?, TrangThai=? WHERE MaHV =?";
    public boolean updateHocVienByMaHV(String mahocvien, HocVien hocVien) {
        boolean updated = false;
        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_HOCVIEN_BYMAHV);
            preparedStatement.setString(1, hocVien.getTenHV());
            preparedStatement.setString(2, hocVien.getEmail());
            preparedStatement.setString(3, hocVien.getSoDienThoai());
            preparedStatement.setString(4, hocVien.getDiaChi());
            preparedStatement.setDate(5, java.sql.Date.valueOf(String.valueOf(hocVien.getNgaySinh())));
            preparedStatement.setBoolean(6, hocVien.getGioiTinh());
            preparedStatement.setString(7, hocVien.getAnhDaiDien());
            preparedStatement.setBoolean(8, hocVien.getTrangThai());
            preparedStatement.setString(9, mahocvien);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                updated = true;
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return updated;
    }

    // Phương thức delete
    public boolean updateTrangThaiHocVien(String mahocvien) {
        boolean updated = false;
        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_TRANGTHAI_HOCVIEN);
            preparedStatement.setString(1, mahocvien);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                updated = true;
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return updated;
    }


    // lấy thông tin từng học viên
    public HocVien getInfoHocVien(String tenDangNhap) {
        HocVien hocVien = null;
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = "SELECT hocvien.*, TaiKhoan.MaTK " +
                    "FROM hocvien " +
                    "INNER JOIN TaiKhoan ON hocvien.MaTK = TaiKhoan.MaTK " +
                    "WHERE TaiKhoan.TenDangNhap = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tenDangNhap);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String tenHV = rs.getString("TenHV");
                String email = rs.getString("Email");
                String soDienThoai = rs.getString("SoDienThoai");
                String diaChi = rs.getString("DiaChi");
                java.sql.Date ngaySinh = rs.getDate("NgaySinh");
                boolean gioiTinh = rs.getBoolean("GioiTinh");
                String anhDaiDien = rs.getString("AnhDaiDien");
                boolean trangThai = rs.getBoolean("TrangThai");
                hocVien = new HocVien(tenHV, email, soDienThoai, diaChi, ngaySinh, gioiTinh, anhDaiDien, trangThai);
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return hocVien;
    }

    // Đổi Mật Khẩu
    private static final String SELECT_PASSWORD_BY_USERNAME = "SELECT MatKhau FROM TaiKhoan WHERE TenDangNhap=?";
    private static final String UPDATE_PASSWORD_BY_USERNAME = "UPDATE TaiKhoan SET MatKhau=? WHERE TenDangNhap=?";

    public boolean changePassword(String tenDangNhap, String matKhauCu, String matKhauMoi) {
        boolean updated = false;
        try {
            Connection conn = MySQLConnection.getConnection();

            // Kiểm tra mật khẩu cũ
            PreparedStatement selectStatement = conn.prepareStatement(SELECT_PASSWORD_BY_USERNAME);
            selectStatement.setString(1, tenDangNhap);
            ResultSet rs = selectStatement.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("MatKhau");
                if (hashedPassword.equals(matKhauCu)) {
                    // Nếu mật khẩu cũ khớp, thì cập nhật mật khẩu mới
                    PreparedStatement updateStatement = conn.prepareStatement(UPDATE_PASSWORD_BY_USERNAME);
                    updateStatement.setString(1, matKhauMoi);
                    updateStatement.setString(2, tenDangNhap);

                    int rowsUpdated = updateStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        updated = true;
                    }
                }
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return updated;
    }
    private static final String UPDATE_TENDANGNHAP_HOCVIEN = "UPDATE TaiKhoan SET TenDangNhap=? WHERE TenDangNhap=?";

    public boolean updateTenDangNhap(String tenDangNhapCu, String tenDangNhapMoi) {
        boolean updated = false;
        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_TENDANGNHAP_HOCVIEN);
            preparedStatement.setString(1, tenDangNhapMoi);
            preparedStatement.setString(2, tenDangNhapCu);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                updated = true;
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
        }
        return updated;
    }


}
