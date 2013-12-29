package org.psjava.judgesubmit;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringEscapeUtils;
import org.psjava.ds.queue.Queue;
import org.psjava.goods.GoodQueueFactory;
import org.psjava.util.AssertStatus;
import org.psjava.util.Pair;

import com.abbajoa.common.httpclient.SocketConnectionData;

public class MockHttpClient {
	public static JudgeHttpClient create(SocketConnectionData conn) {
		final String caller = RecordingHttpClient.toCallerKey(Thread.currentThread().getStackTrace()[2]);
		final Queue<Pair<String, String>> q = GoodQueueFactory.getInstance().create();
		File f = RecordingHttpClient.getRecordFile(caller);
		AssertStatus.assertTrue(f.exists(), "not exist for caller: " + caller);
		try {
			Scanner in = new Scanner(f);
			while (in.hasNextLine()) {
				String key = StringEscapeUtils.unescapeJava(in.nextLine());
				String body = StringEscapeUtils.unescapeJava(in.nextLine());
				q.enque(Pair.create(key, body));
			}
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new JudgeHttpClient() {
			@Override
			public void clearCookie() {
				// ignores.
			}

			@Override
			public String receivePostBodyString(String path, Map<String, String> param, String encoding) throws IOException, JudgeServiceException {
				return dequeBody(q, RecordingHttpClient.toPostRequestKey(path, param, encoding));
			}

			@Override
			public String receiveGetBodyString(String path, String encoding) throws IOException, JudgeServiceException {
				return dequeBody(q, RecordingHttpClient.toGetResultKey(path, encoding));
			}

			private String dequeBody(final Queue<Pair<String, String>> q, String key) {
				AssertStatus.assertTrue(!q.isEmpty(), "no more mock data: " + key);
				Pair<String, String> p = q.deque();
				if (!p.v1.equals(key))
					throw new RuntimeException("invalid request.  next data is " + p.v1 + " but requested " + key);
				return p.v2;
			}

		};
	}
}