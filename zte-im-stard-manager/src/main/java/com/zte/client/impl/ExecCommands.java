package com.zte.client.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调用系统命令.
 * 
 * @author wangpk.
 * 
 */
public class ExecCommands {
	private static ExecCommands exec = null;
	private static Logger logger = LoggerFactory.getLogger(ExecCommands.class);
	@SuppressWarnings("unused")
	private static final String ERROR_RETURN = "error";

	private ExecCommands() {

	}

	public static ExecCommands getInstance() {
		if (exec == null) {
			exec = new ExecCommands();
		}
		return exec;
	}

	protected void process(String path, String tmpPath, String toolPath)
			throws IOException {
		Properties props = System.getProperties();
		String os = props.getProperty("os.name");
		List<String> commands = new ArrayList<String>();
		// 不能有空格
		if (os.toLowerCase().contains("window")) {
			commands.add(toolPath + "\\apktool.bat");
		} else {
			commands.add("java");
			commands.add("-jar");
			commands.add(toolPath + "/apktool.jar");
		}
		commands.add("d");
		commands.add(path);
		commands.add(tmpPath);
		ProcessBuilder builder = new ProcessBuilder(commands);
		command(builder);
	}

	/**
	 * 调用Process执行命令.
	 * 
	 * @param commands
	 * @throws CwebpException
	 * @throws IOException
	 */
	private void command(ProcessBuilder builder) throws IOException {
		BufferedReader br = null;
		Process process = null;
		try {
			builder.redirectErrorStream(true);
			process = builder.start();
			br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				logger.info(line);
			}
			WatchThread wt = new WatchThread(process);
			wt.start();
			process.waitFor();
			wt.setOver(true);
		} catch (java.io.IOException e) {
			logger.error("IOException " + e.getMessage());
			throw new IOException();
		} catch (InterruptedException e) {
			logger.error("", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			if (process.getInputStream() != null) {
				process.getInputStream().close();
			}
		}
	}

}

/**
 * 监视process的输入流是否清空.
 * 
 * @author wangpk
 * 
 */
class WatchThread extends Thread {
	Process p;
	boolean over;

	private static Logger logger = LoggerFactory.getLogger(WatchThread.class);

	public WatchThread(Process p) {
		this.p = p;
		over = false;
	}

	public void run() {
		BufferedReader br = null;
		try {
			if (p == null)
				return;
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while (true) {
				if (p == null || over) {
					break;
				}
				while (br.readLine() != null) {
				}
				;
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}
	}

	public void setOver(boolean over) {
		this.over = over;
	}
}
