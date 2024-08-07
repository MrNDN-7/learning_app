package org.example.cuoiki_code_tutorial.DAOv2;

import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.Utils.ConnectJDBC;
import org.example.cuoiki_code_tutorial.Utils.HandleException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class KhoaHocDAO extends SysDAO <KhoaHoc, String>{
    @Override
    public void insert(KhoaHoc entity) {

    }

    @Override
    public void update(KhoaHoc entity) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public KhoaHoc selectById(String id) {
        return null;
    }

    @Override
    public List<KhoaHoc> selectAll() {
        String sql = "SELECT * FROM khoahoc ";
        List<KhoaHoc> khoaHocs = selectBySql(sql);
        return khoaHocs;
    }

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> khoaHocs = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = ConnectJDBC.query(sql, args);
                while (rs.next()) {
                    String id = rs.getString("MaKhoaHoc");
                    String tenKhoaHoc = rs.getString("TenKhoaHoc");
                    String moTa = rs.getString("MoTa");
                    String hinhAnh = rs.getString("HinhAnh");
                    String ngayTao = rs.getString("NgayTao");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate ngayTaoLocalDate = LocalDate.parse(ngayTao, formatter);
                    int trangThai = rs.getInt("TrangThai");

                    String maAdmin = rs.getString("MaAdmin");
                    khoaHocs.add(new KhoaHoc(id, tenKhoaHoc, moTa, hinhAnh, ngayTaoLocalDate, trangThai, maAdmin));
                }
            } finally {
                if (rs != null) {
                    rs.getStatement().getConnection().close();
                }
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
            throw new RuntimeException(e);
        }
        return khoaHocs;
    }

    public List<KhoaHoc> getKhoaHocDangHoc(String username) {
        String sql = "call get_courses_for_user_stt0(?);";
        List<KhoaHoc> khoaHocs = selectBySql(sql, username);
        return khoaHocs;
    }

    public List<KhoaHoc> getKhoaHocDaHoc(String username) {
        String sql = "call get_courses_for_user_stt1(?);";
        List<KhoaHoc> khoaHocs = selectBySql(sql, username);
        return khoaHocs;
    }

    public String KhoaHocDaHoc(String username) throws SQLException {
        String sql1 = "select count(MaKhoaHoc) from khoahoc;";
        String sql2 = "select count(MaKH) from dangky where MaHV = ?;";
        String res = "";
        int danghoc = 0;
        int khoahoc = 0;
        ResultSet rs = null;
        ResultSet rs2 = null;
        rs = ConnectJDBC.query(sql1);
        if(rs.next())
        {
            khoahoc = rs.getInt(1);
        }
        rs2 = ConnectJDBC.query(sql2, username);
        if(rs2.next())
        {
            danghoc = rs2.getInt(1);
        }
        res = danghoc + "/" + khoahoc;
        return res;
    }


}
