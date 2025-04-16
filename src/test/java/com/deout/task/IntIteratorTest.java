package com.deout.task;


import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class IntIteratorTest {
    int[] initialData;
    IntIterable intIterable;
    Iterator<Integer> iterator;

    @Before
    public void setup() {
        initialData = new int[]{1, 2, 3, 4, 5};
        intIterable = new IntIterable(initialData);
        iterator = intIterable.iterator();
    }

    @Test
    public void testForeach() {
        Integer i = 0;
        for (Integer el : intIterable) {
            assertEquals(i + " element doesn't match", Integer.valueOf(initialData[i]), el);
            i++;
        }
        assertEquals("Didn't went throw array", i, Integer.valueOf(initialData.length));
    }

    @Test
    public void testBlankForeach() {
        int[] arr = new int[0];
        for (Integer unused : new IntIterable(arr)) {
            fail("Shouldn't come here");
        }
    }

    @Test
    public void testRemoveMiddleElement() {
        iterator.next(); // 1
        iterator.next(); // 2
        iterator.next(); // 3
        iterator.remove(); // remove 3 and point to 2

        assertEquals((Integer) 4, iterator.next());
        assertEquals((Integer) 5, iterator.next());
    }

    @Test
    public void testRemoveFirstElement() {
        iterator.next();
        iterator.remove();

        for (Integer i = 2; i < initialData.length; i++) {
            assertEquals(i, iterator.next());
        }
    }

    @Test
    public void testRemoveLastElement() {
        for (int i = 0; i < initialData.length; i++) {
            iterator.next();
        }

        iterator.remove(); // remove 5 and point to 4
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemoveMultipleElements() {
        iterator.next(); // 1
        iterator.next(); // 2
        iterator.remove(); // remove 2 and point to 1
        iterator.next(); // 3
        iterator.remove(); // remove 3 and point to 1

        assertEquals((Integer) 4, iterator.next());
        assertEquals((Integer) 5, iterator.next());
    }

    @Test(expected = IllegalStateException.class)
    public void testFromEmptyArray() {
        int[] given = new int[0];
        intIterable = new IntIterable(given);
        iterator = intIterable.iterator();

        assertFalse(iterator.hasNext());
        iterator.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextOnEmptyArray() {
        int[] given = new int[0];
        intIterable = new IntIterable(given);
        iterator = intIterable.iterator();

        assertFalse(iterator.hasNext());
        iterator.next();
    }
}
