package com.jam.jamcougstatistics;

import java.io.*;
import java.util.ArrayList;

public class DataLoader {
    public static class DataSlots<T extends Number> {
        ArrayList<ArrayList<T>> slots;

        DataSlots(int size) {
            slots = new ArrayList<>(size);
        }

        public void padToSize(int size) {
            for (int i = slots.size(); i < size; i++)
                slots.add(null);
        }

        public void clear() {
            slots.clear();
        }

        public void setSlot(int slotIndex, ArrayList<T> values) {
            padToSize(slotIndex + 1);
            slots.set(slotIndex, values);
        }

        public boolean hasSlot(int slotIndex) {
            return (slotIndex < slots.size()) && (slots.get(slotIndex) != null);
        }

        public void addToSlot(int slotIndex, T t) {
            // Make sure the list is long enough
            padToSize(slotIndex + 1);

            // Make sure the slot is initialized
            ArrayList<T> values = slots.get(slotIndex);
            if (values == null) {
                values = new ArrayList<>();
                slots.set(slotIndex, values);
            }
            // Add to the slot
            values.add(t);
        }


        public void assignSlotsFrom(DataSlots<T> otherSlots) {
            int otherSize = otherSlots.slots.size();
            int thisSize = slots.size();
            int minSize = Math.min(otherSize, thisSize);
            for (int overlapSlotIndex = 0; overlapSlotIndex < minSize; overlapSlotIndex++) {
                if (otherSlots.hasSlot(overlapSlotIndex)) {
                    slots.set(overlapSlotIndex, otherSlots.slots.get(overlapSlotIndex));
                }
            }
            for (int extraSlotIndex = otherSize; extraSlotIndex < thisSize; extraSlotIndex++) {
                slots.add(otherSlots.slots.get(extraSlotIndex));
            }
        }
    }

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

    public static ArrayList<Integer> LoadSingleDataSet(File file) throws Exception {
        InputStream ifstream = new FileInputStream(file);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);
        ArrayList<Integer> ret = new ArrayList<>();
        for (String line : reader.lines().toArray(String[]::new)) {
            try {
                ret.add(Integer.parseInt(line));
            } catch (Exception e) {
                throw new Exception();
            }
        }
        return ret;
    }

    public static DataSlots<Integer> LoadDataSet(File file) throws Exception {
        InputStream ifstream = new FileInputStream(file);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);

        DataSlots<Integer> data = new DataSlots<>(2);
        for (String line : reader.lines().toArray(String[]::new)) {
            String[] splitLine = line.split(",");
            for (int i = 0; i < splitLine.length; i++) {
                if (splitLine[i].length() == 0) {
                    continue;
                }
                data.addToSlot(i, Integer.parseInt(splitLine[i]));
            }
        }
        return data;
    }

    public static DataSlots<Integer> LoadDoubleDataSet(File file) throws Exception {
        InputStream ifstream = new FileInputStream(file);
        InputStreamReader ireader = new InputStreamReader(ifstream);
        BufferedReader reader = new BufferedReader(ireader);

        DataSlots<Integer> data = new DataSlots<>(2);
        for (String line : reader.lines().toArray(String[]::new)) {
            String[] splitLine = line.split(",");
            if (splitLine.length != 2) throw new Exception();
            for (int i = 0; i < splitLine.length; i++) {
                if (splitLine[i].length() == 0) {
                    continue;
                }
                data.addToSlot(i, Integer.parseInt(splitLine[i]));
            }
        }
        return data;
    }
}