package pageFactories.inventory;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utilities.Reports;
import utilities.Utility;

import java.util.List;

public class PgInventoryAdjustment {

    public static void selectAdjustmentType(String adjustmentType) throws Exception {
        String inputSearchField_xpath = "//*[@id='InventoryAdjustmentAdjustmentTypeValue']/div/div[3]/input";
        try {
            Utility.getLocator(inputSearchField_xpath, "xpath").sendKeys(adjustmentType);
            Thread.sleep(500);
            Utility.getLocator(inputSearchField_xpath, "xpath").sendKeys(Keys.ENTER);
            Reports.pass("Enter Adjustment type and press Enter", "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Adjustment type is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static boolean verifyLocationToBeAdjusted(String locationName) throws Exception {
        String locationField_id = "LocationInformationLocationValueEditingInput";
        try {
            if (Utility.getLocator(locationField_id, "id").getAttribute("value").equalsIgnoreCase(locationName)) {
                Reports.pass("Location to be adjusted is displayed as expected", locationName);
                return true;
            } else {
                Reports.fail("Location to be adjusted is not displayed as expected", "");
                return false;
            }
        } catch (Exception e) {
            Reports.fail("Cannot verify the location", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static boolean verifyItemToBeAdjusted(String itemNumber) throws Exception {
        String itemField_id = "ItemInformationItemValueEditingInput";
        try {
            if (Utility.getLocator(itemField_id, "id").getAttribute("value").equalsIgnoreCase(itemNumber)) {
                Reports.pass("Item to be adjusted is displayed as expected", itemNumber);
                return true;
            } else {
                Reports.fail("Item to be adjusted is not displayed as expected", "");
                return false;
            }
        } catch (Exception e) {
            Reports.fail("Cannot verify the Item", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void enterQtyToAdjust(String qty) throws Exception {
        String qtyField_id = "QuantityInformationQuantityValueEditingInput";
        try {
            Utility.getLocator(qtyField_id, "id").sendKeys(qty + Keys.TAB);
            Reports.pass("Enter the Quantity to adjust:" + qty, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Quantity not entered", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clickOnAdjustButton() throws Exception {
        String adjustButton_id = "InventoryAdjustmentActionAdjust";
        try {
            Utility.getLocator(adjustButton_id, "id").click();
            Reports.pass("Click on Adjust button.", "Clicked Successfully.");
        } catch (Exception e) {
            Reports.fail("Adjust button not clicked", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clearUDField(int UDFieldIndex) throws Exception {
        String UDFieldClear_id = "UserDefinedUDF" + UDFieldIndex + "Value_clearButton";
        String UDFHeader_xpath = "//*[text()='User defined fields']/..";
        try {
            if (Utility.getLocatorWithoutWait(UDFHeader_xpath, "xpath").
                    getAttribute("aria-selected").equalsIgnoreCase("false")) {
                Utility.getLocator(UDFHeader_xpath, "xpath").click();
            }
            if(!getUDFieldValue(UDFieldIndex).isEmpty()){
                Utility.getLocator(UDFieldClear_id, "id").click();
                Reports.pass("Clear the User Defined Field " + UDFieldIndex, "Cleared Successfully.");
            }else{
                Reports.info("No value found in User Defined Field " + UDFieldIndex, "");
            }
        } catch (Exception e) {
            Reports.fail("UDF" + UDFieldIndex + " not cleared.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static String getUDFieldValue(int UDFieldIndex) throws Exception {
        String UDField_id = "UserDefinedUDF" + UDFieldIndex + "ValueEditingInput";
        String UDFHeader_xpath = "//*[text()='User defined fields']/..";
        try {
            if (Utility.getLocatorWithoutWait(UDFHeader_xpath, "xpath").
                    getAttribute("aria-selected").equalsIgnoreCase("false")) {
                Utility.getLocator(UDFHeader_xpath, "xpath").click();
            }
            return Utility.getLocator(UDField_id, "id").getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void enterValueInUDField(int UDFieldIndex, String value) throws Exception {
        String UDField_id = "UserDefinedUDF" + UDFieldIndex + "ValueEditingInput";
        String UDFHeader_xpath = "//*[text()='User defined fields']/..";
        try {
            if (Utility.getLocatorWithoutWait(UDFHeader_xpath, "xpath").
                    getAttribute("aria-selected").equalsIgnoreCase("false")) {
                Utility.getLocator(UDFHeader_xpath, "xpath").click();
            }
            Utility.getLocator(UDField_id, "id").clear();
            Utility.getLocator(UDField_id, "id").sendKeys(value);
            Reports.pass("Enter " + value + " in the User Defined Field " + UDFieldIndex, "Clicked Successfully.");
        } catch (Exception e) {
            Reports.fail(value + " is not entered in UDF" + UDFieldIndex, e.toString());
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


    public static void selectSerialsToNegativeAdjustment(int Qty) throws Exception {
        String listOfSerials_xpath = "//*[@id='serialnumberkoGrid']/tbody/tr";

        try {
            List<WebElement> list = Utility.getLocatorList(listOfSerials_xpath, "xpath");
            if (list.size() > 0) {
                for (int i = 0; i < (-1 * Qty); i++) {
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


    public static void enterSerialNumberforPositiveAdjustment(String serialNumber) throws Exception {
        String serialNumberInputField_xpath = "//input[@id='Textbox0']";
        String addSerialButton_id = "addSerialNumber";

        String[] splitSerials = serialNumber.split(",\\s*");

        try {
            for (String serial : splitSerials) {
                Utility.getLocator(serialNumberInputField_xpath, "xpath").sendKeys(serial);
                Utility.getLocator(addSerialButton_id, "id").click();
                Reports.pass("Enter Serial Number and Click on Add.", serial + " added successfully.");
            }
        } catch (Exception e) {
            Reports.fail("Serial number not entered successfully.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}