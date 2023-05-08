package at.jku.swtesting;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;

public class RingBufferModel implements FsmModel {
    private static final int CAPACITY = 3;
    private int size = 0;
    @Override
    public Object getState() {
        if(size == 0) return "Empty";
        if(size == CAPACITY) return "Full";
        else if ((size > 0) && (size < CAPACITY)) {
            return "Partially Filled";
        } else return "Unexpected Model State";
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
    public void dequeueFromEmptyBuffer() {
        throw new RuntimeException("Empty ring buffer.");
    }

    public boolean dequeueFromEmptyBufferGuard() {
        return getState().equals("Empty");
    }

    @Override
    public void reset(boolean b) {
        this.size = 0;
    }

}
