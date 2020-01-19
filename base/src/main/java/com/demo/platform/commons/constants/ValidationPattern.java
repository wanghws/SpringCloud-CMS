package com.demo.platform.commons.constants;

/**
 * Created by github.com/wanghws on 2019-02-22.
 */
public class ValidationPattern {
    public static final String NAME_PATTERN = "^.{1,128}$";
    public static final String TIME_PATTERN = "(((01[0-9]{2}|0[2-9][0-9]{2}|[1-9][0-9]{3})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|((01[0-9]{2}|0[2-9][0-9]{2}|[1-9][0-9]{3})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|((01[0-9]{2}|0[2-9][0-9]{2}|[1-9][0-9]{3})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((04|08|12|16|[2468][048]|[3579][26])00))-0?2-29)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d";
    public static final String INTERVAL_PATTERN = "^[0-9]{1,13}$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_\\.-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    public static final String MOBILE_PATTERN = "(^[0-9\\-]{1,20})$";
    public static final String USER_NAME_PATTERN = "^.{1,32}$";
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*()_+]{6,20}$";
    public static final String ZH_NAME_LEN_PATTERN = "^.{1,64}$";
    public static final String ZH_PATTERN = "[\\u4e00-\\u9fa5]+";//中文验证
}
