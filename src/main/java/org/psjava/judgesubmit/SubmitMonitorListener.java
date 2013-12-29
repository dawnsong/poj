package org.psjava.judgesubmit;

public interface SubmitMonitorListener {
	void statusChanged(SubmitStatus judgeStatus);
	void onCompileError(String message);
}
