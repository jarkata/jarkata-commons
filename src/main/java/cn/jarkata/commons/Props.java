package cn.jarkata.commons;

import cn.jarkata.commons.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class Props {

    private static final Logger logger = LoggerFactory.getLogger(Props.class);

    public static Properties create(String filename) {
        Properties properties = new Properties();
        try {
            InputStream stream = FileUtils.getStream(filename);
            properties.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
        } catch (Exception ex) {
            logger.info("{} Not Found", filename, ex);
        }
        return properties;
    }

    public static Map<String, String> createMap(String filename) {
        Map<String, String> dataMap = new HashMap<>();
        Properties properties = create(filename);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String entryKey = Objects.toString(entry.getKey(), null);
            if (Objects.isNull(entryKey)) {
                continue;
            }
            dataMap.put(entryKey, properties.getProperty(entryKey, ""));
        }
        return dataMap;
    }
}
