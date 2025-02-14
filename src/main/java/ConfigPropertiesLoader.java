import burp.api.montoya.MontoyaApi;

import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesLoader {

    //加载配置文件
    public Properties loadConfig(MontoyaApi api) {
        Properties algorithmConfigProperties = new Properties();
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("EncDecAlgorithm.properties");
            if (resourceAsStream == null) {
                api.logging().logToError("Unable to find EncDecAlgorithm.properties");
                return null;
            }
            algorithmConfigProperties.load(resourceAsStream);
        } catch (Exception e) {
            api.logging().logToError(e);
        }
        return algorithmConfigProperties;
    }
}
