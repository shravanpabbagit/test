package implementation;

import model.TreeModel;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by j.reddy.yedla on 10/20/2016.
 * This class providing methods to parse CSV file.
 */

public class FileParser {

    public List<TreeModel> toReadCSVFile(String filePath) {
        BufferedReader br = null;
        String lines = "";
        String fileDelimiter = ";";
        String[] arrayLines = null;
        List<TreeModel> elements = new ArrayList<TreeModel>();
        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((lines = br.readLine()) != null) {
                if (!lines.startsWith("#")) {
                    arrayLines = new String[7];
                    lines = lines.replaceAll(";;", "; ;");
                    StringTokenizer stringTokenizer = new StringTokenizer(lines, fileDelimiter);
                    int index = 0;
                    while (stringTokenizer.hasMoreTokens()) {
                        arrayLines[index++] = stringTokenizer.nextToken().trim();
                    }

                    elements.add(this.toParseLine(arrayLines));
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(" while processing the CSV file Exception occurred:"+fnfe);
        } catch (Exception e) {
            System.out.println(" while processing the CSV file Exception occurred:"+e);
        }
        return elements;
    }

    private TreeModel toParseLine(String[] line) {
        String name = "";
        String type = "";
        String classification = "";
        int id = 0;
        int parentId = 0;
        int size = 0;
        int checksum = 0;
        TreeModel currentDirectory = null;
        try {
            if (line[0] != null || line[0] != "") {
                id = Integer.parseInt(line[0]);
            }
            if (line[1] != null && line[1].length() > 0) {
                parentId = Integer.parseInt(line[1]);
            }
            if (line[2] != null && line[2].length() > 0) {
                name = line[2];
            }
            if (line[3] != null && line[3].length() > 0) {
                type = line[3];
            }
            if (line[4] != null && line[4].length() > 0) {
                size = Integer.parseInt(line[4]);
            }
            if (line[5] != null && line[5].length() > 0) {
                classification = line[5];
            }
            if (line[6] != null && line[6].length() > 0) {
                checksum = Integer.parseInt(line[6]);
            }
            currentDirectory = new TreeModel(id, parentId, name, type, size, classification, checksum);

        } catch (Exception e) {
            System.out.println(" while parsing the lines Exception occurred: " + e);
        }
        return currentDirectory;
    }


}
