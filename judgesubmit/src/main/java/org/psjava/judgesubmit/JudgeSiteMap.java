package org.psjava.judgesubmit;

import org.psjava.ds.map.Map;
import org.psjava.ds.map.MutableMap;
import org.psjava.goods.GoodMutableMapFactory;

public class JudgeSiteMap {
	public static Map<String, JudgeSite> get() {
		MutableMap<String, JudgeSite> map = GoodMutableMapFactory.getInstance().create();
		map.put("spoj", new SPOJ());
		map.put("poj", new POJ());
		return map;
	}

	private JudgeSiteMap() {
	}
}
