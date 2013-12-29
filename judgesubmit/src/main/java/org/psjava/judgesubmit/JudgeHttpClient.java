package org.psjava.judgesubmit;

import java.io.IOException;
import java.util.Map;

public interface JudgeHttpClient {
	String receivePostBodyString(String path, Map<String, String> param, String encoding) throws IOException, JudgeServiceException;

	String receiveGetBodyString(String path, String encoding) throws IOException, JudgeServiceException;

	void clearCookie();
}