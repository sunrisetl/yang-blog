package com.chen.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Created by Chen
 * 2020/7/29 14:30
 */
//@SpringJUnitConfig

public class TestPassword {  
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("12345");
        System.out.println("======="+encode);

        String otherPassword = "12345";
        boolean matches = bCryptPasswordEncoder.matches(otherPassword, "$2a$10$zX7OqomPMNX2Sh7WcwHMmezdvqbZR4HhfsOZQXQEMhEwuVWT9ew1a");
        System.out.println(matches);
    }

}
