package org.psjava.judgesubmit;

public class SubmitStatus {

	private final int submitID; // TODO remove
	private final SubmitStatusCode code;
	private final long memoryUsageOrZero;
	private final long timeUsageOrZero;
	private final String additionalMessageOrNull;

	public SubmitStatus(int submitID, SubmitStatusCode code, long memoryUsageOrZero, long timeUsageOrZero, String additionalMessageOrNull) {
		this.submitID = submitID;
		this.code = code;
		this.memoryUsageOrZero = memoryUsageOrZero;
		this.timeUsageOrZero = timeUsageOrZero;
		this.additionalMessageOrNull = additionalMessageOrNull;
	}

	public SubmitStatusCode getCode() {
		return code;
	}

	public long getMemoryUsage(long def) {
		return memoryUsageOrZero != 0 ? memoryUsageOrZero : def;
	}

	public int getSubmitID() {
		return submitID;
	}

	public long getTimeUsage(long def) {
		return timeUsageOrZero != 0 ? timeUsageOrZero : def;
	}

	public String getAdditionalMessage(String def) {
		return additionalMessageOrNull != null ? additionalMessageOrNull : def;
	}
}