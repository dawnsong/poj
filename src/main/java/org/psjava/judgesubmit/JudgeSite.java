package org.psjava.judgesubmit;

import org.psjava.ds.set.Set;

import com.abbajoa.common.httpclient.SocketConnectionData;

public interface JudgeSite {
	String getName();

	SocketConnectionData getSocketConnectionData();

	Set<Language> getSupportedLanguageSet();

	Submitter getSubmitter();

	SubmitStatusReceiver getSubmitStatusReceiver();

	CompileErrorMessageReceiver getCompileErrorMessageReceiver();

	// TODO remove after migration
	SubmitStatusReceiverOld getSubmitStatusReceiverOld();

	// TODO remove after migration
	SubmitterOld getSubmitterOld();

	// TODO remove after migration
	CompileErrorMessageReceiverOld getCompileErrorMessageReceiverOld();
}
