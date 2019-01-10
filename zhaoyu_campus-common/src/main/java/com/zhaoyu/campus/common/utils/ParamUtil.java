package com.zhaoyu.campus.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Description: [一句话描述该类的作用]
 * @Author: guoxiaoying01
 * @CreateDate: 19/1/4 16:53
 * @Version: [v1.0]
 */
public class ParamUtil {
    public static final String UNDERLINE_FLAG = "_";

    public static final String COMMA_FLAG = ",";
    private static DecimalFormat df = new DecimalFormat("#0.00");

    static {
        df.setRoundingMode(RoundingMode.HALF_UP);
    }

    /**
     * 如果字符串为空返回
     *
     * @param str
     * @return
     */
    public static boolean isStringEmpty(String str) {
        return (str == null || "".equals(str));
    }


    public static boolean isMapEmpty(Map map) {
        return (null == map || map.isEmpty());
    }

    public static boolean isListEmpty(List list) {
        return (null == list || list.size() == 0);
    }

    public static boolean isCollectionEmpty(Collection list) {
        return (null == list || list.size() == 0);
    }

    public static boolean isArrayEmpty(Object[] list) {
        return (null == list || list.length == 0);
    }

    public static boolean isLongEmpty(Long item) {
        return (null == item || item.compareTo(0L) == 0);
    }

    public static boolean isIntegerEmpty(Integer item) {
        return (null == item || item == 0);
    }

    public static boolean isBigDecimalEmpty(BigDecimal item) {
        return (null == item || 0 == BigDecimal.ZERO.compareTo(item));
    }


    /**
     * 四舍五入保留n位小数
     *
     * @param value value
     * @param scale n位小数
     * @return return
     */
    public static Double formatNum(Double value, int scale) {
        if (null == value) {
            return 0.0;
        }
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 把list转换成相应的map结构
     * <p>
     *
     * @param list list
     * @param key  key
     * @param <T>  </T>
     * @return return
     */
    public static <T> Map<Long, T> changeListToLongKeyMap(List<T> list, String key) {
        Map<Long, T> result = new LinkedHashMap<>();
        if (null == list || list.size() == 0) {
            return result;
        }
        try {
            Field field = getDeclaredField(list.get(0), key);
            field.setAccessible(true);
            for (T item : list) {
                Long id = (Long) field.get(item);
                result.put(id, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param clazz     : 子类类型
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field field = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 提取相关的变量
     *
     * @param list list
     * @param key  key
     * @param <T>  </T>
     * @return return
     */
    public static <T> List<Long> changeListToLongList(List<T> list, String key) {
        if (null == list || list.size() == 0) {
            return new ArrayList<Long>();
        }
        List<Long> result = new ArrayList<>();
        try {
            Field field = getDeclaredField(list.get(0), key);
            field.setAccessible(true);
            for (T item : list) {
                Long id = (Long) field.get(item);
                if (!result.contains(id)) {
                    result.add(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 从map中提取相关变量
     *
     * @param list list
     * @param key  key
     * @return return
     */
    public static <K, V> List<Long> changeMapListToLongList(List<Map<K, V>> list, String key) {
        List<Long> result = new ArrayList<>();
        try {
            for (Map m : list) {
                if (m.containsKey(key)) {
                    Long item = Long.parseLong("" + m.get(key));
                    if (!result.contains(item)) {
                        result.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 从map中提取相关变量
     *
     * @param list list
     * @param key  key
     * @return return
     */
    public static <K, V> List<Long> extractKeyFromMap(List<Map<K, V>> list, String key) {
        List<Long> result = new ArrayList<>();
        try {
            for (Map m : list) {
                if (m.containsKey(key)) {
                    Long item = Long.parseLong("" + m.get(key));
                    if (!result.contains(item)) {
                        result.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据条件过滤list,保留只满足条件的list
     *
     * @param list      list
     * @param key       key
     * @param conditons conditions
     * @param clazz     clazz
     * @param <T>       <T>
     * @return return
     */
    public static <T, V> List<T> filterList(List<T> list, String key, Class<V> type, List<V> conditons, Class<T> clazz) {
        if (null == list || list.size() == 0) {
            return new ArrayList<T>();
        }
        try {
            Field field = getDeclaredField(list.get(0), key);
            field.setAccessible(true);
            Iterator<T> itr = list.iterator();
            while (itr.hasNext()) {
                T item = itr.next();
                V id = (V) field.get(item);
                if (!conditons.contains(id)) {
                    itr.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将固定格式的字符串转换成list
     *
     * @param source    source
     * @param separator separator
     * @return return
     */
    public static List<Long> splitStringToLongList(String source, String separator) {
        List<Long> result = new ArrayList<>();
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(separator)) {
            return result;
        }
        String sourceArr[] = source.split(separator);
        for (int i = 0; i < sourceArr.length; i++) {
            String item = sourceArr[i];
            if (StringUtils.isNumeric(item)) {
                result.add(Long.parseLong(item));
            }
        }
        return result;
    }

    public static <T> String splitListToStr(List<T> datas, String separator) {
        if (ParamUtil.isListEmpty(datas)) {
            return "";
        }
        if (StringUtils.isEmpty(separator)) {
            separator = ParamUtil.COMMA_FLAG;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (T data : datas) {
            sb.append(data + "");
            if (i < datas.size() - 1) {
                sb.append(separator);
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * 将大集合按照数量拆分成小集合
     *
     * @param totalList  被拆分集合
     * @param splitCount 拆分总集合
     * @return return
     */
    public static <T> List<List<T>> listSplit(List<T> totalList, int splitCount) {
        List<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(totalList) || splitCount < 1) {
            return result;
        }
        int size = totalList.size();
        //数据量不足count指定的大小
        if (size <= splitCount) {
            result.add(totalList);
        } else {
            int page = size / splitCount;
            int last = size % splitCount;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < page; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < splitCount; j++) {
                    itemList.add(totalList.get(i * splitCount + j));
                }
                result.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(totalList.get(page * splitCount + i));
                }
                result.add(itemList);
            }
        }
        return result;

    }


    /**
     * 字符串拼接
     *
     * @param source    source
     * @param delimeter 分隔符
     * @param addInfo   addInfo
     * @return return
     */
    public static String addInfo(String source, String delimeter, String addInfo) {
        StringBuffer buffer = new StringBuffer();
        if (StringUtils.isEmpty(source)) {
            buffer.append(addInfo);
        } else {
            buffer.append(source).append(delimeter).append(addInfo);
        }
        return buffer.toString();
    }

    public static String objectToString(Object o) {
        if (null == o) {
            return null;
        }
        return o.toString();
    }

    public static String objectToString(Object o, boolean isDefalutEmpty) {
        if (null == o && isDefalutEmpty) {
            return "";
        } else if (null == o) {
            return null;
        }
        return o.toString();
    }

    public static Long objectToLong(Object o, boolean isDefalutZero) {
        if (null == o && isDefalutZero) {
            return 0L;
        } else if (null == o) {
            return null;
        }
        return Long.parseLong(o.toString());
    }

    public static Long objectToLong(Object o, boolean isDefalutZero, boolean isEmptyZero) {
        if (null == o && isDefalutZero) {
            return 0L;
        } else if ("".equals(o) && isEmptyZero) {
            return 0L;
        } else if (null == o) {
            return null;
        }
        return Long.parseLong(o.toString());
    }

    public static Integer objectToInteger(Object o, boolean isDefalutZero) {
        if (null == o && isDefalutZero) {
            return 0;
        } else if (null == o) {
            return null;
        }
        return Integer.parseInt(o.toString());
    }

    public static Double objectToDouble(Object o, boolean isDefalutZero) {
        if (null == o && isDefalutZero) {
            return 0d;
        } else if (null == o) {
            return null;
        }
        return Double.parseDouble(o.toString());
    }

    public static Boolean objectToBoolean(Object o, boolean isDefalutFalse) {
        if (null == o && isDefalutFalse) {
            return false;
        } else if (null == o) {
            return null;
        }
        return Boolean.parseBoolean(o.toString());
    }

    public static Float objectToFloat(Object o, boolean isDefalutZero) {
        if (null == o && isDefalutZero) {
            return 0F;
        } else if (null == o) {
            return null;
        }
        return Float.parseFloat(o.toString());
    }

    public static List<String> longListToStringList(List<Long> longList) {
        if (null == longList) {
            return null;
        }
        List<String> strList = new ArrayList<>();
        for (Long eachLong : longList) {
            strList.add(String.valueOf(eachLong));
        }
        return strList;
    }

    /**
     * 移除map中的value空值
     *
     * @param map
     * @return
     */
    public static void removeNullValue(Map map) {
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            Object obj = (Object) iterator.next();
            Object value = (Object) map.get(obj);
            remove(value, iterator);
        }
    }

    /**
     * 内部调用
     *
     * @param obj
     * @param iterator
     */
    private static void remove(Object obj, Iterator iterator) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (isStringEmpty(str)) {  //过滤掉为null和""的值 主函数输出结果map：{2=BB, 1=AA, 5=CC, 8=  }
                //if("".equals(str.trim())){  //过滤掉为null、""和" "的值 主函数输出结果map：{2=BB, 1=AA, 5=CC}
                iterator.remove();
            }
        } else if (obj instanceof Collection) {
            Collection col = (Collection) obj;
            if (col == null || col.isEmpty()) {
                iterator.remove();
            }
        } else if (obj instanceof Map) {
            Map temp = (Map) obj;
            if (temp == null || temp.isEmpty()) {
                iterator.remove();
            }
        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            if (array == null || array.length <= 0) {
                iterator.remove();
            }
        } else {
            if (obj == null) {
                iterator.remove();
            }
        }
    }

    /**
     * 四舍五入保留n位小数
     *
     * @param value
     * @param scale
     * @return
     */
    public static BigDecimal formatMoneyConvert(Long value, BigDecimal roundingMode, int scale) {
        if (null == value) {
            return new BigDecimal(0.00);
        }
        if (null == roundingMode || roundingMode.equals(BigDecimal.ZERO)) {
            return new BigDecimal(value);
        }
        BigDecimal bd = new BigDecimal(value);
        return bd.divide(roundingMode, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static Long objectToLong(Object o) {
        if (null == o) {
            return null;
        }
        return Long.parseLong(o.toString());
    }

    public static Integer objectToInteger(Object o) {
        if (null == o) {
            return null;
        }
        return Integer.parseInt(o.toString());
    }

    public static Double objectToDouble(Object o) {
        if (null == o) {
            return null;
        }
        return Double.parseDouble(o.toString());
    }

    public static Boolean objectToBoolean(Object o) {
        if (null == o) {
            return null;
        }
        return Boolean.parseBoolean(o.toString());
    }

    public static Float objectToFloat(Object o) {
        if (null == o) {
            return null;
        }
        return Float.parseFloat(o.toString());
    }

    public static String objectToEmptyString(Object o) {
        if (null == o) {
            return "";
        }
        return o.toString();
    }

    public static String roundUpToTowDigit(BigDecimal value) {
        if (null == value) {
            return "";
        }
        return df.format(value);
    }

    public static String roundUpToTowDigitZero(BigDecimal value) {
        if (null == value) {
            return "0.00";
        }
        return df.format(value);
    }

    /**
     * string转换成Long
     *
     * @param str
     * @return
     */
    public static Long strToLong(String str) {
        if (StringUtils.isEmpty(str)) {
            return -1L;
        }
        try {
            Double doubleStr = Double.parseDouble(str);
            Long longStr = Math.round(doubleStr);
            return longStr;
        } catch (NumberFormatException ex) {

        }
        return 0L;
    }

    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;

    }

    /**
     * 校验map不能为空（基本类型）
     */
    public static void checkMapByKey(String[] keyList, Map<String, Object> map) throws Exception {
        for (String key : keyList) {
            if (!map.containsKey(key)) {
                throw new BizException(0, key + "参数不能为空");
            }
            Object value = map.get(key);
            if (null == value) {
                throw new BizException(0, key + "参数值不能为null");
            }
        }
    }

    /**
     * 把list转换成相应的map结构
     *
     * @param list  list
     * @param key   key
     * @param clazz clazz
     * @param <T>   </T>
     * @return return
     */
    public static <T> Map<Long, T> listToMap(List<T> list, String key, Class<T> clazz) {
        Map<Long, T> result = new LinkedHashMap<>();
        if (null == list || list.size() == 0) {
            return result;
        }
        try {
            Field field = getDeclaredField(clazz, key);
            field.setAccessible(true);
            for (T item : list) {
                Long id = (Long) field.get(item);
                result.put(id, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将大集合按照数量拆分成小集合
     *
     * @param allList    被拆分集合
     * @param splitCount 拆分总集合
     * @return return
     */
    public static <T> List<List<T>> listSplit(Collection<T> allList, int splitCount) {
        List<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(allList) || splitCount < 1) {
            return result;
        }
        List<T> totalList = new ArrayList<T>(allList);
        int size = totalList.size();
        //数据量不足count指定的大小
        if (size <= splitCount) {
            result.add(totalList);
        } else {
            int page = size / splitCount;
            int last = size % splitCount;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < page; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < splitCount; j++) {
                    itemList.add(totalList.get(i * splitCount + j));
                }
                result.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(totalList.get(page * splitCount + i));
                }
                result.add(itemList);
            }
        }
        return result;

    }

    public static int createRandom(int start, int end) {
        if (start > end || start == end) {
            return 0;
        }
        Random random = new Random();
        return random.nextInt(end - start + 1) + start;
    }

    /**
     * 如果是小数，保留三位，非小数，保留整数
     *
     * @param number
     * @return
     */
    public static String getNumberString(BigDecimal number) {
        String numberStr;
        int number1 = number.intValue();
        if ((number.multiply(new BigDecimal(1000))).intValue() == number1 * 1000) {
            numberStr = number1 + "";
        } else {
            DecimalFormat df = new DecimalFormat("#####0.000");
            numberStr = df.format(number);
        }
        return numberStr;
    }
}
