package com.bj58.zhuanzhuan.plugindemo.config;

import cn.hutool.core.util.ObjectUtil;
import com.bj58.zhuanzhuan.plugindemo.gui.MavenVersionConfigForm;
import com.bj58.zhuanzhuan.plugindemo.persistent.MavenVersionState;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts.ConfigurableName;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MavenVersionConfig implements Configurable {
    private MavenVersionConfigForm mavenVersionConfigForm;
    private MavenVersionState mavenVersionState;

    {
        mavenVersionConfigForm = new MavenVersionConfigForm();
        mavenVersionState = ApplicationManager.getApplication().getService(MavenVersionState.class);
    }

    @Override
    public @ConfigurableName String getDisplayName() {
        return "Maven Version AutoBumper";
    }

    @Override
    public @Nullable JComponent createComponent() {
        String host = mavenVersionState.getHost();
        JPanel configPanel = mavenVersionConfigForm.getConfigPanel();
        mavenVersionConfigForm.getHostText().setText(host);
        return configPanel;
    }

    @Override
    public boolean isModified() {
        return ObjectUtil.notEqual(mavenVersionState.getHost(), mavenVersionConfigForm.getHostText().getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        mavenVersionState.setHost(mavenVersionConfigForm.getHostText().getText());
    }
}
