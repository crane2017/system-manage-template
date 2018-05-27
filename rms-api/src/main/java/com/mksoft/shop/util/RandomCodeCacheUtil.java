package com.mksoft.shop.util;

import java.util.HashMap;
import java.util.Map;

public class RandomCodeCacheUtil {

    private static RandomCodeCacheUtil instance;

    private boolean checkRandom = false;
    private Map<String, String> map;

    public static RandomCodeCacheUtil getInstance() {
        if (instance == null)
            instance = new RandomCodeCacheUtil();
        return instance;
    }

    private RandomCodeCacheUtil() {
        map = new HashMap<String, String>();
    }

    public synchronized boolean checkRandom(String key, String randomcode) {
        if (key == null || key.trim().length() == 0) {
            throw new RuntimeException("key不能为空~!");
        }
        String value = map.get(key);
        if (value == null || value.equals(randomcode) == false)
            return false;
        map.remove(key);
        return true;
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public void clear() {
        map.clear();
    }
}
