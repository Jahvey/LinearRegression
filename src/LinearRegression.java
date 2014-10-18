public class LinearRegression {
    double[][] features;
    double[][] test;
    double[] output;
    double[] theta;
//    private double alpha = 0.0000000001;
    double alpha = 0.0002;
    int max_steps = 10000;
    double minDeltaJ = 0.0003;
    int number_test_entry = 1500;
    
    public LinearRegression(String input, String separator,String config) throws Exception {
        Object[] obs = FileReader.read(config);
        
        // get value of alpha from config
        String temp = obs[0].toString();
        temp = temp.substring(0, temp.indexOf('#')).trim();
        this.alpha = Double.parseDouble(temp);
        
        // get max steps
        temp = obs[1].toString();
        temp = temp.substring(0, temp.indexOf('#')).trim();
        this.max_steps = Integer.parseInt(temp);
        
        // get min delta J from config
        temp = obs[2].toString();
        temp = temp.substring(0, temp.indexOf('#')).trim();
        this.minDeltaJ = Double.parseDouble(temp);
        System.out.println(this.alpha);
        
        // check if feature scaling enabled
        temp = obs[3].toString();
        temp = temp.substring(0, temp.indexOf('#')).trim();
        boolean scaling_enabled = temp.contentEquals("1");
        
        // check selected features
        temp = obs[4].toString();
        temp = temp.substring(0, temp.indexOf('#')).trim();
        String[] indeces = temp.split(" ");
        int[] feature_index = new int[indeces.length];
        double[] powers = new double[indeces.length];
        for(int i=0; i < indeces.length; i++) {
            String[] parts = indeces[i].split("\\^");
            feature_index[i] = Integer.parseInt(parts[0]);
            if (parts.length > 1) {
                powers[i] = Double.parseDouble(parts[1]);
            }   else {
                powers[i] = 1.0;
            }
        }
        
        Object[] values = FileReader.read(input);
        features = new double[values.length][];
        output = new double[values.length];
        
        // initialize feature values
        for (int i=0; i < values.length; i++) {
            String v = values[i].toString();
            String[] vals = v.split(separator);
            features[i] = new double[feature_index.length + 1];
            features[i][0] = 1.0;
            // initialize theta to 0
            if (theta == null) {
                theta = new double[feature_index.length + 1];                
            }
            // initialize values of features according to the columns specified in the config
            for (int j=0; j < feature_index.length; j++) {
                features[i][j + 1] = Math.pow(Double.parseDouble(vals[feature_index[j] - 1]), powers[j]);
            }
            output[i] = Double.parseDouble(vals[vals.length - 1]);
        }
        if (scaling_enabled) {
            this.features = normalizeAndScale(features);
        }        
    }
    
    
    public void gradientDescend() {
        Graph g = new Graph();
        g.graph();
        
        int steps = 0;
        double prevJ = j(theta, features, output);
        g.add(steps, prevJ);
        while (steps < max_steps) {
            steps++;
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
            prevJ = newJ;
            g.add(steps, newJ);
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
