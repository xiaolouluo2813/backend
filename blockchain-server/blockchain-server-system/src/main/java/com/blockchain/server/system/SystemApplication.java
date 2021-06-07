package com.blockchain.server.system;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author huangxl
 * @create 2018-11-13 10:15
 */
@ServletComponentScan
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, SystemApplication.class})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}
