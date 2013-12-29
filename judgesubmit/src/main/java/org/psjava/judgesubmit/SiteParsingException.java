package org.psjava.judgesubmit;

@SuppressWarnings("serial")
public class SiteParsingException extends Exception {

	public SiteParsingException() {
	}

	public SiteParsingException(String detail) {
		super(detail);
	}

	public SiteParsingException(Exception e) {
		super(e);
	}

}
