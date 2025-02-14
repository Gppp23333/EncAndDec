import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.HttpResponseEditor;

import javax.swing.*;

public class ReadOnlyEditorDisplayHandler {
    MontoyaApi api;
    Logging logging;

    public ReadOnlyEditorDisplayHandler(MontoyaApi api) {
        this.api = api;
        this.logging = api.logging();
    }

    public void ReadOnlyEditorDisplay(ByteArray editedRequestResponseByteArray) {

        // 创建只读编辑器
        // 如果不可编辑，显示结果在自定义的只读编辑器中
        HttpResponseEditor readOnlyEditor = api.userInterface().createHttpResponseEditor(EditorOptions.READ_ONLY);
        readOnlyEditor.setResponse(HttpResponse.httpResponse(editedRequestResponseByteArray));

        // 使用弹出窗口展示只读编辑器
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Read-Only Editor");
            //System.setProperty("file.encoding","UTF-8");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(readOnlyEditor.uiComponent());
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
