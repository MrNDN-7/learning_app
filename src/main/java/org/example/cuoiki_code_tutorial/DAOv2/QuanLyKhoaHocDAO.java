package org.example.cuoiki_code_tutorial.DAOv2;

import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.Utils.ConnectJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuanLyKhoaHocDAO extends SysDAO<KhoaHoc, String>{


    @Override
    public void insert(KhoaHoc entity) {
        String sql="Call InsertKhoaHoc(?, ?, ?, ?)";
        ConnectJDBC.update(sql,
                entity.getTenKH(),
                entity.getMoTa(),
                entity.getHinhAnh(),
                entity.getNgayTao()
                );
    }

    @Override
    public void update(KhoaHoc entity) {
        String sql="UPDATE KhoaHoc SET TenKhoaHoc = ?, MoTa = ?, HinhAnh = ?, NgayTao = ?, TrangThai = ? WHERE MaKhoaHoc=?";
        ConnectJDBC.update(sql,
                entity.getTenKH(),
                entity.getMoTa(),
                entity.getHinhAnh(),
                entity.getNgayTao(),
                entity.getTrangThai(),
                entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        String sql="UPDATE KhoaHoc set TrangThai = 0 WHERE MaKhoaHoc=?";
        ConnectJDBC.update(sql, id);
    }

    @Override
    public KhoaHoc selectById(String id) {
        String sql="SELECT * FROM KhoaHoc WHERE MaKhoaHoc=?";
        List<KhoaHoc> list = selectBySql(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<KhoaHoc> selectAll() {
        String sql="SELECT * FROM KhoaHoc where TrangThai = 1";
        return selectBySql(sql);
    }

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = ConnectJDBC.query(sql, args);
                while(rs.next()){
                    KhoaHoc entity=new KhoaHoc();
                    entity.setMaKH(rs.getString("MaKhoaHoc"));
                    entity.setTenKH(rs.getString("TenKhoaHoc"));
                    entity.setMoTa(rs.getString("MoTa"));
                    entity.setHinhAnh(rs.getString("HinhAnh"));
                    entity.setNgayTao(LocalDate.parse(rs.getString("NgayTao")));
                    entity.setMaAD(rs.getString("MaAdmin"));
                    list.add(entity);
                }
            }
            finally{
                rs.getStatement().getConnection().close();
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
}
