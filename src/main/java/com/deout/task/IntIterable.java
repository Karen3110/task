package com.deout.task;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class IntIterable implements Iterable<Integer> {
    private final List<Integer> data;
    private int lastIterationIndex = 0;
    private boolean canRemove = false; // to ensure remove() is only called after next()

    public IntIterable(int[] data) {
        this.data = Arrays.stream(data).boxed().collect(Collectors.toList());
    }

    public Iterator<Integer> iterator() {
        return new IntIterator();
    }

    private class IntIterator implements Iterator<Integer> {

        @Override
        public boolean hasNext() {
            return lastIterationIndex < data.size();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to get.");
            }
            canRemove = true;
            return data.get(lastIterationIndex++);
        }

        /**
         * Ensures that we keep contract between next() and remove().
         * @see Iterator
         */
        @Override
        public void remove() {
            if (!canRemove) {

                throw new IllegalStateException("Cannot call remove() before next()");
            }
            data.remove(--lastIterationIndex);
            canRemove = false;
        }
    }
}
