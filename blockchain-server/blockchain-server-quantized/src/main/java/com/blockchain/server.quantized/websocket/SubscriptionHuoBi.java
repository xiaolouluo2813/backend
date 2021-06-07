package com.blockchain.server.quantized.websocket;

import com.blockchain.server.quantized.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author: Liusd
 * @create: 2019-04-19 17:52
 * <p>
 * 订阅火币
 **/
@Component
public class SubscriptionHuoBi implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionHuoBi.class);

    @Autowired
    private OrderService orderService;
 

    /** 
    * @Description: 执行初始化方法，加载火币支持的交易对 
    * @Param: [args] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/4/30 
    */ 
    @Override
    public void run(ApplicationArguments args) {
        //延迟5s执行方法。
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //刷新交易对信息
                orderService.initOrder();
            }
        });
        t.start();
    }

}
