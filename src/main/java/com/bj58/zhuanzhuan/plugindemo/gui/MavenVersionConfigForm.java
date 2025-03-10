package com.bj58.zhuanzhuan.plugindemo.gui;

import javax.swing.*;

public class MavenVersionConfigForm {
    private JPanel configPanel;
    private JLabel hostLabel;

    public JPanel getConfigPanel() {
        return configPanel;
    }

    public JLabel getHostLabel() {
        return hostLabel;
    }

    public JTextField getHostText() {
        return hostText;
    }

    private JTextField hostText;

    public JRadioButton getVersionAutoCopyButton() {
        return versionAutoCopyButton;
    }

    private JRadioButton versionAutoCopyButton;
}
