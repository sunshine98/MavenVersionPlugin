package com.liuyangjun.plugindemo.action;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.Messages;
import com.liuyangjun.plugindemo.config.CommonConfig;
import com.liuyangjun.plugindemo.persistent.MavenVersionState;

import java.util.List;

public class GoogleSearchAction extends AnAction {
    private MavenVersionState mavenVersionState;
    {
        mavenVersionState = ApplicationManager.getApplication().getService(MavenVersionState.class);
    }
    @Override
    public void actionPerformed(AnActionEvent e) {

        //-----------------------***【获取用户数据】***----------------------------------
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        String selectedText = editor.getSelectionModel().getSelectedText();
        if (StrUtil.isEmpty(selectedText)) {
            Messages.showInfoMessage("未选中任何内容", "操作失败");
            return;
        }
        // TODO【liuyangjun】 2025/3/12:兼容XX.version格式的文本
        String group1 = ReUtil.getGroup1(CommonConfig.ARTIFACT_ID_PATTERN, selectedText);
        if (ObjectUtil.isNotEmpty(group1)) {
            //去除version
            selectedText = group1;
        }
        String host = mavenVersionState.getHost();
        String url = StrUtil.format(CommonConfig.NEXUS_SERVICE_LOCAL_LUCENE_SEARCH, host);

        //-----------------------***【构建请求】***----------------------------------
        UrlBuilder urlBuilder = UrlBuilder.of(url);
        UrlBuilder finalUrl = urlBuilder.addQuery("collapseresults", "true")
                .addQuery("_dc", System.currentTimeMillis())
                .addQuery("q", selectedText);
        String body = null;
        HttpRequest request = HttpUtil.createGet(finalUrl.build()).header("Accept", "application/json");
        try (HttpResponse response = request.execute()) {
            body = response.body();
        }catch (IORuntimeException ioRuntimeException){
            Messages.showInfoMessage("Nexus host 配置错误："+host, "操作失败");
            return;
        }
        if (ObjectUtil.isEmpty(body)) {
            Messages.showInfoMessage("无响应:\n" + body, "操作失败");

        }
        String latestRelease = JSONUtil.parseObj(body).getByPath("data[0].latestRelease", String.class);
        String latestSnapshot = JSONUtil.parseObj(body).getByPath("data[0].latestSnapshot", String.class);
        String artifactId = JSONUtil.parseObj(body).getByPath("data[0].artifactId", String.class);
        List<String> split = StrUtil.split(latestRelease, StrPool.C_DOT);
        if (split.size() != 3) {
            Messages.showInfoMessage("无法解析内容:\n" + body, "操作失败");
            return;
        }
        String minVersion = split.get(2);
        int minV = NumberUtil.parseInt(minVersion);
        String newVersion = StrUtil.join(".", split.get(0), split.get(1), minV + 1);


        //-----------------------***【剪贴板】***----------------------------------
        Boolean neeedAutoCopy = mavenVersionState.getNeeedAutoCopy();
        if (neeedAutoCopy) {
            CopyPasteManager.copyTextToClipboard(newVersion);
        }
        String text = StrUtil.format(CommonConfig.TIP_TEXT, artifactId,latestSnapshot, latestRelease, newVersion,neeedAutoCopy?"（已复制到剪贴板）":"");
        Notification notification = new Notification("myNotiGroup", "生成成功", text, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification,e.getProject());
    }

}
