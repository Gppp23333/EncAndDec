import burp.api.montoya.MontoyaApi;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CustomDynamicComponentHandler {
    private final JPanel jPanel;
    private final List<Component> dynamicComponents;
    private String selectedAlgorithmType;
    private String selectedAlgorithm;
    private String selectedAlgorithmMode;
    private String selectedAlgorithmPadding;
    private final ArrayList<String> paramValueArrayList;
    private final GridBagConstraints gbc;
    private final int componentWidth;
    private final int componentHeight;
    MontoyaApi api;  // 假设你使用的 API 类
    private final String configPropertiesParentFieldName;
    String[] decryptEncryptMenuName;
    Properties algorithmConfigProperties;
    private final CustomContextMenuItemsProvider customContextMenuItemsProvider;

    public CustomDynamicComponentHandler(JPanel jPanel, List<Component> dynamicComponents,
                                         ArrayList<String> paramValueArrayList, GridBagConstraints gbc,
                                         int componentWidth, int componentHeight, MontoyaApi api,
                                         String ConfigPropertiesParentFieldName, String[] decryptEncryptMenuName,
                                         Properties algorithmConfigProperties) {
        this.jPanel = jPanel;
        this.dynamicComponents = dynamicComponents;
        this.paramValueArrayList = paramValueArrayList;
        this.gbc = gbc;
        this.componentWidth = componentWidth;
        this.componentHeight = componentHeight;
        this.api = api;
        this.configPropertiesParentFieldName = ConfigPropertiesParentFieldName;
        this.decryptEncryptMenuName = decryptEncryptMenuName;
        this.algorithmConfigProperties = algorithmConfigProperties;
        this.customContextMenuItemsProvider = new CustomContextMenuItemsProvider(api);
    }

    public void handleButtonClick(String selectedAlgorithmType, String selectedAlgorithm,
                                  String selectedAlgorithmMode, String selectedAlgorithmPadding) {
        this.selectedAlgorithmType = selectedAlgorithmType;
        this.selectedAlgorithm = selectedAlgorithm;
        this.selectedAlgorithmMode = selectedAlgorithmMode;
        this.selectedAlgorithmPadding = selectedAlgorithmPadding;

        // **清除旧组件**
        for (Component comp : dynamicComponents) {
            jPanel.remove(comp);
        }
        dynamicComponents.clear();
        paramValueArrayList.clear();

        // 获取新参数
        EncryptionDecryptionConfigurationNeedParamNameHandler encryptionDecryptionConfigurationNeedParamNameHandler =
                new EncryptionDecryptionConfigurationNeedParamNameHandler(api);
        List<String> needParamNames = encryptionDecryptionConfigurationNeedParamNameHandler
                .EncryptionDecryptionConfigurationNeedParamName(
                        configPropertiesParentFieldName, selectedAlgorithmType, selectedAlgorithm,
                        selectedAlgorithmMode, selectedAlgorithmPadding, true,
                        decryptEncryptMenuName, algorithmConfigProperties
                );

        EncryptionDecryptionConfigurationParamNameArrayHandler paramNameArrayHandler =
                new EncryptionDecryptionConfigurationParamNameArrayHandler(api);
        String[] newParamNames = paramNameArrayHandler
                .EncryptionDecryptionConfigurationParamNameArray(needParamNames);

        Boolean[] options = {false, true};
        List<JTextField> textFieldsList = new ArrayList<>();
        List<JComboBox<Boolean>> comboBoxsList = new ArrayList<>();

        // **动态生成组件**
        for (int i = 0; i < newParamNames.length; i++) {
            gbc.gridx = 0; // 每行起始列
            if (i % 2 == 0) {
                gbc.gridy++;  // 递增行数，确保每对组件换行
            }
            if (i < needParamNames.size() * 2) {
                if (i % 2 == 0) {
                    JLabel label = new JLabel(newParamNames[i]);
                    jPanel.add(label, gbc);
                    dynamicComponents.add(label);
                } else {
                    gbc.gridx = 1;
                    JTextField textField = new JTextField();
                    textField.setPreferredSize(new Dimension(componentWidth, componentHeight)); // 固定宽度
                    jPanel.add(textField, gbc);
                    textFieldsList.add(textField);
                    dynamicComponents.add(textField);
                }
            } else {
                if (i == needParamNames.size() * 2) {
                    JLabel label1 = new JLabel(newParamNames[i]);
                    jPanel.add(label1, gbc);
                    dynamicComponents.add(label1);
                } else if (i == needParamNames.size() * 2 + 1) {
                    gbc.gridx = 1;
                    JComboBox<Boolean> booleanJComboBox = new JComboBox<>(options);
                    booleanJComboBox.setPreferredSize(new Dimension(componentWidth, componentHeight));
                    booleanJComboBox.setSelectedItem(true);
                    comboBoxsList.add(booleanJComboBox);
                    jPanel.add(booleanJComboBox, gbc);
                    dynamicComponents.add(booleanJComboBox);
                } else if (i == needParamNames.size() * 2 + 2) {
                    JLabel label2 = new JLabel(newParamNames[i]);
                    jPanel.add(label2, gbc);
                    dynamicComponents.add(label2);
                } else if (i == needParamNames.size() * 2 + 3) {
                    gbc.gridx = 1;
                    JComboBox<Boolean> booleanJComboBox = new JComboBox<>(options);
                    booleanJComboBox.setPreferredSize(new Dimension(componentWidth, componentHeight));
                    booleanJComboBox.setSelectedItem(false);
                    comboBoxsList.add(booleanJComboBox);
                    jPanel.add(booleanJComboBox, gbc);
                    dynamicComponents.add(booleanJComboBox);
                } else if (i == needParamNames.size() * 2 + 4) {
                    gbc.gridy++;
                    JButton setConfigButton = new JButton(newParamNames[i]);
                    jPanel.add(setConfigButton, gbc);
                    dynamicComponents.add(setConfigButton);

                    setConfigButton.addActionListener(l -> handleButtonClick(textFieldsList, comboBoxsList));

                }
            }
        }

        // **刷新 UI**
        jPanel.revalidate();
        jPanel.repaint();
    }

    private void handleButtonClick(List<JTextField> textFieldsList, List<JComboBox<Boolean>> comboBoxsList) {
        // 获取算法配置信息
        ArrayList<String> algorithmConfigInformation = algorithmConfigSave();
        paramValueArrayList.clear();
        // 确保 algorithmConfigInformation 不为空且包含有效数据
        if (algorithmConfigInformation == null || algorithmConfigInformation.size() < 4) {
            api.logging().logToError("Invalid algorithm config information.");
            return;
        }

        // 处理 textFields 和 comboBoxes 中的值，并加入 paramValueArrayList
        for (JTextField textField : textFieldsList) {
            paramValueArrayList.add(textField.getText().trim());
        }

        for (JComboBox<Boolean> jComboBox : comboBoxsList) {
            paramValueArrayList.add(String.valueOf(jComboBox.getSelectedItem()));
        }


        //注册到Intruder模块
        if (Boolean.parseBoolean(paramValueArrayList.get(paramValueArrayList.size() - 1))) {

            CustomIntruderPayloadsProcessor customIntruderPayloadsProcessor = getCustomIntruderPayloadsProcessor();
            customIntruderPayloadsProcessor.inputAlgorithmParameters(algorithmConfigInformation.get(0), algorithmConfigInformation.get(1), algorithmConfigInformation.get(2), algorithmConfigInformation.get(3), paramValueArrayList);
            api.intruder().registerPayloadProcessor(customIntruderPayloadsProcessor);
        } else {
            paramValueArrayList.remove(paramValueArrayList.size() - 1); // 移除最后一个元素
        }

        // 设置菜单项
        customContextMenuItemsProvider.algorithmConfigInformation(algorithmConfigInformation);
        customContextMenuItemsProvider.algorithmParamValue(paramValueArrayList);

        // 注册菜单项
        api.userInterface().registerContextMenuItemsProvider(customContextMenuItemsProvider);
    }

    public ArrayList<String> algorithmConfigSave() {
        return new ArrayList<>(Arrays.asList(
                selectedAlgorithmType,
                selectedAlgorithm,
                selectedAlgorithmMode,
                selectedAlgorithmPadding
        ));
    }

    private CustomIntruderPayloadsProcessor getCustomIntruderPayloadsProcessor() {
        CustomIntruderPayloadsConfigCreateDialog customIntruderPayloadsConfigCreateDialog = new CustomIntruderPayloadsConfigCreateDialog(api);
        ArrayList<String> inputPrefixSuffix = customIntruderPayloadsConfigCreateDialog.IntruderPayloadsConfigCreateDialog();

        CustomIntruderPayloadsProcessor customIntruderPayloadsProcessor = new CustomIntruderPayloadsProcessor(api);
        customIntruderPayloadsProcessor.inputPrefixSuffix(inputPrefixSuffix);
        return customIntruderPayloadsProcessor;
    }

    public ArrayList<String> getParamValueArrayList() {
        return paramValueArrayList;
    }
}

