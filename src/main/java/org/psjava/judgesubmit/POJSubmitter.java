package org.psjava.judgesubmit;

import java.io.IOException;
import java.util.Map;

import org.psjava.ds.map.MutableMap;
import org.psjava.goods.GoodMutableMapFactory;

public class POJSubmitter implements Submitter {

	@Override
	public String submitAndGetId(JudgeHttpClient hc, String problemId, String userId, String password, Language language, String code) throws IOException, InvalidInputException, SiteParsingException, JudgeServiceException {
		login(hc, userId, password);
		submit(hc, problemId, language, code);
		return receiveLastSubmitId(hc, problemId, userId, language);
	}

	private static String toLangauageCode(Language language) {
		return getLanguageCodeMap().get(language);
	}

	public static MutableMap<Language, String> getLanguageCodeMap() {
		MutableMap<Language, String> map = GoodMutableMapFactory.getInstance().create();
		map.put(Language.JAVA, "2");
		map.put(Language.CPP, "0");
		map.put(Language.C, "1");
		return map;
	}

	private static void login(JudgeHttpClient hc, String userId, String password) throws IOException, JudgeServiceException, InvalidInputException {
		Map<String, String> param = StringMapFromVarargs.create("user_id1", userId, "password1", password);
		String html = hc.receivePostBodyString("/login", param, POJ.ENCODING);
		if (html.contains("Login failed!"))
			throw InvalidLogInDataException.create();
	}

	private static void submit(JudgeHttpClient hc, String problemId, Language language, String code) throws IOException, JudgeServiceException, InvalidInputException, SiteParsingException {
		Map<String, String> param = StringMapFromVarargs.create("problem_id", problemId, "language", toLangauageCode(language), "source", code);
		String html = hc.receivePostBodyString("/submit", param, POJ.ENCODING);
		if (html.contains("Error"))
			throw new InvalidInputException(SiteParsingUtil.extractMiddleString(html, 0, "<li>", "</li>"));
	}

	private static String receiveLastSubmitId(JudgeHttpClient hc, String problemId, String userId, Language language) throws IOException, JudgeServiceException, SiteParsingException {
		String url = String.format("/status?problem_id=%s&user_id=%s&language=%s", problemId, userId, toLangauageCode(language));
		String statusHtml = hc.receiveGetBodyString(url, POJ.ENCODING);
		return SiteParsingUtil.extractMiddleString(statusHtml, 0, POJSubmitStatusReceiver.SUBMIT_ID_PART_START, "<");
	}

}
