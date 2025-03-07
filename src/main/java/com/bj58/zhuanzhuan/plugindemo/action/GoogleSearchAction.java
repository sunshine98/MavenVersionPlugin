package com.bj58.zhuanzhuan.plugindemo.action;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.Messages;

import java.util.List;

public class GoogleSearchAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        //-----------------------***【获取用户数据】***----------------------------------
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        String selectedText = editor.getSelectionModel().getSelectedText();
        if (StrUtil.isEmpty(selectedText)) {
            Messages.showInfoMessage("未选中任何内容", "操作失败");
            return;
        }

        //-----------------------***【构建请求】***----------------------------------
        UrlBuilder urlBuilder = UrlBuilder.of("https://nexus.zhuanspirit.com/nexus/service/local/lucene/search");
        UrlBuilder finalUrl = urlBuilder.addQuery("collapseresults", "true")
                .addQuery("_dc", System.currentTimeMillis())
                .addQuery("q", selectedText);
        String body = null;
        HttpRequest request = HttpUtil.createGet(finalUrl.build()).header("Accept", "application/json");
        try (HttpResponse response = request.execute()) {
            body = response.body();
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
        CopyPasteManager.copyTextToClipboard(newVersion);
        String text = StrUtil.format("""
                <div style="font-family: Arial, sans-serif; padding: 15px; background-color: #E6EEF7; color: #2D2D2D; text-align: center; line-height: 1.4;">
                  <!-- 居中显示的值 -->
                  <div style="margin-bottom: 12px; font-size: 1.15em; color: #2D2D2D; font-weight: bold;">
                    {}
                  </div>
                 \s
                  <ul style="list-style-type: none; padding: 0; margin: 0; text-align: left;">
                    <li style="margin: 4px 0;">
                      <strong>最新 Snapshot 版本:</strong>
                      <span style="color: #0052CC;">{}</span>
                    </li>
                    <li style="margin: 4px 0;">
                      <strong>最新 Release 版本:</strong>
                      <span style="color: #28A745;">{}</span>
                    </li>
                    <li style="margin: 4px 0;">
                      <strong>生成 Release 版本:</strong>
                      <span style="color: #28A745;">{}</span>
                      <span style="color: #6C757D; font-size: 0.85em;">（已复制到剪贴板）</span>
                    </li>
                  </ul>
                 \s
                  <p style="color: #6C757D; font-size: 0.9em; margin-top: 8px; text-align: right;">
                    Powered by 刘扬俊
                  </p>
                </div>
                """, artifactId,latestSnapshot, latestRelease, newVersion);
//        Messages.showInfoMessage(text, "操作成功");
        Notification notification = new Notification("myNotiGroup", "生成成功", text, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification,e.getProject());
    }

}
