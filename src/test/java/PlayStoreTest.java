import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.boon.core.Sys;
import org.boon.di.In;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class PlayStoreTest
{

    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "Untitled";
    private AndroidDriver<AndroidElement> driver = null;//was protected
    private SeeTestClient client;//was protected
    private DesiredCapabilities dc = new DesiredCapabilities();
    private String accessKey = "eyJ4cC51Ijo5MiwieHAucCI6MSwieHAubSI6Ik1UVTNNamczTlRZMU56VXdOdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4ODgyMzU2NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.3xru3c5BKYTHq4BNpzNasP63-dVHeYqUoFmNYJvrz5s";


    @Before
    public void setUp() throws MalformedURLException {
//        dc.setCapability("reportDirectory", reportDirectory);
//        dc.setCapability("nvProfile", "Monitor");
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);

        dc.setCapability(MobileCapabilityType.UDID, Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber());//works
//        dc.setCapability(MobileCapabilityType.UDID, "17b3dcc17d43");//works

        if(Main.cloud){
            dc.setCapability("accessKey", Main.accessKey);
            driver = new AndroidDriver<>(new URL(Main.ServerIP), dc);
        }
        else {
            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
        }
//        driver.setLogLevel(Level.INFO);
        client = new SeeTestClient(driver);
        client.setReporter("xml","run_"+Main.timeStampString+ File.separator + Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber()+File.separator +"PlayStoreTest"+Main.devicesList.get(Integer.parseInt(Thread.currentThread().getName())).getIter(),"PlayStoreTest");




    }


    @Test
    public void testLogin() {
        String top10[] = new String [10];
        int i=0;
        gotToTop10();
        client.sleep(3000);
        client.click("NATIVE", "//*[@id='toggle_switch_button']", 0, 1);//click show installed


        String [] strArray = get5firstIndexFromTop10();

//        int c=0;
        for(int j=0;j<5;j++){
            top10[j]=strArray[j];
//            c++;
        }
        for(i =6;i<11;i++){
            addToArrByIndex(top10,i);
        }
        System.out.println("*****************************************************************************************\nTop10 in PlayStore\n*****************************************************************************************");
        for(int j=0;j<10;j++){
            System.out.println((j+1)+"  "+top10[j]);
        }

    }

    /**
     * Scrolls until index is present and add it to the arr
     * @param arr
     * @param index
     */
    private void addToArrByIndex(String [] arr ,int index){
        scrollUntilIndex(index);
        String [] strArray3 = client.getAllValues("NATIVE", "//*[@id='data_view']", "contentDescription");
        String[] lines3 ;
        for(int j=0;j<strArray3.length;j++){
            lines3 = strArray3[j].split("\r\n|\r|\n");
            for(int i = 0;i<lines3.length;i++) {
                if (lines3[i].equals(Integer.toString(index)))  arr[index-1]=lines3[i+1].split("App: ")[1];
            }
        }

    }

    /**
     * clicks top 10 free
     */
    private void gotToTop10(){
        client.launch("com.android.vending/.AssetBrowserActivity",false,true);
        client.click("NATIVE", "//*[@id='play_header_list_tab_container']/*[@id='title']", 1, 1);//click next
    }

    private String[] get5firstIndexFromTop10(){
                String [] strArray = client.getAllValues("NATIVE", "//*[@id='data_view']", "contentDescription");
        for(int i=0;i<strArray.length;i++){
            String[] lines = strArray[i].split("\r\n|\r|\n");
            strArray[i] = lines[1].split("App: ")[1];

        }
        return strArray;

    }

    /**
     * scrolls until index is found in UI
     * @param index
     */
    private void scrollUntilIndex(int index){
        boolean indexFound = false;
        String [] strArray3 = client.getAllValues("NATIVE", "//*[@id='data_view']", "contentDescription");
        String[] lines3 = strArray3[0].split("\r\n|\r|\n");
        for(int j=0;j<strArray3.length;j++){
            lines3 = strArray3[j].split("\r\n|\r|\n");
            for(int i = 0;i<lines3.length;i++) {
                if (lines3[i].equals(Integer.toString(index))) indexFound = true;
            }
        }
        while(!indexFound){
            client.elementSwipe("NATIVE", "//*[@id='inline_top_charts_content_viewpager']", 0, "Down", 1, 8000);
            strArray3 = client.getAllValues("NATIVE", "//*[@id='data_view']", "contentDescription");
            lines3 = strArray3[0].split("\r\n|\r|\n");
            client.sleep(3000);
            for(int j=0;j<strArray3.length;j++){
                lines3 = strArray3[j].split("\r\n|\r|\n");
                for(int i = 0;i<lines3.length;i++) {
                    if (lines3[i].equals(Integer.toString(index))) indexFound = true;
                }
            }
        }
    }


    @After
    public void tearDown() {
        driver.quit();
    }

}
