package org.psjava.judgesubmit;

import java.io.IOException;

public interface Submitter {
	String submitAndGetId(JudgeHttpClient hc, String problemId, String userId, String password, Language language, String sourceCode) throws IOException, InvalidInputException, SiteParsingException, JudgeServiceException;
}
