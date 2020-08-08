package net.chen.response;

/**
 * Created by Chen
 * 2020/7/31 21:19
 */
public enum  ResponseState {
    SUCCESS(true,200,"操作成功"),
    FAILED(false,500,"操作失败"),
    JOIN_SUCCESS(true,200,"注册成功"),
    GET_RESOURCE_FAILED(false,500,"获取资源失败"),
    LOGIN_FAILED(false,500,"登录失败");
    ResponseState(boolean success,int code,String message){
        this.code = code;
        this.message = message;
        this.success = success;
    }

    private int code;
    private String message;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
