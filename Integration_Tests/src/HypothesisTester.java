import java.util.ArrayList;

public class HypothesisTester {

    public TestSummary TestHypothesis(ArrayList<Double> sample, double mu, int test_type, double expected_significance)
    {
        if (test_type == 0) {
            expected_significance *= 2;
        }
        double significance = this.CalculateSignificance(sample, mu);
        if (test_type == 1) {
            significance = 1 - significance;
        }
        double actual_significance = this.CalculateSignificance(sample, mu);
        return new TestSummary(expected_significance, actual_significance,expected_significance >= actual_significance, test_type);
    }

    public double CalculateSignificance(ArrayList<Double> sample, double mu) {
        double score;
        SignificanceCalculator sigCalc;
        if (sample.size() > 40) {
            score = ZCalculator.ZScore(sample, (float) mu);
            sigCalc = new ZScoreSignificanceCalculator();
        }
        else {
            score = TCalculator.TScore(sample, (float)mu);
            sigCalc = new TScoreSignificanceCalculator(sample.size()-1);
        }

        return sigCalc.CalculateSignificance(score);

    }
}
