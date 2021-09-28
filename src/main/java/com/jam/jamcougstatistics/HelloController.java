package com.jam.jamcougstatistics;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onTestClick(){}

    @FXML
    protected void LoadDataClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open data set");
        File selected_file = fileChooser.showOpenDialog(new Stage());
        if (selected_file == null){
            Alert warning = new Alert(Alert.AlertType.ERROR, "Error file unable to be opened.", ButtonType.CLOSE);
            warning.showAndWait();
        }

        if (!selected_file.getName().endsWith(".csv")) {
            Alert warning = new Alert(Alert.AlertType.ERROR, "File must be a csv file.", ButtonType.CLOSE);
            warning.showAndWait();
        }
    }
}