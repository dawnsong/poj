package org.psjava.judgesubmit;

public class InvalidSubmitIdException {
	public static InvalidInputException create(String id) {
		return new InvalidInputException("Invalid submit id: " + id);
	}
}
