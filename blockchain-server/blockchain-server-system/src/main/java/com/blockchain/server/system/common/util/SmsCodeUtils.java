package com.blockchain.server.system.common.util;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.system.common.constant.RedisConstant;
import com.blockchain.server.system.common.enums.SmsCountEnum;
import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SmsCodeUtils {

    @Autowired
    private SendSmsgCode sendSmsgCode;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送短信并保存到缓存中
     *
     * @param phone        手机号
     * @param smsCountEnum 枚举类型
     */
    public void sendSmsCodeAndStoreToCache(String phone,String internationalCode, SmsCountEnum smsCountEnum) {
        ExceptionPreconditionUtils.notEmpty(phone);  //检查参数是否为空
        String code = "666666";
        if(!sendSmsgCode.isClosed()){
            code = RandomStringUtils.random(6, false, true);
        }
        sendSmsgCode.sendSmsg(phone,code,internationalCode,smsCountEnum.getTempleteId());
        redisTemplate.opsForValue().set(RedisConstant.getSmsHashKey(smsCountEnum.getKey(),phone),code,sendSmsgCode.getTimeout(), TimeUnit.MINUTES);
    }

    /**
     * 验证手机号和验证码
     *
     * @param verifyCode   输入的验证码信息
     * @param phoneNum     手机号
     * @param smsCountEnum 枚举
     */
    public void validateVerifyCode(String verifyCode, String phoneNum, SmsCountEnum smsCountEnum) {
        if(sendSmsgCode.isClosed()){
            return;
        }
        ExceptionPreconditionUtils.notEmpty(verifyCode, phoneNum, smsCountEnum);  //检查参数是否为空
        String redisKey = RedisConstant.getSmsHashKey(smsCountEnum.getKey(),phoneNum);
        if(!redisTemplate.hasKey(redisKey)){
            throw new SystemException(SystemResultEnums.SMS_CODE_NOT_EXIST);
        }
        String code = (String) redisTemplate.opsForValue().get(redisKey);
        if(!verifyCode.equals(code)){
            throw new SystemException(SystemResultEnums.SMS_VERIFY_FAIL);
        }
    }

    /**
     * 删除缓存的key
     * @param phoneNum 手机号
     * @param smsCountEnum 验证码枚举类型
     */
    public void removeKey(String phoneNum, SmsCountEnum smsCountEnum){
        String redisKey = RedisConstant.getSmsHashKey(smsCountEnum.getKey(),phoneNum);
        //验证完成，删除缓存信息
        redisTemplate.delete(redisKey);
    }
}
