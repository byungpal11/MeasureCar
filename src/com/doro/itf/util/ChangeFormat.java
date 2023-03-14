package com.doro.itf.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChangeFormat {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public ChangeFormat() {
	}

	public String changeDecimalFormat(int number, String format) {
		DecimalFormatSymbols symbol = new DecimalFormatSymbols();
		DecimalFormat df = new DecimalFormat(format, symbol);
		return df.format(number);
	}

	public Date getCalDate(int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		// java.util.Calendar.MONTH, -1
		calendar.add(field, amount);

		return calendar.getTime();
	}

	public String getDate(String format, Date date) {
		String day = (new SimpleDateFormat(format)).format(date);
		return day;
	}

	public String getToday(String format) {
		String today = (new SimpleDateFormat(format)).format(new Date());
		return today;
	}

	public String chageDateFormat(Date date, String format) {
		return (new SimpleDateFormat(format)).format(date);
	}

	public byte[] hexToBytes(char[] hex) {
		int length = hex.length / 2;
		byte[] raw = new byte[length];
		for (int i = 0; i < length; i++) {
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;

			if (value > 127) {
				value -= 256;
			}
			raw[i] = (byte) value;
		}

		return raw;
	}

	public byte[] hexToBytes(String hex) {
		return hexToBytes(hex.toCharArray());
	}

	public String byteToHex(byte b) {
		char[] buf = { HEX_DIGITS[(b >>> 4) & 0x0F], HEX_DIGITS[b & 0x0F] };

		return new String(buf);
	}

	public String byteToHex(String str) {
		byte[] b = str.getBytes();
		String hex = "";

		for (int i = 0; i < b.length; i++) {
			hex = hex + byteToHex(b[i]);
		}

		return hex;
	}

	public String byteToHex(byte[] b) {
		String hex = "";

		for (int i = 0; i < b.length; i++) {
			hex = hex + byteToHex(b[i]);
		}

		return hex;
	}

}
