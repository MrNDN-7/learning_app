package org.example.cuoiki_code_tutorial.DAOv2;

import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.Models.KiemThu;
import org.example.cuoiki_code_tutorial.Utils.ConnectJDBC;
import org.example.cuoiki_code_tutorial.Utils.HandleException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class KiemThuDAO extends SysDAO <KiemThu, String>{
    @Override
    public void insert(KiemThu entity) {
        String sql="call InsertKiemThu(?, ?, ?, ?)";

        ConnectJDBC.update(sql,
                entity.getInput(),
                entity.getOutput(),
                entity.getMaKH(),
                entity.getThuTu());
    }

    @Override
    public void update(KiemThu entity) {
        String sql="UPDATE kiemthu SET MaKhoaHoc = ?, input =?, output = ?, trangthai = ? where ThuTu = ? and ThuTuKiemThu = ?";
        ConnectJDBC.update(sql,
                entity.getMaKH(),
                entity.getInput(),
                entity.getOutput());
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public KiemThu selectById(String id) {
        return null;
    }

    @Override
    public List<KiemThu> selectAll() {
        String query = "Select * from kiemthu;";
        List<KiemThu> kiemThus = selectBySql(query);
        return kiemThus;
    }

    @Override
    protected List<KiemThu> selectBySql(String sql, Object... args) {
        List<KiemThu> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = ConnectJDBC.query(sql, args);
                while (rs.next()) {
                    KiemThu entity = new KiemThu();
                    // Extract values from result set and set them in the BaiHoc object
                    entity.setMaKH(rs.getString("MaKhoaHoc"));
                    entity.setThuTu(rs.getInt("ThuTu"));
                    entity.setThuTuKiemThu(rs.getInt("ThuTuKiemThu"));
                    entity.setInput(rs.getString("input"));
                    entity.setOutput(rs.getString("output"));
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

    public List<KiemThu> getKiemThuByMaKHThuTu(int thuTu, String maKH)
    {
        String query = "Select * from kiemthu where MaKhoaHoc = ? AND ThuTu = ?;";
        List<KiemThu> kiemThus = selectBySql(query, maKH, thuTu);
        return kiemThus;
    }
}
