package com.jam.jamcougstatistics;

import java.util.ArrayList;

public class DataSlots<T extends Number> {
    ArrayList<ArrayList<T>> slots;

    public DataSlots(int size) {
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

    public ArrayList<T> getSlot(int slotIndex) {
        return slotIndex<slots.size()?slots.get(slotIndex):null;
    }

    public String slotString(int slotIndex) {
        ArrayList<T> slot = getSlot(slotIndex);
        return (slot == null)?"No data":slot.toString();
    }

    public boolean hasSlot(int slotIndex) {
        return getSlot(slotIndex) != null;
    }
    public int size() {
        return slots.size();
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
        int otherSize = otherSlots.size();
        int thisSize = size();

        int minSize = Math.min(otherSize, thisSize);
        padToSize(minSize);

        for (int overlapSlotIndex = 0; overlapSlotIndex < minSize; overlapSlotIndex++) {
            if (otherSlots.hasSlot(overlapSlotIndex)) {
                setSlot(overlapSlotIndex, otherSlots.getSlot(overlapSlotIndex));
            }
        }
        for (int extraSlotIndex = thisSize; extraSlotIndex < otherSize; extraSlotIndex++) {
            slots.add(otherSlots.getSlot(extraSlotIndex));
        }
    }
}
