package com.doro.itf.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {


	public Property() {

	}

	public String ReadConfig(String str) throws IOException {
		String msg;

		// 프로퍼티 파일 위치
		String propFile = "./conf/Measurecar.properties";

		// 프로퍼티 객체 생성
		Properties props = new Properties();

		// 프로퍼티 파일 스트림에 담기
		FileInputStream fis = new FileInputStream(propFile);

		// 프로퍼티 파일 로딩
		props.load(fis);

		// 항목 읽기
		msg = props.getProperty(str);

		return msg;

	}

}

