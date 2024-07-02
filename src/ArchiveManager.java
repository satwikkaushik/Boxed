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
    File directory = null;
    final int BUFFER_SIZE = 1024;
    final String SEPARATOR = "~boxed&&boxed~";

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
                directory = new File(directoryPath);
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

                            // separating files
                            bout.write(SEPARATOR.getBytes());
                        }
                    }

                    System.out.println("Archived Successfully");
                    return true;

                } catch (IOException e) {
                    System.out.println("Error occured while archiving files");
                    return false;
                }
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
    protected boolean unbox(String archiveName) {
        return false;
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

        String[] fileDeatils = file.getName().split("\\.");

        str.append(fileDeatils[0]); // file name
        str.append(fileDeatils[1]); // file extension

        return str.toString();
    }
}

