package cn.com.qws.exception;

import cn.com.qws.common.Constants;
import cn.com.qws.common.ResultJson;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @Author qinweisi
 * @Description 控制器的异常处理类
 * @ControllerAdvice 这个注解是指这个类是处理其他controller抛出的异常
 **/
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * @Author qinweisi
     * @Description 运行时异常
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultJson runtimeExceptionHandler(RuntimeException runtimeException) {
        runtimeException.printStackTrace();
        return retParam(1000, Constants.MsgCode.RUN_TIME_EXCEPTION);
    }

    /**
     * @Author qinweisi
     * @Description 空指针异常
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResultJson nullPointerExceptionHandler(NullPointerException ex) {
        ex.printStackTrace();
        return retParam(1001, Constants.MsgCode.NULL_POINTER_EXCEPTION);
    }

    /**
     * @Author qinweisi
     * @Description 类型转换异常
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public ResultJson classCastExceptionHandler(ClassCastException ex) {
        ex.printStackTrace();
        return retParam(1002, Constants.MsgCode.CLASS_CAST_EXCEPTION);
    }

    /**
     * @Author qinweisi
     * @Description IO异常
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResultJson iOExceptionHandler(IOException ex) {
        ex.printStackTrace();
        return retParam(1003, Constants.MsgCode.IO_EXCEPTION);
    }

    /**
     * @Author qinweisi
     * @Description 未知方法异常
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public ResultJson noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        ex.printStackTrace();
        return retParam(1004, Constants.MsgCode.NO_SUCH_METHOD_EXCEPTION);
    }

    /**
     * @Author qinweisi
     * @Description 数组越界异常
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public ResultJson indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        return retParam(1005, Constants.MsgCode.INDEX_OUT_OF_BOUNDS_EXCEPTION);
    }

    /**
     * @Author qinweisi
     * @Description 400错误
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResultJson requestNotReadable(HttpMessageNotReadableException ex) {
        System.out.println("400..requestNotReadable");
        ex.printStackTrace();
        return retParam(400, Constants.MsgCode.FAIL);
    }

    /**
     * @Author qinweisi
     * @Description 400错误
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResultJson requestTypeMismatch(TypeMismatchException ex) {
        System.out.println("400..TypeMismatchException");
        ex.printStackTrace();
        return retParam(400, Constants.MsgCode.FAIL);
    }

    /**
     * @Author qinweisi
     * @Description 400错误
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResultJson requestMissingServletRequest(MissingServletRequestParameterException ex) {
        System.out.println("400..MissingServletRequest");
        ex.printStackTrace();
        return retParam(400, Constants.MsgCode.FAIL);
    }

    /**
     * @Author qinweisi
     * @Description 用户或者密码错误
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResultJson AuthenticationException(AuthenticationException ex) {
        System.out.println("1103..AuthenticationException");
        ex.printStackTrace();
        return retParam(1103, Constants.MsgCode.USER_OR_PASSWORD_FAULT);
    }

    /**
//     * @Author qinweisi
//     * @Description 账号不存在
//     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
//     * @ResponseBody 将返回的值转成json格式的数据
//     **/
    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseBody
    public ResultJson UsernameNotFoundException(UsernameNotFoundException ex) {
        System.out.println("1104..UsernameNotFoundException");
        ex.printStackTrace();
        return retParam(1104, Constants.MsgCode.USER_IS_NOT_EXIST);
    }

    /**
     * @Author qinweisi
     * @Description 405错误
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResultJson request405() {
        System.out.println("405...");
        return retParam(405, Constants.MsgCode.FAIL);
    }

    /**
     * @Author qinweisi
     * @Description 406错误
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ResultJson request406() {
        System.out.println("406...");
        return retParam(406, Constants.MsgCode.FAIL);
    }

    /**
     * @Author qinweisi
     * @Description 500错误
     * @ExceptionHandler 这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
     * @ResponseBody 将返回的值转成json格式的数据
     **/
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public ResultJson server500(RuntimeException runtimeException) {
        System.out.println("500...");
        return retParam(500, Constants.MsgCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * @Author qinweisi
     * @Description 格式化错误信息
     **/
    public static ResultJson retParam(int status, String msg) {
        return new ResultJson(status,msg,null);
    }
}