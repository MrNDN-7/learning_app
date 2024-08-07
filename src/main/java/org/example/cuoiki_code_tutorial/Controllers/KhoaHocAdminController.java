package org.example.cuoiki_code_tutorial.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.BaiHocDAO;
import org.example.cuoiki_code_tutorial.DAOv2.ChuongDAO;
import org.example.cuoiki_code_tutorial.DAOv2.QuanLyKhoaHocDAO;
import org.example.cuoiki_code_tutorial.Models.BaiHoc;
import org.example.cuoiki_code_tutorial.Models.Chuong;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class KhoaHocAdminController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Label labelTitle;

    @FXML
    private TextField tenKhoaHocField;

    @FXML
    private ImageView mainImage;

    @FXML
    private HTMLEditor HtmlMoTa;

    @FXML
    private Button btnAnhChinh;

    @FXML
    private Button btnThemAnhMoTa;

    @FXML
    private Button saveButton;

    @FXML
    private Button btnBack;



    private QuanLyKhoaHocDAO khoaHocDAO;
    private ChuongDAO chuongDAO = new ChuongDAO();

    private KhoaHoc newKhoaHoc;
    private boolean isThem = false;
    private String filePath;

    public KhoaHocAdminController() {
        khoaHocDAO = new QuanLyKhoaHocDAO();
    }

    @FXML
    public void initialize() {

        saveButton.setOnAction(event -> {
            saveKhoaHoc();
        });
        btnBack.setOnAction(event -> {
            backToPreviousScene(btnBack);
        });
        btnAnhChinh.setOnAction(event -> {
            chooseImage();
        });
        btnThemAnhMoTa.setOnAction(event -> {
            chooseImageForMoTa();
        });
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
            chuong.setMaKH(newKhoaHoc.getMaKH());
            // Lưu chương vào cơ sở dữ liệu
            chuongDAO.insert(chuong);
            // Thêm giao diện cho tab mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/chinh_sua_bai_hoc.fxml"));
            Parent root;
            try {
                root = loader.load();
                ChinhSuaBaiHocController controller = loader.getController();
                controller.setThem(true, new Chuong(), new KhoaHoc());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        vBox.getChildren().addAll(label, tenChuongField, addButton);
        tab.setContent(vBox);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
    private void backToPreviousScene(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/trang_chu_tac_gia.fxml"));
            Parent root = loader.load();

            // Truyền dữ liệu nếu cần
            TrangChuTacGiaController controller = loader.getController();
            //controller.setData(data);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void EditKhoaHoc(KhoaHoc khoaHoc){
        this.mainImage.setImage(new Image(khoaHoc.getHinhAnh()));
        this.HtmlMoTa.setHtmlText(khoaHoc.getMoTa());
        this.tenKhoaHocField.setText(khoaHoc.getTenKH());
        loadChuong(khoaHoc);
    }
    private void chooseImageForMoTa() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn Hình Ảnh");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                // Thư mục lưu trữ hình ảnh
                String imageDirectory = "src/main/resources/Store/";
                int maxCount = getMaxFileCount(imageDirectory);
                String imageName = "image" + (maxCount + 1) + getFileExtension(selectedFile.getName());
                // Copy hình ảnh vào thư mục lưu trữ
                Path targetPath = new File(imageDirectory + imageName).toPath();
                Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                // Thêm hình ảnh vào trình chỉnh sửa HTML
                String imagePath = targetPath.toUri().toString();
                HtmlMoTa.setHtmlText(HtmlMoTa.getHtmlText() + "<img src=\"" + imagePath + "\">");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn Ảnh");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                Image image = new Image(new FileInputStream(file));

                if (file != null) {
                    mainImage.setImage(image);
                    // Thư mục lưu trữ hình ảnh
                    String imageDirectory = "src/main/resources/Store/";
                    int maxCount = getMaxFileCount(imageDirectory);
                    String imageName = "image" + (maxCount + 1) + getFileExtension(file.getName());
                    // Copy hình ảnh vào thư mục lưu trữ
                    Path targetPath = new File(imageDirectory + imageName).toPath();
                    filePath = targetPath.toString();
                    // Thêm hình ảnh vào trình chỉnh sửa HTML

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveKhoaHoc() {
        String tenKhoaHoc = tenKhoaHocField.getText();
        String moTa = HtmlMoTa.getHtmlText();
        // You may need to get the image path from mainImage and process it accordingly

        // Create a new KhoaHoc object
        newKhoaHoc = new KhoaHoc();
        newKhoaHoc.setTenKH(tenKhoaHoc);
        newKhoaHoc.setMoTa(moTa);
        newKhoaHoc.setHinhAnh(filePath);
        LocalDate ngayHienTai = LocalDate.now();

        // Định dạng ngày theo chuẩn "yyyy-MM-đ"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String ngayTaoFormatted = ngayHienTai.format(formatter);
        newKhoaHoc.setNgayTao(LocalDate.parse(ngayTaoFormatted));

        // Save or update KhoaHoc object using KhoaHocService
        if(isThem)
            khoaHocDAO.insert(newKhoaHoc);
        else
            khoaHocDAO.update(newKhoaHoc);
        backToPreviousScene(btnBack);
        // After saving, you may want to clear the fields or show a success message
        clearFields();
    }

    private void clearFields() {
        tenKhoaHocField.clear();
        HtmlMoTa.setHtmlText("");
        mainImage.setImage(new Image("file: src/main/resources/Store/memeAvt.jpg"));
        // Clear other fields as needed
    }
    public void setIsThem(boolean isThem, KhoaHoc newKhoaHoc) {
        this.isThem = isThem;
        this.newKhoaHoc = newKhoaHoc;
        if(!isThem)
            EditKhoaHoc(newKhoaHoc);
    }
    private int getMaxFileCount(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        int maxCount = 0;
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.startsWith("image")) {
                        int count = Integer.parseInt(fileName.substring(5, fileName.lastIndexOf('.')));
                        if (count > maxCount) {
                            maxCount = count;
                        }
                    }
                }
            }
        }
        return maxCount;
    }

    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex != -1 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex);
        }
        return "";
    }
    private void loadChuong(KhoaHoc khoaHoc) {
        List<Chuong> chuongList = chuongDAO.selectAll();
        List<Chuong> chuongList1 = new ArrayList<>();
        for (Chuong chuong : chuongList) {
            if(chuong.getMaKH().equals(khoaHoc.getMaKH())){
                chuongList1.add(chuong);
            }
        }

        for (Chuong chuong : chuongList1) {
            ScrollPane scrollPane = new ScrollPane();
            Tab tab = new Tab(chuong.getTenChuong());
            tab.setClosable(false);
            BaiHocDAO baiHocDao = new BaiHocDAO();
            List<BaiHoc> baiHocList = baiHocDao.selectAll();
            List<BaiHoc> baiHocList1 = new ArrayList<>();
            for (BaiHoc baiHoc : baiHocList) {
                if(baiHoc.getMaKhoaHoc().equals(khoaHoc.getMaKH()) && baiHoc.getMaChuong().equals(chuong.getMaChuong())){
                    baiHocList1.add(baiHoc);
                }
            }
            VBox vBoxCacBaiHoc = new VBox();
            vBoxCacBaiHoc.setAlignment(Pos.TOP_CENTER);
            vBoxCacBaiHoc.setPadding(new Insets(10, 10, 10, 10));
            vBoxCacBaiHoc.setSpacing(20);
            for (BaiHoc bai : baiHocList1) {
                Button btn = new Button("Bài " + bai.getThuTu() + ": " +bai.getTenBaiHoc());
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/chinh_sua_bai_hoc.fxml"));
                        Parent root;
                        try {
                            root = loader.load();
                            ChinhSuaBaiHocController controller = loader.getController();
                            controller.setBaiHoc(bai);
                            controller.setThem(false, chuong, khoaHoc);
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
                vBoxCacBaiHoc.getChildren().add(btn);
            }
            // Thêm button "+" vào cuối danh sách bài học của chương
            Button btnPlus = new Button("+");
            btnPlus.setPrefWidth(1000);
            btnPlus.setPrefHeight(80);
            btnPlus.setStyle("-fx-font-size: 14px;");
            btnPlus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    themBaiHoc(chuong, khoaHoc);
                }
            });
            vBoxCacBaiHoc.getChildren().add(btnPlus);

            scrollPane.setContent(vBoxCacBaiHoc);
            tab.setContent(scrollPane);
            tabPane.getTabs().add(tab);
        }
    }

    private void themBaiHoc(Chuong chuong, KhoaHoc khoaHoc) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/chinh_sua_bai_hoc.fxml"));
        Parent root;
        try {
            root = loader.load();
            ChinhSuaBaiHocController controller = loader.getController();
            controller.setThem(true, chuong, khoaHoc);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // You may need to add other methods for handling image selection, navigation, etc.
}
