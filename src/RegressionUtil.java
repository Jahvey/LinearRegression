// NOTE: by matrix or vector we mean array. We use matrix to make it sound more mathematical. :)

public class RegressionUtil {
    
    /* This function accepts a matrix of features from x[0] to x[n].
    Then it returns the normalized and scaled feature matrix.
    */
    public static double[][] normalizeAndScale(double[][] features) {
        double[][] res = new double[features.length][features[0].length];

        // set x[0]s equal to 1
        for (int i=0; i < features.length; i++) {
            res[i][0] = 1.0;
        }
        
        // do normalization and scaling for x[1] to x[n]
        for (int x=1; x < features[0].length; x++) {
            double total = 0;
            double max = features[0][x];
            double min = features[0][x];
            // computes the total of a feature and gets the max and min values
            for (int j=0; j < features.length; j++) {
                total += features[j][x];
                if (features[j][x] > max) {
                    max = features[j][x];
                }
                if (features[j][x] < min) {
                    min = features[j][x];
                }
            }
            double ave = total / features.length;
            // computes the new value of the feature applying mean normalization and scaling
            for (int j=0; j < features.length; j++) {
                res[j][x] = (features[j][x] - ave) / (max - min);
            }
        }
        return res;
    }
    
    /* This function accepts two vectors (thetas and x's). It will return value
    value of h(x) = x[0]*theta[0] + x[1]*theta[1] + ... x[n]*theta[n].
    */
    public static double hOfx(double[] theta, double[] xs) {
        double total = 0;        
        for (int i=0; i < theta.length; i++) {
            total += theta[i] * xs[i];
        }
        return total;
    }
    
    /* This function computes for a J(theta), which is the error. It accepts a 
    theta vector and feature matrix.
    */
    public static double j(double[] theta, double[][] features, double[] output) {
        double res = 0;
        for (int i=0; i < features.length; i++) {
            double hx = hOfx(theta, features[i]);
            res += Math.pow(hx - output[i], 2);
        }
        return res / (2 * features.length);
    }
    
    // delete later
    public static void print(double[][] features) {
        for (int i=0; i < features.length; i++) {
            System.out.print("\n[ ");
            for (int j=0; j < features[i].length; j++) {
                System.out.print(features[i][j] + " ");
            }
            System.out.print("]");
        }
    }
    
    public static void print(double[] output) {
        System.out.print("\n[ ");
        for (int i=0; i < output.length; i++) {
            System.out.print(output[i] + " ");
        }
        System.out.print("]");
    }
}
