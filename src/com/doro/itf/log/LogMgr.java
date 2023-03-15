package com.doro.itf.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.util.Date;

//LOG
public class LogMgr {

	private volatile static LogMgr instance = null;

	public LogMgr() {

	}

	public static LogMgr getInstance() {
		if (instance == null) {
			synchronized (LogMgr.class) {
				if (instance == null) {
					instance = new LogMgr();
				}
			}
		}
		return instance;
	}

	public void WriteLog(String strlog, boolean print) {
		String Path;
		String Descripton;

		Path = LogPath();
		Descripton = LogDescription();

		File dir = new File("./log");
		FileWriter writer = null;

		if (!dir.exists())
			dir.mkdirs();

		try {
			writer = new FileWriter(Path, true);
			writer.write(Descripton);
			writer.write(strlog);
			writer.write("\r\n");
			writer.flush();
			writer.close();
			if (print) {
				System.out.println(strlog);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String LogPath() {
		String strLogPath;

		// LocalDateTime currenttime = LocalDateTime.now();
		// DateTimeFormatter currentdate_fomat = DateTimeFormatter.ofPattern("yyyyMMdd");

		Date currenttime =new Date(System.currentTimeMillis());
		SimpleDateFormat format_current =new SimpleDateFormat("yyyyMMdd");

		strLogPath = "./log/" + format_current.format(currenttime)+ "MeasureCar.log";

		return strLogPath;

	}


	public static String LogDescription() {
		String str;
		// LocalDateTime currenttime = LocalDateTime.now();
		// DateTimeFormatter currentdate_fomat = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
		Date currenttime =new Date(System.currentTimeMillis());
		SimpleDateFormat format_current =new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

		str = "[" + format_current.format(currenttime) + "]:";

		return str;
	}
}
