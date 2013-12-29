package org.psjava.judgesubmit;

import org.psjava.ds.map.KeysInMap;
import org.psjava.ds.set.Set;
import org.psjava.ds.set.SetFromIterable;

import com.abbajoa.common.httpclient.SocketConnectionData;

public class POJ implements JudgeSite {

	public static final String ENCODING = "GB2312";
	public static final SocketConnectionData CONN = new SocketConnectionData(false, "poj.org", 80);

	@Override
	public CompileErrorMessageReceiver getCompileErrorMessageReceiver() {
		return new POJCompileErrorMessageReceiver();
	}

	@Override
	public CompileErrorMessageReceiverOld getCompileErrorMessageReceiverOld() {
		throw new RuntimeException();
	}

	@Override
	public String getName() {
		return "PKU Judge Online";
	}

	@Override
	public SocketConnectionData getSocketConnectionData() {
		return CONN;
	}

	@Override
	public SubmitStatusReceiver getSubmitStatusReceiver() {
		return new POJSubmitStatusReceiver();
	}

	@Override
	public SubmitStatusReceiverOld getSubmitStatusReceiverOld() {
		throw new RuntimeException();
	}

	@Override
	public Submitter getSubmitter() {
		return new POJSubmitter();
	}

	@Override
	public SubmitterOld getSubmitterOld() {
		throw new RuntimeException();
	}

	@Override
	public Set<Language> getSupportedLanguageSet() {
		return SetFromIterable.create(KeysInMap.get(POJSubmitter.getLanguageCodeMap()));
	}

}
