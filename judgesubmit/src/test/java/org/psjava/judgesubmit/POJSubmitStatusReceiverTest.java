package org.psjava.judgesubmit;

import org.junit.Assert;
import org.junit.Test;

public class POJSubmitStatusReceiverTest {

	@Test
	public void testNormalAcceptedCase() throws Exception {
		SubmitStatus r = newInstance().receive(MockHttpClient.create(POJ.CONN), "12352987");
		Assert.assertEquals(SubmitStatusCode.ACCEPTED, r.getCode());
		Assert.assertEquals(3346432, r.getMemoryUsage(-1));
		Assert.assertEquals(141, r.getTimeUsage(-1));
	}

	@Test
	public void testFailCases() throws Exception {
		JudgeHttpClient client = MockHttpClient.create(POJ.CONN);
		Assert.assertEquals(SubmitStatusCode.WRONG_ANSWER, newInstance().receive(client, "12355286").getCode());
		Assert.assertEquals(SubmitStatusCode.COMPILE_ERROR, newInstance().receive(client, "12355332").getCode());
		Assert.assertEquals(SubmitStatusCode.TIME_LIMIT_EXCEED, newInstance().receive(client, "12355331").getCode());
		Assert.assertEquals(SubmitStatusCode.RUNTIME_ERROR, newInstance().receive(client, "12355318").getCode());
		Assert.assertEquals(SubmitStatusCode.PRESENTATION_ERROR, newInstance().receive(client, "12354957").getCode());
		Assert.assertEquals(SubmitStatusCode.MEMORY_LIMITE_EXCEED, newInstance().receive(client, "12354951").getCode());
		Assert.assertEquals(SubmitStatusCode.OUTPUT_LIMIT_EXCEED, newInstance().receive(client, "12354059").getCode());
	}

	private POJSubmitStatusReceiver newInstance() {
		return new POJSubmitStatusReceiver();
	}

}
