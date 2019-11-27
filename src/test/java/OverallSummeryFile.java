import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class to handle the Overall Summary File
 * Handle thread safe operations
 */
public class OverallSummeryFile{
    private ArrayList<OverallSummary> list;
    private File f;
    private String path;
    /**
    *constructor
     */
    public OverallSummeryFile(){
        list = new ArrayList<OverallSummary>();

//        path = "C:\\Users\\eden.gesser\\appiumstudioenterprise-reports"+ File.separator+"run_" + Main.timeStampString + File.separator + "OverallSummary.txt";
        path = "OverallSummary.txt";
        f = new File(path);
//        try {
//            f.getParentFile().mkdirs();
//            f.createNewFile();
//        }
//        catch(IOException ex){
//            System.out.println("I/O");
//        }
//        System.out.println(path);

    }
    /**
     * adds a new device
     * thread safe
     */
    public  synchronized void addDevice(String serialNumber){
        list.add(new OverallSummary(serialNumber));

    }
    /**
     *   modify the file
     */
    public  synchronized void modify(){
//        path = "C:\\Users\\eden.gesser\\appiumstudioenterprise-reports"+ File.separator+"run_" + Main.timeStampString + File.separator + "OverallSummary.txt";
        path = "OverallSummary.txt";
        try (FileWriter fw = new FileWriter(path, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             for(OverallSummary ovsu: list){
//                 System.out.println(ovsu);
                 out.println(ovsu.toString());

             }
        }

        catch(IOException e){
            e.printStackTrace();
        }

    }
/***
* getters and setters
 ***/
    public ArrayList<OverallSummary> getList() {
        return list;
    }

    public OverallSummary getBySerialNum(String ser){
        for(OverallSummary overallSummary: list){
            if(ser.equals(overallSummary.getSerialNumber())){
                return overallSummary;
            }
        }
        return null;
    }

    public void setList(ArrayList<OverallSummary> list) {
        this.list = list;
    }

    public File getF() {
        return f;
    }

    public void setF(File f) {
        this.f = f;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}



