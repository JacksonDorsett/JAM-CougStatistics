package com.jam.jamcougstatistics;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicStatsTest {
    
    @Test
    public void testMean() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        double mean = BasicStats.Mean(data);
        Assertions.assertEquals(3, mean);
    }
    
    @Test
    public void testMeanEmpty() {
        List<Integer> data = List.of();
        double mean = BasicStats.Mean(data);
        Assertions.assertEquals(0, mean);
    }

    @Test
    public void testMeanOne() {
        List<Integer> data = List.of(1);
        double mean = BasicStats.Mean(data);
        Assertions.assertEquals(1, mean);
    }

    @Test
    public void testMedian() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        double median = BasicStats.Median(data);
        Assertions.assertEquals(3, median);
    }

    @Test
    public void testMedianEmpty() {
        List<Integer> data = List.of();
        double median = BasicStats.Median(data);
        Assertions.assertEquals(0, median);
    }

    @Test
    public void testMedianOne() {
        List<Integer> data = List.of(1);
        double median = BasicStats.Median(data);
        Assertions.assertEquals(1, median);
    }

    @Test
    public void testMedianEven() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4);
        double median = BasicStats.Median(data);
        Assertions.assertEquals(2.5, median);
    }

    @Test
    public void testMode() {
        List<Integer> data = Arrays.asList(1, 1, 2, 3, 4, 5);
        double mode = BasicStats.Mode(data);
        Assertions.assertEquals(1, mode);
    }

    @Test
    public void testModeEmpty() {
        List<Integer> data = List.of();
        double mode = BasicStats.Mode(data);
        Assertions.assertEquals(0, mode);
    }

    @Test
    public void testModeOne() {
        List<Integer> data = List.of(1);
        double mode = BasicStats.Mode(data);
        Assertions.assertEquals(1, mode);
    }

    @Test
    public void testModeEven() {
        List<Integer> data = Arrays.asList(1, 1, 2, 2, 3, 4, 4, 4, 5);
        double mode = BasicStats.Mode(data);
        Assertions.assertEquals(4, mode);
    }
}
