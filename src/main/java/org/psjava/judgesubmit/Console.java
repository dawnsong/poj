package org.psjava.judgesubmit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.psjava.ds.array.Array;
import org.psjava.ds.array.DynamicArray;
import org.psjava.ds.map.KeysInMap;
import org.psjava.ds.map.Map;
import org.psjava.ds.set.Set;
import org.psjava.ds.set.SetFromVarargs;
import org.psjava.util.Pair;
import org.psjava.util.StringMerger;

public class Console {

	// TODO remove unknown after migration
	public static final Set<SubmitStatusCode> ON_PROGRESS = SetFromVarargs.create(SubmitStatusCode.COMPILING, SubmitStatusCode.RUNNING, SubmitStatusCode.WAITING, SubmitStatusCode.UNKNOWN, SubmitStatusCode.JUDGING);

	public static void main(String[] args) {
		if (args.length == 0) {
			printUsage();
			return;
		}
		CommandLineParser parser = new GnuParser();
		try {
			CommandLine cmd = parser.parse(createOptions(), args);
			String[] withoutOptionArgs = cmd.getArgs();
			if (withoutOptionArgs.length < 3)
				throw new InvalidCommandRuleException("too few parameters");
			if (withoutOptionArgs.length > 3)
				throw new InvalidCommandRuleException("too many parameters");
			String siteCode = withoutOptionArgs[0];
			String problemId = withoutOptionArgs[1];
			String inputCharacterSet = cmd.getOptionValue("c", "UTF-8");
			File sourceFile = new File(withoutOptionArgs[2]);
			if (!JudgeSiteMap.get().containsKey(siteCode))
				throw new InvalidCommandRuleException("Unknown site code: " + siteCode);
			if (!sourceFile.exists())
				throw new InvalidInputException("Source file is not exist: " + sourceFile.getAbsolutePath());
			if (!Charset.isSupported(inputCharacterSet))
				throw new InvalidCommandRuleException("Unsupported character set: " + inputCharacterSet);
			java.util.Set<String> searchDirs = new TreeSet<String>();
			Language language;
			if (cmd.hasOption("l")) {
				String code = cmd.getOptionValue("l");
				language = toLanguage(code, null);
				if (language == null)
					throw new InvalidCommandRuleException("Invalid language code: " + code);
			} else {
				if (!LanguageDetector.isSupported(sourceFile))
					throw new InvalidCommandRuleException("Unsupported file type: " + sourceFile.getName());
				language = LanguageDetector.detect(sourceFile);
				if (cmd.hasOption("s")) {
					if (!CodeCombineAdapter.isSupported(language))
						throw new InvalidCommandRuleException("Not supported language for CodeCombine: " + language);
					for (String dir : cmd.getOptionValues("s")) {
						if (!new File(dir).exists())
							throw new InvalidInputException("Search directory is not exist: " + dir);
						searchDirs.add(dir);
					}
				}
			}

			String userid;
			String password;
			if (cmd.hasOption('u'))
				userid = cmd.getOptionValue('u', "");
			else
				userid = Util.readNormal("Enter user id: ");
			if (cmd.hasOption('p'))
				password = cmd.getOptionValue('p', "");
			else
				password = Util.readPassword("Enter password: ");

			JudgeSite site = JudgeSiteMap.get().get(siteCode);
			FileInputStream fis = new FileInputStream(sourceFile);
			String code = StreamUtil.readAllAsString(fis, inputCharacterSet);
			JudgeSubmit.submitAngGetResult(site, problemId, userid, password, code, language, searchDirs, new SubmitMonitorListener() {
				@Override
				public void statusChanged(SubmitStatus judgeStatus) {
					System.out.println(getTimeDescription(judgeStatus.getTimeUsage(-1)) + " " + getMemoryDescription(judgeStatus.getMemoryUsage(-1)) + " " + judgeStatus.getCode());
				}

				@Override
				public void onCompileError(String message) {
					System.out.println("Compile Error:");
					System.out.println(message);
				}
			});
			fis.close();
		} catch (ParseException e) {
			System.out.println("Invalid parameter: " + e.getMessage());
			System.out.println();
			printUsage();
		} catch (InvalidCommandRuleException e) {
			System.out.println("Invalid command line: " + e.getMessage());
			System.out.println();
			printUsage();
		} catch (InvalidInputException e) {
			System.out.println("Invalid Input: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O exception: " + e.toString());
		} catch (SiteParsingException e) {
			System.out.println("Parsing error:");
			e.printStackTrace(); // TODO add crash report
		} catch (JudgeServiceException e) {
			System.out.println("Service error:");
			e.printStackTrace(); // TODO add crash report
		}
	}

	private static Options createOptions() {
		Options options = new Options();
		options.addOption("u", "user-id", true, "user id for the judge site");
		options.addOption("p", "password", true, "password for the judge site");
		options.addOption("c", "char-set", true, "character set of input files. default is UTF-8");
		options.addOption("l", "language", true, "language code of source file. without this option, language is detected by file's extension.");
		DynamicArray<String> supported = DynamicArray.create();
		for (Language lang : Language.values())
			if (CodeCombineAdapter.isSupported(lang))
				supported.addToLast(toLanguageCode(lang));
		options.addOption("s", "search-path", true, "search path(s) for external source code\nsupported languages: " + StringMerger.merge(supported, ",") + "\n" + "merging is performed by CodeCombine\n(https://github.com/abbajoa/codecombine)\n");
		return options;
	}

	protected static Language toLanguage(String codeFromConsole, Language def) {
		for (Pair<String, Language> p : getLanguageAndCodes())
			if (p.v1.equals(codeFromConsole))
				return p.v2;
		return def;
	}

	protected static String toLanguageCode(Language lang) {
		for (Pair<String, Language> p : getLanguageAndCodes())
			if (p.v2.equals(lang))
				return p.v1;
		throw new RuntimeException();
	}

	private static Array<Pair<String, Language>> getLanguageAndCodes() {
		DynamicArray<Pair<String, Language>> pairs = DynamicArray.create();
		pairs.addToLast(Pair.create("java", Language.JAVA));
		pairs.addToLast(Pair.create("cpp", Language.CPP));
		pairs.addToLast(Pair.create("c", Language.C));
		return pairs;
	}

	public static void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("judgesubmit [-options] [site code] [problem id] [source file path]", createOptions());
		System.out.println();
		System.out.println("supported judge site code:");
		Map<String, JudgeSite> map = JudgeSiteMap.get();
		for (String code : KeysInMap.get(map)) {
			JudgeSite site = map.get(code);
			System.out.println("\t" + code + "\t" + site.getName() + " (" + site.getSocketConnectionData().host + ")");
			DynamicArray<String> supported = DynamicArray.create();
			for (Language lang : site.getSupportedLanguageSet())
				supported.addToLast(toLanguageCode(lang));
			System.out.print("\t\tlanguage code: " + StringMerger.merge(supported, ","));
			System.out.println();
		}
	}

	public static String getTimeDescription(long timeOrNegativeOne) {
		if (timeOrNegativeOne == -1) {
			return "--:--.---s";
		} else {
			long remain = timeOrNegativeOne;
			long milli = remain % 1000;
			timeOrNegativeOne /= 1000;
			long sec = timeOrNegativeOne % 60;
			timeOrNegativeOne /= 60;
			long min = timeOrNegativeOne;
			return String.format("%02d:%02d.%03ds", min, sec, milli);
		}
	}

	public static String getMemoryDescription(long usageOrNegativeOne) {
		if (usageOrNegativeOne == -1) {
			return "---,---KB";
		} else {
			long remain = usageOrNegativeOne;
			remain /= 1024;
			long post = remain % 1000;
			long pre = remain / 1000;
			return String.format("%03d,%03dKB", pre, post);
		}
	}

}