import com.experitest.client.AppiumClient;
import com.experitest.client.Client;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class MyClient extends AppiumClient{
private String AppPath,device;

        public MyClient(String host, int port,String path, boolean useSessionID) {
            super(host, port, true);
        }
        @Override
        public void click(String zone, String element, int index, int clickCount)
        {
            try{
                super.click(zone, element, index, clickCount);
            } catch(Exception e)
            {
                super.collectSupportData("C:\\supportData.zip","path","adb:GT-I9300","click error", "expected click to work", "click has failed");
            }
        }

        @Override
        public String generateReport(boolean ReleaseClient, String ExternalToolPropFilePath){

            System.out.println("Overridden");
            return super.generateReport(ReleaseClient,ExternalToolPropFilePath);

    }


}
