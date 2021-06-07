package com.blockchain.server.btc;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, BTCApplication.class})
public class BTCApplication {
    public static void main(String[] args) {
        SpringApplication.run(BTCApplication.class);
    }
}
