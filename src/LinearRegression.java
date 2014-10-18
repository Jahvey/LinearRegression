public class LinearRegression {
    private double[][] features;
    private double[][] test;
    private double[] output;
    private double[] theta;
    private double alpha = 0.0001;
    private int max_steps = 10000;
    private double minDeltaJ = 0.0003;
    private int number_test_entry = 1500;
    
    public LinearRegression(String input, String config) throws Exception {
        Object[] values = FileReader.read(input);
        features = new double[values.length][];
        output = new double[values.length];
        // initialize feature values
        // todo do something with config
        for (int i=0; i < values.length; i++) {
            String v = values[i].toString();
            String[] vals = v.split(";");
            features[i] = new double[vals.length];
            features[i][0] = 1.0;
            if (theta == null) {
                theta = new double[vals.length];
                for (int j=0; j < vals.length; j++) {
                    theta[j] = 0;
                }
            }
            for (int j=0; j < vals.length - 1; j++) {
                features[i][j + 1] = Double.parseDouble(vals[j]);
            }
            output[i] = Double.parseDouble(vals[vals.length - 1]);
        }
        
        double[][] scaled = normalizeAndScale(features);        
        
    }
    
    public void gradientDescend() {
        int steps = 0;
        double prevJ = j(theta, features, output);
        while (steps < max_steps) {
            steps++;
            System.out.println(steps);
            double[] hMinusY = new double[features.length];
            for (int i=0; i < features.length; i++) {
                hMinusY[i] = hOfx(theta, features[i]) - output[i];
            }
            
            double[] newTheta = new double[theta.length];
            for (int i=0; i < theta.length; i++) {
                newTheta[i] = theta[i] - alpha * gradient(hMinusY, features, i);
            }
            theta = newTheta;
            double newJ = j(theta, features, output);
            if (Math.abs(prevJ - newJ) < minDeltaJ) {
                System.out.println("prev: " + prevJ);
                System.out.println("new: " + newJ);
                System.out.println("diff: " + (prevJ - newJ));
                System.out.println("End gd a j = " + newJ);
                return;
            }
            System.out.println(newJ);
            prevJ = newJ;
        }
        System.out.println("End after " + steps + " steps");
        System.out.println("with " + prevJ + " error");
    }
    
    /* This function will compute for the partial derivative of J with respect of
    theta[i], which is the gradient. It accepts the h(x) - y for all data entry, 
    the matrix feature, and the current index of theta we are updating.
    */
    public double gradient(double[] hMinusY, double[][] features, int curJ) {
        double res = 0;
        for (int i=0; i < hMinusY.length; i++) {
            res += hMinusY[i] * features[i][curJ];
        }
        return res / hMinusY.length;
    }
    
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
