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

        LOG.info("EXECUTING : command line runner");
        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }

        try {
            //String response = sheetsService.create("google-spreadsheet-importer");
            shareService.shareSpreadSheet("1uO_k2dgwLNfthn5bxoKCenxL0kx_wfE6bQ8Ra19J614");
            //System.out.println("PLANILHA CRIADA: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}