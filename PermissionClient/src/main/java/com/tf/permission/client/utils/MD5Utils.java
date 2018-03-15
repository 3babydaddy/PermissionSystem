package com.tf.permission.client.utils;

import java.security.MessageDigest;

public class MD5Utils {

	/**
	 * MD5加密字符串
	 * @param str
	 * @param salt 用户名盐
	 * @return
	 */
	public static String md5(String str,String salt) {
		return md5(str, "UTF-8",salt);
	}
	
	/**
	 * MD5加密字符串
	 * @param str
	 * @param encode 字符串编码
	 * @return
	 */
	public static String md5(String str, String encode,String salt) {
		if (str != null) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				byte[] returnByte = md5.digest((salt+str).getBytes(encode));
				StringBuffer ret=new StringBuffer();
				for(int i=0;i<returnByte.length;i++){
				  ret.append(Character.forDigit((returnByte[i]>>4)&0xf,16));
				  ret.append(Character.forDigit(returnByte[i]&0xf,16));
				}
				return ret.toString().toUpperCase();
			} catch (Exception e) {
				 e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		//123456 E10ADC3949BA59ABBE56E057F20F883E 
		System.out.println(md5("123456","UTF-8","md5ceshi"));
	}
}
