package org.example.cuoiki_code_tutorial.DAOv2;

import org.example.cuoiki_code_tutorial.Models.Chuong;
import org.example.cuoiki_code_tutorial.Utils.ConnectJDBC;
import org.example.cuoiki_code_tutorial.Utils.HandleException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChuongDAO extends SysDAO<Chuong, String>{
    @Override
    public void insert(Chuong entity) {
        String sql="Call ThemChuongMoi(?, ?)";
        ConnectJDBC.update(sql,
                entity.getTenChuong(),
                entity.getMaKH());
    }

    @Override
    public void update(Chuong entity) {
        String sql="UPDATE Chuong SET TenChuong = ?, ThuTu = ?, MaKH = ?WHERE MaChuong=?";
        ConnectJDBC.update(sql,
                entity.getTenChuong(),
                entity.getThuTu(),
                entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        String sql="Delete from chuong where MaChuong=?";
        ConnectJDBC.update(sql, id);
    }

    @Override
    public Chuong selectById(String id) {
        String sql="SELECT * FROM Chuong WHERE MaChuong=?";
        List<Chuong> list = selectBySql(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<Chuong> selectAll() {
        String sql="SELECT * FROM Chuong Order by thuTu";
        return selectBySql(sql);
    }

    @Override
    protected List<Chuong> selectBySql(String sql, Object... args) {

        List<Chuong> chuongList = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = ConnectJDBC.query(sql, args);
                while (rs.next()) {
                    String id = rs.getString("MaChuong");
                    String tenChuong = rs.getString("TenChuong");
                    int thuTu = rs.getInt("ThuTu");
                    String maKH = rs.getString("MaKH"); // MaKH lấy từ ResultSet
                    chuongList.add(new Chuong(id, tenChuong, thuTu, maKH)); // Tạo đối tượng Chuong và thêm vào danh sách
                }
            } finally {
                rs.getStatement().getConnection().close(); // Đóng ResultSet và kết nối
            }
        } catch (SQLException e) {
            HandleException.printSQLException(e);
            throw new RuntimeException(e);
        }
        return chuongList;
    }

    public List<Chuong> getChuongByMaKhoaHoc(String maKH)
    {
        String query = "select * from chuong where MaKH = ? Order by thuTu";
        return this.selectBySql(query, maKH);
    }


}
