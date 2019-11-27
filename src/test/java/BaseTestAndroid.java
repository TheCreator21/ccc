import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTestAndroid {
    protected AndroidDriver<AndroidElement> driver = null;
    protected String reportFormat = "xml";
    protected SeeTestClient client;
    protected DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {
//        dc.setCapability("reportDirectory", reportDirectory);
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", "chromeTest");
        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());//works
//        dc.setCapability(MobileCapabilityType.UDID, "17b3dcc17d43");
        dc.setBrowserName(MobileBrowserType.CHROMIUM);
//        dc.setCapability("nvProfile", "Monitor");
        if(Main.cloud){
            dc.setCapability("accessKey", Main.accessKey);
//            driver = new AndroidDriver<>(new URL("http://192.168.2.249:9192/wd/hub"), dc);
            driver = new AndroidDriver<>(new URL(Main.ServerIP), dc);
        }
        else {
            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        }
//        driver.setLogLevel(Level.INFO);
        client = new SeeTestClient(driver);

    }
}
