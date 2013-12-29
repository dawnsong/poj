package org.psjava.judgesubmit;

import java.io.IOException;

import org.psjava.ds.map.MutableMap;
import org.psjava.goods.GoodMutableMapFactory;

public class POJSubmitStatusReceiver implements SubmitStatusReceiver {

	public static final String SUBMIT_ID_PART_START = "<tr align=center><td>";

	@Override
	public SubmitStatus receive(JudgeHttpClient client, String submitId) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException {
		int nextId = SiteParsingUtil.parseInt(submitId) + 1;
		String r = client.receiveGetBodyString("/status?top=" + nextId, POJ.ENCODING);
		if (!r.contains(SUBMIT_ID_PART_START))
			throw InvalidSubmitIdException.create(submitId); // TODO add unit test
		String firstId = SiteParsingUtil.extractMiddleString(r, 0, SUBMIT_ID_PART_START, "</td>");
		if (!firstId.equals(submitId))
			throw InvalidSubmitIdException.create(submitId);
		int fontTagStart = SiteParsingUtil.getStrictIndexOf(r, "<font", r.indexOf(SUBMIT_ID_PART_START));
		String codeString = SiteParsingUtil.extractMiddleString(r, fontTagStart, ">", "</font>");
		SubmitStatusCode code = convertToCode(codeString);
		int memPartStart = SiteParsingUtil.getStrictIndexOf(r, "<td>", fontTagStart);
		String memString = SiteParsingUtil.extractMiddleString(r, memPartStart, "<td>", "</td>").trim();
		long mem = parseMemory(memString);
		int timePartStart = SiteParsingUtil.getStrictIndexOf(r, "<td>", memPartStart + 1);
		String timeString = SiteParsingUtil.extractMiddleString(r, timePartStart, "<td>", "</td>").trim();
		long time = parseTime(memString, timeString);
		return new SubmitStatus(0, code, mem, time, null);
	}

	private static SubmitStatusCode convertToCode(String codeString) throws SiteParsingException {
		MutableMap<String, SubmitStatusCode> map = GoodMutableMapFactory.getInstance().create();
		map.put("Accepted", SubmitStatusCode.ACCEPTED);
		map.put("Wrong Answer", SubmitStatusCode.WRONG_ANSWER);
		map.put("Compile Error", SubmitStatusCode.COMPILE_ERROR);
		map.put("Time Limit Exceeded", SubmitStatusCode.TIME_LIMIT_EXCEED);
		map.put("Runtime Error", SubmitStatusCode.RUNTIME_ERROR);
		map.put("Presentation Error", SubmitStatusCode.PRESENTATION_ERROR);
		map.put("Memory Limit Exceeded", SubmitStatusCode.MEMORY_LIMITE_EXCEED);
		map.put("Output Limit Exceeded", SubmitStatusCode.OUTPUT_LIMIT_EXCEED);
		map.put("System Error", SubmitStatusCode.SYSTEM_ERROR);
		map.put("Validate Error", SubmitStatusCode.SYSTEM_ERROR);
		map.put("Running & Judging", SubmitStatusCode.RUNNING);
		map.put("Compiling", SubmitStatusCode.COMPILING);
		if (!map.containsKey(codeString))
			throw new SiteParsingException("Unknown code: " + codeString);
		return map.get(codeString);
	}

	private long parseTime(String memString, String timeString) throws SiteParsingException {
		if (timeString.length() == 0) {
			return 0;
		} else {
			SiteParsingUtil.assertTrue(timeString.endsWith("MS"));
			return SiteParsingUtil.parseInt(timeString.substring(0, memString.length() - 2));
		}
	}

	private long parseMemory(String memString) throws SiteParsingException {
		if (memString.length() == 0) {
			return 0;
		} else {
			SiteParsingUtil.assertTrue(memString.endsWith("K"));
			return SiteParsingUtil.parseInt(memString.substring(0, memString.length() - 1)) * 1024;
		}
	}

}
