/*
Written by Anthony Nguyen
T-Score calculator
It can shine your shoes, wash your car, and do the dishes. Cannot calculate a t score.
*/
import java.util.List;

public class TCalculator {

    // Takes a list of floats as input (the sample) and mu, the population mean
    public static float TScore(List<Float> input, float mu)  {

        // Calculate the sum of the input list through an elementary for loop
        // I'm sure there's a better way but I'm just too lazy
        float sum = 0;
        for (float i : input)
            sum += i;
        
        // Calculate the mean through a hyper advanced formula beyond comprehension
        float mean = sum / input.size();

        // Calculating the sample standard deviation
        float deviations = 0;
        for(float i : input)
            deviations += Math.pow(i - mean, 2);

        // Note. Sample Std is where the deviations are divided by n-1 rather than n
        float sd = (float) Math.sqrt(deviations / (input.size() - 1));

        // T score formula. Sample mean minus population mean, divided by sample std divided by square root of sample size n
        return (float)((mean - mu)/(sd/Math.sqrt(input.size())));
    }
    
}
