
/**
 * The ArchiveManager class is responsible for creating, extracting, listing, and adding files to an archive.
 * It provides methods to box (create a new archive), unbox (extract files from an archive), list (list the contents of an archive),
 * and add (add a new file to an existing archive).
 * The archive format used is a custom format where files are stored with their headers and separated by a specific separator.
 */

import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ArchiveManager {
    FileManager fileManager = null;
    final int BUFFER_SIZE = 128;

    public ArchiveManager() {
        fileManager = new FileManager();
    }

    // creating a new archive
    protected boolean box(String directoryPath) {

        // check if directory exists
        if (fileManager.dirExists(directoryPath)) {
            ArrayList<File> files = fileManager.listFiles(directoryPath);

            if (files != null) {
                // create a new archive
                String archiveName = directoryPath + ".boxed";

                // writing data
                try (BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(archiveName))) {

                    for (File file : files) {
                        // writing file header
                        String header = determineFileHeader(file);
                        bout.write(header.getBytes());

                        // writing file contents
                        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file))) {
                            byte[] buffer = new byte[BUFFER_SIZE];
                            int len;

                            while ((len = bin.read(buffer)) > 0) {
                                bout.write(buffer, 0, len);
                            }
                        } catch (IOException e) {
                            System.out.println("Error while reading file contents");
                        }
                    }

                    System.out.println("Archived Successfully");
                    return true;

                } catch (IOException e) {
                    System.out.println("Error occured while archiving files");
                    return false;
                }
            } else {
                System.out.println("Directory is either empty or contains sub-directories");
                return false;
            }
        } else {
            System.out.println("Directory does not exist");
            return false;
        }
    }

    // extracting files from an archive
    protected boolean unbox(String archiveName) {
        // check if archive exists
        if (!fileManager.archiveExists(archiveName)) {
            System.out.println("Archive does not exist");
            return false;
        }

        // create directory
        String directoryPath = archiveName.substring(0, archiveName.lastIndexOf('.')) + "_unboxed";
        File directory = new File(directoryPath);
        if (!directory.mkdir()) {
            System.out.println("Error while creating directory");
            return false;
        }

        // read data
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(archiveName))) {
            boolean isHeader = true;
            int readLength = 0;
            String currFile = null;
            byte excessByte = (byte)0;

            while(true){
                if(isHeader){
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int len = bin.read(buffer);
                    if (len < 0) {
                        bin.close();
                        return true;
                    }

                    String[] headerContents = new String(buffer).split(",");
                    // headerContents = [filename, timestamp, size, padding]

                    // creating file
                    File file = new File(directoryPath + "/" + headerContents[0]);
                    if (!file.createNewFile()) {
                        System.out.println("Error while creating file");
                        return false;
                    }
                    // modifying file's timestamp
                    file.setLastModified(Long.parseLong(headerContents[1]));
            
                    readLength = Integer.parseInt(headerContents[2]);
                    currFile = file.getAbsolutePath();
                    isHeader = false;
                    excessByte = buffer[len - 1];
                } else {
                    // write file contents
                    try(BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(currFile, true))){
                        // dynamic buffer size
                        byte[] buffer2 = new byte[readLength - 1];
                        int len = bin.read(buffer2);
                        
                        if(len < 0){
                            bin.close();
                            return true;
                        } else {
                            bout.write(excessByte);
                            bout.write(buffer2, 0, len);
                        }
                        // resetting data for next file
                        readLength = 0;
                        isHeader = true;
                        currFile = null;
                        
                    } catch (IOException e){
                        System.out.println("Error while writing file contents");
                        return false;
                    }
                }
                
            }

        } catch (IOException e) {
            System.out.println("Error while reading archive");
            return false;
        }
    }

    // listing contents of an archive
    protected boolean list(String archiveName) {
        return false;
    }

    // adding a new file to an existing archive
    protected boolean add(String fileName, String archiveName) {
        return false;
    }

    // extracting metadata of the file
    private String determineFileHeader(File file) {
        StringBuilder str = new StringBuilder();

        str.append(file.getName()) // file extension
                .append(",")
                .append(file.lastModified()) // timestamp
                .append(",")
                .append(file.length()) // size
                .append(",");

        // making sure the size becomes equal to BUFFER_SIZE
        // padding extra data
        if (str.length() < BUFFER_SIZE) {
            StringBuilder paddedData = new StringBuilder();
            for (int i = 0; i < (BUFFER_SIZE - str.length()) - 1; i++) {
                paddedData.append('0');
            }
            str.append(paddedData.toString());
        }

        return str.toString();
    }

}