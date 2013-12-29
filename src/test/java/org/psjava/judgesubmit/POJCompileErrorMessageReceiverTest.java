package org.psjava.judgesubmit;

import junit.framework.Assert;

import org.junit.Test;

public class POJCompileErrorMessageReceiverTest {

	@Test
	public void testReceiving() throws Exception {
		String r = new POJCompileErrorMessageReceiver().receive(MockHttpClient.create(POJ.CONN), "", "", "12355517");
		Assert.assertTrue(r.contains("class, interface, or enum expected"));
	}

}
