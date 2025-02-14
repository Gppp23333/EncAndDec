import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import java.util.List;

public class EncryptionDecryptionConfigurationParamNameArrayHandler {
    MontoyaApi api;
    Logging logging;

    public EncryptionDecryptionConfigurationParamNameArrayHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public String[] EncryptionDecryptionConfigurationParamNameArray(List<String> paramNames) {
        // 创建一个新的数组，将每个原数组元素后添加一个空字符串，并扩展新数组以容纳5个新元素
        String[] newParamNames = new String[paramNames.size() * 2 + 5]; // 使用 paramNames.size() 获取 List 的大小

        // 将原 List 的元素和空字符串添加到新的数组中
        for (int i = 0; i < paramNames.size(); i++) {
            newParamNames[i * 2] = paramNames.get(i);    // 复制原 List 元素
            newParamNames[i * 2 + 1] = "";               // 为每个元素添加一个空字符串
        }

        // 填充最后 5 个新元素
        int baseIndex = paramNames.size() * 2; // 新元素开始的索引位置
        newParamNames[baseIndex] = "isBase64";
        newParamNames[baseIndex + 1] = "";  // 用于下拉框
        newParamNames[baseIndex + 2] = "toIntruder";
        newParamNames[baseIndex + 3] = "";  // 用于下拉框
        newParamNames[baseIndex + 4] = "ConfirmParameters";  // 用于按钮

        return newParamNames;
    }
}
