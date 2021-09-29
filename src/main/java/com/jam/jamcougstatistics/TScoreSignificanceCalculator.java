package com.jam.jamcougstatistics;

import org.apache.commons.math3.distribution.TDistribution;
public class TScoreSignificanceCalculator extends SignificanceCalculator {
    public TScoreSignificanceCalculator(double degrees_of_freedom){
        this.degrees_of_freedom = degrees_of_freedom;
    }

    private double degrees_of_freedom;

    @Override
    double CalculateSignificance(double score) {
        TDistribution td = new TDistribution(degrees_of_freedom);
        return td.cumulativeProbability(score);
    }
}
