
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
        JFileChooser chooser = new JFileChooser(".");
        chooser.setDialogTitle("Input File");
        int option = chooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getPath();
            String separator = JOptionPane.showInputDialog(null, "Character separator in the input file:");
            
            chooser = new JFileChooser(".");
            chooser.setDialogTitle("Config File");
            option = chooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                String config_path = chooser.getSelectedFile().getPath();
                LinearRegression lr = new LinearRegression(filename, separator, config_path);
                lr.gradientDescend();
                RegressionUtil.print(lr.theta);
            }
        }        
    }
}
