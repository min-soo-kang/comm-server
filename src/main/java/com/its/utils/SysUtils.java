/*
 ***************************************************************************************************
 *               Copyright �� 2009-2010 HANTE Information Co., Ltd. Seoul, Korea
 *                                   All rights reserved.
 * 
 * �ý��� ��ƿ��Ƽ Ŭ����
 * 
 * Programmer(s): shjung@hanteinfo.com
 *
 * REVISION HISTORY
 *     1. [2010/12/22][VER-1.0][shjung] : created
 *     2. 
 ***************************************************************************************************
 */
package com.its.utils;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * A class to SysUtils
 * 
 * 
 */
public final class SysUtils
{

    private SysUtils()
    {
    }

    public static String getMethodName() {
		return Thread.currentThread().getStackTrace()[1].getMethodName();
	}

    public static int parseInteger(String paramVal, int defVal) {
    	int result = defVal;
    	try {
    		result = Integer.parseInt(paramVal);
    	} catch(Exception e) {
    		
    	}
    	return result;
    }

    public static float parseFloat(String paramVal, float defVal) {
    	float result = defVal;
    	try {
    		result = Float.parseFloat(paramVal);
    	} catch(Exception e) {
    		
    	}
    	return result;
    }
    
    public static double parseDouble(String paramVal, double defVal) {
    	double result = defVal;
    	try {
    		result = Double.parseDouble(paramVal);
    	} catch(Exception e) {
    		
    	}
    	return result;
    }

    /**
     * 현재시각을 문자열로 얻는다.
     *
     */
	public static String getSysTime()
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date dtLog = new java.util.Date();
		return sdfDate.format(dtLog);
/*
		Calendar now = Calendar.getInstance();
		String year = "0000" + now.get(Calendar.YEAR);
		String mon  = "00"   + now.get(Calendar.MONTH) + 1;
		String day  = "00"   + now.get(Calendar.DAY_OF_MONTH);
		String hour = "00"   + now.get(Calendar.HOUR_OF_DAY);
        String min  = "00"   + now.get(Calendar.MINUTE);
        String sec  = "00"   + now.get(Calendar.SECOND);

        year = year.substring(year.length() - 4, year.length());
        mon  =  mon.substring( mon.length() - 2,  mon.length());
        day  =  day.substring( day.length() - 2,  day.length());
        hour = hour.substring(hour.length() - 2, hour.length());
        min  =  min.substring( min.length() - 2,  min.length());
        sec  =  sec.substring( sec.length() - 2,  sec.length());

        return year+mon+day+hour+min+sec;
*/
	}

	public static int gapTime(String startTm, String endTm, int calcType) {

		if (startTm == null || startTm.equals("") || startTm.length() != 14) {
			return -1;
		}
		if (endTm == null || endTm.equals("") || endTm.length() != 14) {
			return -1;
		}

		FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyyMMddHHmmss", new Locale("ko_KR")); //Locale.getDefault());

		Date startDateTime, endDateTime;
		try {
			startDateTime = fastDateFormat.parse(startTm);
			endDateTime = fastDateFormat.parse(endTm);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}

		GregorianCalendar gcStartDateTime = new GregorianCalendar();
		GregorianCalendar gcEndDateTime = new GregorianCalendar();
		gcStartDateTime.setTime(startDateTime);
		gcEndDateTime.setTime(endDateTime);

		long gap = gcEndDateTime.getTimeInMillis() - gcStartDateTime.getTimeInMillis();
		long hour = gap / 1000L / 60L / 60L;
		long min = gap / 1000L / 60L;
		long second = gap / 1000L;
		if (Calendar.HOUR == calcType)
			return (int)hour;
		if (Calendar.MINUTE == calcType)
			return (int)min;
		if (Calendar.SECOND == calcType)
			return (int)second;
		return -1;
	}

	public static long ipToLong(String ipAddress) {
		String[] ipAddressInArray = ipAddress.split("\\.");
		long result = 0;
		for (int i = 0; i < ipAddressInArray.length; i++) {
			int power = 3 - i;
			int ip = Integer.parseInt(ipAddressInArray[i]);
			result += ip * Math.pow(256, power);
		}
		return result;
	}
	public static long ipToLong2(String ipAddress) {
		long result = 0;
		String[] ipAddressInArray = ipAddress.split("\\.");

		for (int i = 3; i >= 0; i--) {
			long ip = Long.parseLong(ipAddressInArray[3 - i]);
			//left shifting 24,16,8,0 and bitwise OR
			//1. 192 << 24
			//1. 168 << 16
			//1. 1   << 8
			//1. 2   << 0
			result |= ip << (i * 8);
		}
		return result;
	}
	public static String longToIp(long i) {

		return ((i >> 24) & 0xFF) + 
                   "." + ((i >> 16) & 0xFF) + 
                   "." + ((i >> 8) & 0xFF) + 
                   "." + (i & 0xFF);

	}
	public static String longToIp2(long ip) {
		StringBuilder sb = new StringBuilder(15);

		for (int i = 0; i < 4; i++) {

			// 1. 2
			// 2. 1
			// 3. 168
			// 4. 192
			sb.insert(0, Long.toString(ip & 0xff));

			if (i < 3) {
				sb.insert(0, '.');
			}

			// 1. 192.168.1.2
			// 2. 192.168.1
			// 3. 192.168
			// 4. 192
			ip = ip >> 8;

		}

		return sb.toString();
	}
    public static String byteArrayToHex(byte[] AData)
    {
    	if ((AData == null) || (AData.length == 0)) {
    		return "";
	    }
    	
    	int ALen = AData.length;
    	int line = ALen / 16;
	    int pos;
	    
    	StringBuffer sb = new StringBuffer((ALen*3)+line);
	    sb.append("\r\n");
	    for (int ii = 0; ii < ALen; ii += 16)
	    {
	    	for (int jj = 0; jj < 16; jj++) {
		    	pos = ii + jj;
		    	if (pos < ALen) {
		    		String hexNumber = "0" + Integer.toHexString(0xFF & AData[pos]).toUpperCase();
			      	sb.append(hexNumber.substring(hexNumber.length() - 2));
			      	sb.append(" ");
		    	}
		    	else {
		    		break;
		    	}
	    	}
		    sb.append("\r\n");
	    }
	    return sb.toString();
    }

    public static int getBitValue(byte b, int pos) {
		int val = (b >> pos) & 0x01;
		//int val = (b >> (7-pos)) & 0x01;
		return val;
	}
    
    public static String bytes2String(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length);
        for (int ii = 0; ii < data.length; ++ ii) {
            if (data[ii] >= 0x21 && data[ii] <= 0x7E) {
            	sb.append((char) data[ii]);
            }
            else {
            	break;	//FOR VDS Protocol
            }
        }
        return sb.toString();
    }

    public static short swapEndian(short x)
    {
    	return (short)(x << 8 | x >> 8 & 0xFF);
    }
    public static int swapEndian(int x)
    {
    	return swapEndian((short)x) << 16 | swapEndian((short)(x >> 16)) & 0xFFFF;
    }

	//  byte[] byteArray = ByteBuffer.allocate(4).putInt(value).array();
	public static byte[] intToByteArray(int value) {
			byte[] byteArray = new byte[4];
			byteArray[0] = (byte)(value >> 24);
			byteArray[1] = (byte)(value >> 16);
			byteArray[2] = (byte)(value >> 8);
			byteArray[3] = (byte)(value);
			return byteArray;
		}
		
	public static int byteArrayToInt(byte bytes[]) {
		return ((((int)bytes[0] & 0xff) << 24) |
				(((int)bytes[1] & 0xff) << 16) |
				(((int)bytes[2] & 0xff) << 8) |
				(((int)bytes[3] & 0xff)));
	} 
	public static byte[] floatToByteArray(float value) {
		int floatValue =  Float.floatToIntBits(value);
		return intToByteArray(floatValue);
	}

	public static float byteArrayToFloat(byte bytes[]) {
		int value =  byteArrayToInt(bytes);
		return Float.intBitsToFloat(value);
	}

	public static long toUnsigned(int value) {
		return ((long)value & 0xFFFFFFFFL);
	}

}
