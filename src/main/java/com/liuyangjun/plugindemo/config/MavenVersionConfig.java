package com.liuyangjun.plugindemo.config;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.liuyangjun.plugindemo.gui.MavenVersionConfigForm;
import com.liuyangjun.plugindemo.persistent.MavenVersionState;
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
        return mavenVersionConfigForm.getConfigPanel();
    }

    @Override
    public boolean isModified() {
        boolean hostChange = ObjectUtil.notEqual(mavenVersionState.getHost(), mavenVersionConfigForm.getHostText().getText());
        boolean autoCopyChange = ObjectUtil.notEqual(mavenVersionState.getNeeedAutoCopy(), mavenVersionConfigForm.getVersionAutoCopyButton().isSelected());
        return hostChange || autoCopyChange;
    }

    @Override
    public void reset() {
        mavenVersionConfigForm.getHostText().setText(mavenVersionState.getHost());
        mavenVersionConfigForm.getVersionAutoCopyButton().setSelected(mavenVersionState.getNeeedAutoCopy());
    }

    @Override
    public void apply() throws ConfigurationException {
        String text = mavenVersionConfigForm.getHostText().getText();
        if (!Validator.isUrl(text)) {
            throw new ConfigurationException("请输入正确的host地址！");
        }
        mavenVersionState.setHost(text);
        boolean selected = mavenVersionConfigForm.getVersionAutoCopyButton().isSelected();
        mavenVersionState.setNeeedAutoCopy(selected);

    }
}
