package com.doro.itf.job;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.doro.itf.data.Badloaddata;
import com.doro.itf.db.Dbservice;
import com.doro.itf.file.FileUtil;
import com.doro.itf.log.LogMgr;
import com.doro.itf.properties.Property;

public class WatchDir extends Thread {

    private boolean runnable;
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

        LocalDateTime currentdate = LocalDateTime.now();
        // DateTimeFormatter formatter_year = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter formatter_month = DateTimeFormatter.ofPattern("MM");
        // DateTimeFormatter formatter_day = DateTimeFormatter.ofPattern("dd");
        String year = Integer.toString(currentdate.getYear());
        String month = currentdate.format(formatter_month);
        String day = Integer.toString(currentdate.getDayOfMonth());

        // System.out.println(year + month + day);

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
                    if (dbservice.insertbnds())
                        fileutil.PassFileMove(filePath, dirpath, fileName);
                    else
                        fileutil.FailFileMove(filePath, dirpath, fileName);
                } else {
                    badloaddata.DataRead(filePath, fileName);
                    if (dbservice.updatebnds())
                        fileutil.PassFileMove(filePath, dirpath, fileName);
                    else
                        fileutil.FailFileMove(filePath, dirpath, fileName);
                }

            }

        }

    }

    @Override
    public void run() {

        while (runnable) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            try {
                watchdirectory();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }

    }

}
