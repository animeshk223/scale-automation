package utilities;


import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author abdul.samad
 */
public class GenerateDynamicSuite {
    @Test
    public static void GenrateTestNG() {

        XmlSuite suite = new XmlSuite();
        suite.setName("SCALE-IHS Regression");


        XmlTest test = new XmlTest(suite);
        test.setName("Regression Test Flows");
        test.setVerbose(4);
        List<XmlClass> classes = new ArrayList<XmlClass>();
        List<XmlSuite> suites = new ArrayList<XmlSuite>();

        //InitializeExcel(ConstantPaths.EXCEL_PATH, "");

        String sheetID = "1YXZb-jfW3p8ITApXNVMaiwEGCWsaF7xTYsLDrbFCNZ4";
        String sheetName = "DynamicSuite";
        int rowToStartFrom = 1;
        Object[][] dynamicSuite = new Object[0][0];

        try {
            dynamicSuite = GoogleSheetHandler.readData(sheetID, sheetName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < dynamicSuite.length; i++) {
            if (dynamicSuite[i][2].equals("Y")) {
                String ScriptPath = dynamicSuite[i][0].toString()+"."+dynamicSuite[i][1].toString();
                System.out.println("*************** " + ScriptPath);
                classes.add(new XmlClass(ScriptPath));
            }
        }

        test.setXmlClasses(classes);
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();
    }

}