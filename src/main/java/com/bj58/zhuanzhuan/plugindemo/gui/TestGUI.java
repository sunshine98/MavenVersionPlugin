package com.bj58.zhuanzhuan.plugindemo.gui;

import com.intellij.openapi.ui.TextFieldWithBrowseButton;

import javax.swing.*;

public class TestGUI {
    public JTextField getUsername() {
        return username;
    }

    public JTextField getPassword() {
        return password;
    }

    public JLabel getUserNameLabel() {
        return userNameLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    private JTextField username;
    private JTextField password;
    private JLabel userNameLabel;
    private JLabel passwordLabel;

    public JPanel getMyPanel() {
        return myPanel;
    }

    private JPanel myPanel;
    private TextFieldWithBrowseButton browseButton;
}
