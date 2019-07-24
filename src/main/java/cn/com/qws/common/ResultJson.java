package cn.com.qws.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

/**
 * json返回结果
 *
 * @author zhangrui
 * @version 1.0
 * @时间 2016年9月21日
 * @描述
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultJson extends HashMap<String, Object> {

    private Integer code;// 状态码
    private String msg;// 信息
    private Object data;// 数据

    /**
     * @Author qinweisi
     * @Description 无构造参数，表示失败
     **/
    public ResultJson() {
        this.put("code", Constants.ResultCode.FAIL);
        this.put("msg", Constants.MsgCode.FAIL);
    }

    /**
     * @Author qinweisi
     * @Description 异常
     **/
    public ResultJson(Exception e) {
        e.printStackTrace();
        this.put("code", Constants.ResultCode.INTERNAL_SERVER_ERROR);
        this.put("msg", Constants.MsgCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * @Author qinweisi
     * @Description 自定义
     **/
    public ResultJson(Integer code,String msg,Object data) {
        this.put("code", code);
        this.put("msg", msg);
        if(data != null){
            this.put("data", data);
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public ResultJson setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultJson setData(Object data) {
        this.data = data;
        return this;
    }

    public ResultJson setCode(Integer code) {
        this.code = code;
        return this;
    }
}
