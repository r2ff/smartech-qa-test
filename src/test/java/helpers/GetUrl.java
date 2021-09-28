package helpers;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.WebDriverRunner.url;

public class GetUrl {

    public static void longLoadPage() {
        try {
            url();
        } catch (org.openqa.selenium.TimeoutException te) {
            executeJavaScript("window.stop();");
        }
    }

}
