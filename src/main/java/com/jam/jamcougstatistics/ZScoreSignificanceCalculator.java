package com.jam.jamcougstatistics;

import org.apache.commons.math3.distribution.NormalDistribution;
public class ZScoreSignificanceCalculator extends SignificanceCalculator {
    public double CalculateSignificance(double score) {
        NormalDistribution nd = new NormalDistribution();
        return nd.cumulativeProbability(score);
    }
}