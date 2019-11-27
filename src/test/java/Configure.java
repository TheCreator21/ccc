import java.io.*;
import java.util.ArrayList;

public class Configure {
    private File fi ;
    private String fileName = "conf.txt";
    private ArrayList <Device> fromFile;
    String path;
    public Configure(){
        fromFile = new ArrayList<Device>();


    }

    public void parseFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String st;
            while ((st = br.readLine()) != null){
                System.out.println(st);
                fromFile.add(new Device(st,"xx"));
            }

        }
        catch (IOException io){
            System.out.println("file not found");
    }
}

    public ArrayList<Device> trim(ArrayList<Device> toTrim){
        ArrayList<Device> toReturn = new ArrayList<Device>();
        for(Device d:toTrim){
            for(Device a: fromFile)
            if (d.getSerialNumber().equals(a.getSerialNumber())){
                toReturn.add(d);
            }
        }
        return toReturn;
    }

}
