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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.KhoaHocDAO;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AllKhoaHocController implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView imglogoCodeLearn, imglogout;

    public void loadKhoaHoc( ) {

        KhoaHocDAO khoaHocDAO = new KhoaHocDAO();
        List<KhoaHoc> khs = khoaHocDAO.selectAll();
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
        scrollPane.setContent(flowPane);
////        scrollPane.setMinWidth(870);
        scrollPane.setMaxWidth(900);
//        scrollPane.setMinHeight(320);
        scrollPane.setMaxHeight(540);
        scrollPane.setFitToWidth(true); // Tự động co chiều ngang của ScrollPane
        scrollPane.setFitToHeight(true); // Tự động co chiều cao của ScrollPane
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        // Thêm ScrollPane vào layout chính
        //SuggestedCourses.getChildren().add(scrollPane);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imgLogo = new Image(getClass().getResource("/Image/logo_codelearn.png").toString());
        Image imgLogout = new Image(getClass().getResource("/Image/logout.png").toString());
        imglogoCodeLearn.setImage(imgLogo);
        imglogout.setImage(imgLogout);

        imglogoCodeLearn.setOnMouseClicked(e->{
            String resPath = "/FXML/user_home.fxml";
            String cssPath = "/CSS/styles_userhome.css";
            Stage stage = (Stage) imglogoCodeLearn.getScene().getWindow();
            SceneLoader.loadScene(resPath, cssPath, stage);
        });

        loadKhoaHoc();
    }

    public void loadGTKH(KhoaHoc khoaHoc, Button button) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gioiThieuKhoaHoc.fxml"));
//        GioiThieuKhoaHocController gioiThieuKhoaHocController = new GioiThieuKhoaHocController();
//        loader.setController(gioiThieuKhoaHocController);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create new scene
        Scene scene = new Scene(root);

        // Get the stage
        Stage stage = (Stage) button.getScene().getWindow();
        String pathToStyle = "/CSS/styles_GTKhoaHoc.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());

        // Set the new scene
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
