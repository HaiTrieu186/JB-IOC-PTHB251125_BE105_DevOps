package com.storex.payment.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Custom Logback Appender: ban canh bao len Discord webhook moi khi co log ERROR.
 * Dung java.net.http.HttpClient (khong phu thuoc Spring), vi Logback appender
 * duoc khoi tao truoc ca Spring Context nen khong the @Autowired RestTemplate vao day.
 *
 * Cau hinh webhookUrl trong logback-spring.xml qua the <webhookUrl>.
 */
public class DiscordAppender extends AppenderBase<ILoggingEvent> {

    // Duoc Logback tu dong set gia tri thong qua setter, khai bao trong logback-spring.xml
    private String webhookUrl;

    private HttpClient httpClient;

    @Override
    public void start() {
        if (webhookUrl == null || webhookUrl.isBlank()) {
            addError("webhookUrl chua duoc cau hinh cho DiscordAppender");
            return;
        }
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (!isStarted()) {
            return;
        }

        // Chi ban canh bao khi log muc ERROR
        if (event.getLevel().levelInt != ch.qos.logback.classic.Level.ERROR_INT) {
            return;
        }

        try {
            String content = buildMessage(event);
            String payload = "{\"content\": \"" + escapeJson(content) + "\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(webhookUrl))
                    .timeout(Duration.ofSeconds(5))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            // Gui bat dong bo, khong block thread ghi log
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                    .exceptionally(ex -> {
                        addError("Gui webhook Discord that bai: " + ex.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            addError("Loi khi build/gui log len Discord: " + e.getMessage());
        }
    }

    private String buildMessage(ILoggingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(":rotating_light: **[ERROR] StoreX System**\n");
        sb.append("Logger: `").append(event.getLoggerName()).append("`\n");
        sb.append("Thread: `").append(event.getThreadName()).append("`\n");
        sb.append("Message: ").append(event.getFormattedMessage());

        if (event.getThrowableProxy() != null) {
            sb.append("\nException: `")
              .append(event.getThrowableProxy().getClassName())
              .append(": ")
              .append(event.getThrowableProxy().getMessage())
              .append("`");
        }
        return sb.toString();
    }

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }

    // Setter bat buoc de Logback inject gia tri tu logback-spring.xml
    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }
}
