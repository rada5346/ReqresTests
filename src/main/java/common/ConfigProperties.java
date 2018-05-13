package common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
    public static String URL;

    public ConfigProperties() {
        try {

            Properties prop = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("test.properties");
            prop.load(input);
            URL = prop.getProperty("URL");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
