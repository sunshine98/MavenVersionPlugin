package com.liuyangjun.plugindemo.config;

public interface CommonConfig {

    /** nexus服务本地lucene搜索url格式 */
    String NEXUS_SERVICE_LOCAL_LUCENE_SEARCH = "{}/nexus/service/local/lucene/search";
    /** 提示文本 */
    String TIP_TEXT = """
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
                  <span style="color: #6C757D; font-size: 0.85em;">{}</span>
                </li>
              </ul>
             \s
              <p style="color: #6C757D; font-size: 0.9em; margin-top: 8px; text-align: right;">
                Powered by 刘扬俊
              </p>
            </div>
            """;
    /** 插件显示名称 */
    String DISPLAY_NAME = "Maven Version AutoBumper";
    /** 默认host 不可访问 */
    String DEFAULT_HOST = "https://nexus.abc.com";
    /** 默认自动复制 */
    boolean DEFAULT_AUTO_COPY = true;
}
