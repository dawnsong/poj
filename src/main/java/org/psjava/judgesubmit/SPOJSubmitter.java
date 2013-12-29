package org.psjava.judgesubmit;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class SPOJSubmitter implements Submitter {

	@Override
	public String submitAndGetId(JudgeHttpClient hc, String problemId, String userId, String password, Language language, String code) throws IOException, InvalidInputException, SiteParsingException, JudgeServiceException {
		String html = receiveHtml(hc, problemId, userId, password, language, code);
		return extractSubmitId(html, problemId);
	}

	private String receiveHtml(JudgeHttpClient hc, String problemId, String userId, String password, Language language, String code) throws IOException, JudgeServiceException {
		Map<String, String> param = createParam(problemId, userId, password, language, code);
		return hc.receivePostBodyString("/submit/complete/", param, SPOJ.ENCODING);
	}

	protected static String extractSubmitId(String html, String problemId) throws InvalidInputException, SiteParsingException {
		if (html.contains("not authorised "))
			throw InvalidLogInDataException.create();
		if (html.contains("Wrong problem code")) 
			throw InvalidProblemIdException.create(problemId);
		if (!html.contains("Solution submitted!"))
			throw new SiteParsingException(SiteParsingUtil.extractMiddleString(html, 0, "<p align=\"center\">", "<br>"));
		String submitId = SiteParsingUtil.extractMiddleString(html, 0, "newSubmissionId\" value=\"", "\"/>");
		return submitId;
	}

	protected static Map<String, String> createParam(String problemId, String userId, String password, Language language, String code) {
		Map<String, String> r = new TreeMap<String, String>();
		r.put("login_user", userId);
		r.put("password", password);
		r.put("problemcode", problemId);
		r.put("lang", "" + SPOJ.getLanguageCodeMap().get(language));
		r.put("file", code + "\n");
		return r;
	}
}
