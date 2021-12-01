package com.jam.jamcougstatistics;

import java.util.ArrayList;

public class HypothesisTester {

    public TestSummary TestHypothesis(ArrayList<Double> sample, double mu, int test_type, double expected_significance) throws NoDataException
    {
        // != test
        if (test_type == 0) {
            expected_significance *= 2;
        }
        double significance = this.CalculateSignificance(sample, mu);
        // X <= x
        if (test_type == 1) {
            significance = 1 - significance;
        }
        return new TestSummary(expected_significance, significance,expected_significance >= significance, test_type);
    }

    public double CalculateSignificance(ArrayList<Double> sample, double mu) throws NoDataException{
        if (sample.size() == 0)
        {
            throw new NoDataException("Cannot calculate significance without data points.");
        }
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
