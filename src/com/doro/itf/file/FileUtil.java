package com.doro.itf.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

//commons-io-2.11.0.jar use
public class FileUtil {

    public FileUtil() {

    }

    public void PassFileMove(String inFilePath, String filePath, String FileName) {
        String newFilePath = filePath + "/PASS";
        File dir = new File(newFilePath);

        if (!dir.exists())
            dir.mkdir();

        try {
            FileMove(inFilePath, newFilePath + "/" + FileName);

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void FailFileMove(String inFilePath, String filePath, String FileName) {
        String newFilePath = filePath + "/FAIL";
        File dir = new File(newFilePath);

        if (!dir.exists())
            dir.mkdir();

        try {
            FileMove(inFilePath, newFilePath + "/" + FileName);
        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void FileMove(String inFilePath, String outFilePath) {

        File inFile = new File(inFilePath);
        File outFile = new File(outFilePath);

        try {
            FileUtils.moveFile(inFile, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // public void FileMove(String inFileName, String outFileName) {

    // try {
    // FileInputStream fis = new FileInputStream(inFileName);
    // FileOutputStream fos = new FileOutputStream(outFileName);

    // int data = 0;
    // while ((data = fis.read()) != -1) {
    // fos.write(data);
    // }
    // fis.close();
    // fos.close();

    // } catch (IOException e) {
    // e.printStackTrace();
    // }

    // }

    public void FileRename(String inFilePath, String outFilePath) {

        File inFile = new File(inFilePath);
        File outFile = new File(outFilePath);

        try {
            FileUtils.moveFile(inFile, outFile);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void FileRemove(String inFilePath) {

        File inFile = new File(inFilePath);

        try {
            FileUtils.forceDelete(inFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void FileCopy(String inFilePath, String outFilePath) {

        File inFile = new File(inFilePath);
        File outFile = new File(outFilePath);

        try {
            FileUtils.copyFile(inFile, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
