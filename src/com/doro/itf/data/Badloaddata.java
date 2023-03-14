package com.doro.itf.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Badloaddata {

    private volatile static Badloaddata instance = null;
    public String Ic_code = "",
            DetectionDate = "",
            DetectionRoad = "",
            DetectionNum = "",
            CarNumber = "",
            CarType = "",
            ReportType = "",
            ReportReason = "",
            FsID = "superAdmin",
            LsID = "superAdmin";

    public Badloaddata() {

    }

    public static Badloaddata getInstance() {
        if (instance == null) {
            synchronized (Badloaddata.class) {
                if (instance == null) {
                    instance = new Badloaddata();
                }
            }
        }
        return instance;
    }

    public String getIc_code() {
        return this.Ic_code;
    }

    public String getDetectionDate() {
        return this.DetectionDate;
    }

    public String getDetectionRoad() {
        return this.DetectionRoad;
    }

    public String getDetectionNum() {
        return this.DetectionNum;
    }

    public String getCarNumber() {
        return this.CarNumber;
    }

    public String getCarType() {
        return this.CarType;
    }

    public String getReportType() {
        return this.ReportType;
    }

    public String getReportReason() {
        return this.ReportReason;
    }

    public String getFsID() {
        return this.FsID;
    }

    public String getLsID() {
        return this.LsID;
    }

    public void DataRead(String strPath, String filename) {
        byte[] readdata = new byte[89];
        String Data = "";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(strPath));
            Data = reader.readLine();

            readdata = Data.getBytes("euc-kr");
            reader.close();
        } catch (FileNotFoundException e) {
    
            e.printStackTrace();
        } catch (IOException e) {
    
            e.printStackTrace();
        }

        try {
            this.Ic_code = filename.substring(0, 4);
            this.DetectionDate = new String(readdata, 0, 14, "euc-kr");
            this.DetectionRoad = new String(readdata, 14, 2, "euc-kr");
            this.DetectionNum = new String(readdata, 16, 5, "euc-kr");
            this.CarNumber = new String(readdata, 21, 16, "euc-kr");
            this.CarType = new String(readdata, 37, 1, "euc-kr");
            this.ReportType = new String(readdata, 38, 1, "euc-kr");
            this.ReportReason = new String(readdata, 39, 50, "euc-kr");
        } catch (UnsupportedEncodingException e) {
    
            e.printStackTrace();
        }
        printdata();

    }

    void printdata() {
        String str = "";

        str = getIc_code();
        System.out.println(str);

        str = getDetectionDate();
        System.out.println(str);

        str = getDetectionNum();
        System.out.println(str);

        str = getDetectionRoad();
        System.out.println(str);

        str = getCarNumber();
        System.out.println(str);

        str = getCarType();
        System.out.println(str);

        str = getReportType();
        System.out.println(str);

        str = getReportReason();
        System.out.println(str);
    }

    void insertdata()
    {

    }
}
