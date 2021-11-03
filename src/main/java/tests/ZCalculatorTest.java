package tests;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import com.jam.jamcougstatistics.ZCalculator;

import static org.junit.jupiter.api.Assertions.*;

class ZCalculatorTest {

    @Test
    void ZScore() {
        ArrayList<Double> data = new ArrayList<Double>(Arrays.asList(1d,3.0d, 2.5d, 2.0d,4d,5d,1d, 4d,3d,10d,5d,7d,8d,4d,11d,12d,3d,4d,5d,5d,6d,8d,9d,10d,11d,15d,13d,2d));
        float mu = 7f;
        assertEquals(-0.57669f, ZCalculator.ZScore(data,4),0.001f);
    }
}