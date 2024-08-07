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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.KhoaHocDAO;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TrangChuController implements Initializable {
    @FXML
    private ScrollPane scrollPane;

    private void loadKhoaHoc() {

        KhoaHocDAO khoaHocDAO = new KhoaHocDAO();
        HBox hBox = new HBox();
        List<KhoaHoc> khs = khoaHocDAO.selectAll();

        for(KhoaHoc khoaHoc : khs) {
            VBox vBox = new VBox();
            vBox.setPrefWidth(200);
            vBox.setPadding(new Insets(10, 10, 10, 10));
            vBox.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(khoaHoc.getHinhAnh());
            imageView.setFitWidth(300);
            imageView.setFitHeight(200);
            Label tenKH = new Label(khoaHoc.getTenKH());
            tenKH.setAlignment(Pos.CENTER);
            Label tacGia = new Label(khoaHoc.getMaAD());
            tacGia.setAlignment(Pos.CENTER);
            Label ngayTao = new Label(khoaHoc.getNgayTao().toString());
            ngayTao.setAlignment(Pos.CENTER);
            Button button = new Button("Học Ngay");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        // Load FXML file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gioiThieuKhoaHoc.fxml"));
                        KhoaHocController gioiThieuKhoaHocController = new KhoaHocController(khoaHoc.getMaKH());
                        loader.setController(gioiThieuKhoaHocController);
                        Parent root = loader.load();

                        // Create new scene
                        Scene scene = new Scene(root);

                        // Get the stage
                        Stage stage = (Stage) button.getScene().getWindow();

                        // Set the new scene
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            vBox.getChildren().addAll(imageView, tenKH, tacGia, ngayTao, button);
            hBox.getChildren().add(vBox);
        }
        scrollPane.setContent(hBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadKhoaHoc();
    }
}
