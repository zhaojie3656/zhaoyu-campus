package com.zhaoyu.campus.common.utils;


import com.zhaoyu.campus.common.enumeration.ResultCodeMsgEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @Description: [业务校验通用工具类]
 * @Author: l郭晓颖
 * @CreateDate: 2019/1/4
 * @Version: [v1.0]
 */
public class BPCheckUtil {
    private static Logger logger = LoggerFactory.getLogger(BPCheckUtil.class);

    /**
     * @param param 待校验的参数
     * @param tip   非空提示信心
     */
    public static void paramEmptyCheck(String param, String tip) {
        if (StringUtils.isEmpty(param)) {
            String msg = String.format(ResultCodeMsgEnum.PARAM_ERR.getMsg(), tip);
            throw new BizException(ResultCodeMsgEnum.PARAM_ERR.getCode(), msg);
        }
    }

    public static void checkMaxLength(String property, int maxLength) {
        if (property == null || "".equals(property)) {
            throw new BizException("参数不能为空");
        }
        if (property.length() > maxLength) {
            StringBuilder sb = new StringBuilder("参数(").append(property).append(")的长度不能超过:").append(maxLength);
            throw new BizException(sb.toString());
        }
    }


    public static <T> void checkEmptyInBean(String[] propertyNames, T bean, boolean isContainZero) {
        if (null == bean) {
            throw new BizException("参数不能为空");
        }
        for (String propertyName : propertyNames) {
            Field field = BeanMapper.getDeclaredField(bean.getClass(), propertyName);
            if (null == field) {
                logger.info("field={} is empty in class={}", new Object[]{propertyName, bean.getClass()});
                continue;
            }
            Object fieldVaue = BeanMapper.getFieldValue(bean, field);
            if (StringUtils.isEmpty(fieldVaue)) {
                StringBuilder sb = new StringBuilder("参数(").append(propertyName).append(")不能为空");
                throw new BizException(0, sb.toString());
            }
            if ("0".equals(fieldVaue + "") && isContainZero) {
                StringBuilder sb = new StringBuilder("参数(").append(propertyName).append(")不能为空");
                throw new BizException(0, sb.toString());
            }
            if (fieldVaue instanceof Double && isContainZero && 0d == Double.parseDouble(fieldVaue.toString())) {
                StringBuilder sb = new StringBuilder("参数(").append(propertyName).append(")不能为空");
                throw new BizException(0, sb.toString());
            }
            if (fieldVaue instanceof List && ((List) fieldVaue).isEmpty()) {
                StringBuilder sb = new StringBuilder("参数(").append(propertyName).append(")不能为空");
                throw new BizException(0, sb.toString());
            }
            if (fieldVaue instanceof Object[] && ((Object[]) fieldVaue).length == 0) {
                StringBuilder sb = new StringBuilder("参数(").append(propertyName).append(")不能为空");
                throw new BizException(0, sb.toString());
            }
        }
    }

    public static void checkEmptyInMap(String[] keys, Map<String, Object> map, boolean isContainZero) {
        if (null == map) {
            throw new BizException("参数不能为空");
        }
        for (String key : keys) {
            if (map.containsKey(key)) {
                Object value = map.get(key);
                if (StringUtils.isEmpty(value)) {
                    throw new BizException(0, new StringBuilder("参数(").append(key).append(")不能为空").toString());
                }
                if ("0".equals(value + "") && isContainZero) {
                    throw new BizException(0, new StringBuilder("参数(").append(key).append(")不能为空").toString());
                }
                if (value instanceof Double && isContainZero && 0d == Double.parseDouble(value.toString())) {
                    throw new BizException(0, new StringBuilder("参数(").append(key).append(")不能为空").toString());
                }
                if (value instanceof List && ((List) value).isEmpty()) {
                    throw new BizException(0, new StringBuilder("参数(").append(key).append(")不能为空").toString());
                }
                if (value instanceof Object[] && ((Object[]) value).length == 0) {
                    throw new BizException(0, new StringBuilder("参数(").append(key).append(")不能为空").toString());
                }
            }
        }
    }
}
