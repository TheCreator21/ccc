import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.*;

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.*;

import java.util.logging.Level;


public class EriBankAndroid{
    private Random rand = new Random();
    private double  r ;
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "Untitled";
    private double amountBefore = 0,amountAfter = 0;
    protected AndroidDriver<AndroidElement> driver = null;//was protected
    protected SeeTestClient client;//was protected
//    private MyClient client;// = new MyClient("localhost",4723,"com.experitest.ExperiBank/.LoginActivity",true);//"C:\\Users\\eden.gesser\\Desktop\\eribank.apk"

    DesiredCapabilities dc = new DesiredCapabilities();

//    "17b3dcc17d43"

    @Before
    public void setUp() throws MalformedURLException {
//        dc.setCapability("reportDirectory", reportDirectory);
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability("platformName", "Android");
//        dc.setCapability("nvProfile", "Monitor");
//        System.out.println(Main.devices[Integer.parseInt(Thread.currentThread().getName())]);
        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());//works
//        dc.setCapability(MobileCapabilityType.UDID, "17b3dcc17d43");
        dc.setCapability("instrumentApp", true);

        if(Main.cloud){
            dc.setCapability("accessKey", Main.accessKey);
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
            driver = new AndroidDriver<>(new URL(Main.ServerIP), dc);
        }
        else {
            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        }
//        client = new MyClient("localhost",4723,"com.experitest.ExperiBank/.LoginActivity",true);
//        client.setDevice("adb:xiaomi-redmi_3s-17b3dcc17d43");
//        client.setApplicationTitle("expribank");
//        client.openDevice();
//        driver.setLogLevel(Level.INFO);
        client = new SeeTestClient(driver);//return
        client.setReporter("xml","run_"+Main.timeStampString+ File.separator + Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber()+File.separator +"EriBankTest"+Main.devicesList.get(Integer.parseInt(Thread.currentThread().getName())).getIter(),"EriBank");//return
//        client = new MyClient(Main.ServerIP,4723,"com.experitest.ExperiBank/.LoginActivity",true);


    }


    @Test
    public void testLogin() {
        if(!Main.cloud){
            client.install("com.experitest.ExperiBank/.LoginActivity", true, false);
            client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        }
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

                client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, pair.getKey().toString());
                client.elementSendText("NATIVE", "//*[@hint='Password']", 0, pair.getValue().toString());
                client.click("NATIVE", "//*[@id='loginButton']", 0, 1);
                client.click("NATIVE", "//*[@id='button3']", 0, 1);
//                Assert.assertEquals(loginActivityName,client.getCurrentActivity());



            it.remove(); // avoids a ConcurrentModificationException
        }
        //end of csv file should not log in
        client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, "company");
        client.elementSendText("NATIVE", "//*[@hint='Password']", 0, "company");
        client.click("NATIVE", "//*[@id='loginButton']", 0, 1);
//        Assert.assertNotEquals(loginActivityName,client.getCurrentActivity());
        System.out.println("Login test passed!");
        System.out.println(client.getCurrentActivity());
        String str2 = client.elementGetText("WEB","//*[@nodeName='H1']",0);

        System.out.println("got amount before");
        str2 = str2.substring(17,str2.length()-1);
        amountBefore = Double.parseDouble(str2);
        System.out.println("amount before:  "+amountBefore);
        client.click("NATIVE", "//*[@id='makePaymentButton']", 0, 1);//click on make payment
        client.elementSendText("NATIVE", "//*[@hint='Phone']", 0, "company");//phone
        client.elementSendText("NATIVE", "//*[@hint='Name']", 0, "company");//name
        client.elementSendText("NATIVE", "//*[@hint='Amount']", 0, Double.toString(r));//amount
        client.elementSendText("NATIVE", "//*[@hint='Country']", 0,"company");//country
        client.click("NATIVE", "//*[@id='sendPaymentButton']", 0, 1);//click make payment
        client.click("NATIVE", "//*[@id='button1' and @width>0]", 0, 1);//click make payment
//        String str = client.elementGetProperty("NATIVE", "//*[@class='android.view.View']", 0, "text");
       String str = client.elementGetText("WEB","//*[@nodeName='H1']",0);
//        String str = client.elementGetProperty("NATIVE", "//*[@text and @class='android.view.View']", 0, "text");
        System.out.println(str);
        str = str.substring(17,str.length()-1);
        System.out.println(str);
        amountAfter = Double.parseDouble(str);
        String s = String.valueOf(amountBefore-r);
        System.out.println(s);
        Assert.assertEquals(Double.parseDouble(str),Double.parseDouble(s),0.1);
    }
    @After
    public void tearDown() {
        driver.quit();
    }

}