package at.jku.swtesting;

import java.util.Iterator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {
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
	public void testRingBufferConstructorCreationForDifferentDataTypes(){
		//arrange
		RingBuffer<String> buffer = new RingBuffer<String>(10);
		//safe since the type argument matches the type of the buffer

		buffer.enqueue("Hello");
		buffer.enqueue("World");

		//act
		RingBuffer buffer2 = buffer; //unsafe type cast
		buffer2.enqueue(Integer.getInteger("123")); //adding an element of a different type

		//assert
		assertEquals("Hello", buffer2.dequeue());
	}
}
