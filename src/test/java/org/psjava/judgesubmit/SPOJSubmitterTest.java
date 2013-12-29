package org.psjava.judgesubmit;

import junit.framework.Assert;

import org.junit.Test;

public class SPOJSubmitterTest {

	@Test
	public void testNormalCase() throws Exception {
		String id = new SPOJSubmitter().submitAndGetId(MockHttpClient.create(SPOJ.CONNECTION_DATA), "TEST", "ojh", "ojhtest", Language.JAVA, "MYCODE");
		Assert.assertTrue(id.length() >= 8);
	}

	@Test(expected = InvalidInputException.class)
	public void testLoginFail() throws Exception {
		new SPOJSubmitter().submitAndGetId(MockHttpClient.create(SPOJ.CONNECTION_DATA), "TEST", "ojh", "gg", Language.JAVA, "MYCODE");
	}

	@Test(expected = InvalidInputException.class)
	public void testWrongProblemId() throws Exception {
		new SPOJSubmitter().submitAndGetId(MockHttpClient.create(SPOJ.CONNECTION_DATA), "WRONGKKK", "ojh", "gg", Language.JAVA, "MYCODE");
	}

}
