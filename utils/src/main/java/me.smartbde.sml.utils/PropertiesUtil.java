package me.smartbde.sml.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
    private static final String DEFAULT_PROPERTIES = "application.properties";
    private static final Map<String, Properties> props = new HashMap<>();

    public static String prop(String key, String propfile) {
        if (props.get(propfile) == null) {
            Properties prop = new Properties();
            props.put(propfile, prop);
            FileInputStream fis = null;
            try {
                URL url = PropertiesUtil.class.getClassLoader().getResource(propfile);
                fis = new FileInputStream(url.getPath());
                prop.load(fis);
                return prop.getProperty(key);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        } else {
            return props.get(propfile).getProperty(key);
        }
    }

    public static String prop(String key) {
        return prop(key, DEFAULT_PROPERTIES);
    }
}