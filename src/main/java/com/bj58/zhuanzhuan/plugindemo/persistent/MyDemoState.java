package com.bj58.zhuanzhuan.plugindemo.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@Service
@State(name = "MyDemoState",storages = {@Storage("lyj-demo-state.xml")})
public final class MyDemoState implements PersistentStateComponent<MyDemoState> {
    public String username;
    public String password;

    @Override
    public @Nullable MyDemoState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MyDemoState state) {
        XmlSerializerUtil.copyBean(state,this);
    }
}
