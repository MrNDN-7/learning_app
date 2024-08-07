package org.example.cuoiki_code_tutorial.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.cuoiki_code_tutorial.DAOv2.QuanLyKhoaHocDAO;
import org.example.cuoiki_code_tutorial.Models.KhoaHoc;
import org.example.cuoiki_code_tutorial.Utils.Session;
import org.example.cuoiki_code_tutorial.Utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TrangChuTacGiaController implements Initializable {

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button btnThemKH;
    @FXML
    private VBox vBox; // Đổi từ HBox thành VBox
    @FXML
    private HBox hboxAdmin;
    @FXML
    private Label lblName;
    QuanLyKhoaHocDAO dao = new QuanLyKhoaHocDAO();

    private void loadKhoaHoc() {
        lblName.setText(UserSession.getInstance().getUsername());
        hboxAdmin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

                try {
                    // Tạo loader cho file fxml của trang chỉnh sửa
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/admin.fxml"));
                    Parent root = loader.load();

                    // Truyền thông tin của khóa học cần chỉnh sửa cho controller

                    // Tạo scene mới và thiết lập cho stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        List<KhoaHoc> khs = dao.selectAll();
        List<KhoaHoc> khoaHocReal = new ArrayList<>();
        for (KhoaHoc kh : khs) {

                khoaHocReal.add(kh);

        }
        vBox.getChildren().clear();
        HBox rowBox = new HBox(); // Tạo một VBox mới cho mỗi hàng khóa học
        rowBox.setAlignment(Pos.CENTER);
        rowBox.setSpacing(10);
        int count = 0;
        for (KhoaHoc khoaHoc : khoaHocReal) {
            if (count == 3) { // Nếu đã thêm 3 khóa học vào hàng, thêm hàng vào scrollPane và tạo một hàng mới
                vBox.getChildren().add(rowBox);
                rowBox = new HBox();
                rowBox.setAlignment(Pos.CENTER);
                rowBox.setSpacing(10);
                count = 0;
            }

            VBox vBoxKhoaHoc = createKhoaHocBox(khoaHoc);
            rowBox.getChildren().add(vBoxKhoaHoc);
            count++;
        }

        // Thêm hàng cuối cùng vào scrollPane
        if (!rowBox.getChildren().isEmpty()) {
            vBox.getChildren().add(rowBox);
        }
        btnThemKH.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                try {
                    // Tạo loader cho file fxml của trang chỉnh sửa
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/khoa_hoc_admin.fxml"));
                    Parent root = loader.load();

                    // Lấy controller của trang chỉnh sửa
                    KhoaHocAdminController controller = loader.getController();
                    controller.setIsThem(true, new KhoaHoc());
                    // Truyền thông tin của khóa học cần chỉnh sửa cho controller

                    // Tạo scene mới và thiết lập cho stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private VBox createKhoaHocBox(KhoaHoc khoaHoc) {
        VBox vBoxKhoaHoc = new VBox();
        vBoxKhoaHoc.setId("vBoxKhoaHoc");
        vBoxKhoaHoc.setPrefWidth(200);
        vBoxKhoaHoc.setPadding(new Insets(10, 10, 10, 10));
        vBoxKhoaHoc.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(khoaHoc.getHinhAnh());
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        imageView.setSmooth(true);

        Label tenKH = new Label(khoaHoc.getTenKH());
        tenKH.setAlignment(Pos.CENTER);
        Label tacGia = new Label(khoaHoc.getMaAD());
        tacGia.setAlignment(Pos.CENTER);
        Label ngayTao = new Label(khoaHoc.getNgayTao().toString());
        ngayTao.setAlignment(Pos.CENTER);

        Button editButton = new Button("Edit");
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Lấy stage hiện tại của scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                try {
                    // Tạo loader cho file fxml của trang chỉnh sửa
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/khoa_hoc_admin.fxml"));
                    Parent root = loader.load();

                    // Lấy controller của trang chỉnh sửa
                    KhoaHocAdminController controller = loader.getController();
                    controller.setIsThem(false, khoaHoc);

                    // Tạo scene mới và thiết lập cho stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Xử lý sự kiện khi nút Delete được nhấn
                // Ví dụ: Xóa khóa học từ cơ sở dữ liệu
                dao.delete(khoaHoc.getMaKH());
                // Sau khi xóa, cần cập nhật giao diện bằng cách loại bỏ VBox này
                vBox.getChildren().remove(vBoxKhoaHoc);
                loadKhoaHoc();
            }
        });
        Button themDauVao = new Button("Thêm Bài Kiểm Tra Đầu Vào");
        themDauVao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        vBoxKhoaHoc.getChildren().addAll(imageView, tenKH, tacGia, ngayTao, editButton, deleteButton);
        return vBoxKhoaHoc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadKhoaHoc();
    }

}

