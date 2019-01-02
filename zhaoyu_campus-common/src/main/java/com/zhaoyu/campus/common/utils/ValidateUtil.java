package com.zhaoyu.campus.common.utils;

public class ValidateUtil {
	
	private static final String PHONE_NUM_REGEX = "^((13[0-9])|(15[0-3,5-9])|(18[0,2,3,5-9])|(17[0-8])|(147)){1}[0-9]{8}$";

	public static boolean isPhoneNum(String phoneNum) {
		if (phoneNum == null) {
			return false;
		}
		return phoneNum.matches(PHONE_NUM_REGEX);
	}

}
