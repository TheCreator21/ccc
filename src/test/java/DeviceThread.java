import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class DeviceThread extends Thread {

    int Iter = 1;
    private boolean onRun = false;
    private int testInt = 0;
    private int passCount = 0;
    private int failedCount = 0;
    private String os;
    private ArrayList<String> passed;
    private ArrayList<String> failed;

    public DeviceThread(String name, String os) {
        setName(name);
        this.os = os;
        passed = new ArrayList<String>();
        failed = new ArrayList<String>();

    }

    public void setTestInt(int toSet) {
        this.testInt = toSet;
    }


    public String getIter() {
        return Integer.toString(Iter);
    }

    @Override
    public void run() {
        System.out.println(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber());
        Main.sumFile.addDevice(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber());
        if (Main.runWithTimer) {
            while (System.currentTimeMillis() - Main.timeStamp < Main.timer) {
                System.out.println("running tests on :" + this.getName());
                switch (this.os) {
                    case "android":
                        if ((testInt & 2) == 2) {
                            Result result1 = JUnitCore.runClasses(EriBankAndroid.class);
                            if (result1.wasSuccessful()) {
                                passed.add("EriBankAndroid" + getIter());
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry without reboot
                                for (Failure f : result1.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                result1 = JUnitCore.runClasses(EriBankAndroid.class);
                                if (result1.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//retry with reboot
                                    for (Failure f : result1.getFailures()) {

                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                    JUnitCore.runClasses(RebootAndroid.class);
                                    result1 = JUnitCore.runClasses(EriBankAndroid.class);
                                    if (result1.wasSuccessful()) {
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                    } else {//failed after reboot
                                        String failString = "SafariTest " + getIter() + "\n";
                                        for (Failure f : result1.getFailures()) {
                                            failString += f.toString() + "\n";
                                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                        }
                                    }
                                }
                            }
                            Main.sumFile.modify();
                        }
                        if ((testInt & 4) == 4) {
                            Result result2 = JUnitCore.runClasses(PlayStoreTest.class);
                            if (result2.wasSuccessful()) {
                                passed.add("PlayStoreTest" + getIter());
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry without reboot
                                for (Failure f : result2.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                result2 = JUnitCore.runClasses(PlayStoreTest.class);
                                if (result2.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//retry with reboot
                                    for (Failure f : result2.getFailures()) {

                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                    JUnitCore.runClasses(RebootAndroid.class);
                                    result2 = JUnitCore.runClasses(PlayStoreTest.class);
                                    if (result2.wasSuccessful()) {
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                    } else {//failed after reboot
                                        String failString = "SafariTest " + getIter() + "\n";
                                        for (Failure f : result2.getFailures()) {
                                            failString += f.toString() + "\n";
                                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                        }
                                    }
                                }
                            }
                            Main.sumFile.modify();
                        }

                        if ((testInt & 8) == 8) {
                            Result result3 = JUnitCore.runClasses(myAppTest.class);
                            if (result3.wasSuccessful()) {
                                passed.add("myAppTest" + getIter());
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry without reboot
                                for (Failure f : result3.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                result3 = JUnitCore.runClasses(myAppTest.class);
                                if (result3.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//retry with reboot
                                    for (Failure f : result3.getFailures()) {

                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                    JUnitCore.runClasses(RebootAndroid.class);
                                    result3 = JUnitCore.runClasses(myAppTest.class);
                                    if (result3.wasSuccessful()) {
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                    } else {//failed after reboot
                                        String failString = "SafariTest " + getIter() + "\n";
                                        for (Failure f : result3.getFailures()) {
                                            failString += f.toString() + "\n";
                                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                        }
                                    }
                                }
                            }
                            Main.sumFile.modify();
                        }
                        if ((testInt & 1) == 1) {
                            Result result4 = JUnitCore.runClasses(ChromeTest.class);
                            if (result4.wasSuccessful()) {
                                passed.add("ChromeTest" + getIter());
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry without reboot
                                for (Failure f : result4.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                result4 = JUnitCore.runClasses(ChromeTest.class);
                                if (result4.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//retry with reboot
                                    for (Failure f : result4.getFailures()) {

                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                    JUnitCore.runClasses(RebootAndroid.class);
                                    result4 = JUnitCore.runClasses(ChromeTest.class);
                                    if (result4.wasSuccessful()) {
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                    } else {//failed after reboot
                                        String failString = "SafariTest " + getIter() + "\n";
                                        for (Failure f : result4.getFailures()) {
                                            failString += f.toString() + "\n";
                                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                        }
                                    }
                                }
                            }
                            Main.sumFile.modify();
                        }
                        break;



                    case "ios":
                        if ((testInt & 2) == 2) {
                            Result result1 = JUnitCore.runClasses(eriIOS.class);
                            if (result1.wasSuccessful()) {
                                passed.add("EriBankIos" + getIter());
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry without reboot
                                for (Failure f : result1.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                result1 = JUnitCore.runClasses(eriIOS.class);
                                if (result1.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//retry with reboot
                                    for (Failure f : result1.getFailures()) {

                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                    JUnitCore.runClasses(RebootIOS.class);
                                    result1 = JUnitCore.runClasses(eriIOS.class);
                                    if (result1.wasSuccessful()) {
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                    } else {//failed after reboot
                                        String failString = "SafariTest " + getIter() + "\n";
                                        for (Failure f : result1.getFailures()) {
                                            failString += f.toString() + "\n";
                                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                        }
                                    }
                                }
                            }
                        }
                        Main.sumFile.modify();

                        if ((testInt & 4) == 4) {
                            Result result1 = JUnitCore.runClasses(AppStoreTest.class);
                            if (result1.wasSuccessful()) {
                                passed.add("AppStoreTest" + getIter());
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line

                            } else {//retry without reboot
                                for (Failure f : result1.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                result1 = JUnitCore.runClasses(AppStoreTest.class);
                                if (result1.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//retry with reboot
                                    for (Failure f : result1.getFailures()) {

                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                    JUnitCore.runClasses(RebootIOS.class);
                                    result1 = JUnitCore.runClasses(AppStoreTest.class);
                                    if (result1.wasSuccessful()) {
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                    } else {//failed after reboot
                                        String failString = "SafariTest " + getIter() + "\n";
                                        for (Failure f : result1.getFailures()) {
                                            failString += f.toString() + "\n";
                                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                        }
                                    }
                                }
                            }
                            Main.sumFile.modify();
                        }

                        if ((testInt & 1) == 1) {
                            Result result1 = JUnitCore.runClasses(SafariTest.class);
                            if (result1.wasSuccessful()) {
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry without reboot
                                for (Failure f : result1.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                result1 = JUnitCore.runClasses(SafariTest.class);
                                if (result1.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//retry with reboot
                                    for (Failure f : result1.getFailures()) {

                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                    JUnitCore.runClasses(RebootIOS.class);
                                    result1 = JUnitCore.runClasses(SafariTest.class);
                                    if (result1.wasSuccessful()) {
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                    } else {//failed after reboot
                                        String failString = "SafariTest " + getIter() + "\n";
                                        for (Failure f : result1.getFailures()) {
                                            failString += f.toString() + "\n";
                                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                        }
                                    }
                                }
                            }
                            Main.sumFile.modify();

                        }

                        break;
//            Result result2 = JUnitCore.runClasses(SafariTest.class);

//            System.out.println("was sec:   " + result2.wasSuccessful());

                }
                System.out.println("finished testing");
                onRun = false;
                Iter++;
            }
        }
        else {//no timer
            System.out.println("running tests on :" + this.getName());
            switch (this.os) {
                case "android":

                    if ((testInt & 2) == 2) {
                        Result result1 = JUnitCore.runClasses(EriBankAndroid.class);
                        if (result1.wasSuccessful()) {
                            passed.add("EriBankAndroid" + getIter());
                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                        } else {//retry without reboot
                            for (Failure f : result1.getFailures()) {

                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                            }
                            result1 = JUnitCore.runClasses(EriBankAndroid.class);
                            if (result1.wasSuccessful()) {
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry with reboot
                                for (Failure f : result1.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                JUnitCore.runClasses(RebootAndroid.class);
                                result1 = JUnitCore.runClasses(EriBankAndroid.class);
                                if (result1.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//failed after reboot
                                    String failString = "SafariTest " + getIter() + "\n";
                                    for (Failure f : result1.getFailures()) {
                                        failString += f.toString() + "\n";
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                }
                            }
                        }
                        Main.sumFile.modify();
                    }
                    if ((testInt & 4) == 4) {
                        Result result2 = JUnitCore.runClasses(PlayStoreTest.class);
                        if (result2.wasSuccessful()) {
                            passed.add("PlayStoreTest" + getIter());
                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                        } else {//retry without reboot
                            for (Failure f : result2.getFailures()) {

                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                            }
                            result2 = JUnitCore.runClasses(PlayStoreTest.class);
                            if (result2.wasSuccessful()) {
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry with reboot
                                for (Failure f : result2.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                JUnitCore.runClasses(RebootAndroid.class);
                                result2 = JUnitCore.runClasses(PlayStoreTest.class);
                                if (result2.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//failed after reboot
                                    String failString = "SafariTest " + getIter() + "\n";
                                    for (Failure f : result2.getFailures()) {
                                        failString += f.toString() + "\n";
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                }
                            }
                        }
                        Main.sumFile.modify();
                    }

                    if ((testInt & 8) == 8) {
                        Result result3 = JUnitCore.runClasses(myAppTest.class);
                        if (result3.wasSuccessful()) {
                            passed.add("myAppTest" + getIter());
                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                        } else {//retry without reboot
                            for (Failure f : result3.getFailures()) {

                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                            }
                            result3 = JUnitCore.runClasses(myAppTest.class);
                            if (result3.wasSuccessful()) {
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry with reboot
                                for (Failure f : result3.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                JUnitCore.runClasses(RebootAndroid.class);
                                result3 = JUnitCore.runClasses(myAppTest.class);
                                if (result3.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//failed after reboot
                                    String failString = "SafariTest " + getIter() + "\n";
                                    for (Failure f : result3.getFailures()) {
                                        failString += f.toString() + "\n";
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                }
                            }
                        }
                        Main.sumFile.modify();
                    }
                    if ((testInt & 1) == 1) {
                        Result result4 = JUnitCore.runClasses(ChromeTest.class);
                        if (result4.wasSuccessful()) {
                            passed.add("ChromeTest" + getIter());
                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                        } else {//retry without reboot
                            for (Failure f : result4.getFailures()) {

                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                            }
                            result4 = JUnitCore.runClasses(ChromeTest.class);
                            if (result4.wasSuccessful()) {
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry with reboot
                                for (Failure f : result4.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                JUnitCore.runClasses(RebootAndroid.class);
                                result4 = JUnitCore.runClasses(ChromeTest.class);
                                if (result4.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//failed after reboot
                                    String failString = "SafariTest " + getIter() + "\n";
                                    for (Failure f : result4.getFailures()) {
                                        failString += f.toString() + "\n";
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                }
                            }
                        }
                        Main.sumFile.modify();
                    }
                    break;

                case "ios":
                    if ((testInt & 2) == 2) {
                        Result result1 = JUnitCore.runClasses(eriIOS.class);
                        if (result1.wasSuccessful()) {
                            passed.add("EriBankIos" + getIter());
                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                        } else {//retry without reboot
                            for (Failure f : result1.getFailures()) {

                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                            }
                            result1 = JUnitCore.runClasses(eriIOS.class);
                            if (result1.wasSuccessful()) {
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry with reboot
                                for (Failure f : result1.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                JUnitCore.runClasses(RebootIOS.class);
                                result1 = JUnitCore.runClasses(eriIOS.class);
                                if (result1.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//failed after reboot
                                    String failString = "SafariTest " + getIter() + "\n";
                                    for (Failure f : result1.getFailures()) {
                                        failString += f.toString() + "\n";
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                }
                            }
                        }
                    }
                    Main.sumFile.modify();

                    if ((testInt & 4) == 4) {
                        Result result1 = JUnitCore.runClasses(AppStoreTest.class);
                        if (result1.wasSuccessful()) {
                            passed.add("AppStoreTest" + getIter());
                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line

                        } else {//retry without reboot
                            for (Failure f : result1.getFailures()) {

                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                            }
                            result1 = JUnitCore.runClasses(AppStoreTest.class);
                            if (result1.wasSuccessful()) {
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry with reboot
                                for (Failure f : result1.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                JUnitCore.runClasses(RebootIOS.class);
                                result1 = JUnitCore.runClasses(AppStoreTest.class);
                                if (result1.wasSuccessful()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//failed after reboot
                                    String failString = "SafariTest " + getIter() + "\n";
                                    for (Failure f : result1.getFailures()) {
                                        failString += f.toString() + "\n";
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                }
                            }
                        }
                        Main.sumFile.modify();
                    }

                    if ((testInt & 1) == 1) {
                        Result result1 = JUnitCore.runClasses(SafariTest.class);
                        if (result1.wasSuccessful()) {
                            Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                        } else {//retry without reboot
                            for (Failure f : result1.getFailures()) {

                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                            }
                            result1 = JUnitCore.runClasses(SafariTest.class);//rerun
                            if (result1.wasSuccessful()) {
                                Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                            } else {//retry with reboot
                                for (Failure f : result1.getFailures()) {

                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                }
                                JUnitCore.runClasses(RebootIOS.class);
                                result1 = JUnitCore.runClasses(SafariTest.class);
                                if (result1.wasSuccessful()) {
                                    Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).increasePassed();// new line
                                } else {//failed after reboot
                                    String failString = "SafariTest " + getIter() + "\n";
                                    for (Failure f : result1.getFailures()) {
                                        failString += f.toString() + "\n";
                                        Main.sumFile.getBySerialNum(Main.devicesArrayList.get(Integer.parseInt(getName())).getSerialNumber()).appendFailure(f.toString());
                                    }
                                }
                            }
                        }
                        Main.sumFile.modify();

                    }

                    System.out.println("finished testing");
                    onRun = false;
                    break;

            }
        }
    }
}

//        String path = "C:\\Users\\eden.gesser\\appiumstudioenterprise-reports"+File.separator+"run_" + Main.timeStampString + File.separator + Main.devicesArrayList.get(Integer.parseInt(Thread.currentThread().getName())).getSerialNumber() + File.separator + "OverallSummary.txt";
//        File f = new File(path);
//        try {
//            f.getParentFile().mkdirs();
//            f.createNewFile();
//        }
//        catch(IOException ex){
//
//        }
//
//            try (FileWriter fw = new FileWriter(path, true);
//                 BufferedWriter bw = new BufferedWriter(fw);
//                 PrintWriter out = new PrintWriter(bw)) {
//                if (passed.size() > 0) {
//                    out.println("passed" + passed.size() + ":");
//                    for (String s : passed) {
//                        out.println(s);
//                    }
//                }
//                if (failed.size() > 0) {
//                    out.println("failed:");
//                    int size=failed.size();
//                    for (String s : failed) {
//                        if(!s.equals("")) {
//                            out.println(s);
//                        }
//                        else{
//                            size--;
//                        }
//                    }
//                    out.println("fail count:"+size);
//                }
//            }
//             catch(IOException e){
//                    //exception handling left as an exercise for the reader
//                }
//            }
//    }


