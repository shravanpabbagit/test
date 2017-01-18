package main;

import implementation.FileParserService;
import model.TreeModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by j.reddy.yedla on 10/20/2016.
 * This class is used to print a string containing file nodes in required format.
 */
public class Execution {

    public static void main(String args[]) {
        FileParserService directoryStructureService = new FileParserService();
        List<TreeModel> directories = directoryStructureService.csvFileParser("./src/main/resources/directory-structure.csv");
        TreeModel directoryTree = directoryStructureService.folderStructureCreation(directories);
        String showData = directoryTree.convertTreeToString(0);

        //To print tree format.
        System.out.println(" TREE STRUCTURE");
        System.out.println("-------------------------------");
        System.out.println(showData);

        //To print a string containing all file nodes with classification "Secret".
        System.out.println("\n\nPRINTING SECRET");
        System.out.println("-------------------------------");
        showData = directoryStructureService.getDataByClassification(directories, ClassificationType.SECRET.stringValue());
        System.out.println(showData);

        //To print a string containing file nodes with classification "Top secret"
        System.out.println("\n\nPRINTING TOP SECRET");
        System.out.println("-------------------------------");
        showData = directoryStructureService.getDataByClassification(directories, ClassificationType.TOP_SECRET.stringValue());
        System.out.println(showData);

        //To print a string containing all file nodes with classification "Secret" or "Top secret".
        System.out.println("\n\nSECRET OR TOP SECRET");
        System.out.println("-------------------------------");
        List<String> classifications = new ArrayList<String>();
        classifications.add(ClassificationType.SECRET.stringValue());
        classifications.add(ClassificationType.TOP_SECRET.stringValue());
        showData = directoryStructureService.getFilesByMultiClassification(directories, classifications);
        System.out.println(showData);

        //To print the sum of size for all file nodes with classification "Public".
        System.out.println("\n\nSIZE OF ALL PUBLIC FILES");
        System.out.println("-------------------------------");
        int sum = directoryStructureService.calculateSizeOfFilesByClassification(directories, ClassificationType.PUBLIC.stringValue());
        System.out.println("The sum of all public files is: " + sum);

        //To print a string containing all file nodes under "folder11" with classification other than "Public".
        System.out.println("\n\nNON PUBLIC FILES OF folder11");
        System.out.println("-------------------------------");
        showData = directoryStructureService.getFilesNotInClassification(directoryTree, ClassificationType.FOLDER.stringValue(), ClassificationType.PUBLIC.stringValue());
        System.out.println(showData);

    }
}
