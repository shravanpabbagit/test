package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by j.reddy.yedla on 10/20/2016.
 * This class providing methods to print the files in DirectoryStructure.
 */
public class DirectoryStructure implements Comparable {
//  private   variables
    private TreeModel treeModel;
    private TreeModel parentTreeModel;
    private List<DirectoryStructure> childTreeModel;

    public DirectoryStructure(TreeModel data) {
        this.treeModel = data;
        this.childTreeModel = new ArrayList<DirectoryStructure>();
    }

    public void addChild(DirectoryStructure child) {
        this.childTreeModel.add(child);
    }

    public void setParent(TreeModel parent) {
        this.parentTreeModel = parent;
    }

    public TreeModel getData() {
        return this.treeModel;
    }

    public List<DirectoryStructure> getChildren() {
        return this.childTreeModel;
    }

    public void setChildren(List<DirectoryStructure> children) {
        this.childTreeModel = children;
    }

    @Override
    public int compareTo(Object object) {
        if (this.getClass().isAssignableFrom(object.getClass())) {
            return (this.treeModel.getName().compareTo(((DirectoryStructure) object).getData().getName()));
        }
        return 0;
    }

    public String convertTreeToString(int directoryLevel) {
        String treeTxt = "";
        if (directoryLevel == 0) {
            treeTxt = treeTxt+treeModel.appendTreeNodeToString(directoryLevel);
        }
        if (childTreeModel != null && childTreeModel.size() > 0) {
            directoryLevel=directoryLevel+1;
            for (Iterator<DirectoryStructure> iterator = childTreeModel.iterator(); iterator.hasNext(); ) {
                DirectoryStructure childNode = iterator.next();
                treeTxt = treeTxt+childNode.getData().appendTreeNodeToString(directoryLevel);
                treeTxt = treeTxt+childNode.convertTreeToString(directoryLevel);
            }
        }
        return treeTxt;
    }


}
