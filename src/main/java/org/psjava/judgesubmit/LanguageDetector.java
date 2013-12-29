package org.psjava.judgesubmit;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.psjava.ds.map.MutableMap;
import org.psjava.goods.GoodMutableMapFactory;

public class LanguageDetector {

	public static boolean isSupported(File file) {
		return getMap().containsKey(getKey(file));
	}

	public static Language detect(File file) {
		return getMap().get(getKey(file));
	}

	private static String getKey(File file) {
		return getExtension(file).toLowerCase();
	}

	public static String getExtension(File file) {
		return FilenameUtils.getExtension(file.getAbsolutePath());
	}

	private static MutableMap<String, Language> getMap() {
		MutableMap<String, Language> map = GoodMutableMapFactory.getInstance().create();
		map.put("java", Language.JAVA);
		map.put("cpp", Language.CPP);
		map.put("c", Language.C);
		map.put("cc", Language.CPP);
		return map;
	}

	private LanguageDetector() {
	}
}
