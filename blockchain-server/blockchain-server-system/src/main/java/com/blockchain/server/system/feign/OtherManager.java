package com.blockchain.server.system.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangxl
 * @create 2018-11-09 11:40
 */
@FeignClient("demo2")
public interface OtherManager {
    @GetMapping("/insert")
    String insertUser(@RequestParam(value = "name", defaultValue = "admin") String name);
}
