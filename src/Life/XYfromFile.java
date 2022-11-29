package Life;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class XYfromFile {
    ArrayList<Integer> arrayX = new ArrayList<>();
    ArrayList<Integer> arrayY = new ArrayList<>();
    String fileName;

    public XYfromFile(String fileName) {
        this.fileName = fileName;
        openFile(fileName);
    }

    public void openFile(String fileName){
        try (BufferedReader br1 = new BufferedReader(new FileReader(fileName))) {
            String str;
            String[] arr;
            int i = 0;
            while ((str = br1.readLine()) != null){
                arr = str.split(" ");
                arrayY.add(i, Integer.parseInt(arr[0]));
                arrayX.add(i, Integer.parseInt(arr[1]));
                i++;
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Integer> getArrayX() {
        return arrayX;
    }

    public ArrayList<Integer> getArrayY() {
        return arrayY;
    }
}
