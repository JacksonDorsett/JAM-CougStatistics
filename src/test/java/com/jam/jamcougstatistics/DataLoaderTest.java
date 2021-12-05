// Tested by Marcel Mukundi using a hybrid of BVA, Characteristic analysis, equivalence classes, and more
package com.jam.jamcougstatistics;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/* These tests are a lot more straightforward as this class mostly just loads numbers from a file.
My coverage report noted that I wasn't exercising the function that loaded from the file, so I
generalized it slightly to all Readers so I could test it as well.

There is really only one method, so each method here will be a singlular test case of the following characteristics:

--- Characteristics ---
 * Left slot:
 * - L[Empty]: No data on the left
 * - L[SomeMissing]: Data on the left, with some gaps
 * - L[NoneMissing]: Data on the left, no gaps
 * Right slot:
 * - R[Empty]: No data on the right
 * - R[SomeMissing]: Data on the right, with some gaps
 * - R[NoneMissing]: Data on the right, no gaps

 And even then, all cases are almost entirely interchangeable
 The work here was mostly just to annotate with the characteristics since
 Java's already done all the parsing work
 */
class DataLoaderTest {

    // (L[Empty], R[Empty])
    @Test
    void testEmptyDataSetFromLines() {
        DataSlots<Double> empty = DataLoader.LoadDataSet(new StringReader(""));
        assertEquals(0,empty.size());
    }

    // (L[NoneMissing], R[NoneMissing])
    @Test
    void testSameSizeDataSetFromLines() {
        String intFile = "1.1, 2.2\n" +
                "3.3, 4.4\n" +
                "5.5, 6.6\n" +
                "7.7, 8.8\n";
        DataSlots<Double> sameSize = DataLoader.LoadDataSet(new StringReader(intFile));
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

    // (L[NoneMissing], R[NoneMissing])
    @Test
    void testSameSizeWithDecimalsDataSetFromLines() {
        String fullFile = "1.1, 2.2\n" +
                "3.3, 4.4\n" +
                "5.5, 6.6\n" +
                "7.7, 8.8\n";
        DataSlots<Double> sameSize = DataLoader.LoadDataSet(new StringReader(fullFile));
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

    // (N[Int], L[SomeMissing], R[SomeMissing])
    @Test
    void testEmptyStringDataSetFromLines() {
        String fileWithHoles =  "\n"+
                "1.1,2.2\n"+
                "\n"+
                "3.3,4.4";

        DataSlots<Double> sameSize = DataLoader.LoadDataSet(new StringReader(fileWithHoles));
        ArrayList<Double> odds = new ArrayList<>();
        odds.add(1.1);
        odds.add(3.3);
        ArrayList<Double> evens = new ArrayList<>();
        evens.add(2.2);
        evens.add(4.4);
        assertEquals(odds,sameSize.getSlot(0));
        assertEquals(evens,sameSize.getSlot(1));
    }

    // (L[SomeMissing], R[SomeMissing])
    @Test
    void testDifferentSizeWithGapsDataSetFromLines() {
        String fileWithHoles =  ", 2.2\n"+
                "3.3, \n"+
                ", 6.6\n"+
                "7.7\n"+
                ",8.8\n";
        DataSlots<Double> differentSize = DataLoader.LoadDataSet(new StringReader(fileWithHoles));
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

    // (L[NoneMissing], R[Empty])
    @Test
    void testLeftSlotDataSetFromLines() {
        String leftSlotFile = "1.1\n"+
                "2.2\n"+
                "3.3\n"+
                "4.4\n"+
                "5.5\n"+
                "6.6\n"+
                "7.7\n"+
                "8.8\n";
        DataSlots<Double> single = DataLoader.LoadDataSet(new StringReader((leftSlotFile)));
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

    // (L[Empty], R[NoneMissing])
    @Test
    void testRightSlotDataSetFromLines() {
        String rightSlotFile = ",1.1\n"+
                ",2.2\n"+
                ",3.3\n"+
                ",4.4\n"+
                ",5.5\n"+
                ",6.6\n"+
                ",7.7\n"+
                ",8.8\n";
        DataSlots<Double> single = DataLoader.LoadDataSet(new StringReader(rightSlotFile));
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
    // (L[NoneMissing], R[SomeMissing])
    @Test
    void testLeftSlotWithSomeRightDataSetFromLines() {
        String leftSlotFile = "1.1\n"+
                "2.2\n"+
                "3.3\n"+
                "4.4,4.4\n"+
                "5.5\n"+
                "6.6,6.6\n"+
                "7.7\n"+
                "8.8\n";
        DataSlots<Double> doubleSlot = DataLoader.LoadDataSet(new StringReader((leftSlotFile)));
        assertEquals(2,doubleSlot.size());
        ArrayList<Double> nums = new ArrayList<>();
        nums.add(1.1);
        nums.add(2.2);
        nums.add(3.3);
        nums.add(4.4);
        nums.add(5.5);
        nums.add(6.6);
        nums.add(7.7);
        nums.add(8.8);

        ArrayList<Double> otherNums = new ArrayList<>();
        otherNums.add(4.4);
        otherNums.add(6.6);
        assertEquals(nums,doubleSlot.getSlot(0));
        assertEquals(otherNums,doubleSlot.getSlot(1));
    }

    // (L[SomeMissing], R[NoneMissing])
    @Test
    void testRightSlotWithSomeLeftDataSetFromLines() {
        String rightSlotFile = ",1.1\n"+
                ",2.2\n"+
                ",3.3\n"+
                "4.4,4.4\n"+
                ",5.5\n"+
                "6.6,6.6\n"+
                ",7.7\n"+
                ",8.8\n";
        DataSlots<Double> doubleSlot = DataLoader.LoadDataSet(new StringReader(rightSlotFile));
        assertEquals(2,doubleSlot.size());
        ArrayList<Double> nums = new ArrayList<>();

        nums.add(4.4);
        nums.add(6.6);
        ArrayList<Double> otherNums = new ArrayList<>();
        otherNums.add(1.1);
        otherNums.add(2.2);
        otherNums.add(3.3);
        otherNums.add(4.4);
        otherNums.add(5.5);
        otherNums.add(6.6);
        otherNums.add(7.7);
        otherNums.add(8.8);


        assertEquals(nums,doubleSlot.getSlot(0));
        assertEquals(otherNums,doubleSlot.getSlot(1));
    }

    // (L[SomeMissing], R[Empty])
    @Test
    void testLeftSlotDataSetWithGapsFromLines() {
        String leftSlotFile = "1.1\n"+
                "2.2\n"+
                "3.3\n"+
                "\n"+
                "5.5\n"+
                "\n"+
                "\n"+
                "8.8\n";
        DataSlots<Double> single = DataLoader.LoadDataSet(new StringReader((leftSlotFile)));
        assertEquals(1,single.size());
        ArrayList<Double> nums = new ArrayList<>();
        nums.add(1.1);
        nums.add(2.2);
        nums.add(3.3);
//        nums.add(4.4);
        nums.add(5.5);
//        nums.add(6.6);
//        nums.add(7.7);
        nums.add(8.8);
        assertEquals(nums,single.getSlot(0));
        assertNull(single.getSlot(1));
    }

    // (L[Empty], R[SomeMissing])
    @Test
    void testRightSlotWithGapsDataSetFromLines() {
        String rightSlotFile = ",1.1\n"+
                ",2.2\n"+
                ",3.3\n"+
                ",\n"+
                ",5.5\n"+
                ",\n"+
                ",\n"+
                ",8.8\n";
        DataSlots<Double> single = DataLoader.LoadDataSet(new StringReader(rightSlotFile));
        assertEquals(2,single.size());
        ArrayList<Double> nums = new ArrayList<>();
        nums.add(1.1);
        nums.add(2.2);
        nums.add(3.3);
//        nums.add(4.4);
        nums.add(5.5);
//        nums.add(6.6);
//        nums.add(7.7);
        nums.add(8.8);
        assertEquals(nums,single.getSlot(1));
        assertNull(single.getSlot(0));
    }
}