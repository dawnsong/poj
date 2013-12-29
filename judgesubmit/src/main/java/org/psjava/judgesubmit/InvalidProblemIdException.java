package org.psjava.judgesubmit;

public class InvalidProblemIdException {
	public static InvalidInputException create(String id) {
		return new InvalidInputException("Wrong problem id : " + id);
	}
}
