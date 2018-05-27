package com.mksoft.shop.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mksoft.shop.Constants;
import com.mksoft.shop.service.bo.ResultBo;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.function.Consumer;

public class CommonUtil {
    public static Map getParameterMap(HttpServletRequest request) {
        Map resultMap = new HashMap();
        Map paramWithArray = request.getParameterMap();

        Iterator it = paramWithArray.keySet().iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            String[] val = (String[]) paramWithArray.get(name);

            resultMap.put(name, val[0]);
        }

        if (resultMap.get("pageNo") != null) {
            resultMap.put("pageNo", Integer.parseInt(String.valueOf(resultMap.get("pageNo"))));
        }

        if (resultMap.get("pageSize") != null) {
            resultMap.put("pageSize", Integer.parseInt(String.valueOf(resultMap.get("pageSize"))));
        }

        return resultMap;
    }

    public static Integer calcStartRow(Map param) {
        Integer startRow = null;

        String strPageNo = String.valueOf(param.get("pageNo"));
        String strPageSize = String.valueOf(param.get("pageSize"));

        if (StringUtils.isNotEmpty(strPageNo)
                && StringUtils.isNotEmpty(strPageSize)
                && !"NULL".equals(strPageNo.toUpperCase())
                && !"NULL".equals(strPageSize.toUpperCase())) {
            Integer pageNo = Integer.parseInt(strPageNo);
            Integer pageSize = Integer.parseInt(strPageSize);
            startRow = (pageNo - 1) * pageSize;
        }

        return startRow;
    }

    public static String generateUUID36() {
        String id = UUID.randomUUID().toString();

        return id;
    }

    public static String generateUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");

        return uuid;
    }

    public static Integer birthdate2Age(Date birthdate) {
        if (birthdate == null) {
            return -1;
        } else {
            LocalDate today = LocalDate.now();
            Instant instant = birthdate.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalDate localDate = localDateTime.toLocalDate();

            return Period.between(localDate, today).getYears();
        }
    }

    public static <T> ResultBo<T> processRows(ResultBo<T> result, Consumer<? super T> action) {
        List<T> rows = result.getRows();

        if (rows != null && rows.size() > 0) {
            rows.forEach(action);
        }

        return result;
    }

    public static Map parseHeaderMapFromJwtToken(String jwtToken) {
        String[] arrAuthToken = jwtToken.split("\\.");
        String headerBase64 = arrAuthToken[0].trim();
        byte[] header = Base64.getDecoder().decode(headerBase64);

        return (Map) JSON.parse(header);
    }

    public static String parseSysNameFromJwtToken(String jwtToken) {
        Map headerMap = parseHeaderMapFromJwtToken(jwtToken);

        return (String) headerMap.get(Constants.SYS_NAME_KEY);
    }

    public static int reportCalcStartRow(Map param) {
        Integer pageNo = Integer.parseInt(String.valueOf(param.get("pageNo")));
        Integer pageSize = Integer.parseInt(String.valueOf(param.get("pageSize")));

        return (pageNo - 1) * pageSize;
    }

    public static void obj2json(JSONObject json, Object obj) throws InvocationTargetException, IllegalAccessException {
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

                json.put(field.getName(),o);
            } catch (SecurityException e) {
                continue;
            } catch (NoSuchMethodException e) {
                continue;
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
    }

    public static void map2json(JSONObject json, Map map) throws InvocationTargetException, IllegalAccessException {
        for (Object m : map.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) m;

            String key = entry.getKey();
            Object val = entry.getValue();

            json.put(key,val);
        }
    }

    public static String generateTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}
