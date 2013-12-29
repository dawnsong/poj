package org.psjava.judgesubmit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class FileUtil {

	public static void writeToFile(String input, String encoding, File file) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		OutputStream os = new FileOutputStream(file);
		os.write(input.getBytes(encoding));
		os.close();
	}

}
