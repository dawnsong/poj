package org.psjava.judgesubmit;

import java.io.IOException;
import java.util.Map;

public class SPOJCompileErrorMessageReceiver implements CompileErrorMessageReceiver {

	@Override
	public String receive(JudgeHttpClient client, String id, String password, String submitId) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException {
		Map<String, String> param1 = StringMapFromVarargs.create("login_user", id, "password", password);
		String res1 = client.receivePostBodyString("/logout", param1, SPOJ.ENCODING);
		if (res1.contains("Authentication failed!"))
			throw InvalidLogInDataException.create();
		if (!res1.contains(id))
			throw new SiteParsingException();
		String res2 = client.receiveGetBodyString("/error/" + submitId, SPOJ.ENCODING);
		if (!res2.contains("<pre>"))
			throw InvalidSubmitIdException.create(submitId);
		return SiteParsingUtil.extractMiddleString(res2, 0, "<pre><small>", "</small></pre>").trim();
	}

}
