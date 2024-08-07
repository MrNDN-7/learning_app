package org.example.cuoiki_code_tutorial.Controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.cuoiki_code_tutorial.DAOv2.KiemTraDauVaoDAO;
import org.example.cuoiki_code_tutorial.Models.CauHoi;
import org.example.cuoiki_code_tutorial.Utils.Session;
import org.example.cuoiki_code_tutorial.Utils.UserSession;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.StrictMath.round;


public class KiemTraDauVao implements Initializable {
    @FXML
    private Label courseNameLabel;
    @FXML
    private Label timerLabel, ketqua;
    @FXML
    private VBox questionPane;
    @FXML
    private Button prevButton, submit;
    @FXML
    private Button nextButton;
    @FXML
    private ImageView imglogout, imglogoCodeLearn;

    KiemTraDauVaoDAO kiemTraDauVaoDAO = new KiemTraDauVaoDAO();
    List<CauHoi> cauHoiList = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    private Timeline countdown;
    private int timeRemaining;
    private String maKh;

    @FXML
    private ToggleGroup answerToggleGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imgLogo = new Image(getClass().getResource("/Image/logo_codelearn.png").toString());
        Image imgLogout = new Image(getClass().getResource("/Image/logout.png").toString());
        imglogoCodeLearn.setImage(imgLogo);
        imglogout.setImage(imgLogout);

        imglogoCodeLearn.setOnMouseClicked(e -> {
            String resPath = "/FXML/user_home.fxml";
            String cssPath = "/CSS/styles_userhome.css";
            Stage stage = (Stage) imglogoCodeLearn.getScene().getWindow();
            SceneLoader.loadScene(resPath, cssPath, stage);
        });
    }

    public void loadCauHoi(List<CauHoi> cauHois, int time)
    {
        int t = time * 60;
        startCountdown(3600);
        cauHoiList = cauHois;
        timerLabel.setText("Thời gian làm bài: " + String.valueOf(time));
        courseNameLabel.setText("Kiểm tra đầu vào");
        
        showQuestion(cauHois.get(0));

        maKh = cauHois.get(0).getMaKHoaHoc();

    }


    public void showPreviousQuestion(ActionEvent actionEvent) {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            showQuestion(cauHoiList.get(currentQuestionIndex));
        }
    }
    private int getSelectedRadioOption() {
        Toggle selectedToggle = answerToggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            RadioButton selectedRadio = (RadioButton) selectedToggle;
            Object userData = selectedRadio.getUserData();
            if (userData != null && userData instanceof Integer) {
                return (int) userData;
            }
        }
        return -1; // Trả về -1 nếu không có RadioButton nào được chọn
    }
    public void showNextQuestion(ActionEvent actionEvent) {



        if (currentQuestionIndex < cauHoiList.size() - 1) {
            int da = getSelectedRadioOption();
            if(da != -1)
            {
                if(da == cauHoiList.get(currentQuestionIndex).getDapAn())
                {
                    score++;
                }
            }
            currentQuestionIndex++;
            showQuestion(cauHoiList.get(currentQuestionIndex));
        }
    }

    public void showQuestion(CauHoi question) {
        questionPane.getChildren().clear(); // Xóa câu hỏi cũ

        Label questionLabel = new Label(question.getCauHoi());
        questionPane.getChildren().add(questionLabel);

        ToggleGroup answerGroup = new ToggleGroup();
        answerToggleGroup = answerGroup;

        RadioButton answerRadio1 = new RadioButton(question.getCauTraLoi1());
        answerRadio1.setUserData(1);
        answerRadio1.setToggleGroup(answerGroup);
        questionPane.getChildren().add(answerRadio1);

        RadioButton answerRadio2 = new RadioButton(question.getCauTraLoi2());
        answerRadio2.setUserData(2);
        answerRadio2.setToggleGroup(answerGroup);
        questionPane.getChildren().add(answerRadio2);

        RadioButton answerRadio3 = new RadioButton(question.getCauTraLoi3());
        answerRadio3.setUserData(3);
        answerRadio3.setToggleGroup(answerGroup);
        questionPane.getChildren().add(answerRadio3);

    }

    private void startCountdown(int totalTime) {
        timeRemaining = totalTime;
        timerLabel.setText(formatTime(timeRemaining));

        countdown = new Timeline();
        countdown.setCycleCount(Animation.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            timeRemaining--;
            timerLabel.setText(formatTime(timeRemaining));

            if (timeRemaining <= 0) {
                stopCountdown();
                submitt();
                // Xử lý khi hết giờ
            }
        });

        countdown.getKeyFrames().add(keyFrame);
        countdown.play();
    }
    private void stopCountdown() {
        if (countdown != null) {
            countdown.stop();
        }
    }
    private String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void submit(ActionEvent actionEvent) {

        showAlert("xác nhận nộp bài");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION, WARNING, ERROR as needed
        alert.setTitle("Thông báo");
        alert.setHeaderText(null); // Optional header text
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                submitt();
            }
        }); // Display the alert and wait for user interaction
    }

    public void submitt()
    {
        int da = getSelectedRadioOption();
        if(da != -1)
        {
            if(da == cauHoiList.get(currentQuestionIndex).getDapAn())
            {
                score++;
            }
        }
        questionPane.getChildren().clear();
        ketqua.setVisible(true);
        ketqua.setText("Kết quả: " + score + "/" + String.valueOf(currentQuestionIndex + 1));
        submit.setDisable(true);
        nextButton.setDisable(true);
        prevButton.setDisable(true);
        stopCountdown();

        int a = 10* score;
        int b = currentQuestionIndex + 1;
        float sc = (float) a / b ;

        kiemTraDauVaoDAO.insertDiemDauVao(sc, UserSession.getInstance().getUsername(), maKh);
    }
}




