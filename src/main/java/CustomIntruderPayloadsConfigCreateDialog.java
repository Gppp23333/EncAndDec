import burp.api.montoya.MontoyaApi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomIntruderPayloadsConfigCreateDialog {
    MontoyaApi api;
    private final ArrayList<String> paramHexValueList = new ArrayList<>();

    public CustomIntruderPayloadsConfigCreateDialog(MontoyaApi api) {
        this.api = api;
    }

    public ArrayList<String> IntruderPayloadsConfigCreateDialog() {
        List<JTextField> textFieldsList = new ArrayList<>();

        // 创建模态对话框
        JDialog configEditorDialog = new JDialog((Frame) null, "IntruderPayloadsPrefixSuffixEditor", true);
        configEditorDialog.setSize(560, 315);
        configEditorDialog.setResizable(true);

        // 创建JPanel并使用BoxLayout来垂直排列组件
        JPanel configEditorJPanel = new JPanel();
        configEditorJPanel.setLayout(new BoxLayout(configEditorJPanel, BoxLayout.Y_AXIS));

        // 创建标签
        JLabel prefixLabel = new JLabel("Prefix");
        configEditorJPanel.add(prefixLabel);

        // 创建文本框
        JTextField prefixTextField = new JTextField();
        textFieldsList.add(prefixTextField);
        configEditorJPanel.add(prefixTextField);

        // 创建标签
        JLabel suffixLabel = new JLabel("Suffix");
        configEditorJPanel.add(suffixLabel);

        // 创建文本框
        JTextField suffixTextField = new JTextField();
        textFieldsList.add(suffixTextField);
        configEditorJPanel.add(suffixTextField);

        JButton setConfigButton = new JButton("Create");
        configEditorJPanel.add(setConfigButton);

        // 按钮点击事件
        setConfigButton.addActionListener(e -> {
            paramHexValueList.clear(); // 清空旧数据
            for (JTextField textField : textFieldsList) {
                String input = textField.getText().trim();
                paramHexValueList.add(input);
            }

            // 关闭对话框
            configEditorDialog.dispose();
        });

        // 将 JPanel 添加到对话框并设置窗口居中并显示
        configEditorDialog.add(configEditorJPanel);
        configEditorDialog.setLocationRelativeTo(null);
        configEditorDialog.setVisible(true);

        return paramHexValueList;
    }
}
