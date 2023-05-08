package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

public class RingBufferModelWithAdapter implements FsmModel {

    protected static final int CAPACITY = 3;
    protected int size = 0;
    private RingBuffer<Integer> ringBuffer = new RingBuffer<>(CAPACITY);

    @Override
    public Object getState() {
        if(size == 0) return "Empty";
        if(size == CAPACITY) return "Full";
        else if ((size > 0) && (size < CAPACITY)) {
            return "Partially Filled";
        } else return "Unexpected Model State";
    }

    public boolean enqueueGuard() {return size < CAPACITY;}

    @Action
    public void enqueue() {
        ringBuffer.enqueue(size);
        size++;
    }

    public boolean dequeueGuard() {return size > 0;}

    @Action public void dequeue() {
        ringBuffer.dequeue();
        size--;
    }

    @Override
    public void reset(boolean b) {
        ringBuffer = new RingBuffer<>(CAPACITY);
        size = 0;
    }
}
