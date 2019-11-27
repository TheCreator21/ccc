import com.experitest.appium.SeeTestClient;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;



public class AppStoreTest {
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "AppStoreTest";
    private IOSDriver<IOSElement> driver2 = null;
    private SeeTestClient client2;//was protected
    private DesiredCapabilities dc = new DesiredCapabilities();
//    GridClient gridClient = new GridClient("admin","Aa123456","defualt","https://192.168.2.249:9192");
//    Client client = gridClient.lockDeviceForExecution("AppStoreTest", "@os='ios'", 60, TimeUnit.MINUTES.toMillis(2));

    @Before
    public void setUp() throws MalformedURLException {
//        dc.setCapability("reportDirectory", reportDirectory);
//        dc.setCapability("nvProfile", "Monitor");
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());
//        dc.setCapability(MobileCapabilityType.UDID, "00008030-00096CA11150802E");
        if(Main.cloud){
            dc.setCapability("accessKey", Main.accessKey);
            driver2 = new IOSDriver<>(new URL(Main.ServerIP), dc);
        }
        else {
            driver2 = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        }
//        driver2.setLogLevel(Level.INFO);
        client2 = new SeeTestClient(driver2);
        client2.setSpeed("slow");
        client2.setReporter("xml","run_"+Main.timeStampString+ File.separator + Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber()+File.separator +"AppStoreTest"+Main.devicesList.get(Integer.parseInt(Thread.currentThread().getName())).getIter(),"AppStoreTest");




    }


    @Test
    public void test1() {
     client2.launch("com.apple.AppStore",false,true);
     client2.sleep(3000);
     if(client2.isElementFound("NATIVE","//*[@id='Cancel']")) {
         client2.click("NATIVE","//*[@id='Cancel']",0,1);
     }
     client2.sleep(5000);
     client2.click("NATIVE","//*[@id='Apps']",0,1);
     client2.sleep(3000);
     String topFreeName = swipeToTopFree();
     client2.elementSwipe("NATIVE", "//*[@class='UIACollectionView']", 0, "Down", 350, 10000);
     client2.sleep(3000);
     client2.click("NATIVE","//*[@id='See All' and @class='UIAStaticText' and ./parent::*[./parent::*[@id='"+topFreeName+"']]]",0,1);//[@id='Top Free']
     client2.sleep(3000);

//     getTop10Array();//works
        String topa [] = new String [10];
        System.out.println("*****************************************************************************************\nTop10 in AppStore\n*****************************************************************************************");
        if(Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getCategory().contains("PHONE")) {
            client2.click("NATIVE","//*[@id='Free Apps']",0,1);
            client2.sleep(3000);
            for (int i = 1; i < 11; i++) {
                topa[i - 1] = scrollUntilIndex(i, topa);
            }
        }
        else{//tablet
            client2.sleep(2000);
            String strArr[] = client2.getAllValues("NATIVE", "//*[@class='UIACollectionView' and ./*[@id='Top Free iPad Apps']]", "text");
            for(int i = 1;i<20;i+=2){
                System.out.println(strArr[i]);
            }
        }
     client2.sleep(10000);
    }

    /**
     * returns all values and scrolls until top free is present
     * @return string
     */
    private String swipeToTopFree(){
        boolean isTopFreeFound = false;
        String toReturn="";
        String [] strArray3 = client2.getAllValues("NATIVE", "//*[@class='UIACollectionView']", "text");
        for(int i=0;i<strArray3.length;i++)
        {
            if(strArray3[i].contains("Top Free")){//strArray3[i].equals("Top Free")
                isTopFreeFound=true;
                toReturn=strArray3[i];
            }
        }
        while(!isTopFreeFound) {
            client2.elementSwipe("NATIVE", "//*[@class='UIACollectionView']", 0, "Down", 350, 8000);
            strArray3 = client2.getAllValues("NATIVE", "//*[@class='UIACollectionView']", "text");
            for(int i=0;i<strArray3.length;i++)
            {
                if(strArray3[i].contains("Top Free")){//strArray3[i].equals("Top Free")
                    isTopFreeFound=true;
                    toReturn=strArray3[i];
                }
            }
        }
        return toReturn;

    }
    private void getTop10Array(){
        String arrToReturn[] = new String[10];
        String [] strArray = client2.getAllValues("NATIVE", "//*[@class='UIACollectionView']", "text");
        int c=0;
//        System.out.println("*****************************************************************************************\nTop10 in AppStore\n*****************************************************************************************");
        for(int i=0;i<strArray.length;i+=2){
//            System.out.println(strArray[i+1]);
            arrToReturn[i/2]=strArray[i];
            System.out.println(arrToReturn[i/2]);
        }
    }

    /**
     * Scrolls until index is present
     * @param index
     * @param sArr
     * @return
     */
    private String scrollUntilIndex(int index,String [] sArr){
        String toRet="";
        boolean indexFound = false;
        String [] strArray3 = client2.getAllValues("NATIVE", "//*[@class='UIACollectionView']", "text");
        String [] lines3;
        for(int j=0;j<strArray3.length;j++){
//            System.out.println(strArray3[j]+"\n*****************");
            lines3 = strArray3[j].split(",");
//            System.out.println(lines3[0]);
            if (lines3[0].equals(Integer.toString(index))) {
                indexFound = true;
                toRet = strArray3[j].substring(strArray3[j].indexOf(',')+1);
                System.out.println(index+". "+toRet);
                return toRet;
            }
        }
        while(!indexFound){//search while not found
            client2.elementSwipe("NATIVE", "//*[@class='UIACollectionView']", 0, "Down", 250, 10000);
            strArray3 = client2.getAllValues("NATIVE", "//*[@class='UIACollectionView']", "text");
            client2.sleep(3000);
            for(int j=0;j<strArray3.length;j++){
//                System.out.println(strArray3[j]+"\n*****************");
                lines3 = strArray3[j].split(",");
//                System.out.println(lines3[0]);
                if (lines3[0].equals(Integer.toString(index))){
                    indexFound = true;
                    toRet = strArray3[j].substring(strArray3[j].indexOf(',')+1);
                    System.out.println(index +". "+toRet);
                    return toRet;
                }

            }
        }
        return toRet;
    }
    @After
    public void tearDown() {
        driver2.quit();
    }

}
