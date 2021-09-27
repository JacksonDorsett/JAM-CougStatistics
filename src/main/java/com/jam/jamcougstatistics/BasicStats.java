package com.jam.jamcougstatistics;// Ayub Tahir
// BasicStats.java
// Calculates mean, median, mode, range, and standard deviation.

import java.util.Collections;
import java.util.List;

public class BasicStats {
    
    public static int MeanInt(List<Integer> dataList) {
        int sum = 0;
        
        for (int num : dataList) {
            sum += num;
        }

        return sum / dataList.size();
    }

    public static double MeanDouble(List<Double> dataList) {
        double sum = 0;

        for (double num : dataList) {
            sum += num;
        }

        return sum / dataList.size();
    }

    public static double MedianInt(List<Integer> dataList) {
        int midpoint = dataList.size() / 2;
        // if the list size is even
        if (dataList.size() % 2 == 0) {

            return (dataList.get(midpoint) + dataList.get(midpoint - 1)) / 2;
        }
        // if the list size is odd
        else {
            return dataList.get(midpoint);
        }
    }
    
    public static double MedianDouble(List<Double> dataList) {
        int midpoint = dataList.size() / 2;
        // if the list size is even
        if (dataList.size() % 2 == 0) {

            return (dataList.get(midpoint) + dataList.get(midpoint - 1)) / 2;
        }
        // if the list size is odd
        else {
            return dataList.get(midpoint);
        }
    }
    
    public static int ModeInt(List<Integer> dataList) {
        int maxValue = 0, maxCount = 0;

        for (int i = 0; i < dataList.size(); i++) {
            int count = 0;
            for (int j = 0; j < dataList.size(); j++) {
                if (dataList.get(j) == dataList.get(i))
                    count++;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = dataList.get(i);
            }
        }

        return maxValue;
    }

    public static double ModeDouble(List<Double> dataList) {
        double maxValue = 0.0;
        int maxCount = 0;

        for (int i = 0; i < dataList.size(); i++) {
            int count = 0;
            for (int j = 0; j < dataList.size(); j++) {
                if (dataList.get(j) == dataList.get(i))
                    count++;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = dataList.get(i);
            }
        }

        return maxValue;
    }

    public static int RangeInt(List<Integer> dataList) {
        int max = Collections.max(dataList);
        int min = Collections.min(dataList);

        return max - min;
    }

    public static double RangeDouble(List<Double> dataList) {
        double max = Collections.max(dataList);
        double min = Collections.min(dataList);

        return max - min;
    }

    public static double SDInt(List<Integer> dataList) {
        double sum = 0.0, stdDev = 0.0;

        for (int num : dataList) {
            sum += num;
        }

        double mean = sum / dataList.size();

        for (int num : dataList) {
            stdDev += Math.pow(num - mean, 2);
        }

        return Math.sqrt(stdDev / dataList.size());
    }

    public static double SDDouble(List<Double> dataList) {
        double sum = 0.0, stdDev = 0.0;

        for (double num : dataList) {
            sum += num;
        }

        double mean = sum / dataList.size();

        for (double num : dataList) {
            stdDev += Math.pow(num - mean, 2);
        }

        return Math.sqrt(stdDev / dataList.size());
    }
}

