package org.example.cuoiki_code_tutorial.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.KhoaHocDAO;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;

import java.net.URL;
import java.util.ResourceBundle;

public class KhoaHocController implements Initializable {


    @FXML
    private ScrollPane scrollPane;

    private String MaKH;

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public KhoaHocController(String maKH) {
        MaKH = maKH;
    }

    private KhoaHocDAO khoaHocDAO = new KhoaHocDAO();

    private void loadKhoaHoc(KhoaHoc kh) {


        Button hocNgay = new Button("Vào học ngay");
        hocNgay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Load FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/chuong.fxml"));
                    Parent root = loader.load();

                    // Create new scene
                    Scene scene = new Scene(root);

                    // Get the stage
                    Stage stage = (Stage) hocNgay.getScene().getWindow();

                    // Set the new scene
                    stage.setScene(scene);
                    stage.show();
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
                    // Load FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/trang_chu.fxml"));
                    Parent root = loader.load();

                    // Create new scene
                    Scene scene = new Scene(root);

                    // Get the stage
                    Stage stage = (Stage) hocNgay.getScene().getWindow();

                    // Set the new scene
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(quayVe, hocNgay);
        hBox.setSpacing(100);
        ImageView iv = new ImageView(kh.getHinhAnh());
        iv.setFitWidth(1000);
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(kh.getMoTa());
        VBox khoaHocContainer = new VBox();
        khoaHocContainer.getChildren().addAll(hBox, iv, webView);
        scrollPane.setContent(khoaHocContainer);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadKhoaHoc();
    }
}
