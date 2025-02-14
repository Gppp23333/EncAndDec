import algorithmtype.CustomAlgorithmHandler;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import burp.api.montoya.ui.contextmenu.MessageEditorHttpRequestResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomContextMenuItemsProvider implements ContextMenuItemsProvider {
    MontoyaApi api;
    Logging logging;
    String[] decryptEncryptMenuName = {"Decrypt", "Encrypt"};
    String algorithmType = null;
    String algorithm = null;
    String algorithmMode = null;
    String algorithmPadding = null;
    private ArrayList<String> paramValueArrayList = new ArrayList<>();
    // 初始化一个包含上下文菜单项的空列表组件
    private final ArrayList<Component> menuItems = new ArrayList<>();

    public CustomContextMenuItemsProvider(MontoyaApi api) {
        // 保存对MontoyaApi对象的引用
        this.api = api;
        // 保存对MontoyaApi的日志记录对象的引用
        this.logging = api.logging();
    }


    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event) {
        menuItems.clear();
        Optional<MessageEditorHttpRequestResponse> optionalMessageEditorHttpRequestResponse = event.messageEditorRequestResponse();
        // 仅当在request/response对象上创建菜单时才创建菜单
        if (optionalMessageEditorHttpRequestResponse.isPresent()) {
            MessageEditorHttpRequestResponse messageEditorHttpRequestResponse = optionalMessageEditorHttpRequestResponse.get();
            // 获取request/response对象
            HttpRequestResponse httpRequestResponse = messageEditorHttpRequestResponse.requestResponse();
            // 仅当request/response具有选中部分时才创建菜单
            if (messageEditorHttpRequestResponse.selectionOffsets().isPresent()) {
                Range selectionOffset = messageEditorHttpRequestResponse.selectionOffsets().get();
                MessageEditorHttpRequestResponse.SelectionContext selectionContext = messageEditorHttpRequestResponse.selectionContext();

                JMenuItem decryptMenuItem = new JMenuItem(decryptEncryptMenuName[0]);
                JMenuItem encryptMenuItem = new JMenuItem(decryptEncryptMenuName[1]);

                // 为菜单项添加事件监听器
                ActionListener actionListener = actionEvent -> {
                    boolean isDecrypt = actionEvent.getSource() == decryptMenuItem;

                    SwingUtilities.invokeLater(() -> {
                        // 处理请求或响应的字节对象
                        CustomSelectByteObjectHandler customSelectByteObjectHandler = new CustomSelectByteObjectHandler(api);
                        ByteArray requestResponseByteArray = customSelectByteObjectHandler.SelectByteObjectHandler(httpRequestResponse, selectionContext);

                        // 加解密选定数据的字节对象
                        CustomAlgorithmHandler customAlgorithmHandler = new CustomAlgorithmHandler(api);
                        ByteArray processedByteArray = customAlgorithmHandler.AlgorithmHandler(algorithmType, algorithm, algorithmMode, algorithmPadding, isDecrypt, requestResponseByteArray, selectionOffset, paramValueArrayList);

                        // 处理相关的加解密并同步处理后的数据
                        CustomMenuActionHandler customMenuActionHandler = new CustomMenuActionHandler(api);
                        customMenuActionHandler.MenuActionHandler(messageEditorHttpRequestResponse, requestResponseByteArray, selectionOffset, selectionContext, processedByteArray);
                    });
                };

                // 将监听器绑定到菜单项
                decryptMenuItem.addActionListener(actionListener);
                encryptMenuItem.addActionListener(actionListener);
                // 添加 Decrypt 和 Encrypt 到 主菜单列表
                menuItems.add(decryptMenuItem);
                menuItems.add(encryptMenuItem);

            }
        }

        return menuItems;
    }

    public void algorithmConfigInformation(ArrayList<String> algorithmConfigInformation) {
        this.algorithmType = algorithmConfigInformation.get(0);
        this.algorithm = algorithmConfigInformation.get(1);
        this.algorithmMode = algorithmConfigInformation.get(2);
        this.algorithmPadding = algorithmConfigInformation.get(3);
    }

    public void algorithmParamValue(ArrayList<String> paramValueArrayList) {
        this.paramValueArrayList = paramValueArrayList;
    }
}
