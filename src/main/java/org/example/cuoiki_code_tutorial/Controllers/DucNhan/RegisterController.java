package org.example.cuoiki_code_tutorial.Controllers.DucNhan;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.Dao.DucNhan.RegisterDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button btnDangKy;

    @FXML
    private ComboBox<String> chooseGioiTinh;

    @FXML
    private Label labelDangNhap;

    @FXML
    private DatePicker timeNgaySinh;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSoDienThoai;

    @FXML
    private TextField txtTenHocVien;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Thêm lựa chọn cho ComboBox giới tính
        chooseGioiTinh.getItems().addAll("Nam", "Nữ");

        labelDangNhap.setOnMouseClicked(actionEvent -> {
            try {
                dangNhap();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnDangKy.setOnAction(actionEvent -> dangKy());
    }

    public void dangNhap() throws IOException {
        labelDangNhap.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void dangKy() {
        String tenHocVien = txtTenHocVien.getText();
        String email = txtEmail.getText();
        String soDienThoai = txtSoDienThoai.getText();
        String diaChi = txtDiaChi.getText();
        String ngaySinh = timeNgaySinh.getValue().toString();
        String gioiTinhValue = chooseGioiTinh.getValue();
        int gioiTinh = 0;
        if (gioiTinhValue != null && gioiTinhValue.equals("Nam")) {
            gioiTinh = 1;
        }
        String anhDaiDien = "";
        int trangThai = 1;

        boolean success = RegisterDAO.addHocVien(tenHocVien, email, soDienThoai, diaChi, ngaySinh, gioiTinh, anhDaiDien, trangThai);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Đăng ký thành công", "Học viên đã được đăng ký thành công!");
            txtTenHocVien.clear();
            txtEmail.clear();
            txtSoDienThoai.clear();
            txtDiaChi.clear();
            timeNgaySinh.getEditor().clear();
            chooseGioiTinh.getSelectionModel().clearSelection();
        } else {
            showAlert(Alert.AlertType.ERROR, "Đăng ký thất bại", "Đã xảy ra lỗi khi đăng ký học viên. Vui lòng thử lại sau.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
