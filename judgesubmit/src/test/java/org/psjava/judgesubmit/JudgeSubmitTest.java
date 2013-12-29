package org.psjava.judgesubmit;

import java.util.TreeSet;

import junit.framework.Assert;

import org.junit.Test;

public class JudgeSubmitTest {

	@Test
	public void testAcceptedCase() throws Exception {
		String code = "public class Main { 	public static void main(String[] args) { java.util.Scanner in = new java.util.Scanner(System.in); while(true) { int x = in.nextInt(); if(x == 42) break; System.out.println(x); } } }";
		JudgeHttpClient client = MockHttpClient.create(SPOJ.CONNECTION_DATA);
		SubmitStatus r = JudgeSubmit.submitAngGetResult(client, new SPOJ(), "TEST", "ojh", "ojhtest", code, Language.JAVA, new TreeSet<String>(), new SubmitMonitorListener() {
			@Override
			public void statusChanged(SubmitStatus judgeStatus) {
			}

			@Override
			public void onCompileError(String message) {
			}
		});
		Assert.assertEquals(SubmitStatusCode.ACCEPTED, r.getCode());
	}

	int runningCount = 0;

	@Test
	public void testLongRunning() throws Exception {
		String code = "public class Main { 	public static void main(String[] args) throws Exception { Thread.sleep(5000); } }";
		runningCount = 0;
		JudgeHttpClient client = MockHttpClient.create(SPOJ.CONNECTION_DATA);
		JudgeSubmit.submitAngGetResult(client, new SPOJ(), "TEST", "ojh", "ojhtest", code, Language.JAVA, new TreeSet<String>(), new SubmitMonitorListener() {
			@Override
			public void statusChanged(SubmitStatus judgeStatus) {
				if (judgeStatus.getCode() == SubmitStatusCode.RUNNING)
					runningCount++;
			}

			@Override
			public void onCompileError(String message) {
			}
		});
		Assert.assertTrue(runningCount > 0);
	}

	String errorMessage;

	@Test
	public void testCompileErrorCase() throws Exception {
		String code = "public class ";
		errorMessage = "";
		JudgeHttpClient client = MockHttpClient.create(SPOJ.CONNECTION_DATA);
		SubmitStatus r = JudgeSubmit.submitAngGetResult(client, new SPOJ(), "TEST", "ojh", "ojhtest", code, Language.JAVA, new TreeSet<String>(), new SubmitMonitorListener() {
			@Override
			public void statusChanged(SubmitStatus judgeStatus) {
			}

			@Override
			public void onCompileError(String message) {
				errorMessage = message;
			}
		});
		Assert.assertEquals(SubmitStatusCode.COMPILE_ERROR, r.getCode());
		Assert.assertTrue(errorMessage.length() > 0);
	}
}
