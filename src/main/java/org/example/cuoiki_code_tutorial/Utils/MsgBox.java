package org.example.cuoiki_code_tutorial.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Window;

public class MsgBox {
    /**
     * Hiển thị thông báo cho người dùng
     * @param parent là cửa sổ chứa thông báo
     * @param message là thông báo
     */
    public static void alert(Window parent, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hệ thống");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(parent);
        alert.showAndWait();
    }

    /**
     * Hiển thị thông báo và yêu cầu người dùng xác nhận
     * @param parent là cửa sổ chứa thông báo
     * @param message là câu hỏi yes/no
     * @return là kết quả nhận được true/false
     */
    public static boolean confirm(Window parent, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hệ thống");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(parent);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        return alert.showAndWait().orElse(noButton) == yesButton;
    }

    /**
     * Hiển thị thông báo yêu cầu nhập dữ liệu
     * @param parent là cửa sổ chứa thông báo
     * @param message là thông báo nhắc nhở nhập
     * @return là kết quả nhận được từ người sử dụng nhập vào
     */
    public static String prompt(Window parent, String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Hệ thống");
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.initOwner(parent);

        return dialog.showAndWait().orElse(null);
    }
}
