package org.example.cuoiki_code_tutorial.DAOv2;

import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Utils.ConnectJDBC;
import org.example.cuoiki_code_tutorial.Utils.HandleException;
import org.example.cuoiki_code_tutorial.Utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaiHocDAO extends SysDAO<BaiHoc, String> {

    @Override
    public void insert(BaiHoc entity) {
        String sql = "CALL InsertBaiHoc(?, ?, ?, ?, ?, ?, ?, ?);";
        ConnectJDBC.update(sql,
                entity.getTenBaiHoc(),
                entity.getNoiDung(),
                entity.getThoiLuong(),
                entity.getMaChuong(),//MaChuong
                entity.getGioiHanKyTu(),
                entity.getMucDo(),
                entity.getCodeMau(),
                entity.getMaKhoaHoc());//MaKH
    }

    @Override
    public void update(BaiHoc entity) {
        String sql="UPDATE BaiHoc SET TenBH = ?, NoiDung = ?, ThoiLuong=?,  ThuTu= ?, TrangThai = ? WHERE MaBH =? and MaChuong = ? and MaKhoaHoc = ?";
        ConnectJDBC.update(sql,
                entity.getTenBaiHoc(),
                entity.getNoiDung(),
                entity.getThoiLuong(),
                entity.getThuTu(),
                entity.getTrangThai(),
                entity.getMaBaiHoc(),
                entity.getMaChuong(),
                entity.getMaKhoaHoc());
    }

    @Override
    public void delete(String id) {
        String sql="UPDATE BaiHoc set TrangThai = 0 WHERE MaBH=?";
        ConnectJDBC.update(sql, id);
    }

    @Override
    public BaiHoc selectById(String id) {
        String sql="SELECT * FROM BaiHoc WHERE MaBH=?";
        List<BaiHoc> list = selectBySql(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<BaiHoc> selectAll() {
        String sql="SELECT * FROM BaiHoc where TrangThai=1 Order by ThuTu";
        return selectBySql(sql);
    }

    @Override
    protected List<BaiHoc> selectBySql(String sql, Object... args) {
        List<BaiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = ConnectJDBC.query(sql, args);
                while (rs.next()) {
                    BaiHoc entity = new BaiHoc();
                    // Extract values from result set and set them in the BaiHoc object
                    entity.setMaBaiHoc(rs.getString("MaBH"));
                    entity.setTenBaiHoc(rs.getString("TenBH"));
                    entity.setNoiDung(rs.getString("NoiDung"));
                    entity.setDinhDang(rs.getString("DinhDang"));
                    entity.setThoiLuong(rs.getInt("ThoiLuong"));
                    entity.setMaChuong(rs.getString("MaChuong"));
                    entity.setThuTu(rs.getInt("ThuTu"));
                    entity.setTrangThai(rs.getInt("TrangThai"));
                    entity.setGioiHanKyTu(rs.getInt("GioiHanKyTu"));
                    entity.setMucDo(rs.getString("MucDo"));
                    entity.setCodeMau(rs.getString("CodeMau"));
                    entity.setMaKhoaHoc(rs.getString("MaKhoaHoc"));

                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }

        } catch (SQLException e) {
            HandleException.printSQLException(e);
            throw new RuntimeException(e);
        }
        return list;
    }

    protected BaiHoc selectBaiHocBySql(String sql, Object... args) {
        BaiHoc entity = null;
        try {
            ResultSet rs = null;
            try {
                rs = ConnectJDBC.query(sql, args);
                if (rs.next()) {
                    entity = new BaiHoc();
                    // Extract values from result set and set them in the BaiHoc object
                    entity.setMaBaiHoc(rs.getString("MaBH"));
                    entity.setTenBaiHoc(rs.getString("TenBH"));
                    entity.setNoiDung(rs.getString("NoiDung"));
                    entity.setDinhDang(rs.getString("DinhDang"));
                    entity.setThoiLuong(rs.getInt("ThoiLuong"));
                    entity.setMaChuong(rs.getString("MaChuong"));
                    entity.setThuTu(rs.getInt("ThuTu"));
                    entity.setTrangThai(rs.getInt("TrangThai")); // Assuming TrangThai is an integer
                    entity.setGioiHanKyTu(rs.getInt("GioiHanKyTu"));
                    entity.setMucDo(rs.getString("MucDo"));
                    entity.setCodeMau(rs.getString("CodeMau"));
                    entity.setMaKhoaHoc(rs.getString("MaKhoaHoc"));
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
        return entity;
    }


    public BaiHoc getBaiHocByThuTu(int thuTu, String maKhoaHoc) {
        String sql = "call get_baihoc_by_thutu(?, ?);";
        return (BaiHoc) this.selectBaiHocBySql(sql, thuTu, maKhoaHoc);
    }

    public BaiHoc getBaiHocByMaBHMaChuong(String maBH, String maChuong, String MaKhoaHoc) {
        String query = "call get_baihoc_by_mabh_machuong(?, ?, ?);";
        return (BaiHoc) this.selectBaiHocBySql(query, maBH, maChuong, MaKhoaHoc);

    }

    public List<Integer> getThuTuByMaChuong(String maChuong, String maKhoaHoc) throws SQLException {
        List<Integer> thuTuList = new ArrayList<>();
        String query = "CALL getThuTuByMaChuong(?, ?);";

        try{
            ResultSet rs = ConnectJDBC.query(query, maChuong, maKhoaHoc);
            while(rs.next()){
                thuTuList.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return thuTuList;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    public List<BaiHoc> getListBaiHocByMaChuog(String maChuong, String maKhoaHoc) {
        String sql = "select * from baihoc where MaChuong = ? AND MaKhoaHoc = ?";
        List<BaiHoc> list = selectBySql(sql, maChuong, maKhoaHoc);
        return list;
    }
}
