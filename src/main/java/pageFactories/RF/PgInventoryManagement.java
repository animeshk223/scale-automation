package pageFactories.RF;

import org.openqa.selenium.Keys;
import utilities.Reports;
import utilities.Utility;

public class PgInventoryManagement {

    public static void selectAdjustmentType(String adjType) throws Exception {

        String adjTypeDropDown_id = "combobox_id";

        try {
            Utility.selectDropDown(adjTypeDropDown_id, "id", adjType);
            Reports.pass("Select Adjustment Type " + adjType, "Selected successfully.");
        } catch (Exception e) {
            Reports.fail("Select Adjustment Type " + adjType, "Not selected.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }


    public static void enterItemNumber(String itemNumber) throws Exception {

        String itemInputField_id = "item";

        try {
            Utility.getLocator(itemInputField_id, "id").sendKeys(itemNumber + Keys.TAB);
            Reports.pass("Enter item number " + itemNumber, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter item number " + itemNumber, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }


    public static void enterQty(String Qty) throws Exception {

        String QtyInputField_id = "quantity";

        try {
            Utility.getLocator(QtyInputField_id, "id").sendKeys(Qty + Keys.TAB);
            Reports.pass("Enter Qty " + Qty, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter Qty " + Qty, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }


    public static void enterLPNumber(String LPN) throws Exception {

        String LPnInputField_id = "RFLOGISTICSUNIT";

        try {
            Utility.getLocator(LPnInputField_id, "id").sendKeys(LPN + Keys.TAB);
            Reports.pass("Enter LP number " + LPN, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter LP Number " + LPN, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    public static void enterLocation(String location) throws Exception {

        String fromLocationInputField_id = "location";

        try {
            Utility.getLocator(fromLocationInputField_id, "id").sendKeys(location + Keys.TAB);
            Reports.pass("Enter Location " + location, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter Location " + location, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    public static void enterFromLocation(String location) throws Exception {

        String fromLocationInputField_id = "fromLoc";

        try {
            Utility.getLocator(fromLocationInputField_id, "id").sendKeys(location + Keys.TAB);
            Reports.pass("Enter From Location " + location, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter From Location " + location, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    public static void enterToLocation(String location) throws Exception {

        String fromLocationInputField_id = "toLoc";

        try {
            Utility.getLocator(fromLocationInputField_id, "id").sendKeys(location + Keys.TAB);
            Reports.pass("Enter To Location " + location, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter To Location " + location, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    public static void clickOnOKButton() throws Exception {

        String okButton_id = "submit1";

        try {
            Utility.getLocator(okButton_id, "id").click();
            Reports.pass("Click on OK button", "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Click on OK button", "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }


    public static void verifyInventoryManagementPageDisplayed() throws Exception {

        try {
            if (Utility.driver.getTitle().equalsIgnoreCase("Inventory management")) {
                Reports.pass("Verify if Inventory Management page is displayed.",
                        "Successfully verified.");
            } else Reports.fail("Verify if Inventory management page is displayed.", "Verification failed.");

        } catch (Exception e) {
            Reports.fail("Inventory management page is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void enterLicensePlateNumber(String LicensePlateNumber) throws Exception {

        String LPInputField_id = "RFLOGISTICSUNIT";

        try {
            Utility.getLocator(LPInputField_id, "id").sendKeys(LicensePlateNumber + Keys.TAB);
            Reports.pass("Enter LicensePlate number " + LicensePlateNumber, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter LicensePlate number " + LicensePlateNumber, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
}
