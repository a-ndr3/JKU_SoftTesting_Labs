package at.jku.swtesting;

import nz.ac.waikato.modeljunit.*;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class RingBufferModel implements FsmModel {
    private static final int CAPACITY = 3;
    private int size = 0;
    @Override
    public Object getState() throws RuntimeException {
        if(size == 0) return "Empty";
        if(size == CAPACITY) return "Full";
        if(size > CAPACITY) {
            size = CAPACITY;
            return "Full";
        }
        else if ((size > 0) && (size < CAPACITY)) {
            return "Partially Filled";
        } else throw new RuntimeException("Empty ring buffer.");
    }

    @Action
    public void enqueue() {size++;}

    public boolean enqueueGuard() {return size < CAPACITY;}

    @Action public void dequeue() {size--;}

    public boolean dequeueGuard() {return size > 0;}

    @Action
    public void peek() {
    }

    public boolean peekGuard() {return size > 0;}

    @Action
    public void peekFromEmptyBuffer() {
        try {
            Assert.fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            Assert.assertEquals("Empty ring buffer.", e.getMessage());
        }
    }

    public boolean peekFromEmptyBufferGuard() {return size == 0;}

    @Action
    public void dequeueFromEmptyBuffer() {
        try {
            Assert.fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            Assert.assertEquals("Empty ring buffer.", e.getMessage());
        }
    }

    public boolean dequeueFromEmptyBufferGuard() {
        return size == 0;
    }

    @Action
    public void enqueueToFullBuffer() {size++;}

    public boolean enqueueToFullBufferGuard() {return size == CAPACITY;}

    @Override
    public void reset(boolean b) {
        this.size = 0;
    }

}
