package implementation;

import model.TreeModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by j.reddy.yedla on 10/20/2016.
 * This class providing methods to perform various operations on the directory structure.
 */
public class FileParserService {

    /**
     *  This method Reads the CSV file and Sorts the collection of TreeModel objects
     */
    public List<TreeModel> csvFileParser(String filePath){
        FileParser fileParser = new FileParser();
        List<TreeModel> listOfFiles = fileParser.toReadCSVFile(filePath);
        // Sorting the list here helps in printing the file when queried by classification in a given order
        Collections.sort(listOfFiles);
        return listOfFiles;
    }
    /**
     * It Finds the root node from the collection of TreeModel .
     */
    public TreeModel folderStructureCreation(List<TreeModel> listOfFiles) {
        TreeModel directoryTree = null;
        TreeModel root = null;
        root = this.findRootNode(listOfFiles);
        // Creates a tree structure from the collection directories
        directoryTree = this.createTreeStructure(root, listOfFiles);
        return directoryTree;
    }

    /**
     * Used to Iterate the directories and Finds the Root Node.
     */

    private TreeModel findRootNode(List<TreeModel> listOfFiles) {
        TreeModel currentNode = null;
        Iterator<TreeModel> iterator = listOfFiles.iterator();
        while (iterator.hasNext()) {
            currentNode = iterator.next();
            if (currentNode.getParentId() == 0) {
                break;
            }
        }
        return currentNode;
    }

    /**
     * This method creates relation between parent and child nodes.
     */

    private TreeModel createTreeStructure(TreeModel parent, List<TreeModel> listOfFiles) {
        TreeModel tree = new TreeModel(parent);
        TreeModel currentNode = null;
        TreeModel child = null;
        for (Iterator<TreeModel> iterator = listOfFiles.iterator(); iterator.hasNext(); ) {
            currentNode = iterator.next();
            if (currentNode.getParentId() == parent.getId()) {
                child = createTreeStructure(currentNode, listOfFiles);
                tree.addChild(child);
                parent.setSize(parent.getSize() + currentNode.getSize());
                child.setParent(parent);
            }
        }

        //Sorting the DirectoryStructure Children
        Collections.sort(tree.getChildren());
        return tree;
    }


    /**
     * It used to iterates the classification from the listOfFiles and fetch files based to the classification.
     */
    public String getDataByClassification(List<TreeModel> listOfFiles, String classification) {
        String files = "";
        Iterator<TreeModel> iterator = listOfFiles.iterator();
        while (iterator.hasNext()) {
            TreeModel currentNode =  iterator.next();
            if (currentNode.getClassification() != null && currentNode.getClassification().equalsIgnoreCase(classification)) {
                files = files+currentNode.appendTreeNodeToString(0)+"\n";
            }
        }
        //At the end of each line we added new line. But last line doesn't require one. The substring takes care of that
        files = this.removeNewLineAtEnd(files);
        return files;
    }


    /**
     Used to Prints the data according to the classification.
     */
    public String getFilesByMultiClassification(List<TreeModel> listOfFiles, List<String> classifications) {
        String files = "";
        Iterator<String> iterator = classifications.iterator();
        while (iterator.hasNext()) {
            files += getDataByClassification(listOfFiles, iterator.next())+"\n";
        }
        //At the end of each line we added new line. But last line doesn't require one. The substring takes care of that
        files = this.removeNewLineAtEnd(files);
        return files;
    }

    /**
     *  prints the size of files according to the classification.
     */
    public int calculateSizeOfFilesByClassification(List<TreeModel> listOfFiles, String classification) {
        int sum = 0;
        Iterator<TreeModel> iterator = listOfFiles.iterator();
        while (iterator.hasNext()) {
            TreeModel dm = iterator.next();
            if (dm.getClassification() != null && dm.getClassification().equalsIgnoreCase(classification)) {
                sum = sum + dm.getSize();
            }
        }
        return sum;
    }

    /**
     Used to prints the files which are not in provided classification.
     */
    public String getFilesNotInClassification(TreeModel directoryTree, String parentFolderName, String classification) {
        String files = "";
        String currentFile = "";
        TreeModel parentNode = findParentNode(directoryTree,parentFolderName);
        if(parentNode != null){
            List<String> filteredNodes = getChildNodesNotInClassification(parentNode, classification);
            Collections.sort(filteredNodes);
            for (Iterator<String> iterator = filteredNodes.iterator(); iterator.hasNext(); ) {
                files +=iterator.next()+"\n";
            }
            //At the end of each line we added new line. But last line doesn't require one. The substring takes care of that
            files = this.removeNewLineAtEnd(files);
        }

        return files;
    }

    /**
     * Used to eliminate extra line at the end of each line while displaying string of nodes.
     */
    private String removeNewLineAtEnd(String text){
        if(text.length()>1 && (text.charAt(text.length()-1)== '\n')){
            text = text.substring(0,text.length()-1);
        }
        return text;
    }
    /**
     * This method is Used to find the parent node.
     */
    private TreeModel findParentNode(TreeModel directoryTree, String parentFolderName) {
        List<String> files = new ArrayList<String>();
        List<TreeModel> children = null;
        Iterator<TreeModel> iterator = null;
        TreeModel currentChild = null;
        TreeModel parent =  null;
        if (directoryTree.getData().getName().equalsIgnoreCase(parentFolderName)) {
            parent = directoryTree;
        } else {
            children = directoryTree.getChildren();
            iterator = children.iterator();
            while (iterator.hasNext()) {
                currentChild = iterator.next();
                parent = findParentNode(currentChild, parentFolderName);
                if(parent != null){break;}
            }
        }
        return parent;
    }


    /**
     * This method is Used to find the child nodes not in classification.
     */
    private  List<String> getChildNodesNotInClassification(TreeModel directoryTree, String classification) {
        List<String> files = new ArrayList<String>();
        List<TreeModel> children = directoryTree.getChildren();
        Iterator<TreeModel> iterator = children.iterator();
        TreeModel currentChild = null;
        while (iterator.hasNext()) {
            currentChild = iterator.next();
            if ("Directory".equalsIgnoreCase(currentChild.getData().getType())) {
                files.addAll(getChildNodesNotInClassification(currentChild, classification));
            } else {
                if (!currentChild.getData().getClassification().equalsIgnoreCase(classification)) {
                    files.add(currentChild.getData().appendTreeNodeToString(0));
                }
            }

        }
        return files;
    }



}
