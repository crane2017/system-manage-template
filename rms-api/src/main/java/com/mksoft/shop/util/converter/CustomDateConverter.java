package com.mksoft.shop.util.converter;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class
CustomDateConverter implements Converter {

    public Object convert(Class arg0, Object arg1) {
        if (arg1 instanceof Date) {
            return arg1;
        } else if (arg1 instanceof Long) {
            return new Date((Long) arg1);
        } else {
            String ts = (String) arg1;
            SimpleDateFormat sdf = null;

            if (ts == null || ts.trim().length() == 0) {
                return null;
            }

            if(ts.indexOf("Z") != -1){
                ts = ts.replace("Z", " UTC");
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            } else{
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            }

            Date dt = null;
            try {
                dt = sdf.parse(ts);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                return dt;
            }
        }
    }

}
