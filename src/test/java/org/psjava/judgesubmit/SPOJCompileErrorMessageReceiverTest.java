package org.psjava.judgesubmit;

import junit.framework.Assert;

import org.junit.Test;

public class SPOJCompileErrorMessageReceiverTest {

	@Test
	public void testNormal() throws Exception {
		JudgeHttpClient client = MockHttpClient.create(SPOJ.CONNECTION_DATA);
		String r = getInstance().receive(client, "ojh", "ojhtest", "10435787");
		Assert.assertTrue(r.startsWith("Main.java:1: error: reached end of file while parsing"));
	}

	@Test(expected = InvalidInputException.class)
	public void testInvalidId() throws Exception {
		JudgeHttpClient client = MockHttpClient.create(SPOJ.CONNECTION_DATA);
		getInstance().receive(client, "ojh", "ojhtest", "99999999");
	}

	private SPOJCompileErrorMessageReceiver getInstance() {
		return new SPOJCompileErrorMessageReceiver();
	}

}
