package pageFactories.RF;

import org.openqa.selenium.Keys;
import utilities.Reports;
import utilities.Utility;

public class PgToLicensePlate {

    public static void enterLPNumber(String LPN) throws Exception {

        String LPnInputField_id = "toContID";

        try {
            Utility.getLocator(LPnInputField_id, "id").sendKeys(LPN + Keys.TAB);
            Reports.pass("Enter LP number " + LPN, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter LP Number " + LPN, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static void enterQty(String qty) throws Exception {

        String qtyField_id = "putAwayQty";

        try {
            Utility.getLocator(qtyField_id, "id").sendKeys(qty);
            Reports.pass("Enter Qty " + qty, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter Qty " + qty, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static void clickOKButton() throws Exception {

        String OKButton_xpath = "//*[@value='OK']";

        try {
            Utility.getLocator(OKButton_xpath, "xpath").click();
            Reports.pass("Click On OK Button", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Click On OK Button", "Not clicked.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
