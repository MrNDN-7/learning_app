package org.example.cuoiki_code_tutorial.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.DangKyKhoaHocDAO;
import org.example.cuoiki_code_tutorial.DAOv2.KiemTraDauVaoDAO;
import org.example.cuoiki_code_tutorial.Models.CauHoi;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.Utils.Session;
import org.example.cuoiki_code_tutorial.Utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class GioiThieuKhoaHocController implements Initializable {


    @FXML
    private ImageView imglogout, imglogoCodeLearn;
    @FXML
    private AnchorPane Overview;
    @FXML
    private Label lbl_IDad, lbl_CourseName, lbl_ChungChi, lbl_GioiThieu, lbl_diemdauvao;
    @FXML
    private Button btnStudyNow, btn_SignCourse, btnTestDauVao;
    @FXML
    private WebView wvGioiThieuKH;


    private String maKhoaHoc, userName;

    private KhoaHoc khoaHocf;

    private DangKyKhoaHocDAO dangKyKhoaHocDAO = new DangKyKhoaHocDAO();
    private KiemTraDauVaoDAO kiemTraDauVaoDAO = new KiemTraDauVaoDAO();

    void loadKhoaHoc(KhoaHoc khoaHoc) throws SQLException {
        khoaHocf = khoaHoc;
        maKhoaHoc = khoaHoc.getMaKH();
        userName = UserSession.getInstance().getUsername();
        //Button hocNgay = new Button("Vào học ngay");
        lbl_CourseName.setText(khoaHoc.getTenKH());
        lbl_IDad.setText(khoaHoc.getMaAD());
        WebEngine webEngine = wvGioiThieuKH.getEngine();
        webEngine.loadContent(khoaHoc.getMoTa());


        btnStudyNow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadCTKH(khoaHoc);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button quayVe = new Button("Quay về");
        quayVe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String resPath = "/FXML/user_home.fxml";
                    String cssPath = "/CSS/styles_userhome.css";
                    Stage stage = (Stage) quayVe.getScene().getWindow();
                    SceneLoader.loadScene(resPath, cssPath, stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (dangKyKhoaHocDAO.isDangKyKhaohocByUssername(userName, maKhoaHoc)) {
            btnStudyNow.setVisible(true);
            btn_SignCourse.setVisible(false);
            Float score = dangKyKhoaHocDAO.isKiemtraDauVao(maKhoaHoc, userName);
            if (score != 0) {
                btnTestDauVao.setVisible(false);
                lbl_diemdauvao.setVisible(true);
                lbl_diemdauvao.setText("Đầu vào: " + score);
            } else {
                btnTestDauVao.setVisible(true);
            }

        } else {
            btnStudyNow.setVisible(false);
            btn_SignCourse.setVisible(true);
            btnTestDauVao.setVisible(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Image imgLogo = new Image(getClass().getResource("/Image/logo_codelearn.png").toString());
        Image imgLogout = new Image(getClass().getResource("/Image/logout.png").toString());
        imglogoCodeLearn.setImage(imgLogo);
        imglogout.setImage(imgLogout);

        imglogoCodeLearn.setOnMouseClicked(e -> {
            String resPath = "/FXML/user_home.fxml";
            String cssPath = "/CSS/styles_userhome.css";
            Stage stage = (Stage) imglogoCodeLearn.getScene().getWindow();
            SceneLoader.loadScene(resPath, cssPath, stage);
        });


    }

    public void onClickDangKy(ActionEvent actionEvent) throws SQLException {

        int soLuongBaiHoc = dangKyKhoaHocDAO.getSoLuongBaiHocByMaKhoaHoc(maKhoaHoc);
        if(soLuongBaiHoc == 0)
        {
            soLuongBaiHoc = 20;
        }
        boolean isRegistered = dangKyKhoaHocDAO.dangKyKhoaHoc(userName, maKhoaHoc, soLuongBaiHoc);

        // Display appropriate alert based on registration result
        if (isRegistered) {
            showAlert("Đăng ký khóa học thành công!");
        } else {
            showAlert("Đăng ký khóa học thất bại. Vui lòng thử lại sau.");
        }


    }

    public void onClickTestDauVao(ActionEvent actionEvent) throws SQLException, IOException {
        List<CauHoi> cauHois = kiemTraDauVaoDAO.getCauHoiByMaKhoaHoc(khoaHocf.getMaKH());
        int time = kiemTraDauVaoDAO.getThoiGianKiemTra(khoaHocf.getMaKH(), userName);
        loadkiemTraDauVao(cauHois, time);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION, WARNING, ERROR as needed
        alert.setTitle("Thông báo");
        alert.setHeaderText(null); // Optional header text
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                reloadLayout();
            }
        }); // Display the alert and wait for user interaction
    }

    private void reloadLayout() {
        btnStudyNow.setVisible(true);
        btn_SignCourse.setVisible(false);
        btnTestDauVao.setVisible(true);
    }

    public void loadCTKH(KhoaHoc khoaHoc) throws IOException, SQLException {
        // Tải layout mới
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/chuong.fxml"));
        Parent root = loader.load();

        // Khởi tạo controller
        ChuongController chuongController = loader.getController();
        chuongController.loadAllChuongByKhoaHoc(khoaHoc);

        // Lấy Scene hiện tại từ Stage của button
        Stage stage = (Stage) btnStudyNow.getScene().getWindow();

        // Tạo Scene mới từ Parent (layout) đã tải
        Scene scene = new Scene(root);

        // Áp dụng CSS (nếu có)
        String pathToStyle = "/CSS/chuongCss.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());

        // Hiển thị Scene mới trên Stage
        stage.setScene(scene);
        stage.show();
    }

    public void loadkiemTraDauVao(List<CauHoi> cauHois, int time) throws IOException, SQLException {


        // Tải layout mới
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/KiemTraDauVao.fxml"));
        Parent root = loader.load();

        // Khởi tạo controller
        KiemTraDauVao kiemTraDauVao = loader.getController();
        kiemTraDauVao.loadCauHoi(cauHois, time);

        // Lấy Scene hiện tại từ Stage của button
        Stage stage = (Stage) btnStudyNow.getScene().getWindow();

        // Tạo Scene mới từ Parent (layout) đã tải
        Scene scene = new Scene(root);

        // Áp dụng CSS (nếu có)
        String pathToStyle = "/CSS/styles_KTDV.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());

        // Hiển thị Scene mới trên Stage
        stage.setScene(scene);
        stage.show();
    }


}
