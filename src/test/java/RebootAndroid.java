import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class RebootAndroid {
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "Untitled";
    protected AndroidDriver<AndroidElement> driver = null;
    private SeeTestClient client;
    private  DesiredCapabilities dc = new DesiredCapabilities();
    private String accessKey = "eyJ4cC51Ijo5MiwieHAucCI6MSwieHAubSI6Ik1UVTNNamczTlRZMU56VXdOdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4ODgyMzU2NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.3xru3c5BKYTHq4BNpzNasP63-dVHeYqUoFmNYJvrz5s";



    @Before
    public void setUp() throws MalformedURLException {
//        dc.setCapability("reportDirectory", reportDirectory);
//        dc.setCapability("reportFormat", reportFormat);
//        dc.setCapability("testName", testName);
//        dc.setCapability(MobileCapabilityType.UDID,"0516055aa1bb0802");
        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());

        if(Main.cloud){
            dc.setCapability("accessKey", Main.accessKey);
            driver = new AndroidDriver<>(new URL(Main.ServerIP), dc);
        }
        else {
            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        }

        driver.setLogLevel(Level.INFO);
        client = new SeeTestClient(driver);
    }

    @Test
    public void testUntitled() {
        if(client.reboot(120000)){
            System.out.println("Restarted");
            client.deviceAction("Unlock");
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
