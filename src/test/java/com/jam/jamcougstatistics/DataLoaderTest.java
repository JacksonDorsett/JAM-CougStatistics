package com.jam.jamcougstatistics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.spy;

class DataLoaderTest {


    @Test
    void testEmptyDataSetFromLines() {
        DataSlots<Double> empty = DataLoader.DataSetFromLines(new String[]{});
        assertEquals(0,empty.size());
    }
    @Test
    void testSameSizeDataSetFromLines() {
        String[] lines = new String[]{
                "1,2",
                "3,4",
                "5,6",
                "7,8"
        };
        DataSlots<Double> sameSize = DataLoader.DataSetFromLines(lines);
        assertEquals(2,sameSize.size());
        ArrayList<Double> odds = new ArrayList<>();
        odds.add(1.0);
        odds.add(3.0);
        odds.add(5.0);
        odds.add(7.0);
        ArrayList<Double> evens = new ArrayList<>();
        evens.add(2.0);
        evens.add(4.0);
        evens.add(6.0);
        evens.add(8.0);
        assertEquals(odds,sameSize.getSlot(0));
        assertEquals(evens,sameSize.getSlot(1));
    }
    @Test
    void testSameSizeWithDecimalsDataSetFromLines() {
        String[] lines = new String[]{
                "1.1, 2.2",
                "3.3, 4.4",
                "5.5, 6.6",
                "7.7, 8.8"
        };
        DataSlots<Double> sameSize = DataLoader.DataSetFromLines(lines);
        assertEquals(2,sameSize.size());
        ArrayList<Double> odds = new ArrayList<>();
        odds.add(1.1);
        odds.add(3.3);
        odds.add(5.5);
        odds.add(7.7);
        ArrayList<Double> evens = new ArrayList<>();
        evens.add(2.2);
        evens.add(4.4);
        evens.add(6.6);
        evens.add(8.8);
        assertEquals(odds,sameSize.getSlot(0));
        assertEquals(evens,sameSize.getSlot(1));
    }
    @Test
    void testEmptyStringDataSetFromLines() {
        String[] lines = new String[]{
                "",
                "1,2",
                "",
                "3,4",
        };
        DataSlots<Double> sameSize = DataLoader.DataSetFromLines(lines);
        ArrayList<Double> odds = new ArrayList<>();
        odds.add(1.0);
        odds.add(3.0);
        ArrayList<Double> evens = new ArrayList<>();
        evens.add(2.0);
        evens.add(4.0);
        assertEquals(odds,sameSize.getSlot(0));
        assertEquals(evens,sameSize.getSlot(1));
    }
    @Test
    void testDifferentSizeWithGapsDataSetFromLines() {
        String[] lines = new String[]{
                ", 2.2",
                "3.3, ",
                ", 6.6",
                "7.7",
                ",8.8"
        };
        DataSlots<Double> differentSize = DataLoader.DataSetFromLines(lines);
        assertEquals(2,differentSize.size());
        ArrayList<Double> odds = new ArrayList<>();
        odds.add(3.3);
        odds.add(7.7);
        ArrayList<Double> evens = new ArrayList<>();
        evens.add(2.2);
        evens.add(6.6);
        evens.add(8.8);
        assertEquals(odds,differentSize.getSlot(0));
        assertEquals(evens,differentSize.getSlot(1));
    }
    @Test
    void testLeftSlotDataSetFromLines() {
        String[] lines = new String[]{
                "1.1",
                "2.2",
                "3.3",
                "4.4",
                "5.5",
                "6.6",
                "7.7",
                "8.8"
        };
        DataSlots<Double> single = DataLoader.DataSetFromLines(lines);
        assertEquals(1,single.size());
        ArrayList<Double> nums = new ArrayList<>();
        nums.add(1.1);
        nums.add(2.2);
        nums.add(3.3);
        nums.add(4.4);
        nums.add(5.5);
        nums.add(6.6);
        nums.add(7.7);
        nums.add(8.8);
        assertEquals(nums,single.getSlot(0));
        assertNull(single.getSlot(1));
    }
    @Test
    void testRightSlotDataSetFromLines() {
        String[] lines = new String[]{
                ",1.1",
                ",2.2",
                ",3.3",
                ",4.4",
                ",5.5",
                ",6.6",
                ",7.7",
                ",8.8"
        };
        DataSlots<Double> single = DataLoader.DataSetFromLines(lines);
        assertEquals(2,single.size());
        ArrayList<Double> nums = new ArrayList<>();
        nums.add(1.1);
        nums.add(2.2);
        nums.add(3.3);
        nums.add(4.4);
        nums.add(5.5);
        nums.add(6.6);
        nums.add(7.7);
        nums.add(8.8);
        assertEquals(nums,single.getSlot(1));
        assertNull(single.getSlot(0));
    }
}