package com.blockchain.server.user;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Harvey
 * @date 2019/3/4 18:28
 * @user WIN10
 */
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, UserApplication.class})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}
