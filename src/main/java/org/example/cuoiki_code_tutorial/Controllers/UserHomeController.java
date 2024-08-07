package org.example.cuoiki_code_tutorial.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.Controllers.DucNhan.HocVienController;
import org.example.cuoiki_code_tutorial.DAOv2.DangKyKhoaHocDAO;
import org.example.cuoiki_code_tutorial.DAOv2.KhoaHocDAO;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.Utils.Session;
import org.example.cuoiki_code_tutorial.Utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserHomeController implements Initializable {
    @FXML
    private ImageView imglogoCodeLearn, imglogout;
    @FXML
    private AnchorPane StudyingCourses, CompleteCourses, SuggestedCourses, paneAccount;
    @FXML
    private Line line_StudyingCourse, line_CompleteCourse, line_SuggestedCourse;
    @FXML
    private Label lbl_CompleteCourse, lbl_StudyingCourse, lbl_SuggestedCourse, lbl_username, lblDangHoc;
    @FXML
    private Button btnShowAllCourse;
    @FXML
    private ProgressBar progressBar_expAcc, progressBar_KhoaHoc;

    private String userName;
    KhoaHocDAO khoaHocDAO = new KhoaHocDAO();
    private DangKyKhoaHocDAO dangKyKhoaHocDAO = new DangKyKhoaHocDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imgLogo = new Image(getClass().getResource("/Image/logo_codelearn.png").toString());
        Image imgLogout = new Image(getClass().getResource("/Image/logout.png").toString());
        imglogoCodeLearn.setImage(imgLogo);
        imglogout.setImage(imgLogout);
        loadKhoaHocGoiY();
        loadKhoaHoc();
        //Session.getInstance().setLoggedInUsername("thanhbinhdang");
        userName = UserSession.getInstance().getUsername();


        try {
            lbl_username.setText(dangKyKhoaHocDAO.getTenByMaHV(userName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btnShowAllCourse.setOnAction(e -> {
            try {
                loadAllKhoaHoc(btnShowAllCourse);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        imglogout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showAlert("Đăng xuất");
            }
        });

        paneAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    loadAccount();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        progressBar_expAcc.setProgress(0.5); // Đặt tiến trình ban đầu là 50%
        progressBar_expAcc.idProperty().bind(
                progressBar_expAcc.progressProperty().asString("%.0f%%")
        );
        progressBar_expAcc.accessibleTextProperty();

        String dahoc = null;
        try {
            dahoc = khoaHocDAO.KhoaHocDaHoc(userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblDangHoc.setText(dahoc);
        String[] parts = dahoc.split("/");

        double num1 = Double.parseDouble(parts[0]);
        double num2 = Double.parseDouble(parts[1]);

        double result = num1 / num2;
        progressBar_KhoaHoc.setProgress(result);
        progressBar_KhoaHoc.idProperty().bind(
                progressBar_KhoaHoc.progressProperty().asString("%.0f%%")
        );
        progressBar_KhoaHoc.accessibleTextProperty();


    }

    public void updateProgress(double progress) {
        progressBar_expAcc.setProgress(progress);
    }

    private void loadKhoaHocGoiY() {
        AnchorPane studyingCourses = StudyingCourses;
        AnchorPane suggestedCourses = SuggestedCourses;
        AnchorPane completeCourses = CompleteCourses;

        suggestedCourses.setVisible(true);
        studyingCourses.setVisible(false);
        completeCourses.setVisible(false);

        line_SuggestedCourse.setVisible(true);
        line_CompleteCourse.setVisible(false);
        line_StudyingCourse.setVisible(false);


        List<KhoaHoc> khs = khoaHocDAO.selectAll();
        loadKhoaHoc(khs, suggestedCourses);

    }

    private void loadKhoaHoc(List<KhoaHoc> khs, AnchorPane pane)
    {
        FlowPane flowPane = new FlowPane();
        //flowPane.setRotate(4);
        flowPane.setPrefWrapLength(900);
        if (khs != null) {
            for (KhoaHoc khoaHoc : khs) {
                VBox vBox = new VBox();
                vBox.setPrefWidth(200);
                vBox.setPrefHeight(200);
                vBox.setPadding(new Insets(10, 10, 10, 10));
                vBox.setAlignment(Pos.CENTER);
                ImageView imageView = new ImageView(khoaHoc.getHinhAnh());
                imageView.setFitWidth(200);
                imageView.setFitHeight(150);
                Label tenKH = new Label(khoaHoc.getTenKH());
                tenKH.setAlignment(Pos.CENTER);
                Label tacGia = new Label(khoaHoc.getMaAD());
                tacGia.setAlignment(Pos.CENTER);
                Label ngayTao = new Label(khoaHoc.getNgayTao().toString());
                ngayTao.setAlignment(Pos.CENTER);
                Button button = new Button("Học Ngay");
                vBox.getStyleClass().add("vbox-background");

                vBox.setOnMouseClicked(event -> {
                    try {
//                        Stage stage = (Stage) vBox.getScene().getWindow();
//                        stage.close();
                        loadKH(khoaHoc);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                });
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
//                            Stage stage = (Stage) button.getScene().getWindow();
//                            stage.close();
                            loadKH(khoaHoc);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                vBox.getChildren().addAll(imageView, tenKH, tacGia, ngayTao, button);
                flowPane.getChildren().add(vBox);
            }

        }
        ScrollPane scrollPane = new ScrollPane(flowPane);
////        scrollPane.setMinWidth(870);
        scrollPane.setMaxWidth(900);
        scrollPane.setMinHeight(320);
        scrollPane.setMaxHeight(320);
        scrollPane.setFitToWidth(true); // Tự động co chiều ngang của ScrollPane
        scrollPane.setFitToHeight(true); // Tự động co chiều cao của ScrollPane

        // Thêm ScrollPane vào layout chính
        pane.getChildren().add(scrollPane);

    }


    public void loadHeaer() {

        imglogoCodeLearn.setOnMouseClicked(e -> {
            String resPath = "/FXML/user_home.fxml";
            String cssPath = "CSS/styles_userhome.css";
            Stage stage = (Stage) imglogoCodeLearn.getScene().getWindow();
            SceneLoader.loadScene(resPath, cssPath, stage);
        });

    }

    public void loadKhoaHocDangHoc() {
        AnchorPane studyingCourses = StudyingCourses;
        AnchorPane suggestedCourses = SuggestedCourses;
        AnchorPane completeCourses = CompleteCourses;

        suggestedCourses.setVisible(false);
        studyingCourses.setVisible(true);
        completeCourses.setVisible(false);

        line_SuggestedCourse.setVisible(false);
        line_CompleteCourse.setVisible(false);
        line_StudyingCourse.setVisible(true);
        List<KhoaHoc> khs = khoaHocDAO.getKhoaHocDangHoc(userName);
        loadKhoaHoc(khs, studyingCourses);
    }

    public void loadKhoaHocHoanThanh() {
        AnchorPane studyingCourses = StudyingCourses;
        AnchorPane suggestedCourses = SuggestedCourses;
        AnchorPane completeCourses = CompleteCourses;

        suggestedCourses.setVisible(false);
        studyingCourses.setVisible(false);
        completeCourses.setVisible(true);

        line_SuggestedCourse.setVisible(false);
        line_CompleteCourse.setVisible(true);
        line_StudyingCourse.setVisible(false);
        List<KhoaHoc> khs = khoaHocDAO.getKhoaHocDaHoc(userName);
        loadKhoaHoc(khs, completeCourses);
    }

    public void loadKhoaHoc() {
        lbl_SuggestedCourse.setOnMouseClicked(e -> {
            loadKhoaHocGoiY();
        });
        lbl_StudyingCourse.setOnMouseClicked(e -> {
            loadKhoaHocDangHoc();
        });
        lbl_CompleteCourse.setOnMouseClicked(e -> {
            loadKhoaHocHoanThanh();
        });
    }

    public void loadGTKH(KhoaHoc khoaHoc) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gioiThieuKhoaHoc.fxml"));
        Parent root = loader.load();
        GioiThieuKhoaHocController gioiThieuKhoaHocController = loader.getController();
        gioiThieuKhoaHocController.loadKhoaHoc(khoaHoc);
        // Tạo Scene mới và hiển thị giao diện BaiHocController
        Scene scene = new Scene(root, 1000, 600);
        Stage stage = new Stage();
        String pathToStyle = "/CSS/styles_GTKhoaHoc.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    public void loadKH(KhoaHoc khoaHoc) throws IOException, SQLException {
        // Tải layout mới
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gioiThieuKhoaHoc.fxml"));
        Parent root = loader.load();

        // Khởi tạo controller
        GioiThieuKhoaHocController gioiThieuKhoaHocController = loader.getController();
        gioiThieuKhoaHocController.loadKhoaHoc(khoaHoc);

        // Lấy Scene hiện tại từ Stage của button
        Stage stage = (Stage) SuggestedCourses.getScene().getWindow();

        // Tạo Scene mới từ Parent (layout) đã tải
        Scene scene = new Scene(root);

        // Áp dụng CSS (nếu có)
        String pathToStyle = "/CSS/styles_GTKhoaHoc.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());

        // Hiển thị Scene mới trên Stage
        stage.setScene(scene);
        stage.show();
    }

    public void loadAccount( ) throws IOException, SQLException {
        // Tải layout mới
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/hocvien.fxml"));
        Parent root = loader.load();

        // Khởi tạo controller
        HocVienController hocVienController  = loader.getController();
        hocVienController.ThongTinHocVien();

        // Lấy Scene hiện tại từ Stage của button
        Stage stage = (Stage) SuggestedCourses.getScene().getWindow();

        // Tạo Scene mới từ Parent (layout) đã tải
        Scene scene = new Scene(root);

        // Áp dụng CSS (nếu có)
//        String pathToStyle = "/CSS/styles_GTKhoaHoc.css";
//        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());

        // Hiển thị Scene mới trên Stage
        stage.setScene(scene);
        stage.show();
    }

    public void loadAllKhoaHoc(Button btn) throws IOException {
        String resPath = "/FXML/all_khoa_hoc.fxml";
        String cssPath = "/CSS/styles_all_khoahoc.css";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resPath));
        Parent root = loader.load();

        // Khởi tạo controller
        AllKhoaHocController allKhoaHocController = loader.getController();
        allKhoaHocController.loadKhoaHoc();

        // Lấy Scene hiện tại từ Stage của button
        Stage stage = (Stage) btn.getScene().getWindow();

        // Tạo Scene mới từ Parent (layout) đã tải
        Scene scene = new Scene(root);

        // Áp dụng CSS (nếu có)
        //String pathToStyle = "/CSS/styles_GTKhoaHoc.css";
        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

        // Hiển thị Scene mới trên Stage
        stage.setScene(scene);
        stage.show();
    }
    public void DangXuat()
    {
        Session.getInstance().setLoggedInUsername(null);
        UserSession.getInstance().setUsername(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) imglogoCodeLearn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION, WARNING, ERROR as needed
        alert.setTitle("Thông báo");
        alert.setHeaderText(null); // Optional header text
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DangXuat();

            }
        }); // Display the alert and wait for user interaction
    }

}
