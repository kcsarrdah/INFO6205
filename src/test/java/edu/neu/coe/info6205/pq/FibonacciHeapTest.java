package edu.neu.coe.info6205.pq;

import edu.neu.coe.info6205.util.FibonacciHeap.FibonacciHeap;
import edu.neu.coe.info6205.util.FibonacciHeap.FibonacciHeapNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ConstantConditions")
public class FibonacciHeapTest {


    @Test
    public void testInsertAndPeekMin() {
        FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        pq.insert(3, 3);
        assertEquals(3, (int)pq.peekMin().key);
        pq.insert(1, 1);
        assertEquals(1, (int)pq.peekMin().key);
        pq.insert(2, 2);
        assertEquals(1, (int)pq.peekMin().key);
    }

    @Test
    public void testDeleteMin() {
        FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        pq.insert(3, 3);
        pq.insert(1, 1);
        pq.insert(2, 2);
        assertEquals(1, (int)pq.deleteMin().key);
        assertEquals(2, (int)pq.peekMin().key);
        assertEquals(2, (int)pq.deleteMin().key);
        assertEquals(3, (int)pq.peekMin().key);
        assertEquals(3, (int)pq.deleteMin().key);
        assertTrue(pq.isEmpty());
    }

    @Test
    public void testDecreaseKey() {
        FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        FibonacciHeapNode<Integer> node3 = pq.insert(3, 3);
        FibonacciHeapNode<Integer> node1 = pq.insert(1, 1);
        FibonacciHeapNode<Integer> node2 = pq.insert(2, 2);
        pq.decreaseKey(node3, 0);
        assertEquals(3, pq.size());
        assertEquals(3, (int)pq.peekMin().key);
        pq.decreaseKey(node1, 0);
        assertEquals(3, pq.size());
        assertEquals(3, (int)pq.peekMin().key);
        pq.decreaseKey(node2, 0);
        assertEquals(3, pq.size());
        assertEquals(3, (int)pq.peekMin().key);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseKeyTooMuch() {
        FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        FibonacciHeapNode<Integer> node = pq.insert(1, 1);
        pq.decreaseKey(node, 0);
        pq.decreaseKey(node, -1);
    }

    @Test
    public void testDelete() {
        FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        FibonacciHeapNode<Integer> node3 = pq.insert(3, 3);
        FibonacciHeapNode<Integer> node1 = pq.insert(1, 1);
        FibonacciHeapNode<Integer> node2 = pq.insert(2, 2);
        pq.deleteMin();
        assertEquals(2, pq.size());
        pq.deleteMin();
        assertEquals(1, pq.size());

        pq.deleteMin();
        assertEquals(0, pq.size());

        assertEquals(null, pq.peekMin());
        assertTrue(pq.isEmpty());
    }
    @Test(expected = NullPointerException.class)
    public void testDecreaseKeyInvalidNode() {
        FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        FibonacciHeapNode<Integer> node = new FibonacciHeapNode<>(1, 1);
        pq.decreaseKey(node, 0);
    }

    @Test(expected = NullPointerException.class)
    public void testInsertNullKey() {
        FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        pq.insert(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertNegativePriority() {
        FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        pq.insert(1, -1);
    }

}
