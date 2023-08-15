package pageFactories.RF;

import utilities.Reports;
import utilities.Utility;

public class PgUserDefinedData {

    public static void enterTextInUserDefined(int indexOfUDF, String value) throws Exception {

        String UDF_xpath = "//*[@name='EUD" + indexOfUDF + "']";

        try {
            if (Utility.driver.getTitle().equalsIgnoreCase("User defined data")) {
                Utility.getLocator(UDF_xpath, "xpath").clear();
                Utility.getLocator(UDF_xpath, "xpath").sendKeys(value);
                Reports.pass("Enter user " + value + " in UDF " + indexOfUDF, "Entered successfully.");
            }
        } catch (Exception e) {
            Reports.fail("Enter user " + value + " in UDF " + indexOfUDF, "not entered.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    public static void clearTextInUserDefined(int indexOfUDF) throws Exception {

        String UDF_xpath = "//*[@name='EUD" + indexOfUDF + "']";

        try {
            Utility.getLocator(UDF_xpath, "xpath").clear();
            Reports.pass("Clear the text in UDF " + indexOfUDF, "Cleared successfully.");
        } catch (Exception e) {
            Reports.fail("Clear the text in UDF " + indexOfUDF, "Not cleared.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    public static void clickOKButton() throws Exception {

        String OKButtonOfUDF_xpath = "//*[@value='OK']";

        try {
            if (Utility.driver.getTitle().equalsIgnoreCase("User defined data")) {
                Utility.getLocator(OKButtonOfUDF_xpath, "xpath").click();
                Reports.pass("Click On OK Button", "Clicked successfully.");
            }
        } catch (Exception e) {
            Reports.fail("Click On OK Button", "Not clicked.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
