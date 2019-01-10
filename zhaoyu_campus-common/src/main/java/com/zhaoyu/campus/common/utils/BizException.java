package com.zhaoyu.campus.common.utils;

import com.zhaoyu.campus.common.enumeration.ResultCodeMsgEnum;

/**
 * @Description: [对外提供服务的业务异常（supplier-gateway,supplier-api，web-dubbo服务等消费方自己处理）]
 * @Author: guoxiaoying01
 * @CreateDate: 2019/1/4 11:18
 * @Version: [v1.0]
 */
public class BizException extends RuntimeException {
    public BizException() {
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(int code, String message) {
        super(message);
        this.ret = code;
    }

    public BizException(String message) {
        super(message);
        this.ret = 0;
    }

    public BizException(ResultCodeMsgEnum resultCode) {
        super(resultCode.getMsg());
        this.ret = resultCode.getCode();
    }

    /**
     * 异常编码.
     */
    private int ret = 0;

    public int getRet() {
        return this.ret;
    }
}
