package com.doro.itf.control;

import com.doro.itf.job.WatchDir;
import com.doro.itf.job.WatchDir1;
import com.doro.itf.job.WatchDir2;
import com.doro.itf.log.LogMgr;

public class WatchDog extends Thread {

	private long lAlarmTime = 1 * 60 * 1000;
	private boolean runnable = true;
	private LogMgr log = null;
	public WatchDir watchdir = null;
	public WatchDir1 watchdir1= null;
	public WatchDir2 watchdir2= null;

	public WatchDog() {
		log = LogMgr.getInstance();
	}

	public void doStart() {
		runnable = true;
		this.start();
	}

	public void stopThread() {
		runnable = false;
	}

	@Override
	public void run() {

		while (runnable) {
			System.gc();
			try {

				if (watchdir == null) {
					watchdir = new WatchDir();
				}
				if (!watchdir.isAlive() || !watchdir.isRunnable()) {
					watchdir = null;
					watchdir = new WatchDir();
					watchdir.doStart();
					log.WriteLog("Not Alive!! Watchdir start!!", true);
				} 

				if (watchdir1 == null) {
					watchdir1 = new WatchDir1();
				}
				if (!watchdir1.isAlive() || !watchdir1.isRunnable()) {
					watchdir1 = null;
					watchdir1 = new WatchDir1();
					watchdir1.doStart();
					log.WriteLog("Not Alive!! Watchdir1 start!!", true);
				} 

				if (watchdir2 == null) {
					watchdir2 = new WatchDir2();
				}
				if (!watchdir2.isAlive() || !watchdir2.isRunnable()) {
					watchdir2 = null;
					watchdir2 = new WatchDir2();
					watchdir2.doStart();
					log.WriteLog("Not Alive!! Watchdir2 start!!", true);
				} 

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				try {
					Thread.sleep(lAlarmTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
