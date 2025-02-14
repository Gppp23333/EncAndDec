import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.contextmenu.MessageEditorHttpRequestResponse;

public class CustomMenuActionHandler {
    MontoyaApi api;
    Logging logging;

    public CustomMenuActionHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public void MenuActionHandler(MessageEditorHttpRequestResponse messageEditorHttpRequestResponse, ByteArray requestResponseByteArray, Range selectionOffset, MessageEditorHttpRequestResponse.SelectionContext selectionContext, ByteArray processedByteArray) {

        // 更新HTTP内容，替换选定部分为加解密内容
        CustomUpdateHTTPContentHandler updateHTTPContentHandler = new CustomUpdateHTTPContentHandler(api);
        ByteArray editedRequestResponseByteArray = updateHTTPContentHandler.UpdateHTTPContentHandler(requestResponseByteArray, selectionOffset, processedByteArray);

        // 显示更新后的请求或响应界面
        try {
            if (selectionContext == MessageEditorHttpRequestResponse.SelectionContext.REQUEST) {
                messageEditorHttpRequestResponse.setRequest(HttpRequest.httpRequest(editedRequestResponseByteArray));
            } else {
                messageEditorHttpRequestResponse.setResponse(HttpResponse.httpResponse(editedRequestResponseByteArray));
            }
        } catch (UnsupportedOperationException ex) {
            ReadOnlyEditorDisplayHandler readOnlyEditorDisplayHandler = new ReadOnlyEditorDisplayHandler(api);
            readOnlyEditorDisplayHandler.ReadOnlyEditorDisplay(editedRequestResponseByteArray);
        }
    }
}
