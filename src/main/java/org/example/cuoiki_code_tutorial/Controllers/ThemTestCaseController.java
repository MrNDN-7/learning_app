package org.example.cuoiki_code_tutorial.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.KiemThuDAO;
import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Models.KiemThu;

import java.util.ArrayList;
import java.util.List;

public class ThemTestCaseController {
    @FXML
    private TextArea txtInput, txtOutput;
    @FXML
    private Button btnLuu, btnHuy;
    @FXML
    private CheckBox checkBoxInput;

    private BaiHoc baiHoc;
    private KiemThu kiemThu = new KiemThu();

    private boolean isThem = false;
    public void setThem(boolean isThem, KiemThu kiemThu, BaiHoc baiHoc) {
        this.isThem = isThem;
        this.kiemThu = kiemThu;
        this.baiHoc = baiHoc;

        loadData(kiemThu);
    }

    @FXML
    private void initialize() {
        txtInput.setText("");
        btnLuu.setOnAction(actionEvent -> {
            kiemThu.setInput(txtInput.getText());
            kiemThu.setOutput(txtOutput.getText());
            kiemThu.setMaKH(baiHoc.getMaKhoaHoc());
            kiemThu.setThuTu(baiHoc.getThuTu());
            KiemThuDAO kiemThuDAO = new KiemThuDAO();
            if(isThem)
            {
                kiemThuDAO.insert(kiemThu);
            }
            else
            {
                kiemThuDAO.update(kiemThu);
            }
            btnLuu.getScene().getWindow().hide();
        });
        btnHuy.setOnAction(actionEvent -> {
            btnHuy.getScene().getWindow().hide();
        });
    }
    private void loadData(KiemThu kiemThu){
        txtInput.setText(kiemThu.getInput());
        txtOutput.setText(kiemThu.getOutput());
        checkBoxInput.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == true)
            {
                txtInput.setText("");
            }
        });
        txtInput.setOnKeyTyped(keyEvent -> {
            checkBoxInput.selectedProperty().setValue(false);
        });
    }
}
