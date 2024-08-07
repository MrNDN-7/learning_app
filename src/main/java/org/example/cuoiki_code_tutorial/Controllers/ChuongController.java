package org.example.cuoiki_code_tutorial.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import org.example.cuoiki_code_tutorial.DAOv2.BaiHocDAO;
import org.example.cuoiki_code_tutorial.DAOv2.ChuongDAO;
import org.example.cuoiki_code_tutorial.DAOv2.TienDoDAO;
import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Models.Chuong;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.Models.TienDo;
import org.example.cuoiki_code_tutorial.Utils.Session;
import org.example.cuoiki_code_tutorial.Utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChuongController implements Initializable {

    @FXML
    private VBox chuongContainer;//root
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView imglogoCodeLearn, imglogout;

    Accordion accordion = new Accordion();


    private ChuongDAO chuongDAO = new ChuongDAO();
    private BaiHocDAO baiHocDAO = new BaiHocDAO();


    public void loadAllChuongByKhoaHoc(KhoaHoc khoaHoc) {
        Label lblTenKhoaHoc = new Label(khoaHoc.getTenKH());
        lblTenKhoaHoc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    loadKH(khoaHoc);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        TienDoDAO tienDoDAO = new TienDoDAO();
        List<Integer> lstSTT = new ArrayList<>();
        List<TienDo> tienDos = tienDoDAO.getTienDoByMaTKMaKhoaHoc(UserSession.getInstance().getUsername(), khoaHoc.getMaKH());
        for(TienDo tienDo : tienDos)
        {
            lstSTT.add(tienDo.getTrangThai());
        }
        VBox khoaHocInfo = new VBox(lblTenKhoaHoc);
        //List<Chuong> chuongList = chuongDAO.getAllChuong("python");
        List<Chuong> chuongList = chuongDAO.getChuongByMaKhoaHoc(khoaHoc.getMaKH());
        VBox CacChuong = new VBox();
        for (Chuong chuong : chuongList) {
            Button chuongButton = new Button(chuong.getTenChuong());
            chuongButton.setOnMousePressed(e -> chuongButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #2c31cf;"));
            chuongButton.setOnMouseReleased(e -> chuongButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black;"));
            CacChuong.getChildren().add(chuongButton);

            VBox vBoxCacBaiHoc = new VBox();
            List<BaiHoc> baiHoc = baiHocDAO.getListBaiHocByMaChuog(chuong.getMaChuong(), khoaHoc.getMaKH());
            for (BaiHoc bai : baiHoc) {
                int thutu = bai.getThuTu() - 1;

                Button btn = new Button("Bài " + bai.getThuTu() + ": " + bai.getTenBaiHoc());
                btn.setPrefWidth(Double.MAX_VALUE);
                btn.setAlignment(Pos.CENTER_LEFT);
                final String[] originalColor = {""};
                btn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
                btn.setOnMouseEntered(e -> {
                    originalColor[0] = btn.getStyle(); // Lưu trữ màu cũ của nút Button
                    btn.setStyle("-fx-background-color: #e5f4ff;-fx-border-color: transparent; -fx-text-fill: black;");
                });
                btn.setOnMouseExited(e -> btn.setStyle(originalColor[0])); // Khi rời chuột ra
                if(lstSTT.get(thutu) == 1)
                {
                    btn.setStyle("-fx-background-color: #afe19d  ;-fx-border-color: transparent;-fx-border-radius: 5;  -fx-text-fill: #000000 ;");

                }
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            BaiHocLayout(bai.getThuTu(), bai.getMaChuong(), bai.getMaKhoaHoc(), khoaHoc);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                vBoxCacBaiHoc.getChildren().add(btn);
            }
            TitledPane chapter = new TitledPane(chuong.getTenChuong(), vBoxCacBaiHoc);
            chapter.setExpanded(false);
            chuongButton.setOnAction(event -> toggleBaiHocVisibility(chapter));
            accordion.getPanes().add(chapter);
        }
        chuongContainer.getChildren().add(khoaHocInfo);
        chuongContainer.getChildren().add(accordion);
        scrollPane.setContent(chuongContainer);
        scrollPane.setFitToWidth(true);
    }

    private void toggleBaiHocVisibility(TitledPane chapter) {
        chapter.setExpanded(true);
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
        //loadAllChuongByKhoaHoc("python");
    }

    public void loadBaiHoc(int thutu, String maChuong, String maKhoaHoc) throws IOException, SQLException {
        // Tải layout mới
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/baihoc.fxml"));
        Parent root = loader.load();

        // Khởi tạo controller
        BaiHocController baiHocController = new BaiHocController();
        //baiHocController.getBaiHocByThuTu(thutu, maChuong, maKhoaHoc, );

        // Lấy Scene hiện tại từ Stage của button
        Stage stage = (Stage) imglogoCodeLearn.getScene().getWindow();

        // Tạo Scene mới từ Parent (layout) đã tải
        Scene scene = new Scene(root);

        // Áp dụng CSS (nếu có)
        String pathToStyle = "/CSS/styles_baihoc.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());

        // Hiển thị Scene mới trên Stage
        stage.setScene(scene);
        stage.show();
    }

    public void BaiHocLayout(int thutu, String maChuong, String maKhoaHoc, KhoaHoc khoaHoc) throws IOException, SQLException {

        try {
            Stage stage1 = (Stage) imglogoCodeLearn.getScene().getWindow();
            stage1.close();
            // Load file FXML cho giao diện BaiHocController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/baihoc.fxml"));
            Parent root = loader.load();

            // Lấy instance của BaiHocController
            BaiHocController baiHocController = loader.getController();

            baiHocController.getBaiHocByThuTu(thutu, maChuong, maKhoaHoc, khoaHoc);

            // Tạo Scene mới và hiển thị giao diện BaiHocController
            Scene scene = new Scene(root, 1000, 600);
            Stage stage = new Stage();
            String pathToStyle = "/CSS/styles_baihoc.css";
            scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            // Hiển thị thông báo lỗi cho người dùng
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Có lỗi xảy ra khi chuyển sang layout bài học");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void loadKH (KhoaHoc khoaHoc) throws IOException, SQLException {
        // Tải layout mới
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gioiThieuKhoaHoc.fxml"));
        Parent root = loader.load();

        // Khởi tạo controller
        GioiThieuKhoaHocController gioiThieuKhoaHocController = loader.getController();
        gioiThieuKhoaHocController.loadKhoaHoc(khoaHoc);

        // Lấy Scene hiện tại từ Stage của button
        Stage stage = (Stage) imglogoCodeLearn.getScene().getWindow();

        // Tạo Scene mới từ Parent (layout) đã tải
        Scene scene = new Scene(root);

        // Áp dụng CSS (nếu có)
        String pathToStyle = "/CSS/styles_GTKhoaHoc.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());

        // Hiển thị Scene mới trên Stage
        stage.setScene(scene);
        stage.show();
    }
}

