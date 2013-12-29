package org.psjava.judgesubmit;

import java.util.Map;
import java.util.TreeMap;

public class StringMapFromVarargs {

	public static Map<String, String> create(String... param) {
		java.util.Map<String, String> map = new TreeMap<String, String>();
		for (int i = 0; i < param.length; i += 2)
			map.put(param[i], param[i + 1]);
		return map;
	}

}
