package com.bj58.zhuanzhuan.plugindemo.config;

import com.intellij.ide.plugins.newui.HiDPIPluginLogoIcon;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface CommonConfig {

    Icon logo= IconLoader.getIcon("META-INF/pluginIcon.svg", HiDPIPluginLogoIcon.class);
}
