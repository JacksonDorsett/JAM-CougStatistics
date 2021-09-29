package com.jam.jamcougstatistics;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class HelloController {
    private int dataSlotIndex = 0;
    private FileChooser fileChooser;
    @FXML
    ToggleGroup dataSlotToggles;
    @FXML
    TextField meanOutput;
    @FXML
    TextField medianOutput;
    @FXML
    TextField modeOutput;
    @FXML
    TextField sdOutput;
    @FXML
    TextField rangeOutput;

    @FXML
    TextField slot1Output;
    @FXML
    TextField slot2Output;

    DataSlots<Integer> slots = new DataSlots<>(2);
    public void initialize(){
        setSlotStrings();
        setOutputs();
    }

    private void setSlotStrings() {
        slot1Output.setText(slots.slotString(0));
        slot2Output.setText(slots.slotString(1));
    }

    @FXML
    protected void onTestClick() {
    }

    protected void setOutputs() {
        if (slots.hasSlot(dataSlotIndex)) {
            ArrayList<Integer> slot = slots.getSlot(dataSlotIndex);
            meanOutput.setText(Double.toString(BasicStats.Mean(slot)));
            medianOutput.setText(Double.toString(BasicStats.Median(slot)));
            modeOutput.setText(Double.toString(BasicStats.Mode(slot)));
            sdOutput.setText(Double.toString(BasicStats.SD(slot)));
            rangeOutput.setText(Double.toString(BasicStats.Range(slot)));
        }else{
            String noDataString = "No data";
            meanOutput.setText(noDataString);
            medianOutput.setText(noDataString);
            modeOutput.setText(noDataString);
            sdOutput.setText(noDataString);
            rangeOutput.setText(noDataString);
        }
    }

    @FXML
    protected void SetSlot() {
        dataSlotIndex = dataSlotToggles.getToggles().indexOf(dataSlotToggles.getSelectedToggle());
        setOutputs();
    }

    @FXML
    protected void LoadDataClick() throws Exception {
        if(fileChooser == null){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open data set");
        File selected_file = fileChooser.showOpenDialog(new Stage());
        if (selected_file == null) {
            Alert warning = new Alert(Alert.AlertType.ERROR, "Error file unable to be opened.", ButtonType.CLOSE);
            warning.showAndWait();
        } else if (!selected_file.getName().endsWith(".csv")) {
            Alert warning = new Alert(Alert.AlertType.ERROR, "File must be a csv file.", ButtonType.CLOSE);
            warning.showAndWait();
        }

        DataSlots<Integer> loadedSlots = DataLoader.LoadDataSet(selected_file);

        // If only one dataset is loaded in...
        if (!loadedSlots.hasSlot(1)) {
            // Load it into the selected slot
            slots.setSlot(dataSlotIndex, loadedSlots.getSlot(0));
        } else {
            // Otherwise, load both in
            slots.assignSlotsFrom(loadedSlots);
        }
        setSlotStrings();
        setOutputs();
            fileChooser = null;
        }
    }
}