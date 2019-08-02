package cn.com.qws.utils;

import cn.com.qws.common.ResultJson;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @Describe 通用response异常返回方法
 * @Author zhanglei
 * @Date 2018/5/4 9:35
 **/
public class ResponseUtil {

    /**
     *  通过response返回Json
     * @param response
     * @param
     */
    public static void GoResponseSystemException(HttpServletResponse response, Integer code,String msg){
        ResultJson result = new ResultJson(code,msg,null);
        String json= JSON.toJSONString(result);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 返回参数字符串
     * @param request
     * @return
     */
    public static String GetRequsetParamsString(HttpServletRequest request){
        Enumeration enu=request.getParameterNames();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        while(enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            stringBuffer.append(paraName + ":" + request.getParameter(paraName)+",");
        }
        stringBuffer.append("}");
        return  stringBuffer.toString();
    }
}
