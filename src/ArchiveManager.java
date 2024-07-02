import java.util.ArrayList;
import java.io.File;

public class ArchiveManager {
    FileManager fileManager = null;

    public ArchiveManager(){
        fileManager = new FileManager();
    }

    // creating a new archive
    protected boolean box(String directoryName){

        // check if directory exists
        if(fileManager.dirExists(directoryName)){
            ArrayList<File> files = fileManager.listFiles(directoryName);
            
            if(files != null){
                // create a new archive

                // remove these test lines
                    System.out.println("Creating a new archive");
                    System.out.println("Size: " + files.size());
                    for(File file : files){
                        System.out.println(file.getName());
                    }
                // till here

                return true;
            } 
            
            else {
                System.out.println("Directory is either empty or contains sub-directories");
                return false;
            }
        } 
        
        else {
            System.out.println("Directory does not exist");
        }
        return false;
    }

    // extracting files from an archive
    protected boolean unbox(String archiveName){
        return false;
    }

    // listing contents of an archive
    protected boolean list(String archiveName){
        return false;
    }

    // adding a new file to an existing archive
    protected boolean add(String fileName, String archiveName){
        return false;
    }
}
