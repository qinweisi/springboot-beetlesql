package cn.com.qws.exception;

public class SystemException extends Exception {

    private String msg;

    public SystemException(String msg) {
        super();
        this.msg = msg;
    }

    private static final long serialVersionUID = -2574156604235143315L;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}