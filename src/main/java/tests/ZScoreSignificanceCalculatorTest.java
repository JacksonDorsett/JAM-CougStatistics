package tests;

import com.jam.jamcougstatistics.ZScoreSignificanceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZScoreSignificanceCalculatorTest {

    @Test
    void calculateSignificance() {
        ZScoreSignificanceCalculator calc = new ZScoreSignificanceCalculator();

        assertEquals(.5f, calc.CalculateSignificance(0), .001f);
        assertEquals(0.84134f, calc.CalculateSignificance(1), .001f);
        assertEquals(0.97725f, calc.CalculateSignificance(2), .001f);
        assertEquals(0.15866f, calc.CalculateSignificance(-1), .001f);
    }
}