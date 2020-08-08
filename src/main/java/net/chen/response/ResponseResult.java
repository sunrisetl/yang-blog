package net.chen.response;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Chen
 * 2020/7/31 21:04
 */
public class ResponseResult {
    private boolean success;
    private int code;
    private String message;
    private Object data;



    /** 构造函数*/
    public ResponseResult(ResponseState responseState) {
        this.success = responseState.isSuccess();
        this.code =  responseState.getCode();
        this.message =responseState.getMessage();
    }

    public static ResponseResult GET (ResponseState state){
        return new ResponseResult(state);
    }
    public static  ResponseResult SUCCESS(){
        return new ResponseResult(ResponseState.SUCCESS);
    }

    public static  ResponseResult SUCCESS(String message){
        ResponseResult responseResult = new ResponseResult(ResponseState.SUCCESS);
        responseResult.setMessage(message);
        return responseResult;
    }
    public static ResponseResult FAILED(){
        return new ResponseResult(ResponseState.FAILED);
    }
    public static ResponseResult FAILED( String message){
        ResponseResult responseResult = new ResponseResult(ResponseState.FAILED);
        responseResult.setMessage(message);
        return responseResult;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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

    public Object getData() {
        return data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }
}
