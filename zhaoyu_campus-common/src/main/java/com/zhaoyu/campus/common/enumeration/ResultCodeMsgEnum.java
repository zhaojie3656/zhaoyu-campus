package com.zhaoyu.campus.common.enumeration;

import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.DATA_CONVERSION;

/**
 * 异常枚举
 */
public enum ResultCodeMsgEnum {
    DEV_EXPTION(-1, "非常感谢你，帮程序员找到一个八阿哥！"),
    SYS_ERR(0, "系统异常，请联系管理员！"),
    SUCCESS(200, "成功"),
    PARAM_NULL_ERROR(2, "%s不能为空"),
    PARAM_ERR(10,"参数不能为空"),
    TYPE_ERROR(10001,"[%s],格式错误"),
    DATA_FULL_ERROR(20001,"[%s],不存在"),
    DATA_ERROR(20002,"[%s],错误"),
    ADMIN_ERROR(3001,"操作admin表错误");



    @Getter@Setter
    private int code;
    @Getter@Setter
    private String msg;

    private ResultCodeMsgEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
