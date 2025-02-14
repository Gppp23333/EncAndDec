import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Range;
import burp.api.montoya.logging.Logging;

public class CustomUpdateHTTPContentHandler {
    MontoyaApi api;
    Logging logging;

    public CustomUpdateHTTPContentHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public ByteArray UpdateHTTPContentHandler(ByteArray requestResponseByteArray, Range selectionOffset, ByteArray processedByteArray) {
        // 更新HTTP内容，替换选定部分为加解密内容
        ByteArray editedRequestResponseBytes = requestResponseByteArray.subArray(0, selectionOffset.startIndexInclusive());
        // 因为withAppended()返回的是一个新的Bytes对象，因此editedRequestResponseBytes需要被重新赋值，更新editedRequestResponseBytes
        editedRequestResponseBytes = editedRequestResponseBytes.withAppended(processedByteArray);
        if (selectionOffset.endIndexExclusive() < requestResponseByteArray.length()) {
            editedRequestResponseBytes = editedRequestResponseBytes.withAppended(
                    requestResponseByteArray.subArray(selectionOffset.endIndexExclusive(), requestResponseByteArray.length()));
        }
        return editedRequestResponseBytes;
    }
}
