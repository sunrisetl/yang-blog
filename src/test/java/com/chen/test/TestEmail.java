package com.chen.test;

import net.chen.utils.EmailSender;

import javax.mail.MessagingException;

/**
 * Created by Chen
 * 2020/8/5 20:36
 */
public class TestEmail {
    public static void main(String[] args) throws MessagingException {
        EmailSender.subject("测试邮件发送")
        .from("chen")
        .text("验证码：aaa")
        .to("xiaomoshengdewo@163.com")
        .send();

    }
}
