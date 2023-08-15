package pageFactories.RF;

import org.openqa.selenium.WebElement;
import utilities.Reports;
import utilities.Utility;

import java.util.List;

public class PgSerialNumEntryRF {

    public static void enterSerialNumber(String serialNumbers) throws Exception {

        String serialNumberInputField_name = "SNUM0";
        String okButton_xpath = "//input[@type='submit']";

        String[] splitSerials = serialNumbers.split(",\\s*");

        try {
            for (String serial : splitSerials) {
                Utility.getLocator(serialNumberInputField_name, "name").sendKeys(serial);
                Utility.getLocator(okButton_xpath, "xpath").click();
                Reports.pass("Enter Serial Number and Click on OK.", serial + " added successfully.");
            }
        } catch (Exception e) {
            Reports.fail("Serial number not entered successfully.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void deselectSerials(int qtyToBeDecreased) throws Exception {

        String serialNumberList_xpath = "//form[@name='FORM1']/table/tbody/tr/td/input[@value='Y']";
        String okButton_xpath = "//input[@type='submit']";

        List<WebElement> listOfCheckboxes = Utility.getLocatorList(serialNumberList_xpath, "xpath");

        try {
            for (int i = 0; i < (-1 * qtyToBeDecreased); i++) {
                listOfCheckboxes.get(i).click();
                Reports.pass("Deselect serials to decrease qty", "Deselected:" + (i + 1));
            }

            Utility.getLocator(okButton_xpath, "xpath").click();
            Reports.pass("Click on OK button.", "clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Deselecting serials failed.", e.toString());
            e.printStackTrace();
            throw new Exception(e);
        }

    }

    public static void selectSerials(int qtyToBeTransferred) throws Exception {

        String serialNumberList_xpath = "//form[@name='FORM1']/table/tbody/tr/td/input[@value='Y']";
        String okButton_xpath = "//input[@type='submit']";

        List<WebElement> listOfCheckboxes = Utility.getLocatorList(serialNumberList_xpath, "xpath");

        try {
            for (int i = 0; i < (qtyToBeTransferred); i++) {
                listOfCheckboxes.get(i).click();
                Reports.pass("Select serials to transfer qty", "Selected:" + (i + 1));
            }

            Utility.getLocator(okButton_xpath, "xpath").click();
            Reports.pass("Click on OK button.", "clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Selecting serials failed.", e.toString());
            e.printStackTrace();
            throw new Exception(e);
        }

    }
}
