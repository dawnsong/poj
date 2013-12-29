package org.psjava.judgesubmit;

import java.io.IOException;
import java.util.Collection;

import org.psjava.judgesubmit.Console;
import org.psjava.judgesubmit.InvalidInputException;
import org.psjava.judgesubmit.JudgeServiceException;
import org.psjava.judgesubmit.JudgeSite;
import org.psjava.judgesubmit.Language;
import org.psjava.judgesubmit.SiteParsingException;
import org.psjava.judgesubmit.SubmitStatus;

public class JudgeSubmit {

	public static SubmitStatus submitAngGetResult(JudgeSite site, String problemId, String userId, String password, String code, Language language, Collection<String> searchDirs, SubmitMonitorListener listener) throws IOException, SiteParsingException, InvalidInputException, JudgeServiceException {
		JudgeHttpClient client = RealJudgeHttpClient.create(site.getSocketConnectionData());
		String combined = CodeCombineAdapter.combine(code, language, searchDirs);
		return submitAngGetResult(client, site, problemId, userId, password, combined, language, searchDirs, listener);
	}

	protected static SubmitStatus submitAngGetResult(JudgeHttpClient client, JudgeSite site, String problemId, String userId, String password, String code, Language language, Collection<String> searchDirs, SubmitMonitorListener listener) throws IOException, InvalidInputException,
			SiteParsingException, JudgeServiceException {
		String submitId = site.getSubmitter().submitAndGetId(client, problemId, userId, password, language, code);
		SubmitStatus finalStatus;
		while (true) {
			SubmitStatus status = site.getSubmitStatusReceiver().receive(client, submitId);
			listener.statusChanged(status);
			if (!Console.ON_PROGRESS.contains(status.getCode())) {
				finalStatus = status;
				break;
			}
		}
		if (finalStatus.getCode() == SubmitStatusCode.COMPILE_ERROR)
			listener.onCompileError(site.getCompileErrorMessageReceiver().receive(client, userId, password, submitId));
		return finalStatus;
	}

	private JudgeSubmit() {
	}
}
