package spreadsheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private int MIN_SIZE_ARGS = 3;
    private int COMMAND_POSITION = 2;
    private int COMMAND_FIRST_ARG = 3;
    private int HAS_ONE_ARG = 4;
    private int HAS_TWO_ARGS = 5;

    private static Logger LOG = LoggerFactory
            .getLogger(Application.class);

    @Autowired
    private SheetsService sheetsService;

    @Autowired
    private ShareService shareService;

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {

        if(args.length == 0){
            printManual();

        }else if(args.length > 0 ) {

            if(args.length >= MIN_SIZE_ARGS && args[COMMAND_FIRST_ARG].equals("--help")) {
                printHelp(args[COMMAND_POSITION]);
            }else {
                if (routeCommand("new-sheet",args)) {
                    runCommandNewSheet(args);
                }else if (routeCommand("import-csv",args)) {
                    runCommandImportCSV(args);
                }else if (routeCommand("insert-row",args)) {
                    runCommandInsertRow(args);
                }else{
                    printCommandNotFound(args[0]);
                }
            }
        }

    }

    private void runCommandInsertRow(String[] args) {

    }

    private void runCommandImportCSV(String[] args) {

    }

    private void runCommandNewSheet(String[] args) {

        try {

            if(args.length == HAS_ONE_ARG) {
                String response = sheetsService.create(args[COMMAND_FIRST_ARG]);
                shareService.shareSpreadSheet(response);
            }else{
                printHelp("new-sheet");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean routeCommand(String command, String... args){
        String argsDesc = "";
        for(Object arg:args){
            argsDesc=argsDesc+arg.toString();
        }
        System.out.println("command:" + command + " args:" + argsDesc);
        return (args.length >= MIN_SIZE_ARGS && command.equals(args[COMMAND_POSITION]));
    }

    private void printManual() {
        System.out.println("\n");
        System.out.println("Usage: java -jar google-spread-sheet.jar");
        System.out.println("\n");
        System.out.println("A lightweighht commandline utility for create and manipulate google spreadsheets over API.");
        System.out.println("\n");
        System.out.println("Commands:");
        System.out.println("  new-sheet     Create new sheet on google and return the fileId");
        System.out.println("  import-csv    Import a CSV file for existent sheet by fileId");
        System.out.println("  insert-row    Insert a new row with data on sheet");
        System.out.println("\n");
        System.out.println("Run 'java -jar google-spread-sheet.jar COMMAND --help' for more information on a command.");
    }

    private void printHelp(String command) {
        if(command.equals("new-sheet")){
            System.out.println("\n");
            System.out.println("Usage: java -jar google-spread-sheet.jar new-sheet FILENAME");
            System.out.println("\n");
        }else if(command.equals("import-csv")){
            System.out.println("\n");
            System.out.println("Usage: java -jar google-spread-sheet.jar import-csv CSVFILEPATH");
            System.out.println("\n");
        }else if(command.equals("insert-row")){
            System.out.println("\n");
            System.out.println("Usage: java -jar google-spread-sheet.jar insert-row \"CSVTEXT\"");
            System.out.println("\n");
        }else{
            printCommandNotFound(command);
        }
    }

    private void printCommandNotFound(String command){
        System.out.println("\n");
        System.out.println("command \"" + command + "\" not found.");
    }
}