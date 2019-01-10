package com.zhaoyu.campus.common.utils;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper.实现:
 * <p>
 * 1. 持有Mapper的单例.
 * 2. 返回值类型转换.
 * 3. 批量转换Collection中的所有对象.
 * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 */
public class BeanMapper {
    private static Logger logger = LoggerFactory.getLogger(BeanMapper.class);
    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    /**
     * set field value for given object
     *
     * @param targetObject
     * @param field
     * @param value
     */
    public static void setFieldValue(Object targetObject, Field field, String value) {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);

        Class<?> fieldType = field.getType();

        try {
            if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                field.set(targetObject, Boolean.valueOf(value));
            } else if (fieldType.equals(byte.class) || fieldType.equals(Byte.class)) {
                field.set(targetObject, Byte.valueOf(value));
            } else if (fieldType.equals(char.class) || fieldType.equals(Character.class)) {
                field.set(targetObject, Character.valueOf(value.charAt(0)));
            } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                field.set(targetObject, Double.valueOf(value));
            } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
                field.set(targetObject, Float.valueOf(value));
            } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                field.set(targetObject, Integer.valueOf(value));
            } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                field.set(targetObject, Long.valueOf(value));
            } else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
                field.set(targetObject, Short.valueOf(value));
            } else {
                field.set(targetObject, value);
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("setFieldValue failed for ").append(targetObject.getClass().getName()).append(".").append(field.getName()).append(". value = ").append(value);
            throw new RuntimeException(sb.toString(), e);
        }

        field.setAccessible(isAccessible);
    }

    /**
     * get the field value for given object
     *
     * @param targetObject
     * @param field
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object targetObject, Field field) {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        Object value = null;
        try {
            value = field.get(targetObject);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("getFieldValue failed for ").append(targetObject.getClass().getName()).append(".").append(field.getName()).append(". value = ").append(value);
            throw new RuntimeException(sb.toString(), e);
        }
        field.setAccessible(isAccessible);
        return (T) value;
    }

    public static <T> List<T> changeToCamelClsByMap(List<Map<String,Object>> maps, Class<T> c) {
        List<T> datas = new ArrayList<T>();
        if (null == maps || maps.isEmpty() || null == c) {
            return datas;
        }
        for (Map<String,Object> map : maps) {
            T t = changeToCamelClsByMap(map, c);
            datas.add(t);
        }
        return datas;
    }

    /**
     * 获取实例化对象
     *
     * @param c             对象Class
     * @param understoreMap
     * @return
     */
    public static <T> T changeToCamelClsByMap(Map<String, Object> understoreMap, Class<T> c) {
        if (null == understoreMap) {
            return null;
        }
        try {
            T o = c.newInstance();
            String[] names = understoreMap.keySet().toArray(new String[0]);
            Map<String, Object> methodMap = new HashMap<String, Object>();
            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                StringBuilder tname = new StringBuilder().append("set");
                if (name.indexOf("_") == 1 ||
                        (Character.isLowerCase(name.charAt(0)) && Character.isUpperCase(name.charAt(1)))) {
                    tname.append(name.charAt(0));
                } else {
                    tname.append((char) (name.charAt(0) - 32));
                }
                if (name.indexOf("_") > 0) {
                    int start = 1;
                    int end = name.indexOf("_");
                    while (end > 0) {
                        tname.append(name.substring(start, end));
                        start = end + 1;
                        tname.append((char) (name.charAt(start) - 32));
                        start++;
                        end = name.indexOf("_", start);
                    }
                    if (start < name.length()) {
                        tname.append(name.substring(start, name.length()));
                    }
                } else {
                    tname.append(name.substring(1));
                }
                methodMap.put(tname.toString(), understoreMap.get(name));
            }
            Method[] rs = c.getMethods();
            for (Method method : rs) {
                String mn = method.getName();
                if (mn.startsWith("set")) {
                    if (methodMap.containsKey(mn) && methodMap.get(mn) != null) {
                        try {
                            setValueToObject(method, methodMap.get(mn), o);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
            return o;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
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

    public static List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> results = new ArrayList<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                for (Field field1 : clazz.getDeclaredFields()) {
                    results.add(field1);
                }
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return results;
    }

    private static void setValueToObject(Method method, Object vlaue, Object o) throws Exception {
        if ("java.lang.Long".equals(method.getGenericParameterTypes()[0].getTypeName())) {
            vlaue = ParamUtil.objectToLong(vlaue);
            method.invoke(o, new Object[]{vlaue});
        } else if (method.getParameterTypes()[0] == String.class) {
            vlaue = ParamUtil.objectToString(vlaue);
            method.invoke(o, new Object[]{vlaue});
        } else if (method.getParameterTypes()[0] == Integer.class || method.getParameterTypes()[0] == int.class) {
            vlaue = ParamUtil.objectToInteger(vlaue);
            method.invoke(o, new Object[]{vlaue});
        } else if (method.getParameterTypes()[0] == Double.class || method.getParameterTypes()[0] == double.class) {
            vlaue = ParamUtil.objectToDouble(vlaue);
            method.invoke(o, new Object[]{vlaue});
        } else if (method.getParameterTypes()[0] == Boolean.class || method.getParameterTypes()[0] == boolean.class) {
            vlaue = ParamUtil.objectToBoolean(vlaue);
            method.invoke(o, new Object[]{vlaue});
        } else if (method.getParameterTypes()[0] == Float.class || method.getParameterTypes()[0] == float.class) {
            vlaue = ParamUtil.objectToFloat(vlaue);
            method.invoke(o, new Object[]{vlaue});
        } else if ("java.util.Collection<java.lang.Long>".equals(method.getGenericParameterTypes()[0].getTypeName())) {
            List<Long> listObjects = new ArrayList<Long>();
            for (Object oVlaue : (List) vlaue) {
                listObjects.add(ParamUtil.objectToLong(oVlaue));
            }
            method.invoke(o, new Object[]{listObjects});
        } else if (method.getParameterTypes()[0] == Collection.class && vlaue instanceof Object[]) {
            List listObjects = new ArrayList<>();
            for (Object oVlaue : (Object[]) vlaue) {
                listObjects.add(oVlaue);
            }
            method.invoke(o, new Object[]{listObjects});
        } else {
            method.invoke(o, new Object[]{vlaue});
        }
    }

    /**
     * 获取实例化对象
     *
     * @return
     */
    public static <T, F> T changeToCalmelCls(F understorObject, Class<T> camelCls) {
        Field[] fields = understorObject.getClass().getDeclaredFields();
        T targetObject = null;
        try {
            targetObject = camelCls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Map<String, Object> targetMethodValueMap = new HashMap<String, Object>();
        for (Field eachField : fields) {
            //取出值
            Object value = getFieldValue(understorObject, eachField);
            String name = eachField.getName();
            //单个字母开头
            StringBuilder tname = null;
            if (name.indexOf("_") == 1) {
                tname = new StringBuilder().append("set").append(name.charAt(0));
            } else {
                tname = new StringBuilder().append("set").append((char) (name.charAt(0) - 32));
            }
            if (name.indexOf("_") > 0) {
                int start = 1;
                int end = name.indexOf("_");
                while (end > 0) {
                    tname.append(name.substring(start, end));
                    start = end + 1;
                    tname.append((char) (name.charAt(start) - 32));
                    start++;
                    end = name.indexOf("_", start);
                }
                if (start < name.length()) {
                    tname.append(name.substring(start, name.length()));
                }
            } else {
                tname.append(name.substring(1));
            }
            targetMethodValueMap.put(tname.toString(), value);
        }
        Method[] rs = camelCls.getMethods();
        for (Method method : rs) {
            String mn = method.getName();
            if (mn.startsWith("set")) {
                if (targetMethodValueMap.containsKey(mn) && targetMethodValueMap.get(mn) != null) {
                    try {
                        setValueToObject(method, targetMethodValueMap.get(mn), targetObject);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return targetObject;
    }

    /**
     * @param camelObjects
     * @param understorCls
     * @param longTimesFiles 此参数数组会转换时间比如：c_t 转换为字符串2017-09-04 16:49:15
     * @param <T>
     * @param <F>
     * @return
     */
    public static <T, F> Map<Long, T> changeToUnderstoreClsByIdMap(List<F> camelObjects, Class<T> understorCls, List<String> longTimesFiles) {
        if (ParamUtil.isListEmpty(camelObjects)) {
            return null;
        }
        Map<Long, T> resMap = new HashMap<Long, T>();
        for (F camelObject : camelObjects) {
            Field field = getDeclaredField(camelObject.getClass(), "id");
            field.setAccessible(true);
            Long id = null;
            try {
                id = (Long) field.get(camelObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            T res = changeToUnderstoreCls(camelObject, understorCls, longTimesFiles);
            resMap.put(id, res);
        }
        return resMap;
    }

    public static <T, F> List<T> changeToUnderstoreClses(List<F> camelObjects, Class<T> understorCls, List<String> longTimesFiles) {
        if (ParamUtil.isListEmpty(camelObjects)) {
            return null;
        }
        List<T> results = new ArrayList<T>();
        for (F camelObject : camelObjects) {
            T res = changeToUnderstoreCls(camelObject, understorCls, longTimesFiles);
            if (null != res) {
                results.add(res);
            }
        }
        return results;
    }

    public static <F> List<Map<String, Object>> changeToUnderstoreMaps(List<F> camelObjects, List<String> toTransToStringTimeFiels) {
        List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
        if (ParamUtil.isListEmpty(camelObjects)) {
            return dataMaps;
        }
        for (F camelObject : camelObjects) {
            Map<String, Object> dataMap = changeToUnderstoreMap(camelObject, toTransToStringTimeFiels);
            dataMaps.add(dataMap);
        }
        return dataMaps;
    }

    /**
     * @param camelObject
     * @param toTrasnToStringTimeFiels 需要转换为时间字符串类型的字段数组 比如c_t:转换后2017-09-04 16:49:15
     * @param <F>
     * @return
     */
    public static <F> Map<String, Object> changeToUnderstoreMap(F camelObject, List<String> toTrasnToStringTimeFiels) {
        Map<String, Object> desMap = new HashMap<String, Object>();
        List<Field> fields = getDeclaredFields(camelObject.getClass());
        for (Field eachField : fields) {
            //取出值
            Object value = getFieldValue(camelObject, eachField);
            String name = eachField.getName();
            String underLineName = addUnderscores(name);
            if (!ParamUtil.isListEmpty(toTrasnToStringTimeFiels) && toTrasnToStringTimeFiels.contains(name) && null != value) {
                desMap.put(underLineName, DateUtil.foramtTime(Long.parseLong(value.toString()), DateUtil.YMDHMS));
            } else if (null != value) {
                desMap.put(underLineName, value);
            }
        }
        return desMap;
    }

    /**
     * 获取实例化对象
     *
     * @return
     */
    public static <T, F> T changeToUnderstoreCls(F camelObject, Class<T> understorCls, List<String> longTimesFiles) {
        List<Field> fields = getDeclaredFields(camelObject.getClass());
        T targetObject = null;
        try {
            targetObject = understorCls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Map<String, Object> targetMethodValueMap = new HashMap<String, Object>();
        for (Field eachField : fields) {
            //取出值
            Object value = getFieldValue(camelObject, eachField);
            String name = eachField.getName();
            if (!ParamUtil.isListEmpty(longTimesFiles) && longTimesFiles.contains(name)) {
                value = DateUtil.foramtTime(Long.parseLong(value.toString()), DateUtil.YMDHMS);
            }
            String underLineName = addUnderscores(name);
            StringBuilder tname = new StringBuilder().append("set");
            if (underLineName.indexOf("_") != 1 &&
                    (Character.isLowerCase(underLineName.charAt(0)) && Character.isUpperCase(underLineName.charAt(1)))) {
                tname.append(name.charAt(0));
            } else {
                tname.append((char) (underLineName.charAt(0) - 32));
            }
            tname.append(underLineName.substring(1, underLineName.length()));
            targetMethodValueMap.put(tname.toString(), value);
        }
        Method[] rs = understorCls.getMethods();
        for (Method method : rs) {
            String mn = method.getName();
            if (mn.startsWith("set")) {
                if (targetMethodValueMap.containsKey(mn) && targetMethodValueMap.get(mn) != null) {
                    try {
                        setValueToObject(method, targetMethodValueMap.get(mn), targetObject);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return targetObject;
    }

    public static String addUnderscores(String name) {
        StringBuilder buf = new StringBuilder(name.replace('.', '_'));
        for (int i = 1; i <= (buf.length() - 1); i++) {
            if ((Character.isUpperCase(buf.charAt(i)))
                    && ((Character.isLowerCase(buf.charAt(i - 1))) || (Character.isLowerCase(buf.charAt(i + 1))))) {
                {
                    buf.insert(i++, '_');
                }
            }
        }
        return buf.toString().toLowerCase();
    }

    public static <F> Map<String, Object> changeToMap(F camelObject,List<String> toTrasnToStringTimeFiels) {
        Map<String, Object> desMap=new HashMap<String,Object>();
        Field[] fields = camelObject.getClass().getDeclaredFields();
        for (Field eachField : fields) {
            //取出值
            Object value = getFieldValue(camelObject, eachField);
            String name = eachField.getName();
            if(!ParamUtil.isListEmpty(toTrasnToStringTimeFiels) && toTrasnToStringTimeFiels.contains(name) && null!=value){
                desMap.put(name, DateUtil.foramtTime(Long.parseLong(value.toString()),DateUtil.YMDHMS));
            }else{
                desMap.put(name,value);
            }
        }
        return desMap;
    }

  /*  public static void main(String[] args) {
        addUnderscores("fUser");
    }*/
}
