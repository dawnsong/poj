package org.psjava.judgesubmit;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringEscapeUtils;
import org.psjava.ds.array.DynamicArray;
import org.psjava.util.Pair;

import com.abbajoa.common.httpclient.SocketConnectionData;

public class RecordingHttpClient {
	public static JudgeHttpClient create(SocketConnectionData conn) {
		final JudgeHttpClient real = RealJudgeHttpClient.create(conn);
		final String caller = toCallerKey(Thread.currentThread().getStackTrace()[2]);
		final DynamicArray<Pair<String, String>> resultList = DynamicArray.create();

		return new JudgeHttpClient() {

			@Override
			public void clearCookie() {
				real.clearCookie(); // TODO record this and simulate in mock.
			}

			@Override
			public String receivePostBodyString(String path, Map<String, String> param, String encoding) throws IOException, JudgeServiceException {
				String key = toPostRequestKey(path, param, encoding);
				String body = real.receivePostBodyString(path, param, encoding);
				addResult(resultList, key, body);
				return body;
			}

			@Override
			public String receiveGetBodyString(String path, String encoding) throws IOException, JudgeServiceException {
				String key = toGetResultKey(path, encoding);
				String body = real.receiveGetBodyString(path, encoding);
				addResult(resultList, key, body);
				return body;
			}

			private void addResult(final DynamicArray<Pair<String, String>> resultList, String key, String r) {
				resultList.addToLast(Pair.create(key, r));
				File f = getRecordFile(caller);
				try {
					PrintWriter writer = new PrintWriter(f, "UTF-8");
					for (Pair<String, String> p : resultList) {
						writer.println(StringEscapeUtils.escapeJava(p.v1));
						writer.println(StringEscapeUtils.escapeJava(p.v2));
					}
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

		};
	}

	public static String toCallerKey(StackTraceElement calledStack) {
		return calledStack.getClassName() + "." + calledStack.getMethodName();
	}

	public static String toPostRequestKey(String path, Map<String, String> param, String encoding) {
		return path + ":POST:" + new TreeMap<String, String>(param).toString() + ":" + encoding;
	}

	public static String toGetResultKey(String path, String encoding) {
		return path + ":GET:" + encoding;
	}

	public static File getRecordFile(final String caller) {
		File dir = new File(System.getProperty("user.dir") + "/src/test/resources");
		if (!dir.exists())
			throw new RuntimeException();
		return new File(dir.getAbsolutePath() + "/" + caller + ".txt");
	}
}