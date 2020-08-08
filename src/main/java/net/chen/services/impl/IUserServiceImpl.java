package net.chen.services.impl;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import net.chen.dao.SettingDao;
import net.chen.dao.UserDao;
import net.chen.entity.ChenUser;
import net.chen.entity.Settings;
import net.chen.response.ResponseResult;
import net.chen.response.ResponseState;
import net.chen.services.IUserService;
import net.chen.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Random;

/**
 * Created by Chen
 * 2020/8/2 19:59
 */
@Slf4j
@Service
@Transactional
public class IUserServiceImpl implements IUserService {
    public static final int[] captcha_font_types = {Captcha.FONT_1,
            Captcha.FONT_2,
            Captcha.FONT_3,
            Captcha.FONT_4,
            Captcha.FONT_5,
            Captcha.FONT_6,
            Captcha.FONT_7,
            Captcha.FONT_8,
            Captcha.FONT_9,
            Captcha.FONT_10,
    };
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SettingDao settingDao;
    @Autowired
    private Random random;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TaskService taskService;

    /** 初始化管理员帐户*/
    @Override
    public ResponseResult initMangerAccount(ChenUser chenUser, HttpServletRequest request) {
        //检查数据,是否有初始化
        Settings oneByKey = settingDao.findOneByKey(Constants.Settings.HAS_MANAGE_ACCOUNT_INIT);
        if (oneByKey != null){
            return ResponseResult.FAILED("管理员已经初始化");
        }
        if (StringUtils.isEmpty(chenUser.getUsername())){
            return ResponseResult.FAILED("用户名不能为空");
        }
       if (StringUtils.isEmpty(chenUser.getPassword())){
           return ResponseResult.FAILED("密码不能为空");
       }
       if (StringUtils.isEmpty(chenUser.getEmail())){
           return ResponseResult.FAILED("邮箱不能为空");
       }
       // 数据填充
        chenUser.setId(String.valueOf(snowflakeIdWorker.nextId()));
        chenUser.setRoles(Constants.User.ROLE_ADMIN);
        chenUser.setAvatar(Constants.User.DEFAULT_AVATAR);
        chenUser.setState(Constants.User.DEFAULT_STATE);
        String remoteAddr = request.getRemoteAddr();
        log.info("remoteAddr...."+remoteAddr);
        chenUser.setLoginIp(remoteAddr);
        chenUser.setRegIp(remoteAddr);
        chenUser.setCreateTime(new Date());
        chenUser.setUpdateTime(new Date());
        // 密码加密
        String password = chenUser.getPassword();
        //...
        String encode = bCryptPasswordEncoder.encode(password);
        chenUser.setPassword(encode);
        //保存到数据库
        userDao.save(chenUser);
        //更新已经添加的标记
        Settings settings = new Settings();
        settings.setId(snowflakeIdWorker.nextId()+"");
        settings.setKey(Constants.Settings.HAS_MANAGE_ACCOUNT_INIT);
        settings.setCreate_time(new Date());
        settings.setUpdate_time(new Date());
        settings.setValue("1");
        settingDao.save(settings);

        return ResponseResult.SUCCESS("初始化成功");
    }
    /**获取验证码*/
    @Override
    public void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception {
        if (StringUtils.isEmpty(captchaKey) || captchaKey.length()<13) {
            return;
        }
        long key;
        try {
            key = Long.parseLong(captchaKey);
        }catch (Exception e){
            return;
            //  e.printStackTrace();
        }
        response.setContentType("image/gif");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        int captchaType = random.nextInt(3);
        Captcha captcha = null;
        if (captchaType == 0){
            captcha = new SpecCaptcha(200,60,45);
        }else if (captchaType == 1){
            captcha = new GifCaptcha(200,60);

        }else {
            //算术类型
            captcha = new ArithmeticCaptcha(200,60);
            captcha.setLen(3);
            //((ArithmeticCaptcha)captcha).getArithmeticString();
            // captcha.text();
        }

        captcha.setFont(captcha_font_types[random.nextInt(captcha_font_types.length)]);
        //设置类型
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        String content = captcha.text().toLowerCase();
        redisUtil.set(Constants.User.CAPTCHA_CONTENT+key,content,10 * 60);

        ///输出
        captcha.out(response.getOutputStream());
    }
    /** 发送邮件验证码*/
    @Override
    public ResponseResult sendEmail(String type, String emailAddress, HttpServletRequest request) {
        if (emailAddress==null) {
            return ResponseResult.FAILED("邮箱地址为空");
        }

        //根据类型
        if ("register".equals(type) || "update".equals(type)){
            ChenUser userByEmail = userDao.findOneByEmail(emailAddress);
            if (userByEmail!=null) {
                return ResponseResult.FAILED("该邮箱已经注册!");
            }
        }else if ("forget".equals(type)){
            ChenUser userByEmail = userDao.findOneByEmail(emailAddress);
            if (userByEmail==null) {
                return ResponseResult.FAILED("该邮箱未注册");
            }
        }
        // 防止暴力发送，检查邮箱的地址是否正确，发送验证码
        String remoteAddr = request.getRemoteAddr();
        if (remoteAddr!=null) {
            remoteAddr.replaceAll(":","_");
        }
        //拿出来,
        Integer ipSendTime = (Integer) redisUtil.get(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr);
        if (ipSendTime != null && ipSendTime>10) {
                return ResponseResult.FAILED("请不要过频发送验证码!!");
        }
        Object ipSendAddress = redisUtil.get(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress);
        if (ipSendAddress !=null){
            return ResponseResult.FAILED("请不要过频发送!!");
        }

        //验证邮箱地址
        boolean address = StringUtils.isEmailAddress(emailAddress);
        if (!address){

            return ResponseResult.FAILED("邮箱地址格式不正确!");
        }
        //发送验证码
        int code = random.nextInt(999999);
        if (code < 100000){
             code += 100000;
        }
        log.info("----->" +code);
        //发送验证码，6位数
        try {
            taskService.sendEmailVerifyCode(String.valueOf(code),emailAddress);
        }catch (Exception e){
            return ResponseResult.FAILED("邮箱验证码发送失败,请稍后重试");
            //log.info(e.toString());
        }
        if (ipSendTime == null){
            ipSendTime = 0;
        }
        ipSendTime++;
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr,ipSendTime,60 * 60);
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_ADDRESS+emailAddress,"true",30);
        redisUtil.set(Constants.User.KEY_EMAIL_CODE_CONTENT+emailAddress,String.valueOf(code),60 * 10);
        return ResponseResult.SUCCESS("验证码发送成功");
    }
    /** 注册*/
    @Override
    public ResponseResult register(ChenUser chenUser,String emailCode,String captchaCode, String captchaKey, HttpServletRequest request) {
            /** 用户注册流程
             * 第一步：检查当前用户名是否已经注册
             * 第二步：检查邮箱格式是否正确
             * 第三步：检查该邮箱是否已经注册
             * 第四步：检查邮箱验证码是否正确
             * 第五步：检查图灵验证码是否正确
             * 达到可以注册的条件
             * 第六步：对密码进行加密
             * 第七步：补全数据
             * 包括：注册IP,登录IP,角色,头像,创建时间,更新时间
             * 第八步：保存到数据库中
             * 第九步：返回结果
             * */
            String username = chenUser.getUsername();
        if (StringUtils.isEmpty(username)) {
            return ResponseResult.FAILED("用户不可以为空");
        }
        ChenUser oneByUserName = userDao.findOneByUsername(username);
        if (oneByUserName!=null){
            return ResponseResult.FAILED("该用户已经注册");
        }
        String email = chenUser.getEmail();
        if (StringUtils.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱地址不能为空");
        }
        if (StringUtils.isEmailAddress(email)) {
            return ResponseResult.FAILED("邮箱格式不正确");
        }
        ChenUser byEmail = userDao.findOneByEmail(email);
        if (byEmail==null) {
            return ResponseResult.FAILED("该邮箱地址已经注册");
        }
        //判断验证码
        String emailVerifyCode = (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (StringUtils.isEmpty(emailVerifyCode)) {
            return ResponseResult.FAILED("邮箱验证码不正确");
        }
        //判断前端传来的数据是否与后端生成的验证码匹配
        if (!emailVerifyCode.equals(emailCode)){
            return ResponseResult.FAILED("邮箱验证码错误");
        }else {
                redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT+email);
        }
        //检查图片验证码
        String captchaVerifyCode  = (String) redisUtil.get(Constants.User.CAPTCHA_CONTENT + captchaKey);
        if (StringUtils.isEmpty(captchaVerifyCode)) {
            return ResponseResult.FAILED("验证码已经失效");
        }
        if (!captchaVerifyCode.equals(captchaCode)) {
            return ResponseResult.FAILED("验证码不正确");
        } else {
            redisUtil.del(Constants.User.CAPTCHA_CONTENT + captchaKey);
        }

        //已经验证,符合条件可以开始验证
        //密码进行加密
        String password = chenUser.getPassword();
        if (StringUtils.isEmpty(password)){
            return ResponseResult.FAILED("密码不能为空");
        }
        chenUser.setPassword(bCryptPasswordEncoder.encode(chenUser.getPassword()));

        // 补全数据
        String  ipAddress  = request.getRemoteAddr();
        chenUser.setRegIp(ipAddress);
        chenUser.setRegIp(ipAddress);
        chenUser.setUpdateTime(new Date());
        chenUser.setCreateTime(new Date());
        chenUser.setAvatar(Constants.User.DEFAULT_AVATAR);
        chenUser.setRoles(Constants.User.ROLE_NORMAL);

        //保存到数据库
        userDao.save(chenUser);

        return ResponseResult.GET(ResponseState.JOIN_SUCCESS);
    }
}
