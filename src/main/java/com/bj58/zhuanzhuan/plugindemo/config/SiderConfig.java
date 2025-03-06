package com.bj58.zhuanzhuan.plugindemo.config;

import com.bj58.zhuanzhuan.plugindemo.gui.TestGUI;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SiderConfig implements ToolWindowFactory {
    private TestGUI testGUI;
    private JComponent panel;

    {
        testGUI = new TestGUI();
        panel= testGUI.getMyPanel();
    }
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        toolWindow.getContentManager().addContent(ContentFactory.getInstance().createContent(panel
        ,"转转版本迭代工具"
        ,false));
    }
}
