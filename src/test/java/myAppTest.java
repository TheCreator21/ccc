import com.experitest.appium.SeeTestClient;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.*;

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;


public class myAppTest{
    private String reportDirectory = "reports";
    private String reportFormat = "xml";

    private String testName = "Untitled";
    private int numberOfGame;
    Random rnd = new Random();
    private int RandomScore ;
    private AndroidDriver<AndroidElement> driver = null;
    private SeeTestClient client;
    private DesiredCapabilities dc = new DesiredCapabilities();
    private ReadCsvFiles rcsv;
    private String path="C:\\Users\\eden.gesser\\Desktop\\login2.csv";
    private String device = "17b3dcc17d43";
    private String accessKey = "eyJ4cC51Ijo5MiwieHAucCI6MSwieHAubSI6Ik1UVTNNamczTlRZMU56VXdOdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4ODgyMzU2NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.3xru3c5BKYTHq4BNpzNasP63-dVHeYqUoFmNYJvrz5s";


    public void setDevice(String device) {
        this.device = device;
    }

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability("instrumentApp", true);
        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());//works2


        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.myapplication");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        if(Main.cloud){
            dc.setCapability("accessKey", Main.accessKey);
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.example.myapplication/.LoginActivity");
            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.myapplication");
            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
            driver = new AndroidDriver<>(new URL(Main.ServerIP), dc);
        }
        else {
            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
//            client.install("com.example.myapplication/.LoginActivity",true, false);
//            client.launch("com.example.myapplication/.LoginActivity", true, true);//*[@id='permission_allow_foreground_only_button']
        }
//        driver.setLogLevel(Level.INFO);
        client = new SeeTestClient(driver);
        client.setReporter("xml","run_"+Main.timeStampString+ File.separator + Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber()+File.separator +"myAppTest"+Main.devicesList.get(Integer.parseInt(Thread.currentThread().getName())).getIter(),"EriBank");
        client.setSpeed("slow");
        numberOfGame = 3;
        RandomScore = rnd.nextInt(10)+1;
        rcsv = new ReadCsvFiles(path);

    }

    @Test
    public void testUntitled() {
        if(!Main.cloud) {
           client.install("com.example.myapplication/.LoginActivity",true, false);
            client.launch("com.example.myapplication/.LoginActivity", true, true);//*[@id='permission_allow_foreground_only_button']
        }
        if(client.isElementFound("NATIVE","//*[@id='permission_allow_button']")) {
            client.click("NATIVE", "//*[@id='permission_allow_button']", 0, 1);
        }
        else{
            client.click("NATIVE", "//*[@id='permission_allow_foreground_only_button']", 0, 1);
        }
        HashMap<String,String> users = rcsv.getHm();
        Iterator it = users.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            System.out.println("testing: "+pair.getKey() + " , " + pair.getValue());
            try {

                client.elementSendText("NATIVE", "//*[@id='EditText1']", 0, pair.getKey().toString());
//                client.elementSendText("NATIVE", "//*[@resource-id='com.example.myapplication:id/EditText1']", 0, pair.getKey().toString());
                client.elementSendText("NATIVE", "//*[@id='EditText2']", 0, pair.getValue().toString());
//                client.elementSendText("NATIVE", "//*[@resource-id='com.example.myapplication:id/EditText2']", 0, pair.getValue().toString());
                client.click("NATIVE", "//*[@id='LoginButton']", 0, 1);
            }
            catch (NullPointerException nex){
                nex.printStackTrace();
            }

            it.remove(); // avoids a ConcurrentModificationException
        }
        String v;
        for(int i=0;i<3;i++) {

//            client.click("NATIVE", "//*[@resource-id='com.example.myapplication:id/PlayButton']", 0, 1);
            client.click("NATIVE", "//*[@id='PlayButton']", 0, 1);
            int t =RandomScore;
            while(t>0){
                t--;//*[@id='dot']
//                client.click("NATIVE", "//*[@resource-id='com.example.myapplication:id/dot']", 0, 1);
                client.click("NATIVE", "//*[@id='dot']", 0, 1);
            }
            //*[@id='RecordsToMainBtn']
//            client.click("NATIVE", "//*[@resource-id='com.example.myapplication:id/RecordsToMainBtn']", 0, 1);
            v=client.elementGetText("NATIVE","//*[@id='scroeView']",0);
            System.out.println(v);
            client.sleep(2000);
            v = v.substring(12);
            Assert.assertEquals(RandomScore,Integer.parseInt(v));
            client.click("NATIVE", "//*[@id='RecordsToMainBtn']", 0, 1);
            client.sleep(6000);
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}