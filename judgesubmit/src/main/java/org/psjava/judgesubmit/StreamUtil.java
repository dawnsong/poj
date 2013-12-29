package org.psjava.judgesubmit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	public static String readAllAsString(InputStream is, String charsetName) throws IOException {
		byte[] buf = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while (true) {
			int read = is.read(buf);
			if (read == -1)
				break;
			bos.write(buf, 0, read);
		}
		return new String(bos.toByteArray(), charsetName);
	}

}
