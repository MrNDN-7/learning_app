package org.example.cuoiki_code_tutorial.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.BaiHocDAO;
import org.example.cuoiki_code_tutorial.DAOv2.KiemThuDAO;
import org.example.cuoiki_code_tutorial.DAOv2.TienDoDAO;
import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.Models.KiemThu;
import org.example.cuoiki_code_tutorial.Models.TienDo;
import org.example.cuoiki_code_tutorial.Utils.Session;
import org.example.cuoiki_code_tutorial.Utils.UserSession;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class BaiHocController implements Initializable {
    @FXML
    private AnchorPane layoutBaiHoc;
    @FXML
    private WebView wViewBaiHoc;
    @FXML
    private Label lblTenBaiHoc, lblGHKT;
    @FXML
    private Button btnMucDo, btnBack, btnNopBai, btnclick;
    @FXML
    private Button btnKiemThu1, btnChayThu;
    @FXML
    private HBox hBox;
    @FXML
    private ChoiceBox<String> choiceBoxNgonNgu;
    @FXML
    private VBox vBoxCodeEditor;
    @FXML
    private CodeArea codeArea;
    @FXML
    private TextArea outputArea;


    private BaiHoc baiHoc;
    private KhoaHoc khoaHocf;
    private KiemTraDauVao kiemTraDauVao;
    private static final BaiHocDAO baiHocDAO = new BaiHocDAO();
    private String ngonNgu, maKhoaHocfinal, maChuongfinal;

    private PythonExecutor pythonExecutor;

    private int slKiemThu;

    private List<Map<String, Integer>> danhSachTrangThai = new ArrayList<>();
    TienDoDAO tienDoDAO = new TienDoDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String pathToStyle = "/CSS/styles_baihoc.css";

        layoutBaiHoc.getStylesheets().add(getClass().getResource("/CSS/styles_baihoc.css").toExternalForm());

        ObservableList<String> options = FXCollections.observableArrayList("Python", "Java", "C++");
        choiceBoxNgonNgu.setItems(options);
        choiceBoxNgonNgu.getSelectionModel().select(0);
        choiceBoxNgonNgu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue);
            ngonNgu = choiceBoxNgonNgu.getSelectionModel().getSelectedItem();


            System.out.println(ngonNgu);
        });

        setUpCodeArea();
        pythonExecutor = new PythonExecutor(codeArea, outputArea);

        btnNopBai.setDisable(true);

        //codeArea.replaceText("...\nA simple Python program to display Hello, World! on the screen\nusing");


    }

//    public void initData(String maBH, String maChuong, String MaKHoaHoc) {
//        BaiHocController baiHocController = new BaiHocController();
//
//        try {
//            baiHocController.loadBaiHocByMaBHMaChuong(maBH, maChuong, MaKHoaHoc);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Xử lý lỗi khi tải bài học
//        }
//    }

    public void setUpCodeArea() {
        codeArea.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px; -fx-text-fill: black; ");
        codeArea.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setOnKeyTyped(e -> codeArea.autosize());
        codeArea.setOnKeyPressed(e -> codeArea.autosize());
        codeArea.setTextInsertionStyle(Collections.singleton("-fx-background-color: red;"));
        codeArea.showParagraphAtTop(1);
        codeArea.setStyle("-fx-paragraph-graph-color: #d900d1;");

    }

    public void onBackButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        //goChuongBaiHoc();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/chuong.fxml"));
        Parent root = loader.load();

        // Khởi tạo controller
        ChuongController chuongController = loader.getController();
        chuongController.loadAllChuongByKhoaHoc(khoaHocf);

        // Lấy Scene hiện tại từ Stage của button
        Stage stage = (Stage) btnBack.getScene().getWindow();

        // Tạo Scene mới từ Parent (layout) đã tải
        Scene scene = new Scene(root);

        // Áp dụng CSS (nếu có)
        String pathToStyle = "/CSS/chuongCss.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());

        // Hiển thị Scene mới trên Stage
        stage.setScene(scene);
        stage.show();

    }


//    public void onKiemThuClick(ActionEvent actionEvent) throws SQLException {
//        goKiemtraDauVaoLayout();
//    }

    public void getBaiHocByThuTu(int thuTu, String maChuong, String maKhoaHoc, KhoaHoc khoaHoc) throws SQLException {
        maKhoaHocfinal = maKhoaHoc;
        maChuongfinal = maChuong;
        khoaHocf = khoaHoc;
        if (btnclick != null) {
            changeColorButtonOnclick(btnclick);
        }


        outputArea.clear();
        //checkSuubmit();
        getSoLuongBaiHoc(maChuong, maKhoaHoc);
        baiHoc = baiHocDAO.getBaiHocByThuTu(thuTu, maKhoaHoc);

        List<TienDo> tienDos = tienDoDAO.getTienDoByMaTKMaKhoaHoc(UserSession.getInstance().getUsername(), baiHoc.getMaKhoaHoc());
        danhSachTrangThai.clear();
        for (TienDo tienDo : tienDos) {
            Map<String, Integer> capThuTuTrangThai = new HashMap<>();
            capThuTuTrangThai.put("ThuTu", tienDo.getThuTu());
            capThuTuTrangThai.put("TrangThai", tienDo.getTrangThai());
            danhSachTrangThai.add(capThuTuTrangThai);
        }

        String tenBH = baiHoc.getTenBaiHoc();
        String noiDung = baiHoc.getNoiDung();
        if (tenBH.length() > 50) {
            tenBH = tenBH.substring(0, 50) + "...";
        }
        lblTenBaiHoc.setText(tenBH);
        WebEngine webEngine = wViewBaiHoc.getEngine();
        webEngine.loadContent(noiDung);

        String mucDo = baiHoc.getMucDo();
        int GHKT = baiHoc.getGioiHanKyTu();
        lblGHKT.setText("Giới hạn ký tự: " + GHKT);

        String codeMau = baiHoc.getCodeMau();
        codeArea.replaceText(codeMau);
        System.out.println(codeMau);

        switch (mucDo) {
            case "Dễ":
                btnMucDo.setStyle("-fx-background-color: #59ea25  ;  -fx-text-fill: #000000 ;");
                btnMucDo.setText("Đơn giản");
                break;
            case "Khó":
                btnMucDo.setStyle("-fx-background-color: #FF0000   ;  -fx-text-fill: #FFFFFF  ;");
                btnMucDo.setText("Khó");
                break;
            case "Vừa":
                btnMucDo.setStyle("-fx-background-color: #FFA500   ;  -fx-text-fill: #000000  ;");
                btnMucDo.setText("Vừa");
                break;
            default:
                break;
        }
    }

    public void getSoLuongBaiHoc(String maChuong, String maKhoaHoc) throws SQLException {


        List<Integer> thuTu = new ArrayList<>();


        hBox.getChildren().clear();
        hBox.setStyle("-fx-spacing: 10px;");

        thuTu = baiHocDAO.getThuTuByMaChuong(maChuong, maKhoaHoc);

        for (int i = thuTu.get(0) ; i <= thuTu.get(thuTu.size() - 1); i++) {


            //int count = thuTu.get(i);
            Button button = new Button();
            button.setText(String.valueOf(i));
            button.setPrefSize(35, 30);
            for (Map<String, Integer> capTrangThai : danhSachTrangThai) {
                int thuTu1 = capTrangThai.get("ThuTu");
                int trangThai = capTrangThai.get("TrangThai");
                //System.out.println("ThuTu: " + thuTu + ", TrangThai: " + trangThai);
                if (i == thuTu1 ) {
                    if (trangThai == 1) {
                        changeColorButtonSuccess(button);
                    } else {
                        button.setStyle("-fx-background-color: transparent ; -fx-border-color: black;-fx-border-radius: 5;");
                    }
                }

            }
            button.setOnAction(event -> {
                // Xác định button được click
                Button clickedButton = (Button) event.getSource();
                int clickedValue = Integer.parseInt(clickedButton.getText());
                try {
                    handleButtonClick(event);
                    getBaiHocByThuTu(clickedValue, maChuong, maKhoaHoc, khoaHocf);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            button.setOnMouseClicked(event -> {
                btnclick = button;
            });

            hBox.getChildren().addAll(button, new Region());

        }
    }

//    public void goKiemtraDauVaoLayout() {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/FXML/KiemTraDauVao.fxml"));
//            root.getStyleClass().add("background");
//            Scene scene = new Scene(root, 1000, 600);
//            // Kết nối tệp CSS với scene
//            String pathToStyle = "/CSS/styles_baihoc.css";
//            scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());
//            Stage stage = (Stage) btnKiemThu1.getScene().getWindow();
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void loadBaiHocByMaBHMaChuong(String maBH, String maChuong, String MaKhoaHoc) throws SQLException {
//        setUpCodeArea();
//        pythonExecutor = new PythonExecutor(codeArea, outputArea);
//        getSoLuongBaiHoc(maChuong, MaKhoaHoc);
//        BaiHoc baiHoc = baiHocDAO.getBaiHocByMaBHMaChuong(maBH, maChuong, MaKhoaHoc);
//
//        String tenBH = baiHoc.getTenBaiHoc();
//        String noiDung = baiHoc.getNoiDung();
//        if (tenBH.length() > 50) {
//            tenBH = tenBH.substring(0, 50) + "...";
//        }
//        lblTenBaiHoc.setText(tenBH);
//
//        WebEngine webEngine = wViewBaiHoc.getEngine();
//        webEngine.loadContent(noiDung);
//
//        String mucDo = baiHoc.getMucDo();
//        int GHKT = baiHoc.getGioiHanKyTu();
//        lblGHKT.setText("Giới hạn ký tự: " + GHKT);
//        String codeMau = baiHoc.getCodeMau();
//
//        codeArea.replaceText(codeMau);
//
//        switch (mucDo) {
//            case "Dễ":
//                btnMucDo.setStyle("-fx-background-color: green;");
//                btnMucDo.setText("Dễ");
//                break;
//            case "Khó":
//                btnMucDo.setStyle("-fx-background-color: red;");
//                btnMucDo.setText("Khó");
//                break;
//            case "Vừa":
//                btnMucDo.setStyle("-fx-background-color: blue;");
//                btnMucDo.setText("Vừa");
//                break;
//            default:
//
//                break;
//        }
//    }
//

    public void onClickChayThu(ActionEvent actionEvent) {

        KiemThuDAO kiemThuDAO = new KiemThuDAO();
        List<KiemThu> kiemThus = kiemThuDAO.getKiemThuByMaKHThuTu(baiHoc.getThuTu(), baiHoc.getMaKhoaHoc());
        slKiemThu = kiemThus.size();
        for (KiemThu kiemThu : kiemThus) {
            String i = "0";
            if (kiemThu.getInput() != null) {
                i = kiemThu.getInput();
            }
            String testcse = "";
            String output_str = pythonExecutor.executeCode(testcse);
            String input_str = "Đầu vào: " + i;
            String actual_output_str = "\nĐầu ra thực tế: " + output_str;
            String desired_output_str = "\nĐầu ra mong muốn: " + kiemThu.getOutput();
            String descript = "";

            String outputWithoutSpaces = output_str.replaceAll("\\s", "");
            String kiemThuOutputWithoutSpaces = kiemThu.getOutput().replaceAll("\\s", "");

            if (outputWithoutSpaces.equals(kiemThuOutputWithoutSpaces)) {
                descript = "Kết quả đúng";
                slKiemThu--;
            } else {
                descript = "Kết quả sai";
            }
            String des = "\nMô tả: " + descript;

            String res = input_str + actual_output_str + desired_output_str + des + "\n";


            outputArea.clear();
            outputArea.appendText(res);
            checkSuubmit();
        }

    }

    public void checkSuubmit() {
        if (slKiemThu == 0) {
            btnNopBai.setDisable(false);
        } else {
            btnNopBai.setDisable(true);
        }

    }
//    @FXML
//    public void goChuongBaiHoc() throws SQLException, IOException {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/FXML/chuong.fxml"));
//            root.getStyleClass().add("background");
//            Scene scene = new Scene(root, 1000, 600);
//            // Kết nối tệp CSS với scene
//            String pathToStyle = "/CSS/chuongCss.css";
//            String resPath = "/FXML/chuong.fxml";
//            scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());
//            Stage stage = (Stage) btnBack.getScene().getWindow();
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void onclickNopBai(ActionEvent actionEvent) {
        TienDoDAO tienDoDAO = new TienDoDAO();
        TienDo tienDo = tienDoDAO.getTienDoByThuTu(UserSession.getInstance().getUsername(), baiHoc.getMaKhoaHoc(), baiHoc.getThuTu());

        tienDo.setTrangThai(1);

        tienDoDAO.update(tienDo);

        showAlert("Nộp bài thành công! \n Chuyển sang bài mới");
    }

    public void onKiemThuClick(ActionEvent actionEvent) {
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION, WARNING, ERROR as needed
        alert.setTitle("Thông báo");
        alert.setHeaderText(null); // Optional header text
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    btnNopBai.setDisable(true);

                    getBaiHocByThuTu(baiHoc.getThuTu() + 1, baiHoc.getMaChuong(), baiHoc.getMaKhoaHoc(), khoaHocf);
                    getSoLuongBaiHoc(baiHoc.getMaChuong(), baiHoc.getMaKhoaHoc());

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ;
            }
        }); // Display the alert and wait for user interaction
    }

    public void changeColorButtonSuccess(Button btn) {
        btn.setStyle("-fx-background-color: #59ea25  ;-fx-border-color: black;-fx-border-radius: 5;  -fx-text-fill: #000000 ;");
    }

    public void changeColorButtonOnclick(Button btn) {
        btn.setStyle("-fx-background-color: #486af1  ;-fx-border-color: black;-fx-border-radius: 5;  -fx-text-fill: #ffffff ;");
    }
    public void changeColorDefaultButton(Button btn) {
        btn.setStyle("-fx-background-color: transparent ; -fx-border-color: black;-fx-border-radius: 5;");
    }

    private Button lastClickedButton;

    // Hàm này được gọi khi một nút Button được click
    public void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Kiểm tra xem đã có nút Button được click trước đó hay chưa
        if (lastClickedButton != null) {
            // Nếu đã có, đổi màu của nút Button trước đó về màu mặc định
            changeColorDefaultButton(lastClickedButton);
        }

        // Thiết lập màu cho nút Button mới được click
        changeColorButtonOnclick(clickedButton);

        // Lưu trạng thái của nút Button mới được click
        lastClickedButton = clickedButton;
    }

}
