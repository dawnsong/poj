package org.psjava.judgesubmit;

import java.io.IOException;

import com.abbajoa.common.httpclient.CookieKeepingHttpClient;

public interface SubmitStatusReceiverOld {
	// TODO remove after all.
	SubmitStatus receiveLastStatus(CookieKeepingHttpClient client, String userId, SubmitStatus def) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException;
}
