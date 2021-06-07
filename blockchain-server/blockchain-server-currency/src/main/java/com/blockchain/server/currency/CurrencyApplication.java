package com.blockchain.server.currency;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, CurrencyApplication.class})
public class CurrencyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyApplication.class);
    }
}
