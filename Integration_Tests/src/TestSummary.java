public class TestSummary {
    public TestSummary(double expectedSignificance, double actualSignificance, boolean testPassed, int testType) {
        this.expectedSignificance = expectedSignificance;
        this.significanceScore = actualSignificance;
        this.testPassed = testPassed;
        this.testType = testType;
    }
    private double significanceScore;
    private double expectedSignificance;
    private int testType;
    private boolean testPassed;
    public double getSignificanceScore() {
        return significanceScore;
    }
    public double getExpectedSignificance() {
        return expectedSignificance;
    }
    public boolean isTestPassed() {
        return testPassed;
    }
    public String getTestType() {
        if (testType == -1) return "≥";
        if (testType == 0) return "≠";
        if (testType == 1) return "≤";
        return "";
    }
}