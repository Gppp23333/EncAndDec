import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EncryptionDecryptionConfigurationNeedParamNameHandler {
    MontoyaApi api;
    Logging logging;

    public EncryptionDecryptionConfigurationNeedParamNameHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public List<String> EncryptionDecryptionConfigurationNeedParamName(String configPropertiesParentFieldName, String encryptionAlgorithmProperty, String encryptionAlgorithm, String encryptionAlgorithmMode, String encryptionAlgorithmPadding, Boolean isDecrypt, String[] decryptEncryptMenuName, Properties algorithmConfigProperties) {

        List<String> paramNamesList = new ArrayList<>();

        // 检查 decryptEncryptMenuName 是否足够长
        if (decryptEncryptMenuName == null || decryptEncryptMenuName.length < 2) {
            throw new IllegalArgumentException("decryptEncryptMenuName array should have at least 2 elements.");
        }

        // 选择要使用的菜单项
        String menuName = isDecrypt ? decryptEncryptMenuName[0] : decryptEncryptMenuName[1];

        // 构建属性键
        String result = String.join(".", configPropertiesParentFieldName, encryptionAlgorithmProperty, encryptionAlgorithm, encryptionAlgorithmMode, encryptionAlgorithmPadding, menuName);

        // 获取配置值
        String EncryptionDecryptionConfigurationNeedParamNames = algorithmConfigProperties.getProperty(result);

        // 如果配置值存在且不为空，拆分并加入 paramNamesList
        if (EncryptionDecryptionConfigurationNeedParamNames != null && !EncryptionDecryptionConfigurationNeedParamNames.isEmpty()) {
            paramNamesList.addAll(Arrays.asList(EncryptionDecryptionConfigurationNeedParamNames.split(",")));
        }

        return paramNamesList;
    }
}
