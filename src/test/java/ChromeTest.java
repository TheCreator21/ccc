import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.boon.core.Sys;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class ChromeTest extends BaseTestAndroid{
//    private String reportDirectory = "reports";
//    private String reportFormat = "xml";
//    private String testName = "Untitled";
//    private AndroidDriver<AndroidElement> driver = null;
//    private SeeTestClient client;
//    private  DesiredCapabilities dc = new DesiredCapabilities();
//    private String accessKey = "eyJ4cC51Ijo5MiwieHAucCI6MSwieHAubSI6Ik1UVTNNekEwTWpBd01EazBNdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4ODg0MDIwMDAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.VF6gM49OxiYk3zhGwpMf8fO5OdGlFod-Ls_mKchxha0";


    @Before
    public void setUp() throws MalformedURLException {
        super.setUp();
//        dc.setCapability("reportFormat", reportFormat);
//        dc.setCapability("testName", "chromeTest");
//        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());//works
//        dc.setBrowserName(MobileBrowserType.CHROMIUM);
//
//        if(Main.cloud){
//            dc.setCapability("accessKey", Main.accessKey);
//            driver = new AndroidDriver<>(new URL("http://192.168.2.249:9192/wd/hub"), dc);
//        }
//        else {
//            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
//        }
//
//        client = new SeeTestClient(driver);
        client.setReporter("xml","run_"+Main.timeStampString+ File.separator + Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber()+File.separator +"ChromeTest"+Main.devicesList.get(Integer.parseInt(Thread.currentThread().getName())).getIter(),"ChromeTest");

    }

    @Test
    public void testUntitled() {
        driver.get("http://espn.com");
        client.sleep(6000);
        client.click("WEB","//*[@id='global-nav-mobile-trigger']",0,1);
        client.sleep(6000);
        String [] str = client.getAllValues("WEB","//*[@nodeName='UL' and ./*[./*[@nodeName='H1']]]","text");
        client.sleep(6000);
        passThroughSport(str);

    }

    /**
     * Iterates Over the sport side nav bar and clicks each matching value and check that it directs as correctly
     * @param values
     */
    public void passThroughSport(String [] values)
    {
        String toCheck;
        for(int i=1;i<values.length;i++) {
            if(values[i].equals("Cricket"))break;
            if((!values[i].equals("CFL")&&!values[i].equals("Cricket"))&&!values[i].equals("X Games")) {// those indexes change address
                System.out.println(values[i] + " *!*");
                client.sleep(3000);
                driver.findElement(By.linkText(values[i])).click();

                client.sleep(3000);
                toCheck = driver.findElement(By.linkText(values[i])).getText();
                client.sleep(3000);
                System.out.println("got:" + toCheck);
                Assert.assertEquals(values[i], toCheck);
                client.click("WEB", "//*[@id='global-nav-mobile-trigger']", 0, 1);
                client.sleep(3000);
            }

        }
        System.out.println("finished Web Test");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
