package com.demo.platform.commons.constants;

/**
 * @author github.com/wanghws
 * @date 20191224
 */
public class GlobalResult {
    public final static String SUCCESS = "0";
    public static final String NOT_LOGIN = "400";
    public static final String NOT_PERMISSION = "401";
    public final static String FAIL = "500";
    public final static String PAGE_LIMIT = "501";

    public final static String USER_ID_NOT_NULL = "10001";//用户ID不能为空
    public final static String USER_NOT_FOUND = "10002";//用户不存在
    public static final String VERIF_CODE_INVALID = "10003";//验证码不正确或者已失效
    public static final String EXIST_MOBILE = "10004";//手机号已经存在
    public static final String EXIST_EMAIL = "10005";//邮箱号已经存在
    public static final String RECIPIENT_NOT_NULL = "10006";//联系方式不能为空
    public static final String MOBILE_NOT_FOUND = "10007";//手机号未找到
    public static final String EMAIL_NOT_FOUNT = "10008";//邮箱未找到
    public static final String INVALID_EMAIL = "10009";//邮箱格式不争取
    public static final String INVALID_MOBILE = "10010";//手机号格式不正确
    public static final String PASSWORD_INCORRECT = "10011";//密码不正确
    public static final String VERIF_CODE_NOT_NULL = "10012";//验证码不能为空
    public static final String INVALID_NAME = "10013";//名字格式不正确
    public static final String INVALID_PASSWORD = "10014";//密码格式不正确
    public static final String PASSWORD_NOT_NULL = "10015";//密码不能为空

    public final static String ACCOUNT_OFFICE_NOT_FOUND = "20001";//系统部门记录未找到
    public final static String ACCOUNT_NAME_NOT_NULL = "20002";//系统 名字不能为空
    public final static String ACCOUNT_OFFICE_ID_NOT_NULL = "20003";//系统部门id不能为空
    public final static String ACCOUNT_STATUS_NOT_NULL = "20004";//系统状态不能为空
    public final static String ACCOUNT_STATUS_INVALID = "20005";//系统 状态不正确
    public final static String ACCOUNT_PERMISSION_NOT_NULL = "20006";//权限关键字不能为空
    public final static String ACCOUNT_URL_NOT_NULL = "20007";//权限url不能为空
    public final static String ACCOUNT_PERMISSION_ID_NOT_NULL = "20008";//权限id不能为空
    public final static String ACCOUNT_PERMISSION_RECORD_NOT_FOUND = "20009";//权限记录未找到
    public final static String ACCOUNT_ROLE_NOT_FOUND = "20010";//角色记录未找到
    public final static String ACCOUNT_ROLE_ID_NOT_NULL = "20011";//角色id不能为空
    public final static String ACCOUNT_ROLE_PERMISSION_NOT_FOUND = "20012";//角色权限关系记录未找到
    public final static String ACCOUNT_ROLE_PERMISSION_ID_NOT_NULL = "20013";//角色权限关系id不能为空
    public final static String ACCOUNT_NAME_NOT_REPEAT = "20014";//登录名已存在
    public final static String ACCOUNT_MOBILE_NOT_REPEAT = "20015";//手机号已存在
    public final static String ACCOUNT_EMAIL_NOT_REPEAT = "20016";//邮箱已存在
    public final static String ACCOUNT_PASSWORD_NOT_NULL = "20017";//密码不能为空
    public final static String ACCOUNT_USER_ID_NOT_NULL = "20018";//系统用户ID不能为空
    public final static String ACCOUNT_OFFICE_STATUS_INVALID = "20019";//系统部门状态不正确
    public final static String ACCOUNT_USER_NOT_FOUND = "20020";//系统用户信息未找到
    public final static String ACCOUNT_USER_STATUS_INVALID = "20021";//系统用户状态不正确
    public final static String ACCOUNT_USER_ROLE_NOT_FOUND = "20022";//系统用户角色记录未找到
    public final static String ACCOUNT_PERMISSION_REPEAT = "20023";//该权限关键字已经存在
    public final static String ACCOUNT_URL_REPEAT = "20024";//该URL已经存在
    public final static String ACCOUNT_USER_ROLE_ID_NOT_NULL = "20025";//用户角色关系ID不能为空
}
