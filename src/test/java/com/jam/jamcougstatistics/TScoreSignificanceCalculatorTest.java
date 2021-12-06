// Tested by Anthony Nguyen

package com.jam.jamcougstatistics;

import com.jam.jamcougstatistics.SignificanceCalculator;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.junit.jupiter.api.Test;
import com.jam.jamcougstatistics.TScoreSignificanceCalculator;
import static org.junit.jupiter.api.Assertions.*;

class TScoreSignificanceCalculatorTest {

    // CASE 1: Given degree of freedom is positive
    @Test
    void calculateSignificance() {
        TScoreSignificanceCalculator calc = new TScoreSignificanceCalculator(15);

        assertEquals(.07718333019085027,calc.CalculateSignificance(-1.5f));
    }

    // CASE 2: Given degree of freedom is not positive
    @Test
    void calculateDFZero() {
        TScoreSignificanceCalculator calc = new TScoreSignificanceCalculator(0);
        assertThrows(NotStrictlyPositiveException.class, () -> {calc.CalculateSignificance(-1.5f);});
    }
}