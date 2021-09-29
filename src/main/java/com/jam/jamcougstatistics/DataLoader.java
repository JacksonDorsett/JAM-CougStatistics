package com.jam.jamcougstatistics;

import java.io.*;
import java.util.ArrayList;

public class DataLoader {
    public static boolean IsValid(File file) throws FileNotFoundException {
        InputStream ifstream = new FileInputStream(file);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);

        for (Object line : reader.lines().toArray()) {
            String l = line.toString();
            if (l.split(",").length > 2) {
                return false;
            }
            for (String entry : l.split(",")) {
                if (entry.length() == 0) {
                    continue;
                }
                try {
                    Integer.decode(entry);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    //Assume that stream has been validated
    public static int CountSets(File file) throws FileNotFoundException {
        InputStream ifstream = new FileInputStream(file);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);
        int max = 0;
        for (String line : reader.lines().toArray(String[]::new)) {
            int len = line.split(",").length;
            if (len > max) max = len;
        }
        return max;
    }

    public static ArrayList<Double> LoadSingleDataSet(File file) throws Exception {
        InputStream ifstream = new FileInputStream(file);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);
        ArrayList<Double> ret = new ArrayList<>();
        for (String line : reader.lines().toArray(String[]::new)) {
            try {
                ret.add(Double.parseDouble(line));
            } catch (Exception e) {
                throw new Exception();
            }
        }
        return ret;
    }

    public static DataSlots<Double> LoadDataSet(File file) throws Exception {
        InputStream ifstream = new FileInputStream(file);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);

        DataSlots<Double> data = new DataSlots<>(2);
        for (String line : reader.lines().toArray(String[]::new)) {
            String[] splitLine = line.split(",");
            for (int i = 0; i < splitLine.length; i++) {
                if (splitLine[i].length() == 0) {
                    continue;
                }
                data.addToSlot(i, Double.parseDouble(splitLine[i]));
            }
        }
        return data;
    }

    public static DataSlots<Double> LoadDoubleDataSet(File file) throws Exception {
        InputStream ifstream = new FileInputStream(file);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);

        DataSlots<Double> data = new DataSlots<>(2);
        for (String line : reader.lines().toArray(String[]::new)) {
            String[] splitLine = line.split(",");
            if (splitLine.length != 2) throw new Exception();
            for (int i = 0; i < splitLine.length; i++) {
                if (splitLine[i].length() == 0) {
                    continue;
                }
                data.addToSlot(i, Double.parseDouble(splitLine[i]));
            }
        }
        return data;
    }
}