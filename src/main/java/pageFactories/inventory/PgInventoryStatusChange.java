package pageFactories.inventory;

import org.openqa.selenium.Keys;
import utilities.Reports;
import utilities.Utility;

public class PgInventoryStatusChange {
    public static void selectAdjustmentType(String adjustmentType) throws Exception {
        String inputSearchField_xpath = "//*[@id='InventoryStatusChangeTypeValue']/div/div[3]/input";
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
    public static void selectStatus(String status) throws Exception {
        String inputSearchField_xpath = "//*[@id='QuantityInformationStatusValue']/div/div[3]/input";
        try {
            Utility.getLocator(inputSearchField_xpath, "xpath").clear();
            Utility.getLocator(inputSearchField_xpath, "xpath").sendKeys(status);
            Thread.sleep(500);
            Utility.getLocator(inputSearchField_xpath, "xpath").sendKeys(Keys.TAB);

            Reports.pass("Enter status and press Enter", "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Status is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void clickOnSave() throws Exception {
        String create_xpath = "//a[text()='Save']";
        try {
            Thread.sleep(5000);
            Utility.getLocator(create_xpath, "xpath").click();
            Thread.sleep(12000);
            Reports.pass("Click on Save button.", "Clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Save button is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}
