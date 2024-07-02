import java.io.File;
import java.util.ArrayList;

public class FileManager {

    protected boolean dirExists(String directoryName){
        File directory = new File(directoryName);
        return directory.exists() && directory.isDirectory();
    }

    protected boolean fileExists(String fileName){
        File file = new File(fileName);
        return file.exists() && file.isFile();
    }

    protected ArrayList<File> listFiles(String directoryName){
        File directory = new File(directoryName);
        File[] files = directory.listFiles();

        ArrayList<File> list = new ArrayList<>();

        // checking if no sub-drectories exists
        for(File file : files){
            if(file.isFile()){
                list.add(file);
            }
            else {
                // null means either the parent directory is empty
                // or parent directory contains sub-directories 
                return null;
            }
        }

        return list;
    }
}
