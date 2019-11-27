/**
*File: OverallSummary.java
 * Author: Eden Gesser
 */
public class OverallSummary {
    private int passedCount=0;
    private int failedCount=0;
    private String serialNumber;
    private String exString="";
    /*
    *class's constructor
     */
    public OverallSummary(String SerialNum){
        this.serialNumber=SerialNum;
        exString="";

    }

    /*
    *Appends a new line and a new failure
     */
    public void appendFailure(String append){
        this.setExString(this.getExString()+append+"\n\n");
        this.setFailedCount(this.getFailedCount()+1);
    }
    /*
    *class's getter and setter
     */

    public void increasePassed(){
        passedCount+=1;
    }
    public void increaseFailed(){
        failedCount+=1;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getExString() {
        return exString;
    }

    public void setExString(String exString) {
        this.exString = exString;
    }

    @Override
    public String toString() {
        return getSerialNumber()+": passed: "+getPassedCount()+"failed: "+getFailedCount()+"\n\n"+getExString()+"\n\n";
    }


}
