public class LinearRegression {
    private double[][] features;
    private double[] output;
    private double[] theta;
    
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
                    theta[j] = 1;
                }
            }
            for (int j=0; j < vals.length - 1; j++) {
                features[i][j + 1] = Double.parseDouble(vals[j]);
            }
            output[i] = Double.parseDouble(vals[vals.length - 1]);
        }
        
        double[][] scaled = RegressionUtil.normalizeAndScale(features);
        System.out.println(RegressionUtil.hOfx(theta, scaled[0]));
        
        
    }
    
    
}
