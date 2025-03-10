package com.bj58.zhuanzhuan.plugindemo.persistent;

import cn.hutool.core.util.ObjectUtil;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
@State(name = "mavenVersionState",storages = {@Storage("mavenVersionStateConfig.xml")})
public final class MavenVersionState implements PersistentStateComponent<MavenVersionState> {

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private String host="https://nexus.zhuanspirit.com/";

    @Override
    public @Nullable MavenVersionState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MavenVersionState state) {
        if (ObjectUtil.isNotEmpty(state.getHost())) {
            this.host = state.getHost();
        }
    }
}
