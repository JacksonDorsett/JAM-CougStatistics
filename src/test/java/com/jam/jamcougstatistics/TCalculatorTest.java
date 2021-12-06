// Tested by Anthony Nguyen

package com.jam.jamcougstatistics;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import com.jam.jamcougstatistics.TCalculator;
class TCalculatorTest {

    // CASE 1: The given array has data in it
    @Test
    void testTScore() {
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(1d,3.0d, 2.5d, 2.0d,4d,5d,1d));
        float mu = 3;
        float result = TCalculator.TScore(data,mu);
        Assertions.assertEquals(-0.63330066f,result,0.0001f);
    }

    // CASE 2: The given array has no data in it
    @Test
    void testEmptyArray()   {
        ArrayList<Double> data = new ArrayList<>(Arrays.asList());
        float mu = 3;
        float result = TCalculator.TScore(data,mu);
        Assertions.assertTrue(Float.isNaN(result));
    }
}