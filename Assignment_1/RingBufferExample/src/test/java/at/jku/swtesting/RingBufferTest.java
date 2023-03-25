package at.jku.swtesting;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

	// Test if IllegalArgumentException is thrown if capacity < 0.
	@Test
	public void testRingBufferConstructorWithZeroElement() throws IllegalArgumentException {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			RingBuffer<String> buffer = new RingBuffer<>(0);
		});
		String expectedMessage = "Initial capacity is less than one";
		String actualMessage = exception.getMessage();

		assertEquals(actualMessage, expectedMessage);
	}

	// Test if RuntimeException is thrown in case of zero element and dequeue.
	@Test
	public void testRingBufferDequeueWithZeroElement() throws RuntimeException {
		Exception exception = assertThrows(RuntimeException.class, () -> {
			RingBuffer<String> buffer = new RingBuffer<>(1);
			buffer.dequeue();
		});
		String expectedMessage = "Empty ring buffer.";
		String actualMessage = exception.getMessage();

		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	public void testRingBufferConstructorWithOneElement(){
		//arrange
		RingBuffer<String> buffer = new RingBuffer<>(1);
		var item = "test";
		//act
		buffer.enqueue(item);
		//assert
		assertEquals(item, buffer.dequeue());
	}

	@Test
	public void testUsageOfEnqueueWithIntAndNull(){
		//arrange
		RingBuffer<Integer> buffer = new RingBuffer<>(2);
		//act
		buffer.enqueue(null);
		buffer.enqueue(1);

		var item = buffer.dequeue();
		var item2 = buffer.dequeue();

		//assert
		assertThrows(NullPointerException.class,
				(() -> {
					var x = item + item2;
				}));
	}

	//shows casting error
	@Test
	public void testRingBufferConstructorCreationForDifferentDataTypes(){

		//arrange
		RingBuffer<String> buffer = new RingBuffer<String>(10);

		buffer.enqueue("Hello");
		buffer.enqueue("World");

		//act
		RingBuffer buffer2 = buffer; //unsafe type cast
		buffer2.enqueue(Integer.getInteger("123"));

		//assert
		assertEquals("123", buffer2.dequeue());
	}

	@Test
	public void testEnqueueWithConcurrency() throws InterruptedException {

		final int threads = 10;
		final int elements = 1000;

		RingBuffer<Integer> buffer = new RingBuffer<>(threads * elements);

		var executor = Executors.newFixedThreadPool(threads);

		for (int i = 0; i < threads; i++) {
			executor.execute(() -> {
				for (int j = 0; j < elements; j++) {
					buffer.enqueue(j);
				}
			});
		}

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);

		assertEquals(threads * elements, buffer.size()); //usually it will fail with amount of el < threads*elements
	}
}
