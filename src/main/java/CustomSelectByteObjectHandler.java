import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.contextmenu.MessageEditorHttpRequestResponse;

public class CustomSelectByteObjectHandler {
    MontoyaApi api;
    Logging logging;

    public CustomSelectByteObjectHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public ByteArray SelectByteObjectHandler(HttpRequestResponse httpRequestResponse, MessageEditorHttpRequestResponse.SelectionContext selectionContext) {
        // 获取请求或响应的字节对象(注ByteArray是一个类或对象，selectedBytes.getBytes()是通过getBytes()方法获取得到ascii码字节数组值)
        ByteArray requestResponseByteArray;
        if (selectionContext == MessageEditorHttpRequestResponse.SelectionContext.REQUEST) {
            requestResponseByteArray = httpRequestResponse.request().toByteArray();
        } else {
            requestResponseByteArray = httpRequestResponse.response().toByteArray();
        }

        return requestResponseByteArray;
    }
}
