// Integration tests written by Anthony Nguyen

import static org.mockito.Mockito.when;

import org.junit.*;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;

public class IntegrationTest {

    @Test
    public void integrateDataLoader()  throws Exception {
        HelloController testController = new HelloController();
        testController.initialize();

        Assert.assertEquals("Expected No data", "No data", testController.meanOutput);
        Assert.assertEquals("Expected No data", "No data", testController.medianOutput);
        Assert.assertEquals("Expected No data", "No data", testController.modeOutput);
        Assert.assertEquals("Expected No data", "No data", testController.sdOutput);
        Assert.assertEquals("Expected No data", "No data", testController.rangeOutput);

        PowerMockito.mockStatic(BasicStats.class);
        when(BasicStats.Mean(Mockito.any())).thenReturn(1.25);
        when(BasicStats.Median(Mockito.any())).thenReturn(1.0);
        when(BasicStats.Mode(Mockito.any())).thenReturn(1.0);
        when(BasicStats.SD(Mockito.any())).thenReturn(0.43);
        when(BasicStats.Range(Mockito.any())).thenReturn(1.0);

        testController.LoadData("src/Data/test.csv");
        Assert.assertEquals("Expected 1.25", "1.25", testController.meanOutput);
        Assert.assertEquals("Expected 1.0", "1.0", testController.medianOutput);
        Assert.assertEquals("Expected 1.0", "1.0", testController.modeOutput);
        Assert.assertEquals("Expected 0.43", "0.43", testController.sdOutput);
        Assert.assertEquals("Expected 1.0", "1.0", testController.rangeOutput);
    }

    @Test
    public void integrateBasicStats() throws Exception  {
        HelloController testController = new HelloController();
        testController.initialize();

        testController.LoadData("src/Data/test2.csv");
        Assert.assertEquals("Expected 1.4285714285714286", "1.4285714285714286", testController.meanOutput);
        Assert.assertEquals("Expected 1.0", "1.0", testController.medianOutput);
        Assert.assertEquals("Expected 1.0", "1.0", testController.modeOutput);
        Assert.assertEquals("Expected 0.7284313590846835", "0.7284313590846835", testController.sdOutput);
        Assert.assertEquals("Expected 2.0", "2.0", testController.rangeOutput);
    }

    @Test
    public void integrateZCalculator()  throws Exception    {
        HelloController testController = new HelloController();
        testController.initialize();

        Assert.assertEquals("Expected No data", "No data", testController.zScoreOutput);

        Whitebox.setInternalState(testController, "zScoreX", "1.5");

        testController.LoadData("src/Data/test.csv");
        Assert.assertEquals("Expected 0.5773502588272095", "0.5773502588272095", testController.zScoreOutput);

        testController.LoadData("src/Data/test2.csv");
        Assert.assertEquals("Expected 0.09805802255868912", "0.09805802255868912", testController.zScoreOutput);

        testController.LoadData("src/Data/test3.csv");
        Assert.assertEquals("Expected -0.6255432367324829", "-0.6255432367324829", testController.zScoreOutput);
    }

    @Test
    public void integrateTCalculator() throws Exception {
        HelloController testController = new HelloController();
        testController.initialize();

        Assert.assertEquals("Expected No data", "No data", testController.tScoreOutput);

        Whitebox.setInternalState(testController, "tScoreMu", "1.5");

        testController.LoadData("src/Data/test.csv");
        Assert.assertEquals("Expected -1.0", "-1.0", testController.tScoreOutput);

        testController.LoadData("src/Data/test2.csv");
        Assert.assertEquals("Expected -0.24019211530685425", "-0.24019211530685425", testController.tScoreOutput);

        testController.LoadData("src/Data/test3.csv");
        Assert.assertEquals("Expected 1.6550318002700806", "1.6550318002700806", testController.tScoreOutput);
    }

    @Test
    public void integrateTestSummary()  throws Exception    {
        HelloController testController = new HelloController();
        testController.initialize();

        SignificanceCalculator sigCalc = new ZScoreSignificanceCalculator();
        SignificanceCalculator spyCalc = PowerMockito.spy(sigCalc);

        PowerMockito.whenNew(SignificanceCalculator.class).withAnyArguments().thenReturn(sigCalc);
        PowerMockito.when(spyCalc.CalculateSignificance(Mockito.anyDouble())).thenReturn(1.0);

        testController.usePopulation = false;
        testController.populationField = "5";

        testController.alphaGroup = 0;
        testController.testType = 0;

        testController.LoadData("src/Data/test.csv");
        testController.onTestClick();
        Assert.assertEquals("Expected ≥", "≥", testController.testUsedBox);
        Assert.assertEquals("Expected [1.0, 2.0, 1.0, 1.0]", "[1.0, 2.0, 1.0, 1.0]", testController.sampleUsedBox);
        Assert.assertEquals("Expected 0.1", "0.1", testController.significanceBox);
        Assert.assertEquals("Expected true", "true", testController.testPassedBox);

        testController.alphaGroup = 1;
        testController.onTestClick();
        Assert.assertEquals("Expected 0.05", "0.05", testController.significanceBox);

        testController.alphaGroup = 2;
        testController.onTestClick();
        Assert.assertEquals("Expected 0.01", "0.01", testController.significanceBox);

        testController.testType = 1;
        testController.onTestClick();
        Assert.assertEquals("Expected ≤", "≤", testController.testUsedBox);

        testController.testType = 2;
        testController.onTestClick();
        Assert.assertEquals("Expected ≠", "≠", testController.testUsedBox);

    }

    @Test
    public void integrateSignificanceCalculator() throws Exception  {
        HelloController testController = new HelloController();
        testController.initialize();

        testController.usePopulation = false;
        testController.populationField = "5";

        testController.alphaGroup = 0;
        testController.testType = 0;

        testController.LoadData("src/Data/test.csv");
        testController.onTestClick();

        Assert.assertEquals("Expected 3.2155966346683276E-4", "3.2155966346683276E-4", testController.scoreBox);

        testController.populationField = "10";
        testController.onTestClick();
        Assert.assertEquals("Expected 2.5642584449353625E-5", "2.5642584449353625E-5", testController.scoreBox);
    }
}
