import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.intruder.PayloadData;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.intruder.PayloadProcessor;
import burp.api.montoya.logging.Logging;
import algorithmtype.CustomAlgorithmHandler;

import java.util.ArrayList;

public class CustomIntruderPayloadsProcessor implements PayloadProcessor {
    MontoyaApi api;
    Logging logging;
    String INPUT_PREFIX;
    String INPUT_SUFFIX;
    String algorithmType;
    String algorithm;
    String algorithmMode;
    String algorithmPadding;
    ArrayList<String> paramHexValueList;

    public CustomIntruderPayloadsProcessor(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }


    @Override
    public String displayName() {
        return algorithm + "/" + algorithmMode + "/" + algorithmPadding;
    }

    @Override
    public PayloadProcessingResult processPayload(PayloadData payloadData) {

        // 插入载体字节对象
        ByteArray payloadDataByteArray = payloadData.insertionPoint().baseValue();

        // 对插入载体字节对象进行解密，还原成字符串
        String dataParameter = inputDecryptionDecode(payloadDataByteArray);

        // 获取还原字符串（前缀）
        String prefix = findPrefix(dataParameter);
        if (prefix == null) {
            //返回当前传入载体的字符串
            return PayloadProcessingResult.usePayload(payloadData.currentPayload());
        }
        // 获取还原字符串（后缀）
        String suffix = findSuffix(dataParameter);
        if (suffix == null) {
            //返回当前传入载体的字符串
            return PayloadProcessingResult.usePayload(payloadData.currentPayload());
        }

        // 还原已重建的字节对象
        String rebuiltDataParameter = prefix + payloadData.currentPayload() + suffix;

        ByteArray rebuiltDataParameterByteArray = inputEncryptionEncode(rebuiltDataParameter);

        return PayloadProcessingResult.usePayload(rebuiltDataParameterByteArray);

    }

    private String findPrefix(String dataParameter) {
        if (dataParameter == null || dataParameter.isEmpty()) {
            return null;
        }

        int start = dataParameter.indexOf(INPUT_PREFIX);
        if (start == -1) {
            return null; // 找不到前缀，返回空字符串，避免 NullPointerException
        }

        // 计算 prefix 结束位置
        start += INPUT_PREFIX.length();
        if (start > dataParameter.length()) {
            return null; // 避免越界
        }

        // 返回前缀部分
        return dataParameter.substring(0, start);
    }

    private String findSuffix(String dataParameter) {
        if (dataParameter == null || dataParameter.isEmpty()) {
            return null;
        }

        int start = dataParameter.indexOf(INPUT_PREFIX);
        if (start == -1) {
            return null; // 没找到前缀，返回空
        }

        // 从前缀后面开始找后缀，避免匹配前缀本身
        int end = dataParameter.indexOf(INPUT_SUFFIX, start + INPUT_PREFIX.length());
        api.logging().logToOutput("start: " + start + ", end: " + end);

        if (end == -1) {
            return null; // 没找到后缀，返回空
        }

        api.logging().logToOutput(dataParameter);
        api.logging().logToOutput("Suffix: " + dataParameter.substring(end));

        return dataParameter.substring(end);
    }


    public void inputPrefixSuffix(ArrayList<String> inputPrefixSuffixArrayList) {
        String inputPrefix = inputPrefixSuffixArrayList.get(0);
        String inputSuffix = inputPrefixSuffixArrayList.get(1);
        INPUT_PREFIX = inputPrefix;
        INPUT_SUFFIX = inputSuffix;
    }

    public void setParamHexValueList(ArrayList<String> paramHexValueList) {
        // 在传递之前移除最后一个元素
        if (paramHexValueList != null && !paramHexValueList.isEmpty()) {
            paramHexValueList.remove(paramHexValueList.size() - 1); // 移除最后一个元素
        }

        // 将修改后的 paramHexValueList 赋值给类的成员变量
        this.paramHexValueList = paramHexValueList;
    }

    public void inputAlgorithmParameters(String algorithmType, String algorithm, String algorithmMode, String algorithmPadding, ArrayList<String> paramHexValueList) {

        this.algorithmType = algorithmType;
        this.algorithm = algorithm;
        this.algorithmMode = algorithmMode;
        this.algorithmPadding = algorithmPadding;

        setParamHexValueList(paramHexValueList);
    }

    public String inputDecryptionDecode(ByteArray payloadDataByteArray) {
        Boolean isDecrypt = true;
        CustomAlgorithmHandler customAlgorithmHandler = new CustomAlgorithmHandler(api);
        ByteArray processedByteArray = customAlgorithmHandler.AlgorithmHandler(algorithmType, algorithm, algorithmMode, algorithmPadding, isDecrypt, payloadDataByteArray, null, paramHexValueList);
        return processedByteArray.toString();
    }

    public ByteArray inputEncryptionEncode(String rebuiltDataParameter) {
        Boolean isDecrypt = false;
        ByteArray rebuiltDataParameterByteArray = ByteArray.byteArray(rebuiltDataParameter);
        CustomAlgorithmHandler customAlgorithmHandler = new CustomAlgorithmHandler(api);
        return customAlgorithmHandler.AlgorithmHandler(algorithmType, algorithm, algorithmMode, algorithmPadding, isDecrypt, rebuiltDataParameterByteArray, null, paramHexValueList);
    }
}
