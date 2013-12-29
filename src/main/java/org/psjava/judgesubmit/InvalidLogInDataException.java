package org.psjava.judgesubmit;


public class InvalidLogInDataException {
	public static InvalidInputException create() {
		return new InvalidInputException("Cannot log in the judge site. Check id/password.");
	}
	public static InvalidInputException create(String messageFromSite) {
		return new InvalidInputException("Cannot log in the judge site (" + messageFromSite + ")");
	}
	private InvalidLogInDataException() {
	}
}
