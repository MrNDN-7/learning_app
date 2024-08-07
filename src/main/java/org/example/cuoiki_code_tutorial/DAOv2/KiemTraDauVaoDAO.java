package org.example.cuoiki_code_tutorial.DAOv2;

import org.example.cuoiki_code_tutorial.Models.CauHoi;
import org.example.cuoiki_code_tutorial.Utils.ConnectJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KiemTraDauVaoDAO extends SysDAO <CauHoi, String > {


    @Override
    public void insert(CauHoi entity) {

    }

    @Override
    public void update(CauHoi entity) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public CauHoi selectById(String id) {
        return null;
    }

    @Override
    public List selectAll() {
        return null;
    }

    @Override
    protected List<CauHoi> selectBySql(String sql, Object... args) {
        List<CauHoi> cauHois = new ArrayList<>();
        try {
            ResultSet rs = null;

            try {
                rs = ConnectJDBC.query(sql, args);
                while (rs.next()) {
                    CauHoi cauHoi = new CauHoi();

                    cauHoi.setMaKHoaHoc(rs.getString("MaKH"));
                    cauHoi.setStt(rs.getInt("stt"));
                    cauHoi.setCauHoi(rs.getString("CauHoi"));
                    cauHoi.setCauTraLoi1(rs.getString("CauTraLoi1"));
                    cauHoi.setCauTraLoi2(rs.getString("CauTraLoi2"));
                    cauHoi.setCauTraLoi3(rs.getString("CauTraLoi3"));
                    cauHoi.setDapAn(rs.getInt("DapAn"));

                    cauHois.add(cauHoi);
                }
            } finally {
                if (rs != null) {
                    rs.getStatement().getConnection().close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cauHois;
    }

    public List<CauHoi> getCauHoiByMaKhoaHoc(String maKH)
    {
        String query = "Select * from cauhoidauvao where MaKH = ?;";
        List<CauHoi> cauHois = selectBySql(query, maKH);
        return cauHois;
    }

    public int getThoiGianKiemTra(String maKhoaHoc, String username) throws SQLException {
        String query = "select ThoiGianKiemTraDauVao from dangky where MaKH = ? AND MaHV = ?;";
        int time = 0;
        ResultSet rs = ConnectJDBC.query(query, maKhoaHoc, username);
        while (rs.next()) {
            time = rs.getInt(1);
        }
        return time;
    }

    public void insertDiemDauVao(float score, String unsername , String maKH)
    {
        String query = "UPDATE `codelearn`.`dangky` SET `DiemBaiKiemTra` = ? WHERE (`MaHV` = ?) and (`MaKH` = ?);";

        ConnectJDBC.update(query, score, unsername, maKH);
    }
}
