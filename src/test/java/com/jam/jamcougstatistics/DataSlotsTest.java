package com.jam.jamcougstatistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
class DataSlotsTest {

    DataSlots<Integer> emptyDataSlots;
    DataSlots<Integer> nullDataSlots;
    DataSlots<Integer> fullDataSlots;

    static final int nullSlotsSize = 2;
    static final int fullSlotsSize = 10;
    @BeforeEach
    void setup(){
        emptyDataSlots = new DataSlots<>(0);

        nullDataSlots =new DataSlots<>(nullSlotsSize);

        fullDataSlots =new DataSlots<>(fullSlotsSize);

        for(int i = 0; i < fullSlotsSize; i++){
            ArrayList<Integer> slotData = new ArrayList<>();
            slotData.add(i);
            fullDataSlots.slots.add(slotData);
        }
    }

    @Test
    void padToSize() {
        assertEquals(emptyDataSlots.size(),0);
        int small = 10;
        int med = 20;
        int large = 30;

        // Should pad the size
        emptyDataSlots.padToSize(med);
        assertEquals(emptyDataSlots.size(),med);

        // Should not reduce the size
        emptyDataSlots.padToSize(small);
        assertEquals(emptyDataSlots.size(),med);

        // Should increase the size again
        emptyDataSlots.padToSize(large);
        assertEquals(emptyDataSlots.size(),large);
    }

    // clear, setSlot, and get slot were trivial proxies to the ArrayList api
    //    @Test
    //    void clear() { }
    //
    //    @Test
    //    void setSlot() {}
    //
    //    @Test
    //    void getSlot() { }

    @Test
    void slotString() {
        assertEquals("No data",emptyDataSlots.slotString(0));
        assertEquals("No data",emptyDataSlots.slotString(1));
        assertEquals("No data",emptyDataSlots.slotString(2));


        assertEquals("No data",nullDataSlots.slotString(0));
        assertEquals("No data",nullDataSlots.slotString(1));

        assertEquals(fullDataSlots.slots.get(0).toString(),fullDataSlots.slotString(0));
        assertEquals(fullDataSlots.slots.get(1).toString(),fullDataSlots.slotString(1));
        assertEquals(fullDataSlots.slots.get(2).toString(),fullDataSlots.slotString(2));
    }

    @Test
    void hasSlot() {
        // In theory, no slots for the empty slots
        assertFalse(emptyDataSlots.hasSlot(0));
        assertFalse(emptyDataSlots.hasSlot(1));
        assertFalse(emptyDataSlots.hasSlot(2));

        // Null slots should also show up as nonexistent
        assertFalse(nullDataSlots.hasSlot(0));
        assertFalse(nullDataSlots.hasSlot(1));
        // Even past its size
        assertFalse(nullDataSlots.hasSlot(2));

        // Full slots should exist
        assertTrue(fullDataSlots.hasSlot(0));
        assertTrue(fullDataSlots.hasSlot(1));
        assertTrue(fullDataSlots.hasSlot(2));
    }

    @Test
    void addToSlot() {
        int firstAdded = 10;
        int secondAdded = 20;
        int slotIndex = 1;
        emptyDataSlots.addToSlot(slotIndex,firstAdded);
        emptyDataSlots.addToSlot(slotIndex,secondAdded);
        // The empty slots need padding
        assertEquals(slotIndex+1,emptyDataSlots.size());
        // Added elements are first and second in slot
        assertEquals(firstAdded,emptyDataSlots.slots.get(slotIndex).get(0));
        assertEquals(secondAdded, emptyDataSlots.slots.get(slotIndex).get(1));

        nullDataSlots.addToSlot(slotIndex,firstAdded);
        nullDataSlots.addToSlot(slotIndex,secondAdded);
        // Size not changed
        assertEquals(nullSlotsSize,nullDataSlots.size());
        // Added elements are first and second in slot
        assertEquals(firstAdded,nullDataSlots.slots.get(slotIndex).get(0));
        assertEquals(secondAdded,nullDataSlots.slots.get(slotIndex).get(1));



        fullDataSlots.addToSlot(slotIndex,firstAdded);
        fullDataSlots.addToSlot(slotIndex,secondAdded);
        // Size not changed
        assertEquals(fullSlotsSize,fullDataSlots.size());
        // Indecies offset by one because the first space in the slot was already occupied
        assertEquals(firstAdded,fullDataSlots.slots.get(slotIndex).get(1));
        assertEquals(secondAdded,fullDataSlots.slots.get(slotIndex).get(2));
    }

    @Test
    void assignSlotsFrom() {
        emptyDataSlots.assignSlotsFrom(fullDataSlots);
        nullDataSlots.assignSlotsFrom(fullDataSlots);

        // Both should have been padded
        assertEquals(fullDataSlots.size(),emptyDataSlots.size());
        assertEquals(fullDataSlots.size(),nullDataSlots.size());

        assertEquals(fullDataSlots.slots.get(0),emptyDataSlots.slots.get(0));
        assertEquals(fullDataSlots.slots.get(0),nullDataSlots.slots.get(0));

        assertEquals(fullDataSlots.slots.get(1),emptyDataSlots.slots.get(1));
        assertEquals(fullDataSlots.slots.get(1),nullDataSlots.slots.get(1));

        assertEquals(fullDataSlots.slots.get(2),emptyDataSlots.slots.get(2));
        assertEquals(fullDataSlots.slots.get(2),nullDataSlots.slots.get(2));

        assertEquals(fullDataSlots.slots.get(3),emptyDataSlots.slots.get(3));
        assertEquals(fullDataSlots.slots.get(3),nullDataSlots.slots.get(3));
    }
}