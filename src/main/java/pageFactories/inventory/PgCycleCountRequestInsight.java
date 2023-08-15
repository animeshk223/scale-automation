package pageFactories.inventory;

import org.openqa.selenium.WebDriver;
import utilities.Reports;
import utilities.Utility;

public class PgCycleCountRequestInsight {

    public static WebDriver driver;

    public static void openFilterSection() throws Exception {
        String filterIcon_xpath = "//a[@data-toggle='search']";
        String filterPane_xpath = "//div[@data-name='SearchPane']";
        String basicCriteria_xpath = "//a[text()='Basic criteria']/..";

        try {
            if (Utility.getLocatorWithoutWait(filterPane_xpath, "xpath").
                    getAttribute("style").equalsIgnoreCase("display: none;")) {
                Utility.getLocator(filterIcon_xpath, "xpath").click();
            }
            if (Utility.getLocator(basicCriteria_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(basicCriteria_xpath, "xpath").click();
            }
            Reports.pass("Open Filter pane and Expand Basic Criteria",
                    "Basic Criteria expanded successfully.");
        } catch (Exception e) {
            Reports.fail("Basic Criteria is not expanded.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void enterLocationInFilterPane(String location) throws Exception {
        String locationField_id = "BasicCriteriaLocation";
        try {
            Utility.getLocator(locationField_id, "id").clear();
            Utility.getLocator(locationField_id, "id").sendKeys(location);
            Reports.pass("Enter location <b>" + location + "</b> in basic criteria", "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Location is not entered", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void enterItemNumberInFilterPane(String itemNumber) throws Exception {
        String itemNumberField_id = "BasicCriteriaItem";
        try {
            Utility.getLocator(itemNumberField_id, "id").clear();
            Utility.getLocator(itemNumberField_id, "id").sendKeys(itemNumber);
            Reports.pass("Enter item number <b>" + itemNumber + "</b> in basic criteria", "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Item number is not entered", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void clickOnPlayButton() throws Exception {
        String playButton_id = "InsightMenuApply";
        try {
            Utility.getLocator(playButton_id, "id").click();
            Reports.pass("Click on Play button", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Play button is not clicked", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void clickOnOpenWork() throws Exception {
        Thread.sleep(1000);
        String OpenWork_xpath = "//div[@id='InventoryInsightIndicatorTileOpenWork']";
        try {
            Utility.getLocator(OpenWork_xpath, "xpath").click();
            Reports.pass("Click on Open work.", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Open work is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void switchOFFPendingReviewToggleButton() throws Exception {
        String pendingReviewToggleButton_xpath = "//*[@id='SearchPaneStatusPendingReview']//..";
        try {
            if (Utility.getLocator(pendingReviewToggleButton_xpath, "xpath").
                    getAttribute("class").toLowerCase().contains("off")) {

                Reports.pass("Turn Off Pending Review Toggle button", "Already Turned OFF Pending Review Toggle .");
            } else{
                Utility.getLocator(pendingReviewToggleButton_xpath, "xpath").click();
                Reports.pass("Turn Off Pending Review toggle button", "Turned OFF Pending Review Toggle.");
            }

        } catch (Exception e) {
            Reports.fail("Pending Review Toggle button not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void openStatusFilterSection() throws Exception {

        String status_xpath = "//a[text()='Status']/..";

        try {

            if (Utility.getLocator(status_xpath, "xpath").
                    getAttribute("aria-selected").equalsIgnoreCase("false")) {
                Utility.getLocator(status_xpath, "xpath").click();
            }
            Reports.pass("Open Filter pane and Expand Status Criteria",
                    "Status Criteria expanded successfully.");
        } catch (Exception e) {
            Reports.fail("Status Criteria is not expanded.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}
