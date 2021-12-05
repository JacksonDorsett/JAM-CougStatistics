// Tested by Marcel Mukundi using a hybrid of BVA, Characteristic analysis, equivalence classes, and more
package com.jam.jamcougstatistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/** Some decisions, while making the tests slightly bulkier, were made for the
betterment of the tests

For example, in many cases the cross product of these sets:
- I =  I[Negative], I[Middle], I[Past]
- V = V[Null], V[NonNull]
would have covered all cases and branches
the cases included cover all boundaries, making it more robust
to my code's faulty logic
 */
class DataSlotsTest {

    DataSlots<Integer> emptyDataSlots;
    DataSlots<Integer> nullDataSlots;
    DataSlots<Integer> fullDataSlots;

    static final int nonEmptySlotsOriginalSize = 10;

    static final int negativeIndex = -1;
    static final int firstIndex = 0;
    static final int middleIndex = 5;
    static final int lastIndex = nonEmptySlotsOriginalSize-1;
    static final int pastIndex = nonEmptySlotsOriginalSize+1;

    @BeforeEach
    void setup(){
        emptyDataSlots = new DataSlots<>(0);

        nullDataSlots =new DataSlots<>(nonEmptySlotsOriginalSize);
        for(int i = 0; i < nonEmptySlotsOriginalSize; i++){
            nullDataSlots.slots.add(null);
        }

        fullDataSlots =new DataSlots<>(nonEmptySlotsOriginalSize);
        for(int i = 0; i < nonEmptySlotsOriginalSize; i++){
            ArrayList<Integer> slotData = new ArrayList<>();
            slotData.add(i);
            fullDataSlots.slots.add(slotData);
        }
    }

    @Test
    void testPadToSize() {
        /* --- Characteristics ---
         * Current slots length:
         * - L[Zero]: Zero
         * - L[NonZero]: Non-zero
         * Size to pad to:
         * -S[Negative]: Negative (Do nothing - Technically invalid)
         * -S[LessThan] Less than current slots length (Do nothing)
         * -S[Zero]: Zero (Do nothing) [Boundary]
         * -S[Length] Current slots length (Do nothing) [Boundary]
         * -S[BiggerThan] More than slots length (Extend slots slots size)
         */

        /* --- L[Zero] Tests ---
         * - Here S[Negative] and S[LessThan] are merged together as an equivalence class, S[Negative]*
         * - Here S[Zero] and S[Length] are merged together as an equivalence class, S[Zero]*
         * */
        // (L[Zero], S[Negative]*)
        // Should do nothing
        emptyDataSlots.padToSize(negativeIndex);
        assertEquals(emptyDataSlots.size(),0);

        // (L[Zero], S[Zero]*)
        // Should do nothing
        emptyDataSlots.padToSize(firstIndex);
        assertEquals(emptyDataSlots.size(),0);

        // (L[Zero], S[BiggerThan])
        // Should extend slots
        emptyDataSlots.padToSize(10);
        assertEquals(emptyDataSlots.size(),10);


        /*--- L[NonZero] Tests --- */
        // (L[NonZero], S[Negative])
        // Should do nothing
        fullDataSlots.padToSize(negativeIndex);
        assertEquals(fullDataSlots.size(), nonEmptySlotsOriginalSize);

        // (L[NonZero], S[Zero])
        // Should do nothing
        fullDataSlots.padToSize(firstIndex);
        assertEquals(fullDataSlots.size(), nonEmptySlotsOriginalSize);

        // (L[NonZero], S[LessThan])
        // Should do nothing
        fullDataSlots.padToSize(middleIndex);
        assertEquals(fullDataSlots.size(), nonEmptySlotsOriginalSize);

        // (L[NonZero], S[Length])
        // Should do nothing
        fullDataSlots.padToSize(nonEmptySlotsOriginalSize);
        assertEquals(fullDataSlots.size(), nonEmptySlotsOriginalSize);

        // (L[NonZero], S[BiggerThan])
        // Should extend slots
        fullDataSlots.padToSize(pastIndex);
        assertEquals(fullDataSlots.size(), pastIndex);
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
    void testSlotString() {
        /* --- Characteristics ---
         * Slots length:
         * - L[Zero]: Zero
         * - L[NonZero]: Non-zero
         * Index:
         * -I[Negative] Negative (Invalid)
         * -I[First] FirstItem (Valid) [Boundary]
         * -I[Middle] Middle of array (Valid)
         * -I[Last] LastItem (Valid) [Boundary]
         * -I[Past] Past array length (Invalid)
         * Slot value:
         * -V[Invalid] InvalidSlot (Throw IndexOutOfBoundsException) [Only for I[Negative], I[Past]]
         * -V[Null] Null (Return "No data") [Not for I[Negative], I[Past]]
         * --V[NonNull] Non-null (Return a string different from "No data")  [Not for I[Negative], I[Past]]
         */

        /* --- L[Zero] Tests ---
         * - Here all Index characteristics are
         *         merged together as equivalence classes: I-, I0, I+
         * - Here all Value characteristics are merged together as an equivalence class, V*
         * */

        // The tests on I[Negative] helped me notice that negative indices did not return
        // "No data", as I had assumed, but rather threw an IndexOutOfBoundsException.
        // I thought it best to leave it as such since the index is, indeed, out of bounds
        // and, unlike indices that are too large, can never be in bounds
        // Change:
        // assertEquals("No data",slots.slotString(negativeIndex));
        //    ----> assertThrows(IndexOutOfBoundsException.class,()->slots.slotString(negativeIndex));


        // (L[Zero], I-, V*)
        // Throws IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class,()->emptyDataSlots.slotString(negativeIndex));
        // (L[Zero], I0, V*)
        // Return No Data
        assertEquals("No data",emptyDataSlots.slotString(firstIndex));
        // (L[Zero], I+, V*)
        // Return No Data
        assertEquals("No data",emptyDataSlots.slotString(middleIndex));

        /* --- L[NonZero] Tests --- */
        // (L[NonZero], I[Negative], V[Invalid])
        // Return No Data
        assertThrows(IndexOutOfBoundsException.class,()->fullDataSlots.slotString(negativeIndex));

        // (L[NonZero], I[First], V[NonNull])
        // Return String
        assertEquals(fullDataSlots.slots.get(firstIndex).toString(),fullDataSlots.slotString(firstIndex));
        // (L[NonZero], I[First], V[Null])
        // Return No Data
        assertEquals("No data",nullDataSlots.slotString(firstIndex));

        // (L[NonZero], I[Middle], V[NonNull])
        // Return String
        assertEquals(fullDataSlots.slots.get(middleIndex).toString(),fullDataSlots.slotString(middleIndex));
        // (L[NonZero], I[Middle], V[Null])
        // Return No Data
        assertEquals("No data",nullDataSlots.slotString(middleIndex));

        // (L[NonZero], I[Last], V[NonNull])
        // Return String
        assertEquals(fullDataSlots.slots.get(lastIndex).toString(),fullDataSlots.slotString(lastIndex));
        // (L[NonZero], I[Last], V[Null])
        // Return No Data
        assertEquals("No data",nullDataSlots.slotString(lastIndex));

        // (L[NonZero], I[Past], V[Invalid])
        // Return No Data
        assertEquals("No data",fullDataSlots.slotString(pastIndex));
    }

    @Test
    void testHasSlot() {
        /* --- Characteristics ---
         * Slots length:
         * - L[Zero]: Zero
         * - L[NonZero]: Non-zero
         * Index:
         * -I[Negative] Negative (Invalid)
         * -I[First] FirstItem (Valid) [Boundary]
         * -I[Middle] Middle of array (Valid)
         * -I[Last] LastItem (Valid) [Boundary]
         * -I[Past] Past array length (Invalid)
         * Slot value:
         * -V[Invalid] InvalidSlot (Return False) [Only for I[Negative], I[Past]]
         * -V[Null] Null (Return False) [Not for I[Negative], I[Past]]
         * --V[NonNull] Non-null (Return True)  [Not for I[Negative], I[Past]]
         */

        // The tests on I[Negative] helped me notice that negative indices did not return
        // false, as I had assumed, but rather threw an IndexOutOfBoundsException.
        // I thought it best to leave it as such since the index is, indeed, out of bounds
        // and, unlike indices that are too large, can never be in bounds
        // Change:
        // assertEquals(false,slots.hasSlot(negativeIndex));
        //    ----> assertThrows(IndexOutOfBoundsException.class,()->slots.hasSlot(negativeIndex));

        // I was also able to catch an `assertFalse` which should have been an `assertTrue` while testing

        /* --- L[Zero] Tests ---
         * - Here all Index characteristics are
         *         merged together as equivalence classes: I-, I0, I+
         * - Here all Value characteristics are merged together as an equivalence class, V*
         * */
        // (L[Zero], I-, V*)
        // Throws IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class,()->emptyDataSlots.hasSlot(negativeIndex));
        // (L[Zero], I0, V*)
        // False
        assertFalse(emptyDataSlots.hasSlot(firstIndex));
        // (L[Zero], I+, V*)
        // False
        assertFalse(emptyDataSlots.hasSlot(middleIndex));


        /* --- L[NonZero] Tests --- */
        // (L[NonZero], I[Negative], V[Invalid])
        // Throws IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class,()->fullDataSlots.hasSlot(negativeIndex));

        // (L[NonZero], I[First], V[NonNull])
        // True
        assertTrue(fullDataSlots.hasSlot(firstIndex));
        // (L[NonZero], I[First], V[Null])
        // False
        assertFalse(nullDataSlots.hasSlot(firstIndex));

        // (L[NonZero], I[Middle], V[NonNull])
        // True
        assertTrue(fullDataSlots.hasSlot(middleIndex));
        // (L[NonZero], I[Middle], V[Null])
        // False
        assertFalse(nullDataSlots.hasSlot(middleIndex));

        // (L[NonZero], I[Last], V[NonNull])
        // True
        assertTrue(fullDataSlots.hasSlot(lastIndex));
        // (L[NonZero], I[Last], V[Null])
        // False
        assertFalse(nullDataSlots.hasSlot(lastIndex));

        // (L[NonZero], I[Past], V[Invalid])
        // False
        assertFalse(fullDataSlots.hasSlot(pastIndex));
    }

    @Test
    void testAddToSlot() {
        /* --- Characteristics ---
         * Slots length:
         * - L[Zero]: Zero
         * - L[NonZero]: Non-zero
         * Index:
         * -I[Negative] Negative (Invalid)
         * -I[First] FirstItem (Valid) [Boundary]
         * -I[Middle] Middle of array (Valid)
         * -I[Last] LastItem (Valid) [Boundary]
         * -I[Past] Past array length (Invalid)
         * Slot value:
         * -V[Invalid] InvalidSlot (Do nothing) [Only for I[Negative]]
         * -V[Null] Null (Create slot, add to slot) [Not for I[Negative]]
         * -V[NonNull] Non-null (Add to slot)  [Not for I[Negative]]
         */

        // The tests on I[Negative] helped me notice that negative indices did not do nothing,
        // as I had assumed, but rather threw an IndexOutOfBoundsException.
        // I thought it best to leave it as such since the index is, indeed, out of bounds
        // and, unlike indices that are too large, can never be in bounds
        // Change:
        //  emptyDataSlots.addToSlot(slotIndex, firstValue);
        //  emptyDataSlots.addToSlot(slotIndex, secondValue);;
        //    ----> assertThrows(IndexOutOfBoundsException.class,()->{
        //              emptyDataSlots.addToSlot(slotIndex, firstValue);
        //              emptyDataSlots.addToSlot(slotIndex, secondValue);
        //    });


        /* --- L[Zero] Tests ---
         * - Here all Index characteristics are
         *         merged together as equivalence classes: I-, I0, I+
         * - Here all Value characteristics are merged together as an equivalence class, V*
         * */

        int firstValue = 10;
        int secondValue = 20;
        int slotIndex;
        ArrayList<Integer> existingSlot;
        ArrayList<Integer> createdSlot;

        // (L[Zero], I-, V*)
        // Throws IndexOutOfBoundsException
        emptyDataSlots.clear();
        assertThrows(IndexOutOfBoundsException.class,()->{
              emptyDataSlots.addToSlot(negativeIndex, firstValue);
              emptyDataSlots.addToSlot(negativeIndex, secondValue);
        });
        assertEquals(0,emptyDataSlots.size());

        // (L[Zero], I0, V*)
        // Create a slot at position zero, and add to it
        emptyDataSlots.clear();
        slotIndex = firstIndex;
        // Values added, in correct order
        emptyDataSlots.addToSlot(slotIndex, firstValue);
        emptyDataSlots.addToSlot(slotIndex, secondValue);
        assertEquals(1,emptyDataSlots.size());
        // Slot was actually created
        createdSlot = emptyDataSlots.slots.get(slotIndex);
        assertNotNull(createdSlot);
        // Values added, in correct order
        assertEquals(firstValue,createdSlot.get(0));
        assertEquals(secondValue, createdSlot.get(1));

        // (L[Zero], I+, V*)
        // Pad up to the given value, create a slot, and add to it
        emptyDataSlots.clear();
        slotIndex = pastIndex;
        emptyDataSlots.addToSlot(slotIndex, firstValue);
        emptyDataSlots.addToSlot(slotIndex, secondValue);
        // Slot was actually created
        createdSlot = emptyDataSlots.slots.get(slotIndex);
        assertNotNull(createdSlot);
        // Values added, in correct order
        assertEquals(firstValue,createdSlot.get(0));
        assertEquals(secondValue, createdSlot.get(1));


        /* --- L[NonZero] Tests --- */
        // (L[NonZero], I[Negative], V[Invalid])
        // Do nothing
        assertThrows(IndexOutOfBoundsException.class,()->{
            fullDataSlots.addToSlot(negativeIndex, firstValue);
            fullDataSlots.addToSlot(negativeIndex, secondValue);
        });
        assertEquals(nonEmptySlotsOriginalSize, fullDataSlots.size());

        // (L[NonZero], I[First], V[NonNull])
        // Get the existing slot and add to it
        slotIndex = firstIndex;
        existingSlot = fullDataSlots.slots.get(slotIndex);
        fullDataSlots.addToSlot(slotIndex, firstValue);
        fullDataSlots.addToSlot(slotIndex, secondValue);
        // Slot was reused, not replaced
        createdSlot = fullDataSlots.slots.get(slotIndex);
        assertEquals(existingSlot,createdSlot);
        // Values added, in correct order
        // fullDataSlots already contains values, so the
        // indicies are offset by one
        assertEquals(firstValue, createdSlot.get(1));
        assertEquals(secondValue, createdSlot.get(2));

        // (L[NonZero], I[First], V[Null])
        // Create a slot at position zero, and add to it
        nullDataSlots.addToSlot(slotIndex, firstValue);
        nullDataSlots.addToSlot(slotIndex, secondValue);
        // Slot was actually created
        createdSlot = nullDataSlots.slots.get(slotIndex);
        assertNotNull(nullDataSlots.slots.get(slotIndex));
        // Values added, in correct order
        assertEquals(firstValue, createdSlot.get(0));
        assertEquals(secondValue, createdSlot.get(1));


        // (L[NonZero], I[Middle], V[NonNull])
        // Get the existing slot and add to it
        slotIndex = middleIndex;
        existingSlot = fullDataSlots.slots.get(slotIndex);
        fullDataSlots.addToSlot(slotIndex, firstValue);
        fullDataSlots.addToSlot(slotIndex, secondValue);
        // Slot was reused, not replaced
        createdSlot = fullDataSlots.slots.get(slotIndex);
        assertEquals(existingSlot,createdSlot);
        // Values added, in correct order
        // fullDataSlots already contains values, so the
        // indicies are offset by one
        assertEquals(firstValue, createdSlot.get(1));
        assertEquals(secondValue, createdSlot.get(2));

        // (L[NonZero], I[Middle], V[Null])
        // Create a slot at middle position, and add to it
        nullDataSlots.addToSlot(slotIndex, firstValue);
        nullDataSlots.addToSlot(slotIndex, secondValue);
        // Slot was actually created
        createdSlot = nullDataSlots.slots.get(slotIndex);
        assertNotNull(nullDataSlots.slots.get(slotIndex));
        // Values added, in correct order
        assertEquals(firstValue, createdSlot.get(0));
        assertEquals(secondValue, createdSlot.get(1));


        // (L[NonZero], I[Last], V[NonNull])
        // Get the existing slot and add to it
        slotIndex = lastIndex;
        existingSlot = fullDataSlots.slots.get(slotIndex);
        fullDataSlots.addToSlot(slotIndex, firstValue);
        fullDataSlots.addToSlot(slotIndex, secondValue);
        // Slot was reused, not replaced
        createdSlot = fullDataSlots.slots.get(slotIndex);
        assertEquals(existingSlot,createdSlot);
        // Values added, in correct order
        // fullDataSlots already contains values, so the
        // indicies are offset by one
        assertEquals(firstValue, createdSlot.get(1));
        assertEquals(secondValue, createdSlot.get(2));

        // (L[NonZero], I[Last], V[Null])
        // Create a slot at last position, and add to it
        nullDataSlots.addToSlot(slotIndex, firstValue);
        nullDataSlots.addToSlot(slotIndex, secondValue);
        // Slot was actually created
        createdSlot = nullDataSlots.slots.get(slotIndex);
        assertNotNull(nullDataSlots.slots.get(slotIndex));
        // Values added, in correct order
        assertEquals(firstValue, createdSlot.get(0));
        assertEquals(secondValue, createdSlot.get(1));

        // (L[NonZero], I[Past], V[Invalid])
        // Pad to that slot, Create a slot at last position, and add to it
        slotIndex = pastIndex;
        nullDataSlots.addToSlot(slotIndex, firstValue);
        nullDataSlots.addToSlot(slotIndex, secondValue);
        // Slots were padded
        assertEquals(pastIndex+1, nullDataSlots.slots.size());
        // Slot was actually created
        createdSlot = nullDataSlots.slots.get(slotIndex);
        assertNotNull(nullDataSlots.slots.get(slotIndex));
        // Values added, in correct order
        assertEquals(firstValue, createdSlot.get(0));
        assertEquals(secondValue, createdSlot.get(1));
    }

    @Test
    void testAssignSlotsFrom() {
        /* --- Characteristics ---
         * Destination Slots length:
         * - D[Zero]: Zero
         * - D[NonZero]: Non-zero
         * Source Slots length:
         * - S[Zero]: Empty slots (Do nothing)
         * - S[Smaller]: Smaller than first slots (Only assign as many as in source)
         * - S[Same]: Same size as first slots (Assign all in source)
         * - S[Longer]: Longer than first slots (Pad destination, assign all in source)
         * Source Slots null:
         * - N[Null]: There is a null slot in the source (Do not assign from null slots)
         * - N[NoNull]: There are no null slots in the source
         */

        // After generating the coverage report, I found an uncovered branch;
        // I was never assigning from slots that had null slots! I fixed this,
        // giving 100% coverage

        DataSlots<Integer> otherEmptySlots = new DataSlots<>(0);
        DataSlots<Integer> smallerSlots = new DataSlots<>(0);
        for(int i = 0; i < nonEmptySlotsOriginalSize/2; i++){
            ArrayList<Integer> slotData = new ArrayList<>();
            slotData.add(i);
            smallerSlots.slots.add(slotData);
        }

        DataSlots<Integer> nonEmptySlots = new DataSlots<>(0);
        DataSlots<Integer> otherNonEmptySlots = new DataSlots<>(0);
        for(int i = 0; i < nonEmptySlotsOriginalSize; i++){
            ArrayList<Integer> slotData = new ArrayList<>();
            slotData.add(i);
            nonEmptySlots.slots.add(slotData);

            ArrayList<Integer> otherSlotData = new ArrayList<>();
            otherSlotData.add(i);
            otherNonEmptySlots.slots.add(otherSlotData);
        }

        int longSlotsSize = nonEmptySlotsOriginalSize*2;
        DataSlots<Integer> longSlots = new DataSlots<>(0);
        for(int i = 0; i < longSlotsSize; i++){
            ArrayList<Integer> slotData = new ArrayList<>();
            slotData.add(i);
            longSlots.slots.add(slotData);

        }

        /* --- D[Zero] Tests ---
         * Here, S[Zero], S[Smaller] and S[Same] are merged together as an equivalence class: S[Zero]
         * */

        // (D[Zero], S[Zero], N[NonNull])
        // Nothing will happen
        emptyDataSlots.assignSlotsFrom(otherEmptySlots);
        assertEquals(0, emptyDataSlots.size());
        assertEquals(0, otherEmptySlots.size());

        // (D[Zero], S[Longer], N[NonNull])
        // Pad to length of longer, assign all from longer
        emptyDataSlots.assignSlotsFrom(fullDataSlots);
        assertEquals(emptyDataSlots.size(), fullDataSlots.size());
        for(int i = 0; i < emptyDataSlots.size(); i++){
            assertEquals(emptyDataSlots.getSlot(i), fullDataSlots.getSlot(i),"(D[Zero], S[Longer], N[NonNull]): Slot "+i+" did not match.");
        }

        /* --- D[NonZero] Tests --- */
        // (D[NonZero], S[Zero], N[NonNull])
        // Nothing will happen
        longSlots.assignSlotsFrom(otherEmptySlots);
        assertEquals(longSlotsSize, longSlots.size());
        assertEquals(0, otherEmptySlots.size());

        // (D[NonZero], S[Smaller], N[NonNull])
        // Assign all available slots from the smaller source
        longSlots.assignSlotsFrom(smallerSlots);
        assertEquals(longSlotsSize, longSlots.size());
        for(int i = 0; i < nonEmptySlots.size(); i++){
            assertEquals(fullDataSlots.getSlot(i), nonEmptySlots.getSlot(i),"(D[NonZero], S[Smaller], N[NonNull]): Slot "+i+" did not match.");
        }

        // (D[NonZero], S[Same], N[NonNull])
        // Assign all from longer
        nonEmptySlots.assignSlotsFrom(fullDataSlots);
        assertEquals(nonEmptySlotsOriginalSize, nonEmptySlots.size());
        assertEquals(nonEmptySlotsOriginalSize, fullDataSlots.size());
        for(int i = 0; i < nonEmptySlots.size(); i++){
            assertEquals(fullDataSlots.getSlot(i), nonEmptySlots.getSlot(i),"(D[NonZero], S[Same], N[NonNull]): Slot "+i+" did not match.");
        }

        // (D[NonZero], S[Longer], N[NonNull])
        // Pad to length of longer, assign all from longer
        nonEmptySlots.assignSlotsFrom(longSlots);
        assertEquals(longSlotsSize, nonEmptySlots.size());
        assertEquals(longSlotsSize, longSlots.size());
        for(int i = 0; i < nonEmptySlots.size(); i++){
            assertEquals(longSlots.getSlot(i), nonEmptySlots.getSlot(i),"(D[NonZero], S[Longer], N[NonNull]): Slot "+i+" did not match.");
        }

        // (D[*], S[*], N[Null])
        // Covering all destination and source sizes
        // Do not change any slots
        otherNonEmptySlots.assignSlotsFrom(nullDataSlots);
        assertEquals(nonEmptySlotsOriginalSize, otherNonEmptySlots.size());
        assertEquals(nonEmptySlotsOriginalSize, nullDataSlots.size());
        for(int i = 0; i < nonEmptySlots.size(); i++){
            assertNotNull(nonEmptySlots.getSlot(i),"(D[*], S[*], N[Null]): Slot "+i+" did not match.");
        }
    }
}