import com.experitest.appium.SeeTestClient;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class connectedDeviceCloud {
    private AndroidDriver<AndroidElement> driver3 =null;
    private IOSDriver<IOSElement> driver2 =null;
    private SeeTestClient client;
    private SeeTestClient client2;//was protected
    private DesiredCapabilities dc = new DesiredCapabilities();
    private String accessKey = "eyJ4cC51Ijo5MiwieHAucCI6MSwieHAubSI6Ik1UVTNNekEwTWpBd01EazBNdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4ODg0MDIwMDAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.VF6gM49OxiYk3zhGwpMf8fO5OdGlFod-Ls_mKchxha0";
    public connectedDeviceCloud(){
        dc.setCapability("accessKey", Main.accessKey);
        dc.setCapability("newSessionWaitTimeout", 40);

        }

        public ArrayList<Device> getConnectedDevices(){
            String ret="";
            try {
                driver3 = new AndroidDriver<>(new URL(Main.ServerIP), dc);
                client= new SeeTestClient(driver3);
//                System.out.println(client.getDevicesInformation());
//                System.out.println(client.getConnectedDevices());
                 ret +=  client.getDevicesInformation();
                driver3.quit();
                try {
                    driver2 = new IOSDriver<>(new URL(Main.ServerIP), dc);
                    client= new SeeTestClient(driver2);
                    System.out.println(client.getDevicesInformation());
                    System.out.println(client.getConnectedDevices());
                    ret+=  client.getDevicesInformation();
                    driver2.quit();
                }
                catch(MalformedURLException ex2){
                    ex2.printStackTrace();
                }

            }
            catch(Exception ex){
                try {
                    driver2 = new IOSDriver<>(new URL(Main.ServerIP), dc);
                    client2= new SeeTestClient(driver2);
//                    System.out.println(client.getDevicesInformation());
//                    System.out.println(client.getConnectedDevices());
                    ret +=  client2.getDevicesInformation();
                    driver2.quit();
                }
                catch(MalformedURLException ex2){
                    ex.printStackTrace();
                }
            }

//            System.out.println(ret);
            String [] s = ret.split("\r\n|\r|\n");
            String ret2 = "";
            for(String z:s){
                if(!z.contains("reservedtoyou=\"false\"")){
                    ret2+=z+"\n";

                }
            }
            System.out.println(ret2);
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

    public String [] getConnectedDevices1(){
        String ret="";
        try {
            driver3 = new AndroidDriver<>(new URL(Main.ServerIP), dc);
            client= new SeeTestClient(driver3);
//            System.out.println(client.getConnectedDevices());
            ret =  client.getDevicesInformation();
            driver3.quit();
            try {
                driver2 = new IOSDriver<>(new URL(Main.ServerIP), dc);
                client= new SeeTestClient(driver2);
//                System.out.println(client.getDevicesInformation());
//                System.out.println(client.getConnectedDevices());
                ret +=  client.getDevicesInformation();
                driver2.quit();
            }
            catch(MalformedURLException ex2){
                ex2.printStackTrace();
            }
        }
        catch(Exception ex){
            try {
                driver2 = new IOSDriver<>(new URL(Main.ServerIP), dc);
//                driver2 = new IOSDriver<>(new URL("http://192.168.2.249:9192/wd/hub"), dc);
                client2= new SeeTestClient(driver2);
//                System.out.println(client.getDevicesInformation());
                ret =  client2.getDevicesInformation();
                driver2.quit();
            }
            catch(MalformedURLException ex2){
                ex.printStackTrace();
            }

        }


//        System.out.println(ret);
        String [] s = ret.split("\r\n|\r|\n");
        String ret2 = "";
        for(String z:s){
            if(!z.contains("reservedtoyou=\"false\"")){
                ret2+=z+"\n";

            }
        }
        System.out.println(ret2);
        String []splited =ret2.split("serialnumber=");
        String retArr[] = new String [splited.length-1];
        for(int i=1;i<splited.length;i++){
            splited[i]=splited[i].substring(0,splited[i].indexOf(' '));//tirm string to just serial number format.
            retArr[i-1] = splited[i];
            System.out.println(splited[i]);

        }
        return retArr;
    }



}
