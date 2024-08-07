package org.example.cuoiki_code_tutorial.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.DAOv2.BaiHocDAO;
import org.example.cuoiki_code_tutorial.Models.Chuong;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;

import java.io.IOException;

public class ChinhSuaBaiHocController {

    @FXML
    private TextField txtTenBaiHoc;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label lableTitle;

    @FXML
    private Button btnSave, btnTestCase;

    @FXML
    private Button btnCancel;
    @FXML
    private Slider scrollTL;
    @FXML
    private Slider scrollGH;
    @FXML
    private Label lblTL, lblGHKT;
    @FXML
    private ComboBox cbDoKho;
    private KhoaHocAdminController khoaHocAdminController;

    private boolean isThem = false;
    private Chuong chuong;
    private KhoaHoc khoaHoc;

    private BaiHoc baiHoc = new BaiHoc();

    private BaiHocDAO dao = new BaiHocDAO();

    public void initialize() {
        ObservableList<String> mucDoKho = FXCollections.observableArrayList("Dễ", "Vừa", "Khó");
        cbDoKho.setItems(mucDoKho);
        lblGHKT.setText(String.valueOf(scrollGH.getValue()));
        lblTL.setText(String.valueOf(scrollTL.getValue()));
        scrollTL.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Xử lý sự kiện khi giá trị của slider scrollTL thay đổi
                lblTL.setText(String.valueOf(newValue.intValue()));
            }
        });

        scrollGH.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Xử lý sự kiện khi giá trị của slider scrollGH thay đổi
                lblGHKT.setText(String.valueOf(newValue.intValue()));
            }
        });
        cbDoKho.getSelectionModel().select(0);
        // Khởi tạo sự kiện cho nút "Lưu"
        btnSave.setOnAction(this::handleSaveButton);

        // Khởi tạo sự kiện cho nút "Hủy"
        btnCancel.setOnAction(this::handleCancelButton);
        btnTestCase.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/test_case.fxml"));
                Parent root;
                try {
                    root = loader.load();
                    TestCaseController controller = loader.getController();
                    controller.setBaiHoc(baiHoc);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void loadBaiHoc(BaiHoc baiHoc){
        lableTitle.setText("Chỉnh sửa bài học");
        txtTenBaiHoc.setText(baiHoc.getTenBaiHoc());
        htmlEditor.setHtmlText(baiHoc.getNoiDung());
        scrollTL.setValue(baiHoc.getThoiLuong());
        scrollGH.setValue(baiHoc.getGioiHanKyTu());
        lblGHKT.setText(String.valueOf(scrollGH.getValue()));
        lblTL.setText(String.valueOf(scrollTL.getValue()));
        String mucDoKho = baiHoc.getMucDo();
        int i = mucDoKho.equals("Dễ") ? 0 : mucDoKho.equals("Vừa") ? 1 : 2;
        cbDoKho.getSelectionModel().select(i);
    }

    public void setBaiHoc(BaiHoc baiHoc) {
        this.baiHoc = baiHoc;
        // Hiển thị thông tin của bài học trong form chỉnh sửa
    }

    // Xử lý sự kiện khi nút "Lưu" được nhấn
    private void handleSaveButton(ActionEvent event) {
        // Cập nhật thông tin của bài học và lưu vào cơ sở dữ liệu

        baiHoc.setTenBaiHoc(txtTenBaiHoc.getText());
        baiHoc.setMaChuong(chuong.getMaChuong());
        baiHoc.setNoiDung(htmlEditor.getHtmlText());
        baiHoc.setGioiHanKyTu((int) scrollGH.getValue());
        baiHoc.setThoiLuong((int) scrollTL.getValue());
        baiHoc.setMucDo(cbDoKho.getValue().toString());
        baiHoc.setMaKhoaHoc(khoaHoc.getMaKH());
        BaiHocDAO baiHocDAO = new BaiHocDAO();
        if(isThem)
            baiHocDAO.insert(baiHoc);
        else
            baiHocDAO.update(baiHoc);
        // Đóng cửa sổ sau khi lưu
        btnCancel.getScene().getWindow().hide();
    }

    // Xử lý sự kiện khi nút "Hủy" được nhấn
    private void handleCancelButton(ActionEvent event) {
        // Đóng cửa sổ khi hủy
        btnCancel.getScene().getWindow().hide();
    }
    public void setThem(boolean isThem, Chuong chuong, KhoaHoc khoaHoc) {
        this.isThem = isThem;
        this.chuong = chuong;
        this.khoaHoc = khoaHoc;
        if(!isThem)
        {
            loadBaiHoc(baiHoc);
        }
    }
}
