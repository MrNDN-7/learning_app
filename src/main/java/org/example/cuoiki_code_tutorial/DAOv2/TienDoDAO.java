package org.example.cuoiki_code_tutorial.DAOv2;

import org.example.cuoiki_code_tutorial.Models.KiemThu;
import org.example.cuoiki_code_tutorial.Models.TienDo;
import org.example.cuoiki_code_tutorial.Utils.ConnectJDBC;
import org.example.cuoiki_code_tutorial.Utils.HandleException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TienDoDAO extends SysDAO<TienDo, String>{
    @Override
    public void insert(TienDo entity) {

    }

    @Override
    public void update(TienDo entity) {
        String query = "UPDATE `codelearn`.`khoahoc_baihoc` SET `TrangThai` = ? WHERE (`MaHV` = ?) and (`ThuTu` = ?) and (`MaKhoaHoc` = ?);";
        ConnectJDBC.update(query,
                entity.getTrangThai(),
                entity.getMaTK(),
                entity.getThuTu(),
                entity.getMaKhoaHoc());
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public TienDo selectById(String id) {
        return null;
    }


    @Override
    public List<TienDo> selectAll() {
        String query = "Select * from khoahoc_baihoc;";
        List<TienDo> tienDos = selectBySql(query);
        return tienDos;
    }

    @Override
    protected List<TienDo> selectBySql(String sql, Object... args) {
        List<TienDo> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = ConnectJDBC.query(sql, args);
                while (rs.next()) {
                    TienDo entity = new TienDo();
                    // Extract values from result set and set them in the BaiHoc object
                    entity.setMaKhoaHoc(rs.getString("MaKhoaHoc"));
                    entity.setThuTu(rs.getInt("ThuTu"));
                    entity.setMaTK(rs.getString("MaHV"));
                    entity.setTrangThai(rs.getInt("TrangThai"));

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

    public List<TienDo> getTienDoByMaTKMaKhoaHoc(String maTk, String maKH) {
        String query = "Select * from khoahoc_baihoc where MaHV = ? AND MaKhoaHoc = ?;";
        List<TienDo> tienDos = selectBySql(query, maTk, maKH);
        return tienDos;
    }

    public TienDo getTienDoByThuTu(String maTk, String maKH, int thuTu)
    {
        String query = "Select * from khoahoc_baihoc where MaHV = ? AND MaKhoaHoc = ? AND ThuTu = ?;";
        ResultSet rs = null;
        TienDo tienDo = new TienDo();
        try{
            rs = ConnectJDBC.query(query, maTk, maKH, thuTu);
            if(rs.next())
            {
                tienDo.setMaTK(rs.getString("MaHV"));
                tienDo.setMaKhoaHoc(rs.getString("MaKhoaHoc"));
                tienDo.setThuTu(rs.getInt("ThuTu"));
                tienDo.setTrangThai(rs.getInt("TrangThai"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tienDo;
    }
}
