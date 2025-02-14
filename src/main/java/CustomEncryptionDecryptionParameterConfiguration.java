import burp.api.montoya.MontoyaApi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomEncryptionDecryptionParameterConfiguration extends JComponent {
    MontoyaApi api;
    String[] decryptEncryptMenuName = {"Decrypt", "Encrypt"};
    Properties algorithmConfigProperties;
    String configPropertiesParentFieldName;

    private String selectedAlgorithmType;
    private String selectedAlgorithm;
    private String selectedAlgorithmMode;
    private String selectedAlgorithmPadding;
    private final ArrayList<String> paramValueArrayList = new ArrayList<>();
    private CustomDynamicComponentHandler customDynamicComponentHandler;
    // 常量
    private static final String ALGORITHM_TYPE_KEY = "AlgorithmType:";
    private static final String ALGORITHM_KEY = "Algorithm:";
    private static final String ALGORITHM_MODE_KEY = "AlgorithmMode:";
    private static final String ALGORITHM_PADDING_KEY = "AlgorithmPadding:";
    private static final String ALGORITHM_BUTTON_KEY = "ConfirmAlgorithm";
    // 存储动态创建的组件，避免每次清空整个 jPanel
    private final List<Component> dynamicComponents = new ArrayList<>();
    private static final int componentWidth = 168;
    private static final int componentHeight = 28;


    public CustomEncryptionDecryptionParameterConfiguration(MontoyaApi api, Properties algorithmConfigProperties, String configPropertiesParentFieldName) {
        this.api = api;
        this.algorithmConfigProperties = algorithmConfigProperties;
        this.configPropertiesParentFieldName = configPropertiesParentFieldName;


        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // 创建 JLabel 和 JComboBox 显示算法类型
        JLabel algorithmTypeJLabel = new JLabel(ALGORITHM_TYPE_KEY);
        JLabel algorithmsJLabel = new JLabel(ALGORITHM_KEY);
        JLabel algorithmModesJLabel = new JLabel(ALGORITHM_MODE_KEY);
        JLabel algorithmPaddingsJLabel = new JLabel(ALGORITHM_PADDING_KEY);

        JComboBox<String> algorithmTypeJComboBox = new JComboBox<>();
        algorithmTypeJComboBox.setPreferredSize(new Dimension(componentWidth, componentHeight));
        JComboBox<String> algorithmsJComboBox = new JComboBox<>();
        algorithmsJComboBox.setPreferredSize(new Dimension(componentWidth, componentHeight));
        JComboBox<String> algorithmModesJComboBox = new JComboBox<>();
        algorithmModesJComboBox.setPreferredSize(new Dimension(componentWidth, componentHeight));
        JComboBox<String> algorithmPaddingsJComboBox = new JComboBox<>();

        JButton jConfirmButton = new JButton(ALGORITHM_BUTTON_KEY);

        // 默认隐藏下拉框
        setComboBoxEnabled(algorithmsJComboBox, false);
        setComboBoxEnabled(algorithmModesJComboBox, false);
        setComboBoxEnabled(algorithmPaddingsJComboBox, false);

        // 初始化算法类型
        initializeAlgorithmTypeComboBox(algorithmTypeJComboBox);
        addActionListeners(algorithmTypeJComboBox, algorithmsJComboBox, algorithmModesJComboBox, algorithmPaddingsJComboBox);

        jConfirmButton.addActionListener(e -> {
            customDynamicComponentHandler = new CustomDynamicComponentHandler(jPanel, dynamicComponents, paramValueArrayList, gbc, componentWidth, componentHeight, api, configPropertiesParentFieldName, decryptEncryptMenuName, algorithmConfigProperties);
            customDynamicComponentHandler.handleButtonClick(selectedAlgorithmType, selectedAlgorithm, selectedAlgorithmMode, selectedAlgorithmPadding);
        });

        // **初始化固定组件**
        jPanel.add(algorithmTypeJLabel, gbc);
        gbc.gridx = 1;
        jPanel.add(algorithmTypeJComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        jPanel.add(algorithmsJLabel, gbc);
        gbc.gridx = 1;
        jPanel.add(algorithmsJComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        jPanel.add(algorithmModesJLabel, gbc);
        gbc.gridx = 1;
        jPanel.add(algorithmModesJComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        jPanel.add(algorithmPaddingsJLabel, gbc);
        gbc.gridx = 1;
        jPanel.add(algorithmPaddingsJComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        jPanel.add(jConfirmButton, gbc);

        add(jPanel);
    }

    private void addActionListeners(JComboBox<String> algorithmTypeJComboBox, JComboBox<String> algorithmsJComboBox, JComboBox<String> algorithmModesJComboBox, JComboBox<String> algorithmPaddingsJComboBox) {
        algorithmTypeJComboBox.addActionListener(e -> {
            selectedAlgorithmType = updateAlgorithms(algorithmTypeJComboBox, algorithmsJComboBox, algorithmModesJComboBox, algorithmPaddingsJComboBox);
            algorithmTypeJComboBox.setPopupVisible(false);
        });

        algorithmsJComboBox.addActionListener(e -> {
            selectedAlgorithm = updateAlgorithmModes(algorithmTypeJComboBox, algorithmsJComboBox, algorithmModesJComboBox, algorithmPaddingsJComboBox);
            algorithmsJComboBox.setPopupVisible(false);
        });

        algorithmModesJComboBox.addActionListener(e -> {
            selectedAlgorithmMode = updateAlgorithmPaddings(algorithmTypeJComboBox, algorithmsJComboBox, algorithmModesJComboBox, algorithmPaddingsJComboBox);
            algorithmModesJComboBox.setPopupVisible(false);
        });

        algorithmPaddingsJComboBox.addActionListener(e -> {
            selectedAlgorithmPadding = (String) algorithmPaddingsJComboBox.getSelectedItem();
            algorithmPaddingsJComboBox.setPopupVisible(false); // 关闭下拉框
        });
    }

    // 初始化算法类型下拉框
    private void initializeAlgorithmTypeComboBox(JComboBox<String> algorithmTypeJComboBox) {
        algorithmTypeJComboBox.removeAllItems();
        String algorithmTypes = algorithmConfigProperties.getProperty(configPropertiesParentFieldName);
        for (String algorithmType : algorithmTypes.split(",")) {
            algorithmTypeJComboBox.addItem(algorithmType);  // 添加算法类型到 JComboBox
        }
    }

    // 更新算法下拉框内容
    private String updateAlgorithms(JComboBox<String> algorithmTypeJComboBox, JComboBox<String> algorithmsJComboBox, JComboBox<String> algorithmModesJComboBox, JComboBox<String> algorithmPaddingsJComboBox) {
        String selectedAlgorithmType = (String) algorithmTypeJComboBox.getSelectedItem();
        // 清空下拉框内容
        resetComboBoxContent(algorithmsJComboBox);
        resetComboBoxContent(algorithmModesJComboBox);
        resetComboBoxContent(algorithmPaddingsJComboBox);

        String algorithms = algorithmConfigProperties.getProperty(configPropertiesParentFieldName + "." + selectedAlgorithmType);
        for (String algorithm : algorithms.split(",")) {
            algorithmsJComboBox.addItem(algorithm);
        }

        setComboBoxEnabled(algorithmsJComboBox, true); // 启用算法下拉框
        return selectedAlgorithmType;
    }

    // 更新算法模式下拉框内容
    private String updateAlgorithmModes(JComboBox<String> algorithmTypeJComboBox, JComboBox<String> algorithmsJComboBox, JComboBox<String> algorithmModesJComboBox, JComboBox<String> algorithmPaddingsJComboBox) {
        String selectedAlgorithmType = (String) algorithmTypeJComboBox.getSelectedItem();
        String selectedAlgorithm = (String) algorithmsJComboBox.getSelectedItem();
        // 清空下拉框内容
        resetComboBoxContent(algorithmModesJComboBox);
        resetComboBoxContent(algorithmPaddingsJComboBox);

        String algorithmModes = algorithmConfigProperties.getProperty(configPropertiesParentFieldName + "." + selectedAlgorithmType + "." + selectedAlgorithm + ".mode");
        for (String mode : algorithmModes.split(",")) {
            algorithmModesJComboBox.addItem(mode);
        }

        setComboBoxEnabled(algorithmModesJComboBox, true); // 启用模式下拉框
        return selectedAlgorithm;
    }

    // 更新算法填充方式下拉框内容
    private String updateAlgorithmPaddings(JComboBox<String> algorithmTypeJComboBox, JComboBox<String> algorithmsJComboBox, JComboBox<String> algorithmModesJComboBox, JComboBox<String> algorithmPaddingsJComboBox) {
        String selectedAlgorithmType = (String) algorithmTypeJComboBox.getSelectedItem();
        String selectedAlgorithm = (String) algorithmsJComboBox.getSelectedItem();
        String selectedAlgorithmMode = (String) algorithmModesJComboBox.getSelectedItem();
        // 清空填充方式下拉框
        algorithmPaddingsJComboBox.removeAllItems();

        String algorithmPaddings = algorithmConfigProperties.getProperty(configPropertiesParentFieldName + "." + selectedAlgorithmType + "." + selectedAlgorithm + "." + selectedAlgorithmMode + ".padding");
        for (String padding : algorithmPaddings.split(",")) {
            algorithmPaddingsJComboBox.addItem(padding);
        }

        setComboBoxEnabled(algorithmPaddingsJComboBox, true); // 启用填充方式下拉框
        return selectedAlgorithmMode;
    }

    private void resetComboBoxContent(JComboBox<String> ComboBox) {
        ComboBox.removeAllItems();
    }

    // 设置单个 JComboBox 启用状态
    private void setComboBoxEnabled(JComboBox<String> comboBox, boolean enabled) {
        comboBox.setEnabled(enabled);
    }
}

