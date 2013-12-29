package org.psjava.judgesubmit;

import java.io.IOException;
import java.util.Collection;

import org.psjava.ds.map.Map;
import org.psjava.ds.map.MutableMap;
import org.psjava.goods.GoodMutableMapFactory;

import com.abbajoa.codecombine.LanguageToolSelector;
import com.abbajoa.codecombine.combiner.Combiner;
import com.abbajoa.codecombine.combiner.InvalidDataException;

public class CodeCombineAdapter {

	public static String combine(String code, Language language, Collection<String> searchDirs) throws IOException, InvalidInputException {
		Combiner combiner = LanguageToolSelector.createLanguageTool(CodeCombineAdapter.convertToCombinesLanguage(language)).createCombiner();
		try {
			return combiner.combine(code, searchDirs);
		} catch (InvalidDataException e) {
			throw new InvalidInputException("Invalid data while combining external code: " + e.getMessage());
		}
	}

	private static com.abbajoa.codecombine.Language convertToCombinesLanguage(Language language) {
		return createMap().get(language);
	}

	public static boolean isSupported(Language language) {
		return createMap().containsKey(language);
	}

	private static Map<Language, com.abbajoa.codecombine.Language> createMap() {
		MutableMap<Language, com.abbajoa.codecombine.Language> map = GoodMutableMapFactory.getInstance().create();
		map.put(Language.CPP, com.abbajoa.codecombine.Language.CPP);
		map.put(Language.C, com.abbajoa.codecombine.Language.CPP);
		map.put(Language.JAVA, com.abbajoa.codecombine.Language.JAVA);
		return map;
	}

}
