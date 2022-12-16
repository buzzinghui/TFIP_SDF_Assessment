package task01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailMerge {
    public static void main(String[] args) throws Exception {
        
        String inputFile = args[0];
        String templateFile = args[1];

        if (args.length < 2) {
            System.out.println("Please provide CSV file and tempate file as your command line arguments.");
            return;
        }
        
        List<Map<String, String>> dataList = convertCsvToMap(inputFile);
        String template = convertTemplateFileToString(templateFile);
        parseDataToTemplate(dataList, template);
    }

    public static List<Map<String, String>> convertCsvToMap(String inputFile) {
        
        String delimiter = ",";
        String line = "";
        
        List<Map<String, String>> dataList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String[] columnNames = br.readLine().split(delimiter);
            
            while ((line = br.readLine()) != null) {
                Map<String, String> data = new HashMap<>();
                String[] values = line.split(delimiter);
                for (int i = 0; i < values.length; i++) {
                    if (values[i].contains("\\n")){                        
                        data.put(columnNames[i], values[i].replace("\\n", "\n"));
                    }
                    else {
                        data.put(columnNames[i], values[i]);
                    }
                }
                dataList.add(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public static String convertTemplateFileToString(String templateFile) {
        
        String template = "";
        
        try {
            template = new String(Files.readAllBytes(Paths.get(templateFile)));
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        return template;
    }

    public static void parseDataToTemplate(List<Map<String, String>> dataList, String template) {
        
        dataList.forEach(data -> {
            String emailTemplate = template;
            
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String placeholder = "<<" + entry.getKey() + ">>";
                emailTemplate = emailTemplate.replaceAll(placeholder, entry.getValue());
            }
            System.out.println(emailTemplate);
        });
    }
}
