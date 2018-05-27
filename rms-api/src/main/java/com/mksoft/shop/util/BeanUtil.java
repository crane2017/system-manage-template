package com.mksoft.shop.util;

import com.alibaba.fastjson.JSONObject;
import com.mksoft.shop.util.converter.CustomDateConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    public static void copyProperties(final Object dest, final Object orig) throws InvocationTargetException, IllegalAccessException {
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();

        beanUtilsBean.getConvertUtils().register(new CustomDateConverter(), Date.class);
        beanUtilsBean.getConvertUtils().register(new BigDecimalConverter(null), BigDecimal.class);
        beanUtilsBean.getConvertUtils().register(new SqlTimestampConverter(null), java.sql.Timestamp.class);
        beanUtilsBean.getConvertUtils().register(new SqlDateConverter(null), java.sql.Date.class);
        beanUtilsBean.getConvertUtils().register(new SqlTimeConverter(null), java.sql.Time.class);

        beanUtilsBean.copyProperties(dest, orig);
    }

    public static void map2Json(JSONObject json, Map map) {
        for (Object m : map.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) m;

            String key = entry.getKey();
            Object val = entry.getValue();

            json.put(key, val);
        }
    }

    public static void obj2Json(JSONObject json, Object obj) {
        Map map = new HashMap();

        Class<? extends Object> objClass = obj.getClass();
        Field fields[] = objClass.getDeclaredFields();

        for (Field field : fields) {
            StringBuffer methodName = new StringBuffer();
            methodName.append("get");
            methodName.append(field.getName().substring(0, 1).toUpperCase());
            methodName.append(field.getName().substring(1));

            try {
                Method method = objClass.getMethod(methodName.toString());
                Object o = method.invoke(obj);

                json.put(field.getName(), o);
            } catch (SecurityException e) {
//                logger.error(e);
                continue;
            } catch (NoSuchMethodException e) {
//                logger.error(e);
                continue;
            } catch (IllegalArgumentException e) {
//                logger.error(e);
            } catch (IllegalAccessException e) {
//                logger.error(e);
            } catch (InvocationTargetException e) {
//                logger.error(e);
            }
        }
    }

    public static void map2Obj(Object obj, Map map) throws InvocationTargetException, IllegalAccessException {
        populate(obj, map);
    }

    public static void map2ObjSnack2Camel(Object obj, Map map) throws InvocationTargetException, IllegalAccessException {
        Map camelMap = mapKeySnack2Camel(map);
        populate(obj, camelMap);
    }

    private static void populate(Object obj, Map map) throws InvocationTargetException, IllegalAccessException {
        CustomDateConverter dtConverter = new CustomDateConverter();
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.deregister(Date.class);
        convertUtilsBean.register(dtConverter, Date.class);
        BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean,
                new PropertyUtilsBean());

        beanUtilsBean.populate(obj, map);
    }

    public static Map mapKeySnack2Camel(Map snackMap) {
        Map camelMap = new HashMap();

        for (Object m : snackMap.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) m;

            String snackKey = entry.getKey();
            String camelKey = snack2Camel(snackKey);
            Object val = entry.getValue();

            camelMap.put(camelKey, val);
        }

        return camelMap;
    }

    public static void obj2Map(Map map, Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        map.putAll(BeanUtils.describe(obj));
    }

    public static String snack2Camel(String fieldName) {
        String[] arrS = fieldName.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arrS.length; i++) {
            String pos = arrS[i];

            char initial = pos.charAt(0);

            if (i == 0) {
                initial = Character.toLowerCase(initial);
            } else {
                initial = Character.toUpperCase(initial);
            }

            sb.append(initial);
            sb.append(pos.substring(1));
        }

        return sb.toString();
    }

    public static void clearBlankInMap(Map map) {
        for (Object m : map.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) m;

            String key = entry.getKey();
            Object val = entry.getValue();

            if (val != null && StringUtils.isBlank(val.toString())) {
                map.put(key, null);
            }
        }
    }
}