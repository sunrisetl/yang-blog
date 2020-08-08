package net.chen.services.impl;

import net.chen.utils.EmailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Chen
 * 2020/8/7 20:41
 */
@Service
public class TaskService {
    @Async
    public void sendEmailVerifyCode(String verifyCode,String emailAddress) throws Exception {
        EmailSender.sendRegisterVerifyCode(verifyCode,emailAddress);
    }
}
