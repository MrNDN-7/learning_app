package org.example.cuoiki_code_tutorial.Controllers.DucNhan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.example.cuoiki_code_tutorial.Controllers.UserHomeController;
import org.example.cuoiki_code_tutorial.Dao.DucNhan.HocVienDAO;
import org.example.cuoiki_code_tutorial.Models.HocVien;
import org.example.cuoiki_code_tutorial.Utils.Session;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.scene.image.Image;



public class HocVienController implements Initializable {

    @FXML
    private Button btnAccount;
    @FXML
    private Button btnHome;
    @FXML
    private AnchorPane paneAccount;
    @FXML
    private AnchorPane paneHome;
    @FXML
    private AnchorPane paneUser;
    @FXML
    private AnchorPane panelChangePass;
    @FXML
    private Label labelTen, lblAccount;
    @FXML
    private Button btnCapNhat;
    @FXML
    private Button btnThemAnh;
    @FXML
    private ComboBox<String> checkGioiTinh;
    @FXML
    private ComboBox<String> checkTrangThai;
    @FXML
    private ImageView image;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker txtNgaySinh;
    @FXML
    private TextField txtSDT;
    @FXML
    private TextField txtTen;
    @FXML
    private Button btnPass;


    @FXML
    private PasswordField txtOldPass;
    @FXML
    private  PasswordField txtNewPass;

    @FXML
    private TextField txtShowPassNew;

    @FXML
    private TextField txtShowPassOld;

    @FXML
    private Button btnChangePass;


    private HocVienDAO hocVienDao;

    @FXML
    private ImageView viewNewPass;

    @FXML
    private ImageView viewOldPass;
    @FXML
    private ImageView hideNewPass;

    @FXML
    private ImageView hideOldPass;


    private Image imagee;
    @FXML
    private AnchorPane paneChangeTenDangNhap;
    @FXML
    private  TextField txtOldTenDangNhap;
    @FXML
    private Button btnThayDoiTenDangNhap,btnDoiTenDangNhap;



    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == btnAccount) {
            paneAccount.setVisible(true);
            paneHome.setVisible(false);
            panelChangePass.setVisible(false);
            paneChangeTenDangNhap.setVisible(false);
            ThongTinHocVien();
        } else if (event.getSource() == btnHome) {
            paneAccount.setVisible(false);
            paneHome.setVisible(true);
            panelChangePass.setVisible(false);
            paneChangeTenDangNhap.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCapNhat.setOnAction(ActionEvent->CapNhatThongTin());

        btnThemAnh.setOnAction(ActionEvent->addImageHocVien());
        btnPass.setOnAction(ActionEvent->ChuyenTrangDoiMatKhau());
        btnChangePass.setOnAction(ActionEvent->DoiMatKhau());
        viewNewPass.setOnMouseClicked(ActionEvent->ViewNewPass());
        viewOldPass.setOnMouseClicked(ActionEvent->ViewOldPass());
        hideNewPass.setOnMouseClicked(ActionEvent->HideNewPass());
        hideOldPass.setOnMouseClicked(ActionEvent->HideOldPass());
        btnDoiTenDangNhap.setOnAction(ActionEvent->ChuyenTrangDoiTenDangNhap());
        btnThayDoiTenDangNhap.setOnAction(ActionEvent->DoiTenDangNhap());

        lblAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    loadUserHome();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void ThongTinHocVien() {
        hocVienDao = new HocVienDAO();
        String loggedInUsername = Session.getInstance().getLoggedInUsername();
        if (loggedInUsername != null) {
            HocVien hocVien = hocVienDao.getInfoHocVien(loggedInUsername);
            if (hocVien != null) {
                txtTen.setText(hocVien.getTenHV());
                txtEmail.setText(hocVien.getEmail());
                txtSDT.setText(hocVien.getSoDienThoai());
                txtDiaChi.setText(hocVien.getDiaChi());
                java.sql.Date ngaySinhSQL = (Date) hocVien.getNgaySinh();
                if (ngaySinhSQL != null) {
                    LocalDate ngaySinhLocalDate = ngaySinhSQL.toLocalDate();
                    txtNgaySinh.setValue(ngaySinhLocalDate);
                }

                ObservableList<String> gioiTinhList = FXCollections.observableArrayList("Nam", "Nữ");
                checkGioiTinh.setItems(gioiTinhList);
                if (hocVien.getGioiTinh()) {
                    checkGioiTinh.setValue("Nam");
                } else {
                    checkGioiTinh.setValue("Nữ");
                }

                ObservableList<String> trangThaiList = FXCollections.observableArrayList("Hoạt động", "Không hoạt động");
                checkTrangThai.setItems(trangThaiList);
                if (hocVien.getTrangThai()) {
                    checkTrangThai.setValue("Hoạt động");
                } else {
                    checkTrangThai.setValue("Không hoạt động");
                }
                String uri="file:" +hocVien.getAnhDaiDien();
                imagee=new Image(uri,207,193,false,true);
                image.setImage(imagee);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy thông tin học viên!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chưa đăng nhập");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng đăng nhập để truy cập thông tin học viên!");
            alert.showAndWait();
        }
    }



    public void CapNhatThongTin() {
        String loggedInUsername = Session.getInstance().getLoggedInUsername();
        String tenHV = txtTen.getText();
        String emailHV = txtEmail.getText();
        String sdt = txtSDT.getText();
        LocalDate ngaySinh = txtNgaySinh.getValue();
        String diaChi = txtDiaChi.getText();

        String gioiTinhStr = checkGioiTinh.getValue();
        String trangThaiStr = checkTrangThai.getValue();

        if (tenHV.isEmpty() || emailHV.isEmpty() || sdt.isEmpty() || ngaySinh == null || diaChi.isEmpty() || gioiTinhStr == null || trangThaiStr == null) {
            // Hiển thị cảnh báo nếu có trường nào đó trống
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.showAndWait();
            return;
        }

        java.sql.Date ngaySinhSQL = java.sql.Date.valueOf(ngaySinh);

        boolean gioiTinh = gioiTinhStr.equals("Nam");

        boolean trangThai = trangThaiStr.equals("Hoạt động");

        String anhDaiDien = Session.path;
        HocVien hocVien = new HocVien(tenHV, emailHV, sdt, diaChi, ngaySinhSQL, gioiTinh, anhDaiDien, trangThai);

        boolean updated = hocVienDao.updateHocVien(loggedInUsername, hocVien);

        if (updated) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật thông tin thành công!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật thông tin không thành công!");
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
        Stage stage = (Stage) txtDiaChi.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addImageHocVien() {
        FileChooser open = new FileChooser();
        open.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = open.showOpenDialog(paneUser.getScene().getWindow());
        if (file != null) {
            Session.path = file.getAbsolutePath();
            imagee = new Image(file.toURI().toString(), 207, 193, false, true);
            image.setImage(imagee);
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

    public void ChuyenTrangDoiMatKhau()
    {
        panelChangePass.setVisible(true);
        paneAccount.setVisible(false);
        paneHome.setVisible(false);
        paneChangeTenDangNhap.setVisible(false);
    }
    public void ChuyenTrangDoiTenDangNhap()
    {
        panelChangePass.setVisible(false);
        paneAccount.setVisible(false);
        paneHome.setVisible(false);
        paneChangeTenDangNhap.setVisible(true);
        txtOldTenDangNhap.setText(Session.getInstance().getLoggedInUsername());
    }
    public void DoiTenDangNhap()
    {
        String tenDangNhapCu = Session.getInstance().getLoggedInUsername();
        String tenDangNhapMoi = txtOldTenDangNhap.getText();
        if (!tenDangNhapMoi.isEmpty()) {
            HocVienDAO hocVienDAO= new HocVienDAO();
            boolean changed = hocVienDAO.updateTenDangNhap(tenDangNhapCu, tenDangNhapMoi);
            if (changed) {
                // Thông báo thành công
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Đổi tên đăng nhập thành công!");
                alert.showAndWait();
                paneChangeTenDangNhap.setVisible(false);
                paneAccount.setVisible(true);
                Session.getInstance().setLoggedInUsername(tenDangNhapMoi);


            } else {
                // Thông báo lỗi
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đổi tên đăng nhập không thành công! Vui lòng thử lại.");
                alert.showAndWait();
            }
        } else {
            // Thông báo nếu trường tên đăng nhập mới trống
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập tên đăng nhập mới!");
            alert.showAndWait();
        }
    }

    private void DoiMatKhau()
    {
        String oldPassword = txtOldPass.getText();
        String newPassword = txtNewPass.getText();


        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
            HocVienDAO hocVienDAO= new HocVienDAO();
            String loggedInUsername = Session.getInstance().getLoggedInUsername();
            boolean changed = hocVienDAO.changePassword(loggedInUsername, oldPassword, newPassword);
            if (changed) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Đổi mật khẩu thành công!");
                alert.showAndWait();
                txtOldPass.clear();
                txtNewPass.clear();
                txtShowPassNew.clear();
                txtShowPassOld.clear();
                viewOldPass.setVisible(true);
                viewNewPass.setVisible(true);
                hideOldPass.setVisible(false);
                hideNewPass.setVisible(false);
                txtShowPassOld.setVisible(false);
                txtShowPassNew.setVisible(false);
                txtNewPass.setVisible(true);
                txtOldPass.setVisible(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đổi mật khẩu không thành công! Vui lòng kiểm tra lại mật khẩu cũ.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.showAndWait();
        }
    }

    public void HideOldPass()
    {
        txtShowPassOld.setText(txtOldPass.getText());
        txtOldPass.setVisible(true);
        viewOldPass.setVisible(true);
        hideOldPass.setVisible(false);
        txtShowPassOld.setVisible(false);
    }

    public void ViewOldPass()
    {
        txtShowPassOld.setText(txtOldPass.getText());
        txtOldPass.setVisible(false);
        viewOldPass.setVisible(false);
        hideOldPass.setVisible(true);
        txtShowPassOld.setVisible(true);
    }

    public void HideNewPass()
    {
        txtShowPassNew.setText(txtNewPass.getText());
        txtNewPass.setVisible(true);
        viewNewPass.setVisible(true);
        hideNewPass.setVisible(false);
        txtShowPassNew.setVisible(false);
    }

    public void ViewNewPass()
    {
        txtShowPassNew.setText(txtNewPass.getText());
        txtNewPass.setVisible(false);
        viewNewPass.setVisible(false);
        hideNewPass.setVisible(true);
        txtShowPassNew.setVisible(true);
    }

    public void loadUserHome() throws IOException {
        Stage stage1 = (Stage) lblAccount.getScene().getWindow();
        stage1.close();
        // Load file FXML cho giao diện BaiHocController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/user_home.fxml"));
        Parent root = loader.load();

        // Lấy instance của BaiHocController
        UserHomeController userHomeController = loader.getController();

        // Tạo Scene mới và hiển thị giao diện BaiHocController
        Scene scene = new Scene(root, 1000, 600);
        Stage stage = new Stage();
        String pathToStyle = "/CSS/styles_userhome.css";
        scene.getStylesheets().add(getClass().getResource(pathToStyle).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
