package com.bj58.zhuanzhuan.plugindemo.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
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

    private String host;

    @Override
    public @Nullable MavenVersionState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MavenVersionState state) {
        XmlSerializerUtil.copyBean(state,this);
    }
}
