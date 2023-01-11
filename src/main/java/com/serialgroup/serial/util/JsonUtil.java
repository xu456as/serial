package com.serialgroup.serial.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static volatile ObjectMapper instance = null;

    public static ObjectMapper getInstance() {
        if (instance == null) {
            synchronized (JsonUtil.class) {
                if (instance == null) {
                    instance = new ObjectMapper();
                }
            }
        }
        return instance;
    }

    public static String writeObjectAsString(Object object) throws Exception {
        return getInstance().writeValueAsString(object);
    }

}
