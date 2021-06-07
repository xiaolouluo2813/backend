package com.blockchain.server.aibot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blockchain.server.base.BaseConf;

@SpringBootApplication(scanBasePackageClasses = { BaseConf.class, AibotApplication.class })
public class AibotApplication {
	public static void main(String[] args) {
		SpringApplication.run(AibotApplication.class);
	}
}
