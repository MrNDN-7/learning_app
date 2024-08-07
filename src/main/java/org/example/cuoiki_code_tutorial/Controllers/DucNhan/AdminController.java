package org.example.cuoiki_code_tutorial.Controllers.DucNhan;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.Dao.DucNhan.HocVienDAO;
import org.example.cuoiki_code_tutorial.Dao.DucNhan.TaiKhoanDAO;
import org.example.cuoiki_code_tutorial.Models.HocVien;
import org.example.cuoiki_code_tutorial.Models.TaiKhoan;
import org.example.cuoiki_code_tutorial.Utils.Session;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Button btnCapNhatHocVien, btnThemAnhHocVien, btnThemHocVien, btnXoaHocVien,btnXoaDuLieu;

    @FXML
    private ComboBox<String>  checkGioiTinhHocVien, checkTrangThaiHocVien;

    @FXML
    private TableColumn<HocVien, String> columnDiaChi, columnEmail, columnGioiTinh, columnMaHV, columnMaTK,
            columnSDT, columnTen, columnTrangThai;
    @FXML
    private TableColumn<HocVien, Date> columnNgaySinh;
    @FXML
    private ImageView  imageHocVien;

    @FXML
    private Label labelTen, lblBackHome;

    @FXML
    private TableView<HocVien> tableHocVien;

    @FXML
    private TextField  txtDiaChiHocVien,  txtEmailHocVien, txtSDTHocVien, txtTenHocVien;

    @FXML
    private DatePicker txtNgaySinhHocVien;

    @FXML
    private AnchorPane paneAccount, paneAddStudent, paneAdmin, paneAddLesson, paneHome;

    @FXML
    private Button btnAccount, btnAddLesson, btnAddStudent, btnHome;


    @FXML
    private Button btnLocDanhSach;
    @FXML
    private Button btnXemToanBoDanhSach;
    @FXML
    private ImageView imageDangXuat;

    private ObservableList<HocVien> listhocvien;
    private HocVienDAO hocVienDao;
    private Image imagee;


    // Account
    @FXML
    private Button btnTaiKhoanHoatDong;

    @FXML
    private Button btnTaiKhoanNgungHoatDong;

    @FXML
    private TableColumn<TaiKhoan, String> columnMaTKAccount,columnMatKhau,columnTenDangNhap,columnTrangThaiTaiKhoan;

    @FXML
    private TableView<TaiKhoan> tableTaiKhoan;
    @FXML
    private Button btnCapNhatTaiKhoan,btnClearDuLieuTaiKhoan;
    @FXML
    private Label labelMaTK;
    @FXML
    private  TextField txtTenDangNhap,txtMatKhau;
    @FXML
    private ComboBox<String> checkTrangThaiTaiKhoan;

    //HV->Acocunt
    @FXML
    private AnchorPane paneChangeAccount;
    @FXML
    private  Label labelMaTKHocVien;
    @FXML
    private TextField txtTenDangNhapHocVien,txtMatKhauHocVien;
    @FXML
    private ComboBox<String>checkTrangThaiTaiKhoanHocVien;
    @FXML
    private Button btnCapNhatTaiKhoanHocVien,btnClearDuLieuTaiKhoanHocVien,btnMoveTaiKhoan,btnBackTaiKhoan;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hocVienDao = new HocVienDAO();
        listhocvien = FXCollections.observableArrayList();
        loadData();
        setCellValueFactory();
        loadTaiKhoanAll();
        setCellValueFactoryTaiKhoan();


        lblBackHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage1 = (Stage) lblBackHome.getScene().getWindow();
                stage1.close();
                // Load file FXML cho giao diện BaiHocController
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/trang_chu_tac_gia.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Lấy instance của BaiHocController


                // Tạo Scene mới và hiển thị giao diện BaiHocController
                Scene scene = new Scene(root, 1000, 600);
                Stage stage = new Stage();
                String pathToStyle = "/CSS/styles_userhome.css";
                scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());
                stage.setScene(scene);
                stage.show();
            }
        });

        tableHocVien.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HocVien>() {
            @Override
            public void changed(ObservableValue<? extends HocVien> observableValue, HocVien oldHocVien, HocVien newHocVien) {
                if (newHocVien != null) {
                    // Thiết lập nội dung cho các trường dữ liệu
                    txtTenHocVien.setText(newHocVien.getTenHV());
                    txtEmailHocVien.setText(newHocVien.getEmail());
                    txtSDTHocVien.setText(newHocVien.getSoDienThoai());
                    txtDiaChiHocVien.setText(newHocVien.getDiaChi());

                    // Thiết lập giới tính
                    checkGioiTinhHocVien.setValue(newHocVien.getGioiTinh() ? "Nam" : "Nữ");

                    java.sql.Date ngaySinhSQL = (java.sql.Date) newHocVien.getNgaySinh();
                    if (ngaySinhSQL != null) {
                        LocalDate ngaySinhLocalDate = ngaySinhSQL.toLocalDate();
                        txtNgaySinhHocVien.setValue(ngaySinhLocalDate);
                    }
                    // Thiết lập trạng thái
                    checkTrangThaiHocVien.setValue(newHocVien.getTrangThai() ? "Hoạt động" : "Không hoạt động");
                    String uri="file:" +newHocVien.getAnhDaiDien();
                    imagee=new Image(uri,207,193,false,true);
                    imageHocVien.setImage(imagee);
                }

            }
        });
        tableTaiKhoan.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TaiKhoan>() {
            @Override
            public void changed(ObservableValue<? extends TaiKhoan> observable, TaiKhoan oldValue, TaiKhoan newValue) {
                if (newValue != null) {
                    // Thiết lập giá trị của các trường dữ liệu
                    labelMaTK.setText(newValue.getMaTK());
                    txtTenDangNhap.setText(newValue.getTenDangNhap());
                    txtMatKhau.setText(newValue.getMatKhau());

                    checkTrangThaiTaiKhoan.setValue(newValue.isTrangThai() ? "Hoạt động" : "Không hoạt động");
                }
            }
        });

        // Thêm tùy chọn cho ComboBox Giới tính
        ObservableList<String> gioiTinhOptions = FXCollections.observableArrayList("Nam", "Nữ");
        checkGioiTinhHocVien.setItems(gioiTinhOptions);

        // Thêm tùy chọn cho ComboBox Trạng thái
        ObservableList<String> trangThaiOptions = FXCollections.observableArrayList("Hoạt động", "Không hoạt động");
        checkTrangThaiHocVien.setItems(trangThaiOptions);
        // Thêm tùy chọn cho ComboBox Trạng thái
        ObservableList<String> trangThaiOptionsTaiKhoan = FXCollections.observableArrayList("Hoạt động", "Không hoạt động");
        checkTrangThaiTaiKhoan.setItems(trangThaiOptionsTaiKhoan);
        // Thêm tùy chọn cho ComboBox Trạng thái
        ObservableList<String> trangThaiOptionsTaiKhoanHocVien = FXCollections.observableArrayList("Hoạt động", "Không hoạt động");
        checkTrangThaiTaiKhoanHocVien.setItems(trangThaiOptionsTaiKhoanHocVien);

        btnThemAnhHocVien.setOnAction(ActionEvent->addImageHocVien());
        btnThemHocVien.setOnAction(ActionEvent->themHocVien());
        btnXoaHocVien.setOnAction(ActionEvent->xoaHocVien());
        btnCapNhatHocVien.setOnAction(ActionEvent->capNhatHocVien());
        btnXoaDuLieu.setOnAction(ActionEvent->xoaDuLieuNhap());
        btnLocDanhSach.setOnAction(ActionEvent->loadData());
        btnXemToanBoDanhSach.setOnAction(ActionEvent->loadDataAll());
        imageDangXuat.setOnMouseClicked(ActionEvent->DangXuat());
        btnCapNhatTaiKhoan.setOnAction(ActionEvent->CapNhatTaiKhoan());
        btnClearDuLieuTaiKhoan.setOnAction(ActionEvent->XoaDuLieuNhapTaiKhoan());
        btnTaiKhoanNgungHoatDong.setOnAction(ActionEvent->loadTaiKhoanKhongHoatDong());
        btnTaiKhoanHoatDong.setOnAction(ActionEvent->loadTaiKhoanAll());
        btnMoveTaiKhoan.setOnAction(ActionEvent->MoveToTaiKhoan());
        btnBackTaiKhoan.setOnAction(ActionEvent->BackTaiKhoan());

    }

    public void MoveToTaiKhoan()
    {
        paneChangeAccount.setVisible(true);
    }
    public void BackTaiKhoan()
    {
        paneChangeAccount.setVisible(false);
    }



    public void loadTaiKhoanAll() {
        tableTaiKhoan.getItems().clear();
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
        List<TaiKhoan> taiKhoanList = taiKhoanDAO.getAllTaiKhoan();
        tableTaiKhoan.getItems().addAll(taiKhoanList);
    }

    public void loadTaiKhoanKhongHoatDong() {
        tableTaiKhoan.getItems().clear();
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
        List<TaiKhoan> taiKhoanList = taiKhoanDAO.getAllTaiKhoanKhongHoatDong();
        tableTaiKhoan.getItems().addAll(taiKhoanList);
    }
    private void setCellValueFactoryTaiKhoan() {
        columnMaTKAccount.setCellValueFactory(new PropertyValueFactory<>("MaTK"));
        columnTenDangNhap.setCellValueFactory(new PropertyValueFactory<>("TenDangNhap"));
        columnMatKhau.setCellValueFactory(new PropertyValueFactory<>("MatKhau"));
        columnTrangThaiTaiKhoan.setCellValueFactory(cellData -> {
            boolean trangThai = cellData.getValue().isTrangThai();
            String trangThaiStr = trangThai ? "Hoạt động" : "Ngừng hoạt động";
            return new SimpleStringProperty(trangThaiStr);
        });
    }
    public void XoaDuLieuNhapTaiKhoan()
    {
        labelMaTK.setText(null);
        txtTenDangNhap.clear();
        txtMatKhau.clear();
        checkTrangThaiTaiKhoan.getSelectionModel().clearSelection();
    }
    public void CapNhatTaiKhoan() {
        TaiKhoan selectedTaiKhoan = tableTaiKhoan.getSelectionModel().getSelectedItem();

        if (selectedTaiKhoan == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn tài khoản cần cập nhật.");
            alert.showAndWait();
            return;
        }

        String tenDangNhap = txtTenDangNhap.getText();
        String matKhau = txtMatKhau.getText();
        String trangThaiStr = checkTrangThaiTaiKhoan.getValue();

        if (tenDangNhap.isEmpty() || matKhau.isEmpty() || trangThaiStr == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }

        boolean trangThai = trangThaiStr.equals("Hoạt động");

        selectedTaiKhoan.setTenDangNhap(tenDangNhap);
        selectedTaiKhoan.setMatKhau(matKhau);
        selectedTaiKhoan.setTrangThai(trangThai);

        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
        boolean updated = taiKhoanDAO.updateTaiKhoan(selectedTaiKhoan);

        if (updated) {
            // Hiển thị thông báo thành công nếu cập nhật thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật tài khoản thành công.");
            alert.showAndWait();
            // Làm mới dữ liệu trên bảng sau khi cập nhật thành công
            loadTaiKhoanAll();
            XoaDuLieuNhapTaiKhoan();
        } else {
            // Hiển thị thông báo lỗi nếu cập nhật không thành công
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã xảy ra lỗi khi cập nhật tài khoản.");
            alert.showAndWait();
        }
    }


    public void DangXuat()
    {
        Session.getInstance().setLoggedInUsername(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) imageDangXuat.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void xoaDuLieuNhap()
    {
        txtTenHocVien.clear();
        txtEmailHocVien.clear();
        txtSDTHocVien.clear();
        txtDiaChiHocVien.clear();
        checkGioiTinhHocVien.getSelectionModel().clearSelection();
        txtNgaySinhHocVien.setValue(null);
        checkTrangThaiHocVien.getSelectionModel().clearSelection();
        imageHocVien.setImage(null); // Xóa hình ảnh
    }
    public void themHocVien() {
        // Lấy dữ liệu từ các trường nhập liệu trên giao diện
        String tenHV = txtTenHocVien.getText();
        String email = txtEmailHocVien.getText();
        String soDienThoai = txtSDTHocVien.getText();
        String diaChi = txtDiaChiHocVien.getText();
        LocalDate ngaySinh = txtNgaySinhHocVien.getValue();
        String gioiTinhStr = checkGioiTinhHocVien.getValue(); // Lấy giá trị giới tính từ ComboBox
        String trangThaiStr = checkTrangThaiHocVien.getValue(); // Lấy giá trị trạng thái từ ComboBox
        String anhDaiDien = Session.path;

        // Kiểm tra các trường bắt buộc không được để trống
        if (tenHV.isEmpty() || email.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty() || ngaySinh == null || gioiTinhStr == null || trangThaiStr == null) {
            // Hiển thị thông báo lỗi nếu các trường bắt buộc không được điền đầy đủ
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }

        // Chuyển đổi giới tính từ chuỗi sang boolean
        boolean gioiTinh = gioiTinhStr.equals("Nam");
        // Chuyển đổi trạng thái từ chuỗi sang boolean
        boolean trangThai = trangThaiStr.equals("Hoạt động");
        java.sql.Date ngaySinhSQL = java.sql.Date.valueOf(ngaySinh);

        // Tạo đối tượng HocVien từ dữ liệu đã lấy được
        HocVien hocVien = new HocVien(tenHV, email, soDienThoai, diaChi,ngaySinhSQL, gioiTinh, anhDaiDien, trangThai);

        // Thực hiện thêm Học viên vào cơ sở dữ liệu
        boolean inserted = hocVienDao.insertHocVien(hocVien);

        if (inserted) {
            // Hiển thị thông báo thành công nếu thêm thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Thêm học viên thành công.");
            alert.showAndWait();
            // Làm mới dữ liệu trên bảng sau khi thêm thành công
            loadData();
            xoaDuLieuNhap();
        } else {
            // Hiển thị thông báo lỗi nếu thêm không thành công
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã xảy ra lỗi khi thêm học viên.");
            alert.showAndWait();
        }
    }


    public void capNhatHocVien() {
        // Lấy học viên được chọn từ bảng
        HocVien selectedHocVien = tableHocVien.getSelectionModel().getSelectedItem();
        String mahocvien=selectedHocVien.getMaHV();
        // Kiểm tra xem học viên có được chọn không
        if (selectedHocVien == null) {
            // Hiển thị thông báo lỗi nếu không có học viên được chọn
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn học viên cần cập nhật.");
            alert.showAndWait();
            return;
        }
        String tenHV = txtTenHocVien.getText();
        String email = txtEmailHocVien.getText();
        String soDienThoai = txtSDTHocVien.getText();
        String diaChi = txtDiaChiHocVien.getText();
        LocalDate ngaySinh = txtNgaySinhHocVien.getValue();
        String gioiTinhStr = checkGioiTinhHocVien.getValue();
        String trangThaiStr = checkTrangThaiHocVien.getValue();
        String anhDaiDien = Session.path;

        if (tenHV.isEmpty() || email.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty() || ngaySinh == null || gioiTinhStr == null || trangThaiStr == null) {
            // Hiển thị thông báo lỗi nếu các trường bắt buộc không được điền đầy đủ
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin.");
            alert.showAndWait();
            return;
        }

        boolean gioiTinh = gioiTinhStr.equals("Nam");
        boolean trangThai = trangThaiStr.equals("Hoạt động");
        java.sql.Date ngaySinhSQL = java.sql.Date.valueOf(ngaySinh);

        HocVien updatedHocVien = new HocVien( tenHV, email, soDienThoai, diaChi, ngaySinhSQL, gioiTinh, anhDaiDien, trangThai);

        boolean updated = hocVienDao.updateHocVienByMaHV(mahocvien,updatedHocVien);

        if (updated) {
            // Hiển thị thông báo thành công nếu cập nhật thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật học viên thành công.");
            alert.showAndWait();
            // Làm mới dữ liệu trên bảng sau khi cập nhật thành công
            loadData();
            xoaDuLieuNhap();
        } else {
            // Hiển thị thông báo lỗi nếu cập nhật không thành công
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Đã xảy ra lỗi khi cập nhật học viên.");
            alert.showAndWait();
        }
    }

    public void xoaHocVien() {
        HocVien selectedHocVien = tableHocVien.getSelectionModel().getSelectedItem();

        if (selectedHocVien == null) {
            // Hiển thị thông báo lỗi nếu không có học viên được chọn
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn học viên cần xóa.");
            alert.showAndWait();
            return;
        }

        // Lấy mã học viên
        String maHocVien = selectedHocVien.getMaHV();

        // Hiển thị cảnh báo xác nhận xóa học viên
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Xác nhận xóa");
        confirmationAlert.setHeaderText("Bạn có chắc chắn muốn xóa học viên này?");
        confirmationAlert.setContentText("Hành động này sẽ đưa học viên vào trạng thái không hoạt động.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean updated = hocVienDao.updateTrangThaiHocVien(maHocVien);

            if (updated) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Xóa học viên thành công.");
                alert.showAndWait();
                // Làm mới dữ liệu trên bảng sau khi xóa thành công
                loadData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đã xảy ra lỗi khi xóa học viên.");
                alert.showAndWait();
            }
        }
    }

    public void addImageHocVien() {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = open.showOpenDialog(paneAddStudent.getScene().getWindow());
        if (file != null) {
            Session.path = file.getAbsolutePath();
            imagee = new Image(file.toURI().toString(), 207, 193, false, true);
            imageHocVien.setImage(imagee);
            // Đóng luồng khi không cần thiết
            open = null;
        }
//        FileChooser open = new FileChooser();
//        File file = open.showOpenDialog(paneUser.getScene().getWindow());
//
//        if (file != null) {
//            Session.path = file.getAbsolutePath();
//
//            imagee = new Image(file.toURI().toString(), 207,193,false,true);
//            image.setImage(imagee);
//        }
    }

    public void loadData() {
        listhocvien.clear();
        List<HocVien> hocvien = HocVienDAO.selectAllHocVienHoatDong();
        listhocvien.addAll(hocvien);
        tableHocVien.setItems(listhocvien);
    }
    public void loadDataAll() {
        listhocvien.clear();
        List<HocVien> hocvien = HocVienDAO.selectAllHocVien();
        listhocvien.addAll(hocvien);
        tableHocVien.setItems(listhocvien);
    }

    private void setCellValueFactory() {
        columnMaHV.setCellValueFactory(new PropertyValueFactory<>("MaHV"));
        columnTen.setCellValueFactory(new PropertyValueFactory<>("TenHV"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        columnSDT.setCellValueFactory(new PropertyValueFactory<>("SoDienThoai"));
        columnDiaChi.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));
        columnMaTK.setCellValueFactory(new PropertyValueFactory<>("MaTK"));
        columnNgaySinh.setCellValueFactory(new PropertyValueFactory<>("NgaySinh"));

        // Sử dụng Callback để hiển thị Giới tính và Trạng thái dưới dạng chuỗi
        columnGioiTinh.setCellValueFactory(param -> {
            String gioiTinh = param.getValue().getGioiTinh() ? "Nam" : "Nữ";
            return new SimpleStringProperty(gioiTinh);
        });
        columnTrangThai.setCellValueFactory(param -> {
            String trangThai = param.getValue().getTrangThai() ? "Hoạt động" : "Không hoạt động";
            return new SimpleStringProperty(trangThai);
        });
    }

    @FXML
    private void switchForm(ActionEvent event) {
        paneAccount.setVisible(event.getSource() == btnAccount);
        paneAddStudent.setVisible(event.getSource() == btnAddStudent);
        paneHome.setVisible(event.getSource() == btnHome);
        paneAddLesson.setVisible(event.getSource() == btnAddLesson);
    }
}
