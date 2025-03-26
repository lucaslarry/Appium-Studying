import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Swipe {
    public AppiumDriver driver;
    public AndroidTouchAction actions;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformVersion", "16.0");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("app", System.getProperty("user.dir") + "/apps/ApiDemos-debug.apk");

        caps.setCapability("noReset", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void swipe_test(){
        AndroidElement views = (AndroidElement) driver.findElementByAccessibilityId("Views");
        actions.tap(ElementOption.element(views)).perform();

        AndroidElement photo = (AndroidElement) driver.findElementByAccessibilityId("1.Photos");
        actions.tap(ElementOption.element(photo)).perform();

        AndroidElement pic1 = (AndroidElement) driver.findElements(By.className("android.widget.ImageView")).get(0);
        actions.press(ElementOption.element(pic1))
                .waitAction()
                .moveTo(PointOption.point(-20, 210))
                .release()
                .perform();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


