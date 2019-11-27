import com.experitest.appium.SeeTestClient;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/***
 * Eri Bank Test Class for iOS devices
 */

public class eriIOS {
    private Random rand = new Random();
    private double  r ;
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "EriBank";
    private double amountBefore = 0,amountAfter = 0;
    private IOSDriver<IOSElement> driver2 = null;
    private SeeTestClient client2;//was protected
    private DesiredCapabilities dc = new DesiredCapabilities();


    @Before
    public void setUp() throws MalformedURLException {
//        dc.setCapability("reportDirectory", reportDirectory);
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability("platformName", "iOS");
        dc.setCapability("instrumentApp", true);
        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());
//        dc.setCapability(MobileCapabilityType.UDID, "00008030-00096CA11150802E");
        if(Main.cloud){
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
            dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
            dc.setCapability("accessKey", Main.accessKey);
            driver2 = new IOSDriver<>(new URL(Main.ServerIP), dc);
        }
        else {
            driver2 = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        }

//        driver.setLogLevel(Level.INFO);
        client2 = new SeeTestClient(driver2);
        client2.setSpeed("slow");
        client2.setReporter("xml","run_"+Main.timeStampString+ File.separator + Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber()+File.separator +"EriBankTest"+Main.devicesList.get(Integer.parseInt(Thread.currentThread().getName())).getIter(),"EriBank");



    }


    @Test
    public void test1() {
        if(!Main.cloud){
            client2.install("com.experitest.ExperiBank", true, false);//install eri bank instrumented
            client2.launch("com.experitest.ExperiBank", true, true);//lunch eri bank
        }
        try {
            ReadCsvFiles readCsvFiles = new ReadCsvFiles("C:\\Users\\eden.gesser\\Desktop\\login.csv");//open csv
            HashMap<String, String> users = readCsvFiles.getHm();//csv to hash map<UserName(as key),password(as value)>
            r = rand.nextDouble() * 100;//generate random number in range 0-100
            System.out.println("starting login test    " + r);
//            client2.install("com.experitest.ExperiBank", true, false);//install eri bank instrumented
//            client2.launch("com.experitest.ExperiBank", true, true);//lunch eri bank
            Iterator it = users.entrySet().iterator();
            while (it.hasNext()) {//iterate over the hashmap
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println("testing: " + pair.getKey() + " , " + pair.getValue());

                client2.elementSendText("NATIVE", "//*[@placeholder='Username']", 0, pair.getKey().toString());
                client2.elementSendText("NATIVE", "//*[@placeholder='Password']", 0, pair.getValue().toString());
                client2.click("NATIVE", "//*[@accessibilityLabel='Login']", 0, 1);
                client2.sleep(1000);
                client2.click("NATIVE", "//*[@accessibilityLabel='Dismiss']", 0, 1);


                it.remove(); // avoids a ConcurrentModificationException//*[@accessibilityLabel='Username']
            }
            client2.sleep(1000);
            //end of csv file should not log in
            //should log in
            client2.elementSendText("NATIVE", "//*[@placeholder='Username']", 0, "company");
            client2.elementSendText("NATIVE", "//*[@placeholder='Password']", 0, "company");
            client2.click("NATIVE", "//*[@accessibilityLabel='Login']", 0, 1);
            System.out.println("Login test passed!");
            //starts test payment
            String str2 = client2.elementGetText("WEB", "//*[@nodeName='H1']", 0);
            str2 = str2.substring(str2.indexOf("  ") + 2, str2.length() - 2);
            System.out.println("got amount before"+str2);
            Thread.sleep(10000);
            amountBefore = Double.parseDouble(str2);
            System.out.println("amount before:  " + amountBefore);
            client2.click("NATIVE", "//*[@accessibilityLabel='Make Payment']", 0, 1);//click on make payment
            //make payment fill detailes
            client2.elementSendText("NATIVE", "//*[@placeholder='Phone']", 0, "050");//phone
            client2.elementSendText("NATIVE", "//*[@placeholder='Name']", 0, "company");//name
            client2.elementSendText("NATIVE", "//*[@placeholder='Amount']", 0, Double.toString(r));//amount
            client2.elementSendText("NATIVE", "//*[@placeholder='Country']", 0, "company");//country
            client2.click("NATIVE", "//*[@accessibilityLabel='Send Payment']", 0, 1);//click make payment
            client2.sleep(2000);
            client2.click("NATIVE", "//*[@text and @accessibilityLabel='Yes']", 0, 1);//approve
            client2.sleep(2000);
            String str = client2.elementGetText("WEB", "//*[@nodeName='H1']", 0);
            str = str.substring(17, str.length() - 2);
            System.out.println(str);
            amountAfter = Double.parseDouble(str);
            String s = String.valueOf(amountBefore - r);
            System.out.println(s);
            Assert.assertEquals(Double.parseDouble(str), Double.parseDouble(s), 0.1);
        }
        catch(Exception ex){
            driver2.quit();
        }
    }
    @After
    public void tearDown() {
        driver2.quit();
    }


}
