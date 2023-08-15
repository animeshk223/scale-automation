package pageFactories.RF;

import org.openqa.selenium.Keys;
import utilities.Reports;
import utilities.Utility;

public class PgBlindCycleCount {

    public static void enterLocation(String location) throws Exception {

        String fromLocationInputField_id = "LOCATIONVERIFY";
        String LocationField_xpath="//tr//td//b[text()='Loc:']";

        if(Utility.getLocator(LocationField_xpath, "xpath").isDisplayed()){
            try {
                Utility.getLocator(fromLocationInputField_id, "id").sendKeys(location + Keys.TAB);
                Reports.pass("Enter Location " + location, "Entered successfully.");
            } catch (Exception e) {
                Reports.info("Enter Location " + location, "Already selected.");
            }

        }else{
            Reports.info("Enter Location " + location, "Location field is not present on page");
        }
    }

    public static void enterItemNumber(String itemNumber) throws Exception {

        String itemInputField_xpath = "//input[@id='BCCITEM']";
        String ItemField_xpath = "//tr//td//b[text()='Item:']";

        if (Utility.getLocator(ItemField_xpath, "xpath").isDisplayed()) {
            try {
                Utility.getLocator(itemInputField_xpath, "xpath").sendKeys(itemNumber + Keys.TAB);
                Reports.pass("Enter item number " + itemNumber, "Entered successfully.");
            } catch (Exception e) {
                Reports.info("Enter item number " + itemNumber, "Already selected.");
            }
        }else{
        Reports.info("Enter Item " + itemNumber, "Item field is not present on page");
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
    public static void verifyBCC() throws Exception {

        String BCCheader_xpath = "//h3[text()='Blind cycle count']";

        try {
            Utility.getLocatorWithoutWait(BCCheader_xpath, "xpath");
            Reports.pass("Verifying blind cycle count header." , "Displayed");
        } catch (Exception e) {
            Reports.fail("Verifying blind cycle count header.", "Not displayed.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
    public static void enterLicensePlateNumber(String LicensePlateNumber) throws Exception {

        String LPInputField_id = "BCCLP";

        try {
            Utility.getLocator(LPInputField_id, "id").sendKeys(LicensePlateNumber + Keys.TAB);
            Thread.sleep(500);
            Reports.pass("Enter LicensePlate number " + LicensePlateNumber, "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Enter LicensePlate number " + LicensePlateNumber, "Not Entered.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
    public static void verifyingverifyCC() throws Exception {

        String VerifyCCheader_xpath = "(Verify count)";

        try {
            Utility.getLocatorWithoutWait(VerifyCCheader_xpath, "xpath");
            Reports.pass("Verifying blind cycle count header." , "Displayed");
        } catch (Exception e) {
            Reports.fail("Verifying blind cycle count header.", "Not displayed.");
            e.printStackTrace();
            throw new Exception(e);
        }

    }
    public static void enterCompanyInBCC(String company) throws Exception {
        String company_xpath = "//select[@name='RF_COMPANY']";

        try {

            Utility.getLocator(company_xpath, "xpath").sendKeys(company+Keys.TAB);
            Reports.pass("Enter comapany in Blind count page.", "Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Company is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}
