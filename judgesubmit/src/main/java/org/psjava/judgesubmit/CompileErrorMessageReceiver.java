package org.psjava.judgesubmit;

import java.io.IOException;

public interface CompileErrorMessageReceiver {
	String receive(JudgeHttpClient client, String id, String password, String submitId) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException;
}