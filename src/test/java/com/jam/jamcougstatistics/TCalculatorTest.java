package com.jam.jamcougstatistics;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import com.jam.jamcougstatistics.TCalculator;
class TCalculatorTest {

    @Test
    void TScore() {
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(1d,3.0d, 2.5d, 2.0d,4d,5d,1d));
        float mu = 3;
        float result = TCalculator.TScore(data,mu);
        Assertions.assertEquals(-0.13497111331159506f,result,0.0001f);
    }
}