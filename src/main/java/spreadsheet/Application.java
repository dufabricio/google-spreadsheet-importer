package spreadsheet;

import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private int MIN_SIZE_ARGS = 2;
    private int COMMAND_POSITION = 0;
    private int COMMAND_FIRST_ARG = 1;
    private int HAS_ONE_ARG = 2;
    private int HAS_TWO_ARGS = 3;

    private String CMD_NEW_SHEET = "new-sheet";
    private String CMD_IMPORT_CSV = "import-csv";
    private String CMD_APPEND_ROW = "append-row";

    private static Logger LOG = LoggerFactory
            .getLogger(Application.class);

    @Autowired
    private CSVReader csvReader;

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
                try {
                    if (routeCommand(CMD_NEW_SHEET, args)) {
                        runCommandNewSheet(args);
                    } else if (routeCommand(CMD_IMPORT_CSV, args)) {
                        runCommandImportCSV(args);
                    } else if (routeCommand(CMD_APPEND_ROW, args)) {
                        runCommandAppendRow(args);
                    } else {
                        printCommandNotFound(args[0]);
                    }
                }catch(WrongCommandSintaxException e){
                    printHelp(e.getCommand());
                }
            }
        }

    }

    private void runCommandAppendRow(String[] args) {

    }

    private void runCommandImportCSV(String[] args) {

        if(args.length!=HAS_TWO_ARGS){
            throw new WrongCommandSintaxException(CMD_IMPORT_CSV);
        }

        try {

            String spreadsheetId = args[1];
            String csvPath = args[2];
            List<List<Object>> values = csvReader.getValuesFromCsv(csvPath);
            AppendValuesResponse response = sheetsService.appendValues(spreadsheetId, "A1:A1000","RAW",values);
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void runCommandNewSheet(String[] args) {

        try {

            if(args.length == HAS_ONE_ARG) {
                String response = sheetsService.create(args[COMMAND_FIRST_ARG]);
                shareService.shareSpreadSheet(response);
            }else{
                printHelp(CMD_NEW_SHEET);
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
        if(args.length < MIN_SIZE_ARGS){
            throw new WrongCommandSintaxException(command);
        }

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
        System.out.println("  append-row    Insert a new row with data on sheet");
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
        }else if(command.equals("append-rows")){
            System.out.println("\n");
            System.out.println("Usage: java -jar google-spread-sheet.jar append-row \"CSVTEXT\"");
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