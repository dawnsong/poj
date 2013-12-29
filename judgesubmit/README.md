judgesubmit
==================================================
judgesubmit is a command line submit tool for,
- Sphere Online Judge (www.spoj.com)
- PKU Judge Online (www.poj.org)

### Install

Download the lastest release and unzip. 
- [download](https://github.com/kongbomb/judgesubmit/releases)

judgesubmit requires JRE6 or higher. Install if you don't have it.
* [download](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

### Example

```Bash
$ judgesubmit spoj TEST test.cpp
--:--.---s ---,---KB WAITING
--:--.---s ---,---KB COMPILING
--:--.---s ---,---KB COMPILING
--:--.---s ---,---KB COMPILING
--:--.---s ---,---KB RUNNING
--:--.---s ---,---KB RUNNING
00:04.680s 182,272KB ACCEPTED
```

### Usage

```Bash
usage: judgesubmit [-options] [site code] [problem id] [source file path]
 -c,--char-set <arg>      character set of input files. default is UTF-8
 -l,--language <arg>      language code of source file. without this
                          option, language is detected by file's
                          extension.
 -p,--password <arg>      password for the judge site
 -s,--search-path <arg>   search path(s) for external source code
                          supported languages: java,cpp,c
                          merging is performed by CodeCombine
                          (https://github.com/abbajoa/codecombine)
 -u,--user-id <arg>       user id for the judge site

supported judge site code:
	poj	PKU Judge Online (poj.org)
		language code: c,cpp,java
	spoj	Sphere Online Judge (www.spoj.com)
		language code: c,cpp,java
```
