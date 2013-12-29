package org.psjava.judgesubmit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import com.abbajoa.common.httpclient.CookieKeepingHttpClient;
import com.abbajoa.common.httpclient.CookieKeepingHttpClientFactory;
import com.abbajoa.common.httpclient.HttpGet;
import com.abbajoa.common.httpclient.HttpPost;
import com.abbajoa.common.httpclient.HttpResponse;
import com.abbajoa.common.httpclient.KeepAliveHttpClientFactory;
import com.abbajoa.common.httpclient.SocketConnectionData;
import com.abbajoa.common.httpclient.TransferProgressListener;

public class RealJudgeHttpClient implements JudgeHttpClient {

	@Deprecated
	public static JudgeHttpClient wrap(CookieKeepingHttpClient client) {
		return new RealJudgeHttpClient(client);
	}

	public static JudgeHttpClient create(SocketConnectionData conn) {
		return new RealJudgeHttpClient(conn);
	}

	private static final CookieKeepingHttpClientFactory FACTORY = new CookieKeepingHttpClientFactory(new KeepAliveHttpClientFactory());
	private CookieKeepingHttpClient client;

	private RealJudgeHttpClient(CookieKeepingHttpClient client) {
		this.client = client;
	}

	private RealJudgeHttpClient(SocketConnectionData site) {
		this.client = FACTORY.create(site, "1.1", 10 * 1000, 10 * 1000, 30 * 1000);
	}

	@Override
	public void clearCookie() {
		client.clearCookie();
	}

	@Override
	public String receivePostBodyString(String path, Map<String, String> param, String encoding) throws IOException, JudgeServiceException {
		HttpResponse r = HttpPost.receive(client, path, new TreeMap<String, String>(), param, encoding, TransferProgressListener.EMPTY_LISTENER);
		if (isErrorCode(r.code))
			throw new JudgeServiceException(r.code + ":POST:" + path);
		return extractBody(r, encoding);
	}

	@Override
	public String receiveGetBodyString(String path, String encoding) throws IOException, JudgeServiceException {
		HttpResponse r = HttpGet.request(client, path, new TreeMap<String, String>(), new TreeMap<String, String>(), encoding, TransferProgressListener.EMPTY_LISTENER);
		if (isErrorCode(r.code))
			throw new JudgeServiceException(r.code + ":GET:" + path);
		return extractBody(r, encoding);
	}

	private boolean isErrorCode(int code) {
		return code != 200 && code != 302;
	}

	private String extractBody(HttpResponse r, String encoding) throws UnsupportedEncodingException {
		return new String(r.responseBytes, encoding);
	}

}
