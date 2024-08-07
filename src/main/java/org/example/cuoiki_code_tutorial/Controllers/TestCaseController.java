package org.example.cuoiki_code_tutorial.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.KiemThuDAO;
import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Models.KiemThu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestCaseController {
    @FXML
    private VBox vBoxTestcase;
    private KiemThuDAO kiemThuDAO = new KiemThuDAO();
    private List<KiemThu> kiemThuList = new ArrayList<>();
    private BaiHoc baiHoc;
    public void setBaiHoc(BaiHoc baiHoc) {
        this.baiHoc = baiHoc;
        loadData();
    }
    @FXML
    public void initialize() {

    }
    private void loadData()
    {
        kiemThuList = kiemThuDAO.getKiemThuByMaKHThuTu(baiHoc.getThuTu(), baiHoc.getMaKhoaHoc());
        for (KiemThu kiemThu : kiemThuList) {
            Button btn = new Button("Kiểm thử số: " + kiemThu.getThuTu());
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/them_testcase.fxml"));
                    Parent root;
                    try {
                        root = loader.load();
//                        ChinhSuaBaiHocController controller = loader.getController();
//                        controller.setBaiHoc(bai);
//                        controller.setThem(false, chuong, khoaHoc);
                        ThemTestCaseController caseController = loader.getController();
                        caseController.setThem(false, kiemThu, baiHoc);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            btn.setPrefWidth(1000);
            btn.setPrefHeight(100);
            btn.setStyle("-fx-font-size: 14px;");
            vBoxTestcase.getChildren().add(btn);
        }
        Button btnPlus = new Button("+");
        btnPlus.setPrefWidth(1000);
        btnPlus.setPrefHeight(80);
        btnPlus.setStyle("-fx-font-size: 14px;");
        btnPlus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                themTestCase(baiHoc);
            }
        });
        vBoxTestcase.getChildren().add(btnPlus);
    }
    private void themTestCase(BaiHoc baiHoc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/them_testcase.fxml"));
        Parent root;
        try {
            root = loader.load();
//            ChinhSuaBaiHocController controller = loader.getController();
//            controller.setThem(true, chuong, khoaHoc);
            ThemTestCaseController caseController = loader.getController();
            caseController.setThem(true, new KiemThu(), baiHoc);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
