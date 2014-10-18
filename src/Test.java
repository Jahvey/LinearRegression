/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author eman
 */
public class Test {
    public static void main(String[] args) throws Exception {
        LinearRegression lr = new LinearRegression("DataSet/ex1data2.txt", ",", "config.cfg");
        lr.gradientDescend();
        RegressionUtil.print(lr.theta);
    }
}
