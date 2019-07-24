package cn.com.qws.common;

/**
 * @Description 常量
 * @Author qinweisi
 * @Date 2019/7/19 11:22
 **/
public class Constants {

    public class MsgCode {

        // 操作成功
        public final static String SUCCESS = "操作成功";
        // 操作失败
        public final static String FAIL = "操作失败";
        // 登录用户为空
        public final static String USER_IS_NULL = "登录用户为空";
        // 账号不能为空
        public final static String LOGIN_NAME_IS_NOT_NULL = "账号不能为空";
        // 密码不能为空
        public final static String PASSWORD_IS_NOT_NULL = "密码不能为空";
        // 用户或者密码错误
        public final static String USER_OR_PASSWORD_FAULT = "用户或者密码错误";
        // 账号不存在
        public final static String USER_IS_NOT_EXIST = "账号不存在";
        // 异常错误信息
        public final static String INTERNAL_SERVER_ERROR = "系统异常，请联系管理员";
        public final static String RUN_TIME_EXCEPTION =  "[服务器]运行时异常";
        public final static String NULL_POINTER_EXCEPTION =  "[服务器]空值异常";
        public final static String CLASS_CAST_EXCEPTION =  "[服务器]数据类型转换异常";
        public final static String IO_EXCEPTION =  "[服务器]IO异常";
        public final static String NO_SUCH_METHOD_EXCEPTION =  "[服务器]未知方法异常";
        public final static String INDEX_OUT_OF_BOUNDS_EXCEPTION =  "[服务器]数组越界异常";
    }

    public class ResultCode {

        // 成功
        public final static int SUCCESS = 200;
        // 失败
        public final static int FAIL = 400;
        // 未认证（签名错误）
        public final static int UNAUTHORIZED = 401;
        // 无权限
        public final static int AUTH_FAIL = 403;
        // 接口不存在
        public final static int NOT_FOUND = 404;
        // 服务器内部错误
        public final static int INTERNAL_SERVER_ERROR = 500;
    }

}
