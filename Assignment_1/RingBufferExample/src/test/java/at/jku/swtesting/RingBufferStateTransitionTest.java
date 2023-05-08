package at.jku.swtesting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferStateTransitionTest
{
    @Test
    void testFromSeq_1() {
        RingBuffer<Integer> buffer = new RingBuffer<>(2);
        assertTrue(buffer.isEmpty());
    }

    @Test
    void testFromSeq_2() {
        assertThrows(IllegalArgumentException.class, () -> new RingBuffer<>(-2));
    }

    @Test
    void testFromSeq_3() {
        RingBuffer<Integer> buffer = new RingBuffer<>(3);
        assertTrue(buffer.isEmpty());
        buffer.enqueue(2);
        assertFalse(buffer.isEmpty());
    }

    @Test
    void testFromSeq_4() {
        RingBuffer<Integer> buffer = new RingBuffer<>(3);
        assertTrue(buffer.isEmpty());
        buffer.enqueue(2);
        assertFalse(buffer.isEmpty());
        buffer.dequeue();
        assertTrue(buffer.isEmpty());
    }

    @Test
    void testFromSeq_5() {
        RingBuffer<Integer> buffer = new RingBuffer<>(4);
        assertTrue(buffer.isEmpty());
        buffer.enqueue(2);
        assertFalse(buffer.isEmpty());
        buffer.enqueue(3);
        assertFalse(buffer.isEmpty());
        buffer.dequeue();
        assertFalse(buffer.isEmpty());
    }

    @Test
    void testFromSeq_6() {
        RingBuffer<Integer> buffer = new RingBuffer<>(1);
        assertTrue(buffer.isEmpty());
        buffer.enqueue(1);
        assertTrue(buffer.isFull());
    }

    @Test
    void testFromSeq_7() {
        RingBuffer<Integer> buffer = new RingBuffer<>(4);
        assertTrue(buffer.isEmpty());
        buffer.enqueue(2);
        assertFalse(buffer.isEmpty());
        buffer.enqueue(3);
        assertFalse(buffer.isEmpty());
        buffer.dequeue();
        assertFalse(buffer.isEmpty());
    }

    @Test
    void testFromSeq_8() {
        RingBuffer<Integer> buffer = new RingBuffer<>(4);
        assertThrows(RuntimeException.class, buffer::peek);
    }

    @Test
    void testFromSeq_9() {
        RingBuffer<Integer> buffer = new RingBuffer<>(2);
        buffer.enqueue(1);
        assertFalse(buffer.isEmpty());
        buffer.enqueue(2);
        assertTrue(buffer.isFull());
        buffer.enqueue(1);
        assertTrue(buffer.isFull());
    }

    @Test
    void testFromSeq_10() {
        RingBuffer<Integer> buffer = new RingBuffer<>(1);
        buffer.enqueue(1);
        assertTrue(buffer.isFull());
        buffer.dequeue();
        assertTrue(buffer.isEmpty());
        assertThrows(RuntimeException.class, buffer::dequeue);
    }
}
