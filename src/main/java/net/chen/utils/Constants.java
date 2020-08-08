package net.chen.utils;

/**
 * Created by Chen
 * 2020/8/2 21:31
 */
public interface Constants {
    int DEFAULT_SIZE = 10;
    interface  User{
        //https://cdn.sunofbeaches.com/images/default_avatar.png
        String ROLE_ADMIN = "role_admin";
        String ROLE_NORMAL = "role_normal";
        String DEFAULT_AVATAR = "https://cdn.sunofbeaches.com/images/default_avatar.png";
        String DEFAULT_STATE = "1";
        String CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip_";
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
    }
    interface Settings{
        String HAS_MANAGE_ACCOUNT_INIT = "manger_account_init_state";
    }
}
