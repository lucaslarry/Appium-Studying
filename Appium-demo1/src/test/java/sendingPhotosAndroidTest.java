import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class sendingPhotosAndroidTest {
    public AndroidDriver<AndroidElement> driver;

    private static By backupBtn = By.id("auto_backup_switch");
    private static By touchOutsideBtn = By.id("touch_outside");
    private static By keepOffBtn = By.xpath("//*[@text='KEEP OFF']");
    private static By photo = By.xpath("//android.view.ViewGroup[@content-desc='Photo taken']");

    File classPath, imageDir, img;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "16.0");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("appPackage", "com.google.android.apps.photos");
        caps.setCapability("appActivity", ".home.HomeActivity");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void send_Photo() throws IOException {
        classPath = new File("user.dir");
        imageDir = new File(classPath, "/resources/images");
        img = new File(imageDir.getCanonicalFile(), "test.jpg");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(backupBtn)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(touchOutsideBtn)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(keepOffBtn)).click();

        String Android_Photo_Path = "mnt/sdcard/Pictures";
        driver.pushFile(Android_Photo_Path+"/"+img.getName(),img);
        ExpectedCondition condition = ExpectedConditions.numberOfElementsToBe(photo, 1);
        wait.until(condition);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
