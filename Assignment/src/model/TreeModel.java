package model;

import main.ClassificationType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by j.reddy.yedla on 10/20/2016.
 *  This Class contains setter and getter methods to store the parsed CSV data and to print the Tree structure format.
 */
public class TreeModel implements Comparable  {
    private String name;
    private String classification;
    private String type;
    private int id;
    private int parentId;
    private int size;
    private int checksum;
    private TreeModel parentTreeModel;
    private List<TreeModel> childTreeModel;

    public TreeModel(int id, int parentId, String name, String type, int size, String classification, int checksum) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.type = type;
        this.size = size;
        this.classification = classification;
        this.checksum = checksum;
    }

    public TreeModel(TreeModel data)
    {
        //this.treeModel = data;
        this.childTreeModel = new ArrayList<TreeModel>();
    }
    public String getName() {
        return name;
    }

    public String getClassification() {
        return classification;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addChild(TreeModel child) {
        this.childTreeModel.add(child);
    }

    public void setParent(TreeModel parent) {
        this.parentTreeModel = parent;
    }

    public TreeModel getData() {
        return this;
    }

    public List<TreeModel> getChildren() {
        return this.childTreeModel;
    }

    @Override
    public int compareTo(Object object) {
        if (this.getClass().isAssignableFrom(object.getClass())) {
            return (this.name.compareTo(((TreeModel) object).getName()));
        }
        return 0;
    }


    public String convertTreeToString(int directoryLevel) {
        String treeTxt = "";
        if (directoryLevel == 0) {
            treeTxt = treeTxt+this.appendTreeNodeToString(directoryLevel);
        }
        if (childTreeModel != null && childTreeModel.size() > 0) {
            directoryLevel=directoryLevel+1;
            for (Iterator<TreeModel> iterator = childTreeModel.iterator(); iterator.hasNext(); ) {
                TreeModel childNode = iterator.next();
                treeTxt = treeTxt+childNode.getData().appendTreeNodeToString(directoryLevel);
                treeTxt = treeTxt+childNode.convertTreeToString(directoryLevel);
            }
        }
        return treeTxt;
    }

    /**
     *  Provides the space level while printing the Tree structure.
     */
    private String addNewlineOrSpace(int level) {
        String spaceString = "";
        if(level>0){
            spaceString =spaceString+"\n";
        }
        for (int i = 0; i < level; i++) {
            spaceString =spaceString+ " ";
        }
        return spaceString;
    }

    /**
     * Used to Print Tree structure.
     */
    public String appendTreeNodeToString(int spacesToAdd) {
        String node = "";
        if (type.equalsIgnoreCase(ClassificationType.DIRECTORY.stringValue())) {
            node = addNewlineOrSpace(spacesToAdd) + "name = " + name + ", type = " + type + ", size = " + size;
        } else {
            node = addNewlineOrSpace(spacesToAdd) + "name = " + name + ", type = " + type + ", size = " + size + ", classification = " + classification + ", checksum = " + checksum;
        }
        return node;
    }


}
