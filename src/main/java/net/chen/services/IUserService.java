package net.chen.services;

import net.chen.entity.ChenUser;
import net.chen.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Chen
 * 2020/8/2 19:57
 */
public interface IUserService {
    ResponseResult initMangerAccount(ChenUser chenUser, HttpServletRequest request);

    void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception;

    ResponseResult sendEmail(String type,String emailAddress, HttpServletRequest request);

    ResponseResult register(ChenUser chenUser,String emailCode,String captchaCode,String captchaKey, HttpServletRequest request);

  ///  ResponseResult register(ChenUser chenUser, String emailCode, String captchaKey, HttpServletRequest request);
}
