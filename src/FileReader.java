
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author eman
 */
public class FileReader {
    public static Object[] read(String filename) throws Exception {
        Scanner scan = new Scanner(new java.io.FileReader(filename));
        ArrayList<String> res = new ArrayList<>();          
        while(scan.hasNext()) {
            res.add(scan.nextLine());
        }
        return res.toArray();
    }
}
