package org.example.cuoiki_code_tutorial.Controllers.DucNhan;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.Controllers.BaiHocController;
import org.example.cuoiki_code_tutorial.Controllers.GioiThieuKhoaHocController;
import org.example.cuoiki_code_tutorial.Controllers.SceneLoader;
import org.example.cuoiki_code_tutorial.Controllers.UserHomeController;
import org.example.cuoiki_code_tutorial.DAOv2.DangKyKhoaHocDAO;
import org.example.cuoiki_code_tutorial.Dao.DucNhan.LoginDAO;
import org.example.cuoiki_code_tutorial.Models.TaiKhoan;
import org.example.cuoiki_code_tutorial.Utils.Session;
import org.example.cuoiki_code_tutorial.Utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private Button btnDangNhap;

    @FXML
    private Label labelDangKi;

    @FXML
    private Label labelQuenMatKhau;

    @FXML
    private AnchorPane paneDangNhap;

    @FXML
    private RadioButton rdHocVien;

    @FXML
    private RadioButton rdQuanTriVien;

    @FXML
    private PasswordField txtMatKhau;

    @FXML
    private TextField txtTenDangNhap;

    @FXML
    private TextField txtShowPass;
    @FXML
    private ImageView hidePass;
    @FXML
    private ImageView viewPass;

    //gọi DAO
    private LoginDAO dangNhapDao;
    private DangKyKhoaHocDAO dangKyKhoaHocDAO = new DangKyKhoaHocDAO();

    // biến check vai trò
    private boolean ischeck;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dangNhapDao = new LoginDAO();
        btnDangNhap.setOnAction(ActionEvent->dangnhap());
        labelDangKi.setOnMouseClicked(ActionEvent-> {
            try {
                dangki();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        rdQuanTriVien.setOnAction(actionEvent -> {
            rdQuanTriVien.setSelected(true);
            rdHocVien.setSelected(false);
            ischeck=false;
        });
        rdHocVien.setOnAction(actionEvent -> {
            ischeck=true;
            rdHocVien.setSelected(true);
            rdQuanTriVien.setSelected(false);
        });
        viewPass.setOnMouseClicked(ActionEvent->setViewPass());
        hidePass.setOnMouseClicked(ActionEvent->setHidePass());

    }

    public void dangnhap() {
        String tenDangnhap = txtTenDangNhap.getText();
        String matkhau = txtMatKhau.getText();
        if (!rdQuanTriVien.isSelected() && !rdHocVien.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Đăng nhập không thành công");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn vai trò (Quản trị viên/Học viên) trước khi đăng nhập!");
            alert.showAndWait();
            return;
        }

        TaiKhoan taiKhoan = new TaiKhoan(tenDangnhap, matkhau, ischeck);
        try{
            TaiKhoan checktaikhoan = dangNhapDao.onLogin(taiKhoan);
            String username = txtTenDangNhap.getText();
            if(checktaikhoan != null) {
                if (checktaikhoan.isVaiTro()) {
                    String maHV = dangKyKhoaHocDAO.getMaHVByUsername(username);
                    UserSession.getInstance().setUsername(maHV);
                    Stage stage1 = (Stage) btnDangNhap.getScene().getWindow();
                    stage1.close();
                    // Load file FXML cho giao diện BaiHocController
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/user_home.fxml"));
                    Parent root = loader.load();

                    // Lấy instance của BaiHocController
                    UserHomeController userHomeController = loader.getController();

                    // Tạo Scene mới và hiển thị giao diện BaiHocController
                    Scene scene = new Scene(root, 1000, 600);
                    Stage stage = new Stage();
                    String pathToStyle = "/CSS/styles_userhome.css";
                    scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());
                    stage.setScene(scene);
                    stage.show();


                } else {
                    // Nếu là quản trị viên
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Đăng nhập");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Bạn đang đăng nhập với tư cách là Quản trị viên!");
//                    alert.showAndWait();
                    String maAD = dangKyKhoaHocDAO.getMaADByUsername(username);
                    UserSession.getInstance().setUsername(maAD);
                    Stage stage1 = (Stage) btnDangNhap.getScene().getWindow();
                    stage1.close();
                    // Load file FXML cho giao diện BaiHocController
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/trang_chu_tac_gia.fxml"));
                    Parent root = loader.load();

                    // Lấy instance của BaiHocController


                    // Tạo Scene mới và hiển thị giao diện BaiHocController
                    Scene scene = new Scene(root, 1000, 600);
                    Stage stage = new Stage();
                    String pathToStyle = "/CSS/styles_userhome.css";
                    scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());
                    stage.setScene(scene);
                    stage.show();



                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Đăng nhập không thành công");
                alert.setHeaderText(null);
                alert.setContentText("Tên đăng nhập hoặc mật khẩu không đúng. Vui lòng thử lại!");
                alert.showAndWait();
            }
        } catch (ClassNotFoundException |IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String tendangnhap = txtTenDangNhap.getText();
        Session.getInstance().setLoggedInUsername(tendangnhap);
    }


    public void dangki() throws IOException {
        labelDangKi.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/register.fxml"));
        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/Image/iconCode.png"));
        stage.getIcons().add(icon);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setHidePass()
    {
        txtShowPass.setText(txtMatKhau.getText());
        txtShowPass.setVisible(false);
        txtMatKhau.setVisible(true);
        viewPass.setVisible(true);
        hidePass.setVisible(false);
    }
    public void setViewPass()
    {
        txtShowPass.setText(txtMatKhau.getText());
        txtShowPass.setVisible(true);
        txtMatKhau.setVisible(false);
        viewPass.setVisible(false);
        hidePass.setVisible(true);
    }

}
