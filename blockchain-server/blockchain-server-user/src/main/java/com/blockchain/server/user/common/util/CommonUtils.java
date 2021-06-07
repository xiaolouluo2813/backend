package com.blockchain.server.user.common.util;

/**
 * 存放一些规则性的方法
 *
 * @author huangxl
 * @create 2019-02-23 20:38
 */
public class CommonUtils {

    /**
     * 通过邀请码获取自增码
     *
     * @param invitationCode 邀请码
     */
    public static Integer getUserIncrCodeFromInvitationCode(String invitationCode) {
        try {
            Integer number = Integer.parseInt(invitationCode, 16);
            String numberStr = number + "";
            String substring = numberStr.substring(2);
            return Integer.parseInt(substring);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据自增码和随机数值生成邀请码
     *
     * @param incrCode     自增码
     * @param randomNumber 随机数字
     * @return
     */
    public static String generateInvitationCode(int incrCode, Integer randomNumber) {
        if (randomNumber == null) {
            return null;
        }
        try {
            String hexStr = randomNumber + "" + incrCode;
            Integer hexNumber = Integer.parseInt(hexStr);
            return Integer.toHexString(hexNumber);
        } catch (Exception e) {
            return null;
        }
    }

}
