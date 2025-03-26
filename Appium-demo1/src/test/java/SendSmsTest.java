import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SendSmsTest {
    public AndroidDriver<AndroidElement> driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "16.0");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("appPackage", "com.google.android.apps.messaging");
        caps.setCapability("appActivity", ".ui.ConversationListActivity");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void send_SMS() {
        MobileElement newMessageButton = driver.findElementByAccessibilityId("Start chat");
        newMessageButton.click();

        MobileElement recipientField = driver.findElementById("com.google.android.apps.messaging:id/recipient_text_view");
        recipientField.sendKeys("5551234567"); // Número do destinatário
        driver.findElementById("com.google.android.apps.messaging:id/contact_picker_create_group").click();

        MobileElement messageField = driver.findElementById("com.google.android.apps.messaging:id/compose_message_text");
        messageField.sendKeys("Hello from Appium!");

        MobileElement sendButton = driver.findElementByAccessibilityId("Send SMS");
        sendButton.click();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
