package org.psjava.judgesubmit;

import org.psjava.ds.map.KeysInMap;
import org.psjava.ds.map.MutableMap;
import org.psjava.ds.set.Set;
import org.psjava.ds.set.SetFromIterable;
import org.psjava.goods.GoodMutableMapFactory;

import com.abbajoa.common.httpclient.SocketConnectionData;

public class SPOJ implements JudgeSite {
	public static final SocketConnectionData CONNECTION_DATA = new SocketConnectionData(false, "www.spoj.com", 80);
	public static final String ENCODING = "ISO8859_1";

	@Override
	public CompileErrorMessageReceiver getCompileErrorMessageReceiver() {
		return new SPOJCompileErrorMessageReceiver();
	}

	@Override
	public Set<Language> getSupportedLanguageSet() {
		return SetFromIterable.create(KeysInMap.get(getLanguageCodeMap()));
	}

	@Override
	public String getName() {
		return "Sphere Online Judge";
	}

	@Override
	public SubmitStatusReceiver getSubmitStatusReceiver() {
		return new SPOJSubmitStatusReceiver();
	}

	@Override
	public CompileErrorMessageReceiverOld getCompileErrorMessageReceiverOld() {
		throw new RuntimeException();
	}

	@Override
	public SubmitStatusReceiverOld getSubmitStatusReceiverOld() {
		throw new RuntimeException();
	}

	@Override
	public SubmitterOld getSubmitterOld() {
		throw new RuntimeException();
	}

	@Override
	public Submitter getSubmitter() {
		return new SPOJSubmitter();
	}

	@Override
	public SocketConnectionData getSocketConnectionData() {
		return CONNECTION_DATA;
	}

	public static MutableMap<Language, Integer> getLanguageCodeMap() {
		MutableMap<Language, Integer> r = GoodMutableMapFactory.getInstance().create();
		r.put(Language.JAVA, 10);
		r.put(Language.CPP, 1);
		r.put(Language.C, 11);
		r.put(Language.CPP, 1);
//		r.put(Language.PYTHON, 4);
		return r;
	}
}