package com.blockchain.server.sysconf;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author huangxl
 * @create 2018-11-13 10:15
 */
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, SysconfApplication.class})
public class SysconfApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysconfApplication.class,args);
    }
}
