import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static junit.framework.TestCase.assertTrue;

public class ERIAPPIUM {
    private AndroidDriver driver;
    private Random rand = new Random();
    private double  r ;

    @Before
    public void setUp() {
        // set up appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
//        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("deviceName", "adb:Mi MIX 3");
        capabilities.setCapability("deviceName", "adb:SM-G920F");//0516055aa1bb0802
//        capabilities.setCapability("deviceName", "adb:xiaomi-redmi_3s-17b3dcc17d43");
//        capabilities.setCapability(MobileCapabilityType.UDID, "17b3dcc17d43");
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("w3c", "false");
//        chromeOptions.merge(capabilities);
//        capabilities.setCapability("appPackage", "com.experitest.ExperiBank");
//        capabilities.setCapability("appActivity", "com.experitest.ExperiBank/.LoginActivity");
        try {
            driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        }
        catch (MalformedURLException mex){}

    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testActive() {
        driver.startActivity(new Activity("com.experitest.ExperiBank",".LoginActivity"));
        ReadCsvFiles readCsvFiles = new ReadCsvFiles("C:\\Users\\eden.gesser\\Desktop\\login.csv");
        HashMap<String,String> users = readCsvFiles.getHm();
        r =rand.nextDouble()*100;//com.experitest.ExperiBank/.LoginActivity
        System.out.println("starting login test    "+r);
//        client.install("com.experitest.ExperiBank/.LoginActivity", true, false);
//        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
//        String loginActivityName = client.getCurrentActivity();
        Iterator it = users.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("testing: "+pair.getKey() + " , " + pair.getValue());
            driver.findElement(By.id("//*[@resource-id='com.experitest.ExperiBank:id/usernameTextField']")).sendKeys(pair.getKey().toString());
            driver.findElement(By.id("//*[@resource-id='com.experitest.ExperiBank:id/passwordTextField']")).sendKeys(pair.getValue().toString());
            driver.findElement(By.id("//*[@resource-id='com.experitest.ExperiBank:id/loginButton']")).click();
            driver.findElement(By.id("//*[@resource-id='android:id/button3']")).click();
            it.remove(); // avoids a ConcurrentModificationException
        }


    }
}
