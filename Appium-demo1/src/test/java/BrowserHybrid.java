import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.appium.java_client.MobileBy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class BrowserHybrid {
    public AppiumDriver driver;
    private By chromeBtn = MobileBy.AccessibilityId("buttonStartWebviewCD");
    private static By gotoHomeBtn = By.id("GoBack");

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformVersion", "16.0");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("autoGrantPermissions",true);
        caps.setCapability("app", System.getProperty("user.dir") + "/apps/ApiDemos-debug.apk");

        caps.setCapability("noReset", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    public void switchToWebview() {
        Set<String> contexts = driver.getContextHandles();
        for(String context : contexts) {
            if(context.contains("WEBVIEW")) {
                driver.context(context);
                break;
            }
        }
    }

    @Test
    public void HybridTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(chromeBtn)).click();
        switchToWebview();
        WebElement nameInput = driver.findElement(By.id("name_input"));
        nameInput.clear();
        String name = "Lucas";
        nameInput.sendKeys(name);
        driver.context("NATIVE_APP");
        driver.findElement(gotoHomeBtn).click();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


