package com.blockchain.server.quantized;

import com.blockchain.server.base.BaseConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** 
* @Description: ้ๅๆจกๅ
* @Param:  
* @return:  
* @Author: Liu.sd 
* @Date: 2019/4/18 
*/ 
@SpringBootApplication(scanBasePackageClasses = {BaseConf.class, QuantizedApplication.class})
public class QuantizedApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuantizedApplication.class,args);
    }
}
