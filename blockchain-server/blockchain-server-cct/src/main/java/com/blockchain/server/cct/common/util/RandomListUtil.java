package com.blockchain.server.cct.common.util;

import com.blockchain.server.cct.common.constant.CCTConstant;
import com.blockchain.server.cct.dto.automaticdata.AutomaticdataDTO;
import com.blockchain.server.cct.entity.Automaticdata;

import java.math.BigDecimal;
import java.util.*;

public class RandomListUtil {

    //数据长度
    public static final int listSize = 20;

    /***
     * 返回随机生成的list数据
     * @param param
     * @return
     */
    public static List<AutomaticdataDTO> addRandomList(Automaticdata param) {
        Set<AutomaticdataDTO> nums = new HashSet();
        while (true) {
            //生成随机数
            BigDecimal price = randomNum(param.getMinPrice(), param.getMaxPrice());
            BigDecimal amount = randomNum(param.getMinAmount(), param.getMaxAmount());
            //封装对象
            AutomaticdataDTO automaticdata = new AutomaticdataDTO();
            automaticdata.setCoinName(param.getCoinName());
            automaticdata.setUnitName(param.getUnitName());
            automaticdata.setPrice(price);
            automaticdata.setAmount(amount);
            //添加进set
            nums.add(automaticdata);
            //长度大于规定，退出循环
            if (nums.size() == listSize) {
                break;
            }
        }
        //set转list，返回
        List<AutomaticdataDTO> list = new ArrayList(nums);
        //排序规则
        Collections.sort(list, new Comparator<AutomaticdataDTO>() {
            @Override
            public int compare(AutomaticdataDTO o1, AutomaticdataDTO o2) {
                //如果是买单，倒序
                if (param.getOrderType().equals(CCTConstant.TYPE_BUY)) {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
                //如果是卖单，顺序
                if (param.getOrderType().equals(CCTConstant.TYPE_SELL)) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
                return 0;
            }
        });
        return list;
    }

    /***
     * 根据范围随机生成小数
     * @param min
     * @param max
     * @return
     */
    public static BigDecimal randomNum(Float min, Float max) {
        return new BigDecimal(Math.random() * (max - min) + min).setScale(8, BigDecimal.ROUND_DOWN);
    }
}
