package org.psjava.judgesubmit;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class SPOJSubmitStatusReceiver implements SubmitStatusReceiver {

	@Override
	public SubmitStatus receive(JudgeHttpClient client, String submitId) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException {
		client.clearCookie();
		String jsonString = client.receivePostBodyString("/status/ajax=1,ajaxdiff=1", StringMapFromVarargs.create("ids", submitId), SPOJ.ENCODING);
		JSONArray array = new JSONArray(jsonString);
		if (array.length() == 0)
			throw InvalidSubmitIdException.create(submitId);
		if (array.length() > 1)
			throw new SiteParsingException();

		JSONObject json = array.getJSONObject(0);
		int status = json.getInt("status");
		String statusDescription = json.getString("status_description");
		String time = json.getString("time");
		String mem = json.getString("mem");

		SubmitStatusCode code = converToCode(status, statusDescription);
		String messageOrNull = extractMessageOrNull(code, statusDescription);
		long memoryUsageOrZero = convertToMemoryUsageOrZero(mem);
		long timeUsageOrZero = convertToTimeUsageOrZero(time);
		return new SubmitStatus(-1, code, memoryUsageOrZero, timeUsageOrZero, messageOrNull);
	}

	private long convertToMemoryUsageOrZero(String mem) throws SiteParsingException {
		long memoryUsageOrZero;
		if (mem.equals("-")) {
			return 0;
		} else {
			String trimmed = mem.trim();
			String numPart = trimmed.substring(0, trimmed.length() - 1);
			long unit = extractMemoryUnit(trimmed);
			memoryUsageOrZero = (long) (SiteParsingUtil.parseDouble(numPart) * unit);
			return memoryUsageOrZero;
		}
	}

	private long extractMemoryUnit(String trimmedMemString) throws SiteParsingException {
		switch (trimmedMemString.charAt(trimmedMemString.length() - 1)) {
		case 'M':
			return 1024 * 1024;
		case 'k':
		case 'K':
			return 1024;
		default:
			throw new SiteParsingException(trimmedMemString);
		}
	}

	private long convertToTimeUsageOrZero(String timeString) throws SiteParsingException {
		if (timeString.equals("-")) {
			return 0;
		} else {
			String sec = SiteParsingUtil.extractMiddleString(timeString, 0, ">", "<");
			return (long) (SiteParsingUtil.parseDouble(sec) * 1000);
		}
	}

	private SubmitStatusCode converToCode(int status, String statusDescription) throws SiteParsingException {
		switch (status) {
		case 0:
			return SubmitStatusCode.WAITING;
		case 1:
			return SubmitStatusCode.COMPILING;
		case 3:
			return SubmitStatusCode.RUNNING;
		case 5:
			return SubmitStatusCode.JUDGING;
		case 11:
			return SubmitStatusCode.COMPILE_ERROR;
		case 12:
			return SubmitStatusCode.RUNTIME_ERROR;
		case 13:
			return SubmitStatusCode.TIME_LIMIT_EXCEED;
		case 14:
			return SubmitStatusCode.WRONG_ANSWER;
		case 15:
			return SubmitStatusCode.ACCEPTED;
		default:
			throw new SiteParsingException("unknown status: " + status + "," + statusDescription.trim());
		}
	}

	private String extractMessageOrNull(SubmitStatusCode code, String statusDescription) throws SiteParsingException {
		if (code == SubmitStatusCode.RUNTIME_ERROR) {
			if (statusDescription.contains("<"))
				return SiteParsingUtil.extractMiddleString(statusDescription, 0, ">", "<");
			else
				return SiteParsingUtil.extractMiddleString(statusDescription, 0, "(", ")");
		} else {
			return null;
		}
	}

}
