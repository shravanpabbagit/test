
package test;

import implementation.FileParserService;
import model.TreeModel;
import model.DirectoryStructure;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import java.util.List;


/**
 * Created by j.reddy.yedla on 10/20/2016.
 */

public class DirectoryStructureTest extends TestCase {
    FileParserService fileParserService = null;
    List<TreeModel> listOfFiles = null;
    TreeModel directoryStructure = null;
//set up method
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        fileParserService = new FileParserService();
        listOfFiles = fileParserService.csvFileParser("./src/main/resources/directory-structure-treeTestCases.csv");
        directoryStructure = fileParserService.folderStructureCreation(listOfFiles);
    }
    @After
    public void closeGlobalResources() {
        FileParserService fileParserService = null;
        List<TreeModel> listOfFiles = null;
        TreeModel directoryStructure = null;
    }


    @Test
    public void testConvertTreeToString() throws Exception {
        String expected = "name = folder1, type = directory, size = 90\n"+
                " name = file2, type = file, size = 40, classification = Secret, checksum = 42\n"+
                " name = file3, type = file, size = 50, classification = Public, checksum = 42";
        assertEquals(expected,directoryStructure.convertTreeToString(0));
    }

}

