package algorithmtype;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Range;
import burp.api.montoya.logging.Logging;
import algorithmtype.symmetric.CustomSymmetricAlgorithmHandler;

import java.util.ArrayList;

import static burp.api.montoya.core.ByteArray.byteArray;

public class CustomAlgorithmHandler {
    MontoyaApi api;
    Logging logging;

    public CustomAlgorithmHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public ByteArray AlgorithmHandler(String algorithmType, String algorithm, String algorithmMode, String algorithmPadding, Boolean isDecrypt, ByteArray requestResponseByteArray, Range selectionOffset, ArrayList<String> paramHexValueList) {
        byte[] selectedBytes;
        // 获取选定的字节对象(注ByteArray是一个类或对象，selectedBytes.getBytes()是通过getBytes()方法获取得到字节数组值)
        if (selectionOffset == null) {
            // 如果 selectionOffset 为空，直接将 requestResponseByteArray 转为字节数组
            selectedBytes = requestResponseByteArray.getBytes();
        } else {
            // 如果 selectionOffset 不为空，取出子数组并转换为字节数组
            selectedBytes = requestResponseByteArray.subArray(selectionOffset.startIndexInclusive(), selectionOffset.endIndexExclusive()).getBytes();
        }

        byte[] handlerBytes;
        ByteArray processedByteArray = null;
        if (algorithmType.equals("symmetric")) {
            CustomSymmetricAlgorithmHandler customSymmetricAlgorithmHandler = new CustomSymmetricAlgorithmHandler(api);
            handlerBytes = customSymmetricAlgorithmHandler.SymmetricAlgorithmHandler(algorithm, algorithmMode, algorithmPadding, isDecrypt, selectedBytes, paramHexValueList);
            processedByteArray = byteArray(handlerBytes);
        } else if (algorithmType.equals("asymmetric")) {
            return null;
        }

        return processedByteArray;
    }
}
