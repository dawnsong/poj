package org.psjava.judgesubmit;

public class SiteParsingUtil {

	public static String extractMiddleString(String all, int start, String left, String right) throws SiteParsingException {
		int leftp = getStrictIndexOf(all, left, start);
		int rightp = getStrictIndexOf(all, right, leftp + left.length());
		return all.substring(leftp + left.length(), rightp);
	}

	public static int getStrictIndexOf(String all, String sub, int start) throws SiteParsingException {
		int pos = all.indexOf(sub, start);
		if (pos == -1)
			throw new SiteParsingException("Substring is not exist. " + sub + " in " + all);
		return pos;
	}

	public static void assertContains(String all, String sub) throws SiteParsingException {
		assertTrue(all.contains(sub));
	}

	public static void assertTrue(boolean value) throws SiteParsingException {
		if (!value)
			throw new SiteParsingException();
	}

	public static int parseInt(String s) throws SiteParsingException {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new SiteParsingException(e);
		}
	}

	public static double parseDouble(String s) throws SiteParsingException {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new SiteParsingException(e);
		}
	}

}
