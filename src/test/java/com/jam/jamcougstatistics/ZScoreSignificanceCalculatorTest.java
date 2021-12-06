// Tested by Anthony Nguyen

package com.jam.jamcougstatistics;

import com.jam.jamcougstatistics.ZScoreSignificanceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZScoreSignificanceCalculatorTest {

    // Trivial as the only argument is a given floating point number. Any input should work and any output is checked against the actual
    // expected zscore significance value
    @Test
    void calculateSignificance1() {
        ZScoreSignificanceCalculator calc = new ZScoreSignificanceCalculator();
        assertEquals(.5f, calc.CalculateSignificance(0), .001f);
    }

    @Test
    void calculateSignificance2() {
        ZScoreSignificanceCalculator calc = new ZScoreSignificanceCalculator();
        assertEquals(0.84134f, calc.CalculateSignificance(1), .001f);
    }

    @Test
    void calculateSignificance3() {
        ZScoreSignificanceCalculator calc = new ZScoreSignificanceCalculator();
        assertEquals(0.97725f, calc.CalculateSignificance(2), .001f);
    }

    @Test
    void calculateSignificance4() {
        ZScoreSignificanceCalculator calc = new ZScoreSignificanceCalculator();
        assertEquals(0.15866f, calc.CalculateSignificance(-1), .001f);
    }

    @Test
    void calculateSignificance5() {
        ZScoreSignificanceCalculator calc = new ZScoreSignificanceCalculator();
        assertEquals(0, calc.CalculateSignificance(-10000), .001f);
    }
}