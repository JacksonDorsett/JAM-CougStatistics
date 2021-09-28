package com.jam.jamcougstatistics;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class HelloController {
    @FXML
    ToggleGroup dataSlot;

    DataLoader.DataSlots<Integer> slots = new DataLoader.DataSlots<>(2);
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onTestClick(){}

    @FXML
    protected void LoadDataClick() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open data set");
        File selected_file = fileChooser.showOpenDialog(new Stage());
        if (selected_file == null){
            Alert warning = new Alert(Alert.AlertType.ERROR, "Error file unable to be opened.", ButtonType.CLOSE);
            warning.showAndWait();
        }else if (!selected_file.getName().endsWith(".csv")) {
            Alert warning = new Alert(Alert.AlertType.ERROR, "File must be a csv file.", ButtonType.CLOSE);
            warning.showAndWait();
        }

        DataLoader.DataSlots<Integer> loadedSlots = DataLoader.LoadDataSet(selected_file);
        // If only one dataset is loaded in...
        if(!loadedSlots.hasSlot(1)){
            // Load it into the selected slot
            int index = dataSlot.getToggles().indexOf(dataSlot.getSelectedToggle());
            slots.setSlot(index, loadedSlots.slots.get(0));
        }else{
            // Otherwise, load both in
            slots.assignSlotsFrom(loadedSlots);
        }
        String s1 = slots.hasSlot(0)?slots.slots.get(0).toString():"null";
        String s2 = slots.hasSlot(1)?slots.slots.get(1).toString():"null";
        System.out.printf("Slot 1:%s\nSlot 2:%s\n",s1,s2);
    }
}