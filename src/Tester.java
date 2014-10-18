public class Tester {
    public static void main(String[] args) throws Exception {
        LinearRegression lr = new LinearRegression("DataSet/winequality-red.csv", null);
        lr.gradientDescend();
    }
}
