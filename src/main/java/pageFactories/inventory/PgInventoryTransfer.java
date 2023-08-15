package pageFactories.inventory;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utilities.Reports;
import utilities.Utility;

import java.util.List;

public class PgInventoryTransfer {

    public static void selectTransferType(String transferType) throws Exception {
        String inputSearchField_xpath = "//*[@id='InventoryAdjustmentAdjustmentTypeValue']/div/div[3]/input";
        try {
            Utility.getLocator(inputSearchField_xpath, "xpath").sendKeys(transferType);
            Thread.sleep(500);
            Utility.getLocator(inputSearchField_xpath, "xpath").sendKeys(Keys.ENTER);
            Reports.pass("Enter Transfer type and press Enter", "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Transfer type is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void enterToLocation(String location) throws Exception {
        String toLocationField_id = "LocationInformationToLocationValueEditingInput";
        try {
            Utility.getLocator(toLocationField_id, "id").sendKeys(location + Keys.TAB);
            Reports.pass("Enter the To Location to transfer:" + location, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("To Location not entered", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void enterQtyToTransfer(String qty) throws Exception {
        String qtyField_id = "QuantityInformationQuantityValueEditingInput";
        try {
            Utility.getLocator(qtyField_id, "id").clear();
            Utility.getLocator(qtyField_id, "id").sendKeys(qty + Keys.TAB);
            Reports.pass("Enter the Quantity to transfer:" + qty, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Quantity not entered", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static String getQtyToBeTransfered() throws Exception {
        String qtyField_id = "QuantityInformationQuantityValueEditingInput";
        String qty=null;
        try {
            qty=Utility.getLocator(qtyField_id, "id").getAttribute("value");
            Reports.pass("Quantity to be transfered is:" + qty, "Fetched successfully.");
        } catch (Exception e) {
            Reports.fail("Quantity not entered", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }finally {
            return qty;
        }
    }


    public static void clickOnTransferButton() throws Exception {
        String transferBtn_id = "InventoryAdjustmentActionAdjust";
        try {
            Utility.getLocator(transferBtn_id, "id").click();
            Thread.sleep(5000);
            Reports.pass("Click on Transfer Button.", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Not able to click on transfer button.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void verifySerialNumberPlacerScreenIsDisplayed() throws Exception {
        String serialNoPlacer_id = "serialNumberPlaceholder";
        try {
            if (Utility.getLocator(serialNoPlacer_id, "id").isDisplayed()) {
                Reports.pass("Verify if serial number placer is displayed.", "Verified successfully.");
            } else Reports.fail("Verify if serial number placer is displayed.", "Verification failed.");

        } catch (Exception e) {
            Reports.fail("Error occurred while verifying the serial number placer.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void selectSerialsToTransfer(int Qty) throws Exception {
        String listOfSerials_xpath = "//*[@id='serialnumberkoGrid']/tbody/tr";

        try {
            List<WebElement> list = Utility.getLocatorList(listOfSerials_xpath, "xpath");
            if (list.size() > 0) {
                for (int i = 0; i < (Qty); i++) {
                    list.get(i).click();
                }
                Reports.pass("Select Serial numbers.", "Selected successfully.");
            } else Reports.fail("Select Serial numbers.", "no serials displayed.");

        } catch (Exception e) {
            Reports.fail("Error occurred while verifying the serial number placer.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clickOnSaveOfSerialPlacer() throws Exception {
        String saveButtonOfSerialPlacer_id = "saveclickserialinfo";

        try {
            Utility.getLocator(saveButtonOfSerialPlacer_id, "id").click();
            Reports.pass("Click on Save Button", "clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Save button nit clicked successfully.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}
