package pageFactories.RF;

import org.openqa.selenium.Keys;
import utilities.Reports;
import utilities.Utility;

public class PgCycleCount {
    public static void verifyCC() throws Exception {

        String CCheader_xpath = "//h3[text()='Cycle count']";

        try {
            Utility.getLocatorWithoutWait(CCheader_xpath, "xpath");
            Reports.pass("Verifying Cycle count header." , "Displayed");
        } catch (Exception e) {
            Reports.fail("Verifying Cycle count header.", "Not displayed.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
    public static void enterLocation(String location) throws Exception {

        String fromLocationInputField_id = "LOCATIONVERIFY";

        try {
            Utility.getLocator(fromLocationInputField_id, "id").sendKeys(location + Keys.TAB);
            Reports.pass("Enter Location " + location, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter Location " + location, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static void enterItemNumber(String itemNumber) throws Exception {

        String itemInputField_xpath = "//input[contains(@onblur,'Item')]";

        try {
            Utility.getLocator(itemInputField_xpath, "xpath").sendKeys(itemNumber + Keys.TAB);
            Reports.pass("Enter item number " + itemNumber, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter item number " + itemNumber, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
    public static void enterQty(String Qty) throws Exception {

        String QtyInputField_id = "COUNTQTY";

        try {
            Utility.getLocator(QtyInputField_id, "id").sendKeys(Qty + Keys.TAB);
            Reports.pass("Enter Qty " + Qty, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter Qty " + Qty, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
    public static void clickOnConfirmButton() throws Exception {

        String ConfirmButton_id = "buttonConfirm";

        try {
            Utility.getLocator(ConfirmButton_id, "id").click();
            Reports.pass("Click on Confirm button", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Click on ConfirmK button", "Not Clicked.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
    public static void clickOnDoneButton() throws Exception {

        String DoneButton_id = "button_done";

        try {
            Utility.getLocator(DoneButton_id, "id").click();
            Reports.pass("Click on Done button", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Click on Done button", "Not Clicked.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
}
