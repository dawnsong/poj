package org.psjava.judgesubmit;

import java.io.IOException;

import com.abbajoa.common.httpclient.CookieKeepingHttpClient;

// TODO remove after migration.
public interface CompileErrorMessageReceiverOld {
	String receive(CookieKeepingHttpClient client, String id, String password, int submitId) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException;
}