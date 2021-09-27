/*
Written by Anthony Nguyen
Z-score calculator
Does exactly what you think
*/
import java.util.List;

public class ZCalculator    {

    /* 
        Takes in a list of floats as the sample, and an observed value x. Proceeds to calculate the z score using the given input.
    */
    public static float ZScore(List<Float> input, float x)  {

        // Calculate the sum of the input list through an elementary for loop
        // I'm sure there's a better way but I'm just too lazy
        float sum = 0;
        for (float i : input)
            sum += i;
        
        // Calculate the mean through a hyper advanced formula beyond comprehension
        float mean = sum / input.size();

        //System.out.println("Mean\t:\t" + mean); //  DEBUG

        // Calculating the standard deviation
        float deviations = 0;
        for(float i : input)
            deviations += Math.pow(i - mean, 2);

        float sd = (float) Math.sqrt(deviations / input.size());

        //System.out.println("Standard Deviation\t:\t" + sd); //  DEBUG

        // Calculate the z score. Observed value (x) minus the mean, divided by the standard deviation
        return (x - mean)/sd;
    }

}