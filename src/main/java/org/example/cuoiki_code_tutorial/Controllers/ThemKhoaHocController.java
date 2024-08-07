package org.example.cuoiki_code_tutorial.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import org.example.cuoiki_code_tutorial.DAOv2.BaiHocDAO;
import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Models.Chuong;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.DAOv2.ChuongDAO;
import org.example.cuoiki_code_tutorial.DAOv2.QuanLyKhoaHocDAO;

import java.io.File;

public class ThemKhoaHocController {
    @FXML
    private TextField tenKhoaHocField;

    @FXML
    private HTMLEditor htmlEditorMoTa;

    @FXML
    private Button saveButton;

    @FXML
    private TabPane tabPane;
    KhoaHoc khoaHoc;

    private QuanLyKhoaHocDAO khoaHocDAO = new QuanLyKhoaHocDAO();
    private ChuongDAO chuongDAO = new ChuongDAO();

    @FXML
    public void initialize() {
        saveButton.setOnAction(this::handleSaveButton);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getText().equals("+")) {
                tabPane.getTabs().remove(newValue);
                addTab();
                tabPane.getTabs().add(newValue);
            }
        });
    }

    private void addTab() {
        Tab tab = new Tab();
        tab.setText("Chương Mới");
        VBox vBox = new VBox();
        Label label = new Label("Thêm chương mới");
        TextField tenChuongField = new TextField();
        Button addButton = new Button("Thêm chương");
        addButton.setOnAction(event -> {
            Chuong chuong = new Chuong();
            chuong.setTenChuong(tenChuongField.getText());
            // Lưu chương vào cơ sở dữ liệu
            chuongDAO.insert(chuong);
            // Thêm giao diện cho tab mới
            addBaiHocField(vBox, chuong);
        });
        vBox.getChildren().addAll(label, tenChuongField, addButton);
        tab.setContent(vBox);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private void addBaiHocField(VBox vBox, Chuong chuong) {
        Label label = new Label("Thêm bài học mới cho chương " + chuong.getTenChuong());
        TextField tenBaiHocField = new TextField();
        tenBaiHocField.setPromptText("Nhập tên bài học");

        HTMLEditor htmlEditorNoiDung = new HTMLEditor();
        htmlEditorNoiDung.setPrefHeight(200);

        Slider thoiLuongSlider = new Slider();
        thoiLuongSlider.setMin(10);
        thoiLuongSlider.setMax(60); // Giả sử thời lượng tối đa là 120 phút
        thoiLuongSlider.setShowTickLabels(true);
        thoiLuongSlider.setShowTickMarks(true);
        thoiLuongSlider.setMajorTickUnit(10);

        Slider gioiHan = new Slider();
        gioiHan.setMin(0);
        gioiHan.setMax(200); // Giả sử thời lượng tối đa là 120 phút
        gioiHan.setShowTickLabels(true);
        gioiHan.setShowTickMarks(true);
        gioiHan.setMajorTickUnit(1);

        ObservableList<String> mucDoKho = FXCollections.observableArrayList("Dễ", "Vừa", "Khó");

// Khởi tạo ComboBox và đưa danh sách các mức độ vào ComboBox
        ComboBox<String> mucDo = new ComboBox<>(mucDoKho);

        Button addButton = new Button("Thêm bài học");
        addButton.setOnAction(event -> {
            BaiHoc baiHoc = new BaiHoc();
            baiHoc.setTenBaiHoc(tenBaiHocField.getText());
            baiHoc.setMaChuong(chuong.getMaChuong());
            baiHoc.setNoiDung(htmlEditorNoiDung.getHtmlText());
            baiHoc.setGioiHanKyTu((int) gioiHan.getValue());
            baiHoc.setThoiLuong((int) thoiLuongSlider.getValue());
            baiHoc.setMucDo(mucDo.getValue());
//            baiHoc.setMaKH();
            BaiHocDAO baiHocDAO = new BaiHocDAO();
            baiHocDAO.insert(baiHoc);
        });
        vBox.getChildren().addAll(label, tenBaiHocField, addButton);
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        khoaHoc = new KhoaHoc();
        khoaHoc.setTenKH(tenKhoaHocField.getText());
        khoaHoc.setMoTa(htmlEditorMoTa.getHtmlText());
        // Lưu khóa học vào cơ sở dữ liệu
        khoaHocDAO.insert(khoaHoc);
    }
    @FXML
    private void handleAddImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh chính");
        File file = fileChooser.showOpenDialog(saveButton.getScene().getWindow());
        // Xử lý việc chọn ảnh và cập nhật giao diện
        if (file != null) {
            // Code xử lý khi chọn ảnh thành công
        }
    }

    @FXML
    private void handleAddIntroImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh giới thiệu");
        File file = fileChooser.showOpenDialog(saveButton.getScene().getWindow());
        // Xử lý việc chọn ảnh và cập nhật giao diện
        if (file != null) {
            // Code xử lý khi chọn ảnh thành công
        }
    }
}
