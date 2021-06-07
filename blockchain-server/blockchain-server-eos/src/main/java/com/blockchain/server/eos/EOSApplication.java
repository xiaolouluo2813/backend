package com.blockchain.server.eos;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, EOSApplication.class})
public class EOSApplication {
    public static void main(String[] args) {
        SpringApplication.run(EOSApplication.class);
    }
}
