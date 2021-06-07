package com.blockchain.server.cct;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, CCTApplication.class})
public class CCTApplication {
    public static void main(String[] args) {
        SpringApplication.run(CCTApplication.class);
    }
}
