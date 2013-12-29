package org.psjava.judgesubmit;

import java.io.IOException;

public class Util {

	private Util() {
	}

	public static String readPassword(String msg) throws IOException {
		return new String(getConsole().readPassword(msg));
	}

	public static String readNormal(String msg) throws IOException {
		return new String(getConsole().readLine(msg));
	}

	private static java.io.Console getConsole() throws IOException {
		java.io.Console console = System.console();
		if (console == null)
			throw new IOException("Couldn't get Console instance");
		return console;
	}
}
