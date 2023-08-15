package pageFactories.RF;

import utilities.Reports;
import utilities.Utility;

public class PgWorkProfileSelection {

    public static void clickOnAllWork() throws Exception {
        String allWork_xpath = "//*[text()='All Work']";

        try {
            Utility.getLocator(allWork_xpath, "xpath").click();
            Reports.pass("Click on All Work link","Clicked successfully.");

            if (Utility.driver.getTitle().equalsIgnoreCase("Begin work")) {
                Reports.pass("Verify if All Work page is displayed.", "Verified successfully.");
            } else Reports.fail("Verify if All Work page is displayed.", "Verification failed.");

        } catch (Exception e) {
            Reports.fail("RF page is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void clickOnCycleCount() throws Exception {
        String allWork_xpath = "//*[text()='Cycle Count']";

        try {
            Utility.getLocator(allWork_xpath, "xpath").click();
            Reports.pass("Click on Cycle Count link","Clicked successfully.");

            if (Utility.driver.getTitle().equalsIgnoreCase("Begin work")) {
                Reports.pass("Verify if Begin  Work page is displayed.", "Verified successfully.");
            } else Reports.fail("Verify if Begin Work page is displayed.", "Verification failed.");

        } catch (Exception e) {
            Reports.fail("RF page is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}