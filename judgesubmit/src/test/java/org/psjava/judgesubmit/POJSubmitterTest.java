package org.psjava.judgesubmit;

import junit.framework.Assert;

import org.junit.Test;

public class POJSubmitterTest {

	Submitter instance = new POJSubmitter();

	@Test
	public void testSuccess() throws Exception {
		String code = "lsdjf lkdsjf ldjkj ljlkdjfklaj kfljdklj l kjlkjsd ljfasldkjlkj flkdjs klajs kljdklj ldj flkj dlkjf lkjdlkj ksj lfj dlkaj ldsjkf lkjsd lkjaf lksdj lj sldkj ldsj kljld kjfls jaslj dkjs fldsj ldkjflkj laks jdklj lasjd lskjd";
		String id = instance.submitAndGetId(MockHttpClient.create(POJ.CONN), "1000", "ojh", "ojhtest", Language.JAVA, code);
		Assert.assertTrue(id.length() >= 8);
	}

	@Test(expected = InvalidInputException.class)
	public void testLoginFail() throws Exception {
		instance.submitAndGetId(MockHttpClient.create(POJ.CONN), "1000", "gg", "hehe", Language.JAVA, "");
	}

	@Test(expected = InvalidInputException.class)
	public void testTooShortCode() throws Exception {
		instance.submitAndGetId(MockHttpClient.create(POJ.CONN), "1000", "ojh", "ojhtest", Language.JAVA, "");
	}

}
