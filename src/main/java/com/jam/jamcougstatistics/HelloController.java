package com.jam.jamcougstatistics;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class HelloController {
    static final String noDataString = "No data";
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
    TextField zScoreX;
    @FXML
    TextField zScoreOutput;

    @FXML
    TextField tScoreMu;
    @FXML
    TextField tScoreOutput;

    @FXML
    TextField slot1Output;
    @FXML
    TextField slot2Output;

    DataSlots<Integer> slots = new DataSlots<>(2);

    public void initialize() {
        tScoreMu.textProperty().addListener((observable, oldValue, newValue)->{
            setTScore(newValue);
        });
        zScoreX.textProperty().addListener((observable, oldValue, newValue)->{
            setZScore(newValue);
        });

        setSlotStrings();
        setOutputs();
    }

    private void setSlotStrings() {
        slot1Output.setText(slots.slotString(0));
        slot2Output.setText(slots.slotString(1));
    }

    private void setZScore(String xString) {
        try {
            float x = Float.parseFloat(xString);
            ArrayList<Integer> slot = slots.getSlot(dataSlotIndex);
            double zScore = ZCalculator.ZScore(slot, x);
            zScoreOutput.setText(Double.toString(zScore));
        } catch (Exception e) {
            zScoreOutput.setText(noDataString);
        }
    }

    private void setTScore(String muString) {
        try {
            float mu = Float.parseFloat(muString);
            ArrayList<Integer> slot = slots.getSlot(dataSlotIndex);
            double tScore = TCalculator.TScore(slot, mu);
            tScoreOutput.setText(Double.toString(tScore));
        } catch (Exception e) {
            tScoreOutput.setText(noDataString);
        }
    }

    @FXML
    protected void onTestClick() {
    }

    protected void setOutputs() {
        setGeneralOutputs();
        setTScore(tScoreMu.getText());
        setZScore(zScoreX.getText());
    }

    protected void setGeneralOutputs() {
        if (slots.hasSlot(dataSlotIndex)) {
            ArrayList<Integer> slot = slots.getSlot(dataSlotIndex);
            meanOutput.setText(Double.toString(BasicStats.Mean(slot)));
            medianOutput.setText(Double.toString(BasicStats.Median(slot)));
            modeOutput.setText(Double.toString(BasicStats.Mode(slot)));
            sdOutput.setText(Double.toString(BasicStats.SD(slot)));
            rangeOutput.setText(Double.toString(BasicStats.Range(slot)));
        } else {
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
        if (fileChooser == null) {
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