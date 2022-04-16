package ItsAPackage;

import java.util.*;
import java.io.*;
import static ItsAPackage.EmployeeInfo.*;

public class Main {
    public static MainFrame gui;
    public static MyHashTable ht;
    public static int N;
    public static int colorTheme;
    public static File file, systemData;

    public static void main(String[] args) throws IOException {
        systemData = new File("src/ItsAPackage/SystemSettings.txt");
        readSystemData();
        checkFile();
        readFile();
        gui = new MainFrame();
        gui.changeColorTheme(colorTheme);
    }

    public static void checkFile() throws IOException {
        if (!file.exists()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.close();
        }
    }

    public static void readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int index = 0;
        final int BUCKETAMOUNT = 10;
        ht = new MyHashTable(BUCKETAMOUNT);

        while ((line=br.readLine()) != null) {
            String[] data = line.split("Ï");
            if (Integer.parseInt(data[0])==0)
                ht.addToTable(new FTE(Integer.parseInt(data[1]), data[2], data[3], Gender.values()[Integer.parseInt(data[4])], WorkLocation.values()[Integer.parseInt(data[5])], Integer.parseInt(data[6]), Double.parseDouble(data[7])));
            else
                ht.addToTable(new PTE(Integer.parseInt(data[1]), data[2], data[3], Gender.values()[Integer.parseInt(data[4])], WorkLocation.values()[Integer.parseInt(data[5])], Integer.parseInt(data[6]), Double.parseDouble(data[7]), Integer.parseInt(data[8]), Integer.parseInt(data[9])));
            index++;
        }
        br.close();
        N = index;
    }

    public static void readSystemData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(systemData));
        String line = br.readLine();
        colorTheme = Integer.parseInt(line);
        line = br.readLine();
        file = new File("src/ItsAPackage/DataFileFolder/" + line);
    }
}
