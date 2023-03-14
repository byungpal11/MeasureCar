package com.doro.itf;

/*DATE:
 * SOURCE MAKEER: ITFORWORD
 */


import com.doro.itf.control.WatchDog;
import com.doro.itf.log.LogMgr;


public class MeasureCar {

	public static LogMgr log = null;

	private MeasureCar() {
		log =LogMgr.getInstance();
	}

	private void go(){
		WatchDog watchdog = new WatchDog();
		watchdog.doStart();
	}

	public static void main(String args[]){
		MeasureCar measure =new MeasureCar();
		log.WriteLog("Start MeasureProcess ...", true);	
		measure.go();

	}

}
