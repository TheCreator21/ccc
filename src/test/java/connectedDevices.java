import com.experitest.appium.SeeTestClient;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.boon.core.Sys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

public class connectedDevices {


    private AndroidDriver<AndroidElement> driver = null;//was protected
    private IOSDriver<IOSElement> driver2 = null;
    private AppiumDriver driver3 =null;
    private SeeTestClient client;//was protected
    private SeeTestClient client2;//was protected

    DesiredCapabilities dc = new DesiredCapabilities();

    public connectedDevices(){
        dc.setCapability("newCommandTimeout",20);
    }

    public String [] getConnectedDevices(){

        try {

                driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
                client = new SeeTestClient(driver);

        }
        catch(MalformedURLException ex){
            ex.printStackTrace();
        }
       String ret =  this.client.getDevicesInformation();
//        System.out.println(ret);
        driver.quit();
        String []splited =ret.split("serialnumber=");
        String retArr[] = new String [splited.length-1];
        for(int i=1;i<splited.length;i++){
            splited[i]=splited[i].substring(0,splited[i].indexOf(' '));//tirm string to just serial number format.
            retArr[i-1] = splited[i];
//            System.out.println(splited[i]);

        }
        return retArr;
    }
    public ArrayList<Device> getConnectedDevices2(){

        try {
            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
            client = new SeeTestClient(driver);
        }
        catch(MalformedURLException ex){
            ex.printStackTrace();
        }
        String ret =  this.client.getDevicesInformation();
//        System.out.println(ret);
        driver.quit();
        String [] splitedByOS = ret.split("os=");
        String [] splitedBySerial =ret.split("serialnumber=");
        ArrayList<Device> retArr = new ArrayList<Device>();
        for(int i=1;i<splitedBySerial.length;i++){
            splitedBySerial[i]=splitedBySerial[i].substring(0,splitedBySerial[i].indexOf(' '));
            splitedByOS[i]=splitedByOS[i].substring(0,splitedByOS[i].indexOf(' '));//tirm string to just serial number format.
            retArr.add(i-1, new Device( splitedBySerial[i],splitedByOS[i]));
//            System.out.println(splitedBySerial[i]);
//            System.out.println(splitedByOS[i]);

        }
        return retArr;
    }
    public ArrayList<Device> getConnectedDevices3(){

        try {
            driver3 = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
            client2 = new SeeTestClient(driver3);
        }
        catch(MalformedURLException ex){
            ex.printStackTrace();
        }
        String ret =  this.client2.getDevicesInformation();
//        System.out.println(ret);
        String [] s = ret.split("\r\n|\r|\n");
        String ret2 = "";
        for(String z:s){
            if(!z.contains("reservedtoyou=\"false\"")){
                ret2+=z+"\n";

            }
        }
//        System.out.println(ret2);
        driver3.quit();
        String [] splitedByOS = ret2.split("os=");
        String [] splitedBySerial =ret2.split("serialnumber=");
        String [] splitedByCat = ret2.split("category=");
        ArrayList<Device> retArr = new ArrayList<Device>();
        boolean phoneFlag;
        for(int i=1;i<splitedBySerial.length;i++){
            splitedBySerial[i]=splitedBySerial[i].substring(0,splitedBySerial[i].indexOf(' '));
            splitedByOS[i]=splitedByOS[i].substring(0,splitedByOS[i].indexOf(' '));//tirm string to just serial number format.
            splitedByCat[i]=splitedByCat[i].substring(0,splitedByCat[i].indexOf(' '));//tirm string to just serial number format.
            retArr.add(i-1, new Device( splitedBySerial[i],splitedByOS[i],splitedByCat[i]));
//            System.out.println(splitedBySerial[i]);
//            System.out.println(splitedByOS[i]);
//            System.out.println(splitedByCat[i]);

        }
        return retArr;
    }

    public String [] getConnectedDevices4(){

        try {

            driver3 = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
            client2 = new SeeTestClient(driver3);

        }
        catch(MalformedURLException ex){
            ex.printStackTrace();
        }
        String ret =  this.client2.getDevicesInformation();

//        System.out.println(ret);
        String [] s = ret.split("\r\n|\r|\n");
        String ret2 = "";
        for(String z:s){
            if(!z.contains("reservedtoyou=\"false\"")){
                ret2+=z+"\n";

            }
        }
//        System.out.println(ret2);

        driver3.quit();
        String []splited =ret2.split("serialnumber=");
        String retArr[] = new String [splited.length-1];
        for(int i=1;i<splited.length;i++){
            splited[i]=splited[i].substring(0,splited[i].indexOf(' '));//tirm string to just serial number format.
            retArr[i-1] = splited[i];
//            System.out.println(splited[i]);

        }
        return retArr;
    }
}
