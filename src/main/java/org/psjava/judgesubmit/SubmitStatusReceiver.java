package org.psjava.judgesubmit;

import java.io.IOException;

public interface SubmitStatusReceiver {
	SubmitStatus receive(JudgeHttpClient client, String submitId) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException;
}
