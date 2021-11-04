// Ayub Tahir
// BasicStats.java
// Calculates mean, median, mode, range, and standard deviation.

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicStats {
    
    public static <T extends Number> double Mean(List<T> dataList) {
        double sum = 0;
        
        for (T num : dataList) {
            sum += num.doubleValue();
        }

        return sum / dataList.size();
    }

    public static <T extends Number> double Median(List<T> dataList) {
        List<T> sorted = dataList.stream().sorted().collect(Collectors.toList());
        int midpoint = sorted.size() / 2;
        // if the list size is even
        if (sorted.size() % 2 == 0) {
            return (sorted.get(midpoint).doubleValue() + sorted.get(midpoint - 1).doubleValue()) / 2.0;
        }
        // if the list size is odd
        else {
            return sorted.get(midpoint).doubleValue();
        }
    }
    
    public static <T extends Number> T Mode(List<T> dataList) {
        T maxValue = dataList.get(0);
        int maxCount = 0;
        Map<T,Integer> counts = new HashMap<>();

        for(T value: dataList) {
            int oldCount = counts.getOrDefault(value, 0);
            int newCount = oldCount + 1;
            counts.put(value, newCount);
            if (newCount > maxCount) {
                maxCount = newCount;
                maxValue = value;
            }
        }

        return maxValue;
    }

    public static <T extends Number & Comparable<T>> double Range(List<T> dataList) {
        T max = Collections.max(dataList);
        T min = Collections.min(dataList);

        return max.doubleValue() - min.doubleValue();
    }

    public static <T extends Number> double SD(List<T> dataList) {
        double mean = Mean(dataList);
        double stdDev = 0.0;

        for (T num : dataList) {
            stdDev += Math.pow(num.doubleValue() - mean, 2);
        }

        return Math.sqrt(stdDev / dataList.size());
    }
}

