package com.bj58.zhuanzhuan.plugindemo.action;

import cn.hutool.core.util.RandomUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class TestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String randomString = RandomUtil.randomString(10);
        Messages.showInfoMessage(randomString, "成功");

    }
}
