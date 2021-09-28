package helpers;

import io.qameta.allure.Attachment;

public class AllureAttachments {
    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] addScreenshotAs(String attachName) {
        return DriverUtils.getScreenshotAsBytes();
    }
}
