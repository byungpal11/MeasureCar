package com.doro.itf.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.text.SimpleDateFormat;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.doro.itf.db.Dbservice;
import com.doro.itf.file.FileUtil;
import com.doro.itf.log.LogMgr;
import com.doro.itf.properties.Property;

public class WatchDir2 extends Thread {

    private boolean runnable = false;
    private Property property = null;
    private LogMgr log = null;
    private Dbservice dbservice = null;
    private FileUtil fileutil = null;

    public WatchDir2() {
        property = new Property();
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

        // LocalDateTime currentdate = LocalDateTime.now();
        // LocalDateTime yesterday = currentdate.minusDays(1);
        // DateTimeFormatter formatter_month = DateTimeFormatter.ofPattern("MM");
        // String year = Integer.toString(yesterday.getYear());
        // String month = yesterday.format(formatter_month);
        // String day = Integer.toString(yesterday.getDayOfMonth());

        
        Date currenttime = new Date();
        Date yesterday= new Date(currenttime.getTime() + (1000 * 60 * 60 * 24 * -1));
        SimpleDateFormat format_YYYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_MM= new SimpleDateFormat("MM");
        SimpleDateFormat format_DD = new SimpleDateFormat("dd");

        String year = format_YYYY.format(yesterday);
        String month =  format_MM.format(yesterday);
        String day =  format_DD.format(yesterday);

        String basePath = property.ReadConfig("WIGTPATH");
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
        List<String> wigtdataList = new ArrayList<String>();

        for (int i = 0; i < fList.length; i++) {

            if (fList[i].isDirectory())
                continue;
            filePath = fList[i].getPath();
            fileName = fList[i].getName();
            // System.out.println(fList[i].getPath());
            // System.out.println(fList[i].getName());
            log.WriteLog(fList[i].getName(), false);

            if (!fileName.startsWith("X") && !fileName.startsWith("x")) {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String dataline = "";
                int datalineCnt = 0;
                while ((dataline = reader.readLine()) != null) {
                    if (datalineCnt != 0)
                        wigtdataList.add(dataline);
                    datalineCnt++;
                }
                reader.close();

                if (dbservice.insertweight(wigtdataList)) {
                    log.WriteLog(fList[i].getName() + ":DB Insert Success", false);
                    fileutil.PassFileMove(filePath, dirpath, fileName);
                } else {
                    log.WriteLog(fList[i].getName() + ":DB Insert FAIL", false);
                    fileutil.FailFileMove(filePath, dirpath, fileName);
                }
                wigtdataList.clear();
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
