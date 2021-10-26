package tests;

import com.jam.jamcougstatistics.SignificanceCalculator;
import org.junit.jupiter.api.Test;
import com.jam.jamcougstatistics.TScoreSignificanceCalculator;
import static org.junit.jupiter.api.Assertions.*;

class TScoreSignificanceCalculatorTest {

    @Test
    void calculateSignificance() {
        TScoreSignificanceCalculator calc = new TScoreSignificanceCalculator(15);

        assertEquals(.077183f,calc.CalculateSignificance(-1.5f));
        assertEquals(.5f, calc.CalculateSignificance(0.0f));
        assertEquals(.166585f, calc.CalculateSignificance(1f));
    }
}