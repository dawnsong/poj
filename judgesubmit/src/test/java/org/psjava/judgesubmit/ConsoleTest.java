package org.psjava.judgesubmit;

import org.junit.Assert;
import org.junit.Test;

public class ConsoleTest {

	@Test
	public void testTime() {
		Assert.assertEquals("12:34.567s", Console.getTimeDescription(12 * 60 * 1000 + 34 * 1000 + 567));
	}

	@Test
	public void testMem() {
		Assert.assertEquals("123,456KB", Console.getMemoryDescription(123456 * 1024));
	}

	@Test
	public void testUsage() {
		// Console.printUsage();
	}
}
