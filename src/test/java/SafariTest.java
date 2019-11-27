import com.experitest.appium.SeeTestClient;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;




public class SafariTest {
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "Safari";
    private IOSDriver<IOSElement> driver2 = null;
    private SeeTestClient client2;
    private DesiredCapabilities dc = new DesiredCapabilities();
    private String accessKey = "eyJ4cC51Ijo5MiwieHAucCI6MSwieHAubSI6Ik1UVTNNamczTlRZMU56VXdOdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4ODgyMzU2NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.3xru3c5BKYTHq4BNpzNasP63-dVHeYqUoFmNYJvrz5s";

    @Before
    public void setUp() throws MalformedURLException {
//        dc.setCapability("reportDirectory", reportDirectory);
//        dc.setCapability("nvProfile", "Monitor");
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());//works

//        dc.setCapability(MobileCapabilityType.UDID, "00008030-00096CA11150802E");
        dc.setBrowserName(MobileBrowserType.SAFARI);
        if(Main.cloud){
            dc.setCapability("accessKey", Main.accessKey);
            driver2 = new IOSDriver<>(new URL(Main.ServerIP), dc);
        }
        else {
            driver2 = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        }
//        driver2.setLogLevel(Level.INFO);
        client2 = new SeeTestClient(driver2);
        client2.setReporter("xml","run_"+Main.timeStampString+ File.separator + Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber()+File.separator +"SafariTest"+Main.devicesList.get(Integer.parseInt(Thread.currentThread().getName())).getIter(),"SafariTest");

    }

    @Test
    public void testUntitled() {
        driver2.get("http://espn.com");
        client2.sleep(3000);
        client2.click("WEB","//*[@id='global-nav-mobile-trigger']",0,1);
        client2.sleep(6000);
        String [] str = client2.getAllValues("WEB","//*[@nodeName='UL' and ./*[./*[@nodeName='H1']]]","text");
        client2.sleep(6000);
        passThroughSport(str);

    }


    public void passThroughSport(String [] values)
    {
        String toCheck;
        for(int i=1;i<values.length;i++) {
            if(values[i].equals("Cricket"))break;
            if((!values[i].equals("CFL")&&!values[i].equals("Cricket"))&&!values[i].equals("X Games")) {// those indexes change address
                System.out.println(values[i] + " *!*");
                client2.sleep(2000);
                driver2.findElement(By.linkText(values[i])).click();
                client2.sleep(2000);
                toCheck = driver2.findElement(By.linkText(values[i])).getText();
                client2.sleep(2000);
                System.out.println("got:" + toCheck);
                Assert.assertEquals(values[i], toCheck);
                client2.click("WEB", "//*[@id='global-nav-mobile-trigger']", 0, 1);
                client2.sleep(1000);
            }

        }
    }

    @After
    public void tearDown() {
        driver2.quit();
    }

}
