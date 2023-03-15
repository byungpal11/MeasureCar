package com.doro.itf.job;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.doro.itf.data.Badloaddata;
import com.doro.itf.db.Dbservice;
import com.doro.itf.file.FileUtil;
import com.doro.itf.log.LogMgr;
import com.doro.itf.properties.Property;

public class WatchDir extends Thread {

    private boolean runnable = false;
    private Property property = null;
    private Badloaddata badloaddata = null;
    private LogMgr log = null;
    private Dbservice dbservice = null;
    private FileUtil fileutil = null;

    public WatchDir() {
        property = new Property();
        badloaddata = Badloaddata.getInstance();
        log = LogMgr.getInstance();
        dbservice = new Dbservice();
        fileutil = new FileUtil();

    }

    public boolean isRunnable() {
        return runnable;
    }

    public void doStart() {
        runnable = true;
        this.start();
    }

    public void stopThread() {
        runnable = false;
    }

    public void watchdirectory() throws IOException {

        Date currentdate =new Date(System.currentTimeMillis());
        SimpleDateFormat format_YYYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_MM= new SimpleDateFormat("MM");
        SimpleDateFormat format_DD = new SimpleDateFormat("dd");

        String year = format_YYYY.format(currentdate);
        String month =  format_MM.format(currentdate);
        String day =  format_DD.format(currentdate);
        
        // LocalDateTime currentdate = LocalDateTime.now();
        // DateTimeFormatter formatter_month = DateTimeFormatter.ofPattern("MM");

        // String year = Integer.toString(currentdate.getYear());
        // String month = currentdate.format(formatter_month);
        // String day = Integer.toString(currentdate.getDayOfMonth());

        String basePath = property.ReadConfig("ILSDPATH");
        String dirpath = "";
        dirpath = basePath;

        File dir = new File(dirpath);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        dirpath += year + "/";
        dir = new File(dirpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dirpath += month + "/";
        dir = new File(dirpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dirpath += day + "/";
        dir = new File(dirpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File[] fList = dir.listFiles();

        String filePath = "";
        String fileName = "";

        for (int i = 0; i < fList.length; i++) {

            if (fList[i].isDirectory())
                continue;
            filePath = fList[i].getPath();
            fileName = fList[i].getName();
            System.out.println(fList[i].getPath());
            System.out.println(fList[i].getName());

            if (!fileName.startsWith("X") && !fileName.startsWith("x")) {
                if (fileName.contains("C")) {
                    badloaddata.DataRead(filePath, fileName);
                    if (dbservice.insertbnds()) {
                        fileutil.PassFileMove(filePath, dirpath, fileName);
                        log.WriteLog(fileName + ":insert SUCCESS", false);

                    } else {
                        fileutil.FailFileMove(filePath, dirpath, fileName);
                        log.WriteLog(fileName + ":insert Fail", false);

                    }

                } else {
                    badloaddata.DataRead(filePath, fileName);
                    if (dbservice.updatebnds()) {
                        fileutil.PassFileMove(filePath, dirpath, fileName);
                        log.WriteLog(fileName + ":update SUCCESS", false);

                    } else {
                        fileutil.FailFileMove(filePath, dirpath, fileName);
                        log.WriteLog(fileName + ":update Fail", false);

                    }

                }

            }

        }

    }

    @Override
    public void run() {

        while (runnable) {
            try {
                Thread.sleep(1000);
                watchdirectory();
            } catch (InterruptedException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }

    }

}
