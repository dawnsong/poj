package org.psjava.judgesubmit;

@SuppressWarnings("serial")
public class JudgeServiceException extends Exception {
	public JudgeServiceException(String requestDescription) {
		super(requestDescription);
	}
}
