package org.psjava.judgesubmit;

import java.io.IOException;


import com.abbajoa.common.httpclient.CookieKeepingHttpClient;


public interface SubmitterOld {
	void submit(CookieKeepingHttpClient client, String problemCode, String userId, String password, Language language, String code) throws SiteParsingException, IOException, InvalidInputException, JudgeServiceException;
}
