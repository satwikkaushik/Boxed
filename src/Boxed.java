public class Boxed {
    public static void main(String[] args) {

        // check if any arguments are passed
        if(args.length == 0){
            printHelpPage();
            return;
        }

        // operation to perform
        String command = args[0];

        // making right method calls
        switch (command) {
            // creating a new archive
            case "box":
                if(args.length != 2){
                    System.out.println("Invalid number of arguments");
                    return;
                } 
                else {
                    String directoryName = args[1];
                    ArchiveManager archiveManager = new ArchiveManager();
                    archiveManager.box(directoryName);
                }
                break;
        
            // extracting files from an archive
            case "unbox":
                if(args.length != 2){
                    System.out.println("Invalid number of arguments");
                    return;
                }
                else {
                    String archiveName = args[1];
                    ArchiveManager archiveManager = new ArchiveManager();
                    archiveManager.unbox(archiveName);
                }
                break;

            // listing contents of an archive
            case "list":
                if(args.length != 2){
                    System.out.println("Invalid number of arguments");
                    return;
                }
                else {
                    String archiveName = args[1];
                    ArchiveManager archiveManager = new ArchiveManager();
                    archiveManager.list(archiveName);
                }
                break;
            
            // adding a new file to an existing archive
            case "add":
                if(args.length != 3){
                    System.out.println("Invalid number of arguments");
                    return;
                }
                else {
                    String fileName = args[1];
                    String archiveName = args[2];
                    ArchiveManager archiveManager = new ArchiveManager();
                    archiveManager.add(fileName, archiveName);
                    break;
                }

            // else exit
            default:
                System.out.println("Invalid command");
                printHelpPage();
                return;
        }

    }

    private static void printHelpPage(){
        System.out.println("Usage:");
        System.out.println("  box <directory-name> : create an archive");
        System.out.println("  unbox <archive-name> : extract files from an archive");
        System.out.println("  list <archive-name> : list contents of an archive");
        System.out.println("  add <file-name> <archive-name> : add new file to an existing archive");
    }
}
