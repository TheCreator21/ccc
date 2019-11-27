/**
 * Device class
 */
public class Device {
    private String serialNumber,OS;
    private String category;
    /*
    *Class's constructors
     */
    public Device(String ser,String os){
        this.OS=os;
        this.serialNumber = ser;
        this.category="";

    }

    public Device(String ser, String os, String category){
        this.serialNumber=ser;
        this.OS=os;
        this.category=category;
    }

    @Override
    public String toString(){
        return "Serial number: "+this.serialNumber+" OS: "+this.OS+" Category: "+this.category;
    }
/*
Class's getters and setters
 */
    public String getSerialNumber(){
        return this.serialNumber;
    }


    public String getOS() {
        return OS;
    }

    public void setSerialNumber(String toSet){
        this.serialNumber=toSet;
    }

    public String getCategory() {
        return category;
    }
}
