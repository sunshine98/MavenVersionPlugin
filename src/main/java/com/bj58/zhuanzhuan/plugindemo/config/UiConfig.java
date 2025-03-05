package com.bj58.zhuanzhuan.plugindemo.config;

import com.bj58.zhuanzhuan.plugindemo.gui.TestGUI;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts.ConfigurableName;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UiConfig implements Configurable {

    private TestGUI testGUI = new TestGUI();

    @Override
    public @ConfigurableName String getDisplayName() {
        return "liuyangjun-getDisplayName";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return testGUI.getMyPanel();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
