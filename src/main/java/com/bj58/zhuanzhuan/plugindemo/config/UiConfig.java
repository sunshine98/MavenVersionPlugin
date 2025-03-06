package com.bj58.zhuanzhuan.plugindemo.config;

import cn.hutool.core.util.ObjectUtil;
import com.bj58.zhuanzhuan.plugindemo.gui.TestGUI;
import com.bj58.zhuanzhuan.plugindemo.persistent.MyDemoState;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts.ConfigurableName;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UiConfig implements Configurable {

    private TestGUI testGUI = new TestGUI();
    private MyDemoState service;
    {
        service = ApplicationManager.getApplication().getService(MyDemoState.class);
    }

    @Override
    public @ConfigurableName String getDisplayName() {
        return "lyj测试配置";
    }

    @Override
    public @Nullable JComponent createComponent() {
        testGUI.getUsername().setText(service.username);
        testGUI.getPassword().setText(service.password);
        JPanel myPanel = testGUI.getMyPanel();
        return myPanel;
    }

    @Override
    public boolean isModified() {
        boolean usernameNot = ObjectUtil.notEqual(service.username, testGUI.getUsername());
        boolean passwordNot = ObjectUtil.notEqual(service.password, testGUI.getPassword());
        return usernameNot|passwordNot;
    }

    @Override
    public void apply() throws ConfigurationException {
        service.username = testGUI.getUsername().getText();
        service.password = testGUI.getPassword().getText();
    }


}
