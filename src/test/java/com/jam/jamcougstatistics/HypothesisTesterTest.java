package com.jam.jamcougstatistics;

import com.jam.jamcougstatistics.HypothesisTester;
import com.jam.jamcougstatistics.TestSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HypothesisTesterTest {

    @Test
    void testHypothesis() {
        ArrayList<Double> small_data = new ArrayList<>(Arrays.asList(1.0d, 1.0d, 1.0d, 2.0d, 3.0d, 4.0d, 2.0d, 4.0d));
        HypothesisTester tester = new HypothesisTester();
        float mu = 3;
        //test with constant significance of .1
        TestSummary result1 = tester.TestHypothesis(small_data,mu,-1,.1f);
        assertEquals(0.0709458f, result1.getSignificanceScore(), .001f);
        assertTrue( result1.isTestPassed());
        assertEquals(0.1f, result1.getExpectedSignificance());

        // testing not equals
        TestSummary result2 = tester.TestHypothesis(small_data,mu,0,.1f);
        assertEquals(0.0709458f, result2.getSignificanceScore(), .001f);
        assertTrue( result2.isTestPassed());
        assertEquals(0.2f, result2.getExpectedSignificance());

        TestSummary result3 = tester.TestHypothesis(small_data,mu,0,.1f);
        assertEquals(0.92905f, result3.getSignificanceScore(), .001f);
        assertFalse( result3.isTestPassed());
        assertEquals(0.1f, result3.getExpectedSignificance());
    }

    @Test
    void calculateSignificance() {
        ArrayList<Double> small_data = new ArrayList<>(Arrays.asList(1d,3.0d, 2.5d, 2.0d,4d,5d,1d));
        // test small dataset
        float mu = 3;
        HypothesisTester tester = new HypothesisTester();
        double result = tester.CalculateSignificance(small_data, mu);
        Assertions.assertEquals(-0.13497111331159506d,result,0.0001d);

        //test large dataset
        ArrayList<Double> large_data = new ArrayList<>(Arrays.asList(1d,3.0d, 2.5d, 2.0d,4d,5d,1d, 4d,3d,10d,5d,7d,8d,4d,11d,12d,3d,4d,5d,5d,6d,8d,9d,10d,11d,15d,13d,2d,1d,3.0d, 2.5d, 2.0d,4d,5d,1d, 4d,3d,10d,5d,7d,8d,4d,11d,12d,3d,4d,5d,5d,6d,8d,9d,10d,11d,15d,13d,2d));
        double result2 = tester.CalculateSignificance(large_data, 3f);
        assertEquals(0.20066507d, result2, .001d);
    }

    //edge case
    @Test
    void testSignificanceForEmptyInput(){
        ArrayList<Double> no_data = new ArrayList<Double>();
        float mu = 4;
        HypothesisTester tester = new HypothesisTester();
        Exception exception = assertThrows(NoDataException.class, () -> {
            tester.CalculateSignificance(no_data,mu)
        });
        assertEquals("Cannot calculate significance without data points.", exception.getMessage())
    }

    @Test
    void testHypothesisForEmptyInput(){
        ArrayList<Double> no_data = new ArrayList<Double>();
        float = mu = 4;
        HypothesisTester tester = new HypothesisTester();
        //change test type, const significance
        assertThrows(NoDataException.class, () -> {tester.test_hypothesis(no_data,mu,1,.05d)});
        assertThrows(NoDataException.class, () -> {tester.test_hypothesis(no_data,mu,0,.05d)});
        assertThrows(NoDataException.class, () -> {tester.test_hypothesis(no_data,mu,-1,.05d)});
        //const test type, change significance
        assertThrows(NoDataException.class, () -> {tester.test_hypothesis(no_data,mu,1,.55d)});
        assertThrows(NoDataException.class, () -> {tester.test_hypothesis(no_data,mu,1,.1d)});
    }

    @Test
    void testSignificanceWithNoVariance(){
        ArrayList<Double> data = new ArrayList<Double>();
        for(int i = 0; i < 50; i++) data.add(5);
        float mu = 5;
        HypothesisTester tester = new HypothesisTester();

        //below
        assertEquals(0d, tester.CalculateSignificance(data, 4d));
        //above
        assertEquals(1d, tester.CalculateSignificance(data, 6d));
        //equals
        assertEquals(Double.NaN, tester.CalculateSignificance(data, 5d));

    }

    @Test
    void testSmallSignificanceWithNoVariance(){
        ArrayList<Double> data = new ArrayList<Double>();
        for(int i = 0; i < 5; i++) data.add(5);
        float mu = 5;
        HypothesisTester tester = new HypothesisTester();

        //below
        assertEquals(0d, tester.CalculateSignificance(data, 4d));
        //above
        assertEquals(1d, tester.CalculateSignificance(data, 6d));
        //equals
        assertEquals(Double.NaN, tester.CalculateSignificance(data, 5d));
    }

    @Test
    void testHypothesisWithoutVariance(){
        ArrayList<Double> big_data = new ArrayList<Double>();
        for(int i = 0; i < 50; i++) data.add(5);
        float mu = 4;
        HypothesisTester tester = new HypothesisTester();

        assertEquals(0d, tester.test_hypothesis(big_data,mu,-1d,.05).getSignificanceScore());
        assertEquals(1d, tester.test_hypothesis(big_data,mu,0d,.05).getSignificanceScore());
        assertEquals(1d, tester.test_hypothesis(big_data,mu,1d,.05).getSignificanceScore());

        mu = 5;
        assertEquals(Double.NaN, tester.test_hypothesis(big_data,mu,-1d,.05).getSignificanceScore());
        assertEquals(Double.NaN, tester.test_hypothesis(big_data,mu,0d,.05).getSignificanceScore());
        assertEquals(Double.NaN, tester.test_hypothesis(big_data,mu,1d,.05).getSignificanceScore());

        mu = 6;
        assertEquals(1d, tester.test_hypothesis(big_data,mu,-1d,.05).getSignificanceScore());
        assertEquals(1d, tester.test_hypothesis(big_data,mu,0d,.05).getSignificanceScore());
        assertEquals(0d, tester.test_hypothesis(big_data,mu,1d,.05).getSignificanceScore());

    }
}