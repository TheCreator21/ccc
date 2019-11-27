
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

public static ArrayList<Device> devicesArrayList;
//public static String  devices[];//= {"17b3dcc17d43"} ;//,"0516055aa1bb0802"};//,"62211517HG216BB0571"};
static long timeStamp;
static HashMap<String,String> text;
static long timer;
static boolean runWithTimer =false;
public static String timeStampString;
static ArrayList<DeviceThread> devicesList;
static OverallSummeryFile sumFile;
static boolean cloud=true;//change manual
final static String ServerIP = "https://localhost/wd/hub";
static String accessKey ="eyJ4cC51Ijo5MiwieHAucCI6MSwieHAubSI6Ik1UVTNNekEwTWpBd01EazBNdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4ODg0MDIwMDAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.VF6gM49OxiYk3zhGwpMf8fO5OdGlFod-Ls_mKchxha0";

    public static void main(String args[])
    {
        //new comman
        //end of new command
        text = new HashMap<String,String>();
        timer = 60*60*1000;//defualt time 1 hr
        timeStamp = System.currentTimeMillis();//time stamp
        timeStampString = Long.toString(timeStamp);
        sumFile= new OverallSummeryFile();//File Manager Object
        boolean [] toStartFlags;
        Scanner in = new Scanner(System.in);//input
        String [] a;
        if(cloud){//query for cloud devices
         connectedDeviceCloud connCloud = new connectedDeviceCloud();
         a = connCloud.getConnectedDevices1();
         devicesArrayList = connCloud.getConnectedDevices();
        }
        else {//else AppiumStudio
            connectedDevices conn = new connectedDevices();
            a = conn.getConnectedDevices4();
            devicesArrayList = conn.getConnectedDevices3();
        }
        System.out.println(devicesArrayList);
//        System.out.println(a[0]);
//        System.out.println(a[0].substring(1,a[0].length()-1));



        toStartFlags = new boolean[a.length];
        Configure configure = new Configure();
        configure.parseFile();
//        ArrayList<DeviceThread> devicesList = new ArrayList<DeviceThread>();
        devicesList = new ArrayList<DeviceThread>();
        int testsInt;
        String input="";
        for(boolean b:toStartFlags){b=false;}

        System.out.println("type 'y/n' for timer");
        input = in.nextLine();
        if(input.equals("y"))//get input in in mins
        {
            runWithTimer=true;
            System.out.println("type time in minutes");
            input = in.nextLine();
            timer = Long.parseLong(input)*60000;
            System.out.println(timer);

        }
        System.out.println("read conf? 'y/n'");
        input = in.nextLine();
        if(input.equals("y")){
            for(int i=0;i<a.length;i++){
                devicesArrayList.get(i).setSerialNumber(a[i].substring(1,a[i].length()-1));
            }
            devicesArrayList = configure.trim(devicesArrayList);
            System.out.println(devicesArrayList);
            for(int i=0;i<devicesArrayList.size();i++){
                devicesList.add(new DeviceThread(Integer.toString(i),devicesArrayList.get(i).getOS().substring(1,devicesArrayList.get(i).getOS().length()-1)));
                devicesList.get(i).setTestInt(15);
                devicesList.get(i).start();
            }
        }
        else {
            for (int i = 0; i < a.length; i++) {
                testsInt = 0;
                devicesArrayList.get(i).setSerialNumber(a[i].substring(1, a[i].length() - 1));
                devicesList.add(new DeviceThread(Integer.toString(i), devicesArrayList.get(i).getOS().substring(1, devicesArrayList.get(i).getOS().length() - 1)));
                //start tests
                //                devicesList.get(i).start();
                //*********!***
                System.out.println("type 'y/n' for start test for" + devicesArrayList.get(i));
                input = in.nextLine();
                if (input.equals("y")) {
                    toStartFlags[i] = true;
//                devicesList.get(i).start();

                    System.out.println("type 'y/n' for Web test" + devicesArrayList.get(i));
                    input = in.nextLine();
                    if (input.equals("y")) {
                        testsInt += 1;
                    }
                    System.out.println("type 'y/n' for EriBank test" + devicesArrayList.get(i));
                    input = in.nextLine();
                    if (input.equals("y")) {
                        testsInt += 2;
                    }
                    System.out.println("type 'y/n' for AppStore/PlayStore test" + devicesArrayList.get(i));
                    input = in.nextLine();
                    if (input.equals("y")) {
                        testsInt += 4;
                    }
                    if (devicesArrayList.get(i).getOS().substring(1, devicesArrayList.get(i).getOS().length() - 1).equals("android")) {
                        System.out.println("type 'y/n' for MyApp test" + devicesArrayList.get(i));
                        input = in.nextLine();
                        if (input.equals("y")) {
                            testsInt += 8;
                        }
                    }
                    devicesList.get(i).setTestInt(testsInt);
                } else {
                    toStartFlags[i] = false;
                }

            }
            for (int i = 0; i < devicesList.size(); i++) {

                if (toStartFlags[i]) {
                    devicesList.get(i).start();
                }

            }

        }
    }

}
