package pageFactories.RF;

import utilities.Reports;
import utilities.Utility;

public class PgBeginWork {

    public static void enterWorkUnit(String workUnit) throws Exception {
        String workUnitInoutField_xpath = "//*[@name='location']";

        try {
            Utility.getLocator(workUnitInoutField_xpath, "xpath").sendKeys(workUnit);
            Reports.pass("Enter Work unit","Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Enter Work unit", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void clickOKButton() throws Exception {
        String okButton_id = "bStart";

        try {
            Utility.getLocator(okButton_id, "id").click();
            Reports.pass("Click on OK button","Clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Click on OK button", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void enterLocation(String Loc) throws Exception {
        String workLocationField_xpath = "//*[@id='form1']/table/tbody/tr[1]/td/input";

        try {
            Utility.getLocator(workLocationField_xpath, "xpath").sendKeys(Loc);
            Reports.pass("Enter Location","Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Enter Location", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}