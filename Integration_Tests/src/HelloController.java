import java.io.File;
import java.util.ArrayList;

public class HelloController {
    static final String noDataString = "No data";
    private int dataSlotIndex = 0;

    String meanOutput;

    String medianOutput;

    String modeOutput;

    String sdOutput;

    String rangeOutput;

    String zScoreX;

    String zScoreOutput;

    String tScoreMu;

    String tScoreOutput;

    String slot1Output;

    String slot2Output;

    String populationField;

    String testPassedBox;

    String testUsedBox;

    String sampleUsedBox;

    String scoreBox;

    String significanceBox;

    DataSlots<Double> slots = new DataSlots<>(2);

    // UNIT TESTING VARIABLES

    boolean usePopulation;

    int alphaGroup;

    int testType;

    public void initialize() {
        setSlotStrings();
        setOutputs();
    }

    private void setSlotStrings() {
        slot1Output = slots.slotString(0);
        slot2Output = slots.slotString(1);
    }

    private void setZScore(String xString) {
        try {
            float x = Float.parseFloat(xString);
            ArrayList<Double> slot = slots.getSlot(dataSlotIndex);
            double zScore = ZCalculator.ZScore(slot, x);
            zScoreOutput = Double.toString(zScore);
        } catch (Exception e) {
            zScoreOutput = noDataString;
        }
    }

    private void setTScore(String muString) {
        try {
            float mu = Float.parseFloat(muString);
            ArrayList<Double> slot = slots.getSlot(dataSlotIndex);
            double tScore = TCalculator.TScore(slot, mu);
            tScoreOutput = Double.toString(tScore);
        } catch (Exception e) {
            tScoreOutput = noDataString;
        }
    }

    protected void onTestClick() {

        try {
            ArrayList<Double> slot = slots.getSlot(dataSlotIndex);
            double mu;
            double expectedSig;
            int tType;
            if (usePopulation){
                if (slot.size() < 40){
                    throw new Exception("Error: population must be specified with under 40 samples");
                }
                mu = BasicStats.SD(slot);

                System.out.println(mu);
            }
            else{
                if (populationField == ""){
                    throw new Exception("Error: population must be specified");
                }
                mu = Double.parseDouble(populationField);

            }
            // Get expected significance.
            int i = alphaGroup;
            switch (i){
                case 0:
                    expectedSig = .1;
                    break;
                case 1:
                    expectedSig = .05;
                    break;
                case 2:
                    expectedSig = .01;
                    break;
                default:
                    throw new Exception("please select a significance value.");
            }

            // Get Test Type
            switch (testType){
                case 0:
                    tType = -1;
                    break;
                case 1:
                    tType = 1;
                    break;
                case 2:
                    tType = 0;
                    break;
                default:
                    throw new Exception("please select a test type.");
            }
            HypothesisTester ht = new HypothesisTester();
            setTestResults(ht.TestHypothesis(slot,mu,tType,expectedSig));
        } catch (Exception e) {
            System.out.println(e.toString());
            setTestResults(null);

        }
    }

    protected void setTestResults(TestSummary summary){
        if (summary == null){
            testUsedBox = noDataString;
            sampleUsedBox = noDataString;
            scoreBox = noDataString;
            significanceBox = noDataString;
            testPassedBox = noDataString;
        }

        testUsedBox = summary.getTestType();
        scoreBox = ((Double)summary.getSignificanceScore()).toString();
        sampleUsedBox = slots.slotString(dataSlotIndex);
        significanceBox = ((Double) summary.getExpectedSignificance()).toString();
        testPassedBox = ((Boolean)summary.isTestPassed()).toString();
    }

    protected void setOutputs() {
        setGeneralOutputs();
        setTScore(tScoreMu);
        setZScore(zScoreX);
    }

    protected void setGeneralOutputs() {
        if (slots.hasSlot(dataSlotIndex)) {
            ArrayList<Double> slot = slots.getSlot(dataSlotIndex);
            meanOutput = Double.toString(BasicStats.Mean(slot));
            medianOutput = Double.toString(BasicStats.Median(slot));
            modeOutput = Double.toString(BasicStats.Mode(slot));
            sdOutput = Double.toString(BasicStats.SD(slot));
            rangeOutput = Double.toString(BasicStats.Range(slot));
        } else {
            meanOutput = noDataString;
            medianOutput = noDataString;
            modeOutput = noDataString;
            sdOutput = noDataString;
            rangeOutput = noDataString;
        }
    }

    protected void LoadData(String fileName) throws Exception {

        File selected_file = new File(fileName);

        DataSlots<Double> loadedSlots = DataLoader.LoadDataSet(selected_file);

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
    }
}