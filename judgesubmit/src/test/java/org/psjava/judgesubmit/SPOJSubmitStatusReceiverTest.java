package org.psjava.judgesubmit;

import junit.framework.Assert;

import org.junit.Test;

public class SPOJSubmitStatusReceiverTest {

	@Test
	public void testAccepted() throws Exception {
		SubmitStatus r = getInstance().receive(MockHttpClient.create(SPOJ.CONNECTION_DATA), "10442782");
		Assert.assertEquals(SubmitStatusCode.ACCEPTED, r.getCode());
		Assert.assertEquals(2290L, r.getTimeUsage(-1));
		Assert.assertEquals(7235174L, r.getMemoryUsage(-1));
	}

	@Test(expected = InvalidInputException.class)
	public void testInvalidSubmitId() throws Exception {
		getInstance().receive(MockHttpClient.create(SPOJ.CONNECTION_DATA), "99999999");
	}

	@Test
	public void testRuntimeError() throws Exception {
		JudgeHttpClient client = MockHttpClient.create(SPOJ.CONNECTION_DATA);
		SubmitStatus r1 = getInstance().receive(client, "10443136");
		Assert.assertEquals(SubmitStatusCode.RUNTIME_ERROR, r1.getCode());
		Assert.assertEquals("SIGKILL", r1.getAdditionalMessage(""));
		SubmitStatus r2 = getInstance().receive(client, "10443100");
		Assert.assertEquals(SubmitStatusCode.RUNTIME_ERROR, r2.getCode());
		Assert.assertEquals("NZEC", r2.getAdditionalMessage(""));
	}

	@Test
	public void testOtherStatusCodes() throws Exception {
		JudgeHttpClient client = MockHttpClient.create(SPOJ.CONNECTION_DATA);
		Assert.assertEquals(SubmitStatusCode.WRONG_ANSWER, getInstance().receive(client, "10442793").getCode());
		Assert.assertEquals(SubmitStatusCode.TIME_LIMIT_EXCEED, getInstance().receive(client, "10442791").getCode());
		Assert.assertEquals(SubmitStatusCode.RUNTIME_ERROR, getInstance().receive(client, "10443136").getCode());
	}

	private SPOJSubmitStatusReceiver getInstance() {
		return new SPOJSubmitStatusReceiver();
	}

}
