package org.psjava.judgesubmit;

import java.io.IOException;

public class POJCompileErrorMessageReceiver implements CompileErrorMessageReceiver {

	@Override
	public String receive(JudgeHttpClient client, String id, String password, String submitId) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException {
		String path = "/showcompileinfo?solution_id=" + submitId;
		String content = client.receiveGetBodyString(path, POJ.ENCODING);
		return SiteParsingUtil.extractMiddleString(content, 0, "<pre>", "</pre>");
	}

}
