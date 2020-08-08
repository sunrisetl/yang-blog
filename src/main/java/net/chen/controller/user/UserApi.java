package net.chen.controller.user;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import net.chen.entity.ChenUser;
import net.chen.response.ResponseResult;
import net.chen.services.IUserService;
import net.chen.utils.Constants;
import net.chen.utils.RedisUtil;
import net.chen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Chen
 * 2020/8/1 11:37
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private IUserService userService;


    /**
     *  @parm
     *  初始化管理员账号
     * */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody ChenUser chenUser, HttpServletRequest request){
        log.info("username==>"+chenUser.getUsername());
        log.info("password==>"+chenUser.getPassword());
        log.info("email==>"+chenUser.getEmail());
        return userService.initMangerAccount(chenUser,request);
    }


    /**
     * @param chenUser
     * 注册
     * */
    @PostMapping
    public ResponseResult register(@RequestBody ChenUser chenUser,
                                   @RequestParam("email_code") String emailCode,
                                   @RequestParam("captcha_code") String captchaCode,
                                   @RequestParam("captcha_key") String captchaKey,
                                   HttpServletRequest request){
        return  userService.register(chenUser,emailCode,captchaCode,captchaKey,request);
    }

    /**
     *  @parm
     * 登录
     * */
    @PostMapping("/{captcha}")
    public ResponseResult login(@PathVariable("captcha") String captcha,ChenUser chenUser){

        return null;
    }



    /** 获取验证码*/
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response,@RequestParam("captcha_key") String captchaKey)  {
        try {
            userService.createCaptcha(response,captchaKey);
        }catch (Exception e){
            log.error(e.toString());
        }


    }

    /** 发送邮件*/
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request,@RequestParam("type") String type,@RequestParam("email") String emailAddress){

        //log.info("email==>"+emailAddress);
        return userService.sendEmail(type,emailAddress,request);
    }

    /** 修改密码*/
    @PutMapping("/password/{userId}")
    public ResponseResult updatePassword(@PathVariable("userId") String userId,@RequestBody ChenUser chenUser){
            return null;
    }

    /** 获取作者信息*/
    @GetMapping("/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId") String userId){
        return null;
    }

    /** 更新用户信息*/
    @PutMapping("/{userId}")
    public ResponseResult updateUserInfo(@PathVariable("userId") String userId,@RequestBody ChenUser chenUser){
        return null;
    }

    /** 获取用户列表*/
    @GetMapping("/list")
    public ResponseResult listUser(@RequestParam("page") int page,@RequestParam("size") int size){
            return null;
    }
    /** 删除用户*/
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId){
        return null;
    }
}
