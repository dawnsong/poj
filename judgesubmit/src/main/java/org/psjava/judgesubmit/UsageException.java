package org.psjava.judgesubmit;

@SuppressWarnings("serial")
public class UsageException extends Exception {

	public UsageException(Exception src) {
		super(src);
	}
	
	public UsageException(String msg) {
		super(msg);
	}

}
