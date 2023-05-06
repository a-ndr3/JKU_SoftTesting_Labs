package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

public class RingBufferModel implements FsmModel {
    private static final int CAPACITY = 3;
    private int size = 0;
    @Override
    public Object getState() {
        if(size == 0) return "Empty";
        if(size == CAPACITY) return "Full";
        return "Partially filled";
    }

    @Action public void enqueue() {size++;}
    public boolean enqueueGuard() {return size < CAPACITY;}

    @Action public void dequeue() {size--;}

    public boolean dequeueGuard() {return size > 0;}

    @Override
    public void reset(boolean b) {
        this.size = 0;
    }
}
