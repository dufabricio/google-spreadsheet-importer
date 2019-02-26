package dufabricio.googlespreadsheet;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CSVReader {

    public List<List<Object>> getValuesFromCsv(String csvPath){

        String csvFile = csvPath;
        String line = "";
        String cvsSplitBy = ",";

        List<List<Object>> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                List<Object> row = Arrays.asList(line.split(cvsSplitBy));
                result.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}
