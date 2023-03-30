package at.jku.swtesting;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;
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

	// Test if RuntimeException is thrown in case of zero element and peek.
	@Test
	public void testRingBufferPeekWithZeroElement() throws RuntimeException {
		Exception exception = assertThrows(RuntimeException.class, () -> {
			RingBuffer<String> buffer = new RingBuffer<>(1);
			buffer.peek();
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

	/* unsafe
		RingBuffer<String> buffer = new RingBuffer<>(3);

		buffer.enqueue("Hello");
		buffer.enqueue("World");
		RingBuffer buffer2 = buffer;

		try
		{
			buffer2.enqueue(Integer.parseInt("123"));
		}
		catch (RuntimeException e)
		{

		}
	}
	*/

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

	@Test
	public void testSize() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		buffer.enqueue("Hello");
		buffer.enqueue("World");

		assertEquals(2, buffer.size());
	}
	@Test
	public void testPeek() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		buffer.enqueue("Hello");
		buffer.enqueue("World");

		assertEquals("Hello", buffer.peek());
	}

	@Test
	public void testIsEmpty() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		assertTrue(buffer.isEmpty());
		buffer.enqueue("Hello");
		buffer.enqueue("World");

		assertFalse(buffer.isEmpty());
	}

	@Test
	public void testIsFull() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		assertFalse(buffer.isFull());
		buffer.enqueue("Hello");
		buffer.enqueue("World");
		buffer.enqueue("!");

		assertTrue(buffer.isFull());
	}

	@Test
	public void testCapacity() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		assertEquals(3, buffer.capacity());
	}

	@Test
	public void testEnqueueWhenFullCapacity() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		buffer.enqueue("Hello");
		buffer.enqueue("World");
		buffer.enqueue("!");
		// Since the buffer is full, the oldest element will be removed and the new element will be added.
		buffer.enqueue("Goodbye");

		assertEquals("World", buffer.peek());
	}

	@Test
	public void testIterator() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		buffer.enqueue("Hello");
		buffer.enqueue("World");
		buffer.enqueue("!");

		Iterator<String> it = buffer.iterator();
		assertEquals("Hello", it.next());
		assertEquals("World", it.next());
		assertEquals("!", it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIteratorWithConcurrency() throws InterruptedException {

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

		int count = 0;
		for (Integer i : buffer) {
			count++;
		}

		assertEquals(threads * elements, count);
	}

	@Test
	public void testHasNextWhenEmpty() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		Iterator<String> it = buffer.iterator();
		assertFalse(it.hasNext());
	}

	@Test
	public void testHasNextWhenNotEmpty() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		buffer.enqueue("Hello");
		buffer.enqueue("World");
		buffer.enqueue("!");

		Iterator<String> it = buffer.iterator();
		assertTrue(it.hasNext());
	}

	@Test
	public void testNextWhenEmpty() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		Iterator<String> it = buffer.iterator();
		assertThrows(NoSuchElementException.class, it::next);
	}

	@Test
	public void testNextWhenNotEmpty() {
		RingBuffer<String> buffer = new RingBuffer<>(3);

		buffer.enqueue("Hello");
		buffer.enqueue("World");
		buffer.enqueue("!");

		Iterator<String> it = buffer.iterator();
		assertEquals("Hello", it.next());
		assertEquals("World", it.next());
		assertEquals("!", it.next());
	}
}
