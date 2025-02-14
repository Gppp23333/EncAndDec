import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.Properties;

public class EncAndDec implements BurpExtension {

    MontoyaApi api;
    Logging logging;
    String extensionName = "JUGCrypt";
    Properties algorithmConfigProperties;
    String configPropertiesParentFieldName = "encryption.algorithm";

    @Override
    public void initialize(MontoyaApi api) {
        // 保存对MontoyaApi对象的引用
        this.api = api;

        // api.logging() 返回一个对象，我们可以用它来打印消息到stdout和stderr
        this.logging = api.logging();

        // 设置扩展名的名称
        this.api.extension().setName(extensionName);

        // 将消息打印到stdout
        this.logging.logToOutput("***" + extensionName + " plug-in loaded successfully!***");

        // 加载配置
        ConfigPropertiesLoader configPropertiesLoader = new ConfigPropertiesLoader();
        algorithmConfigProperties = configPropertiesLoader.loadConfig(api);

        CustomEncryptionDecryptionParameterConfiguration customEncryptionDecryptionParameterConfiguration = new CustomEncryptionDecryptionParameterConfiguration(api, algorithmConfigProperties, configPropertiesParentFieldName);

        api.userInterface().registerSuiteTab(extensionName + "-Configuration", customEncryptionDecryptionParameterConfiguration);
    }
}
