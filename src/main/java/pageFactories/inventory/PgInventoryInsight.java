package pageFactories.inventory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utilities.Reports;
import utilities.Utility;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class PgInventoryInsight {
    public static WebDriver driver;

    static String actionDropDownMenu_xpath = "//*[text()='Actions']";

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


    public static void clickOnPlayButton() throws Exception {
        String playButton_id = "InsightMenuApply";
        try {
            Utility.getLocator(playButton_id, "id").click();
            Thread.sleep(1000);
            Reports.pass("Click on Play button", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Play button is not clicked", e.toString());
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

    public static void waitForDisappearanceOfToastMsg(){
        String popup_id = "toast-container";
        try{
            while(Utility.getLocatorWithoutWait(popup_id,"id").isDisplayed()){
                Thread.sleep(500);
            }
        }catch(Exception e){
            System.out.println("toast disappeared");
        }

    }


    public static void enterLPNumberInFilterPane(String lpNo) throws Exception {
        String licensePlateField_id = "BasicCriteriaLicensePlate";
        try {
            Utility.getLocator(licensePlateField_id, "id").clear();
            Utility.getLocator(licensePlateField_id, "id").sendKeys(lpNo);
            Reports.pass("Enter LP number <b>" + lpNo + "</b> in basic criteria", "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("LP number is not entered", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void selectInventory(String inventoryName) throws Exception {
        String checkboxOfInventory_xpath = "//*[text()='" + inventoryName + "']/..//span[@name='chk']";
        String nameOfInventory_xpath = "//*[text()='" + inventoryName + "']";
        try {
            if (Utility.getLocator(checkboxOfInventory_xpath, "xpath").
                    getAttribute("data-chk").equalsIgnoreCase("off")) {
                Utility.getLocator(nameOfInventory_xpath, "xpath").click();
            }
            Reports.pass("Select checkbox of <b>" + inventoryName + "</b> inventory.", "Selected successfully.");
        } catch (Exception e) {
            Reports.fail(inventoryName + " inventory is not selected.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clickOnAdjust() throws Exception {
        String adjust_xpath = "//a[text()='Adjust']";
        try {
            Utility.getLocator(actionDropDownMenu_xpath, "xpath").click();
            Utility.getLocator(adjust_xpath, "xpath").click();
            if (Utility.driver.getTitle().equalsIgnoreCase("Inventory adjustment")) {
                Reports.pass("Go to Actions menu and click on Adjust.", "Clicked successfully and Inventory adjustment page is displayed.");
            } else Reports.fail("Inventory adjustment page is not displayed.", "--");

        } catch (Exception e) {
            Reports.fail("Adjust is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clickOnTransfer() throws Exception {
        String transfer_xpath = "//a[text()='Transfer']";
        try {
            Utility.getLocator(actionDropDownMenu_xpath, "xpath").click();
            Utility.getLocator(transfer_xpath, "xpath").click();
            if (Utility.driver.getTitle().equalsIgnoreCase("Inventory transfer")) {
                Reports.pass("Go to Actions menu and click on Transfer.", "Clicked successfully and Inventory Transfer page is displayed.");
            } else {
                Reports.fail("Inventory Transfer page is not displayed.", "--");
                throw (new Exception("Inventory Transfer page is not displayed."));
            }

        } catch (Exception e) {
            Reports.fail("Transfer is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clickOnCreateCount() throws Exception {
        String createCount_xpath = "//a[text()='Create Count']";
        try {
            Utility.getLocator(actionDropDownMenu_xpath, "xpath").click();
            Utility.getLocator(createCount_xpath, "xpath").click();
            Reports.pass("Go to Actions menu and click on Create Count.", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Create Count is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void switchOnAllLPToggleButton() throws Exception {
        String allLPToggleButton_xpath = "//*[@id='ScreenControlToggleSwitch3452']/div";
        try {
            if (Utility.getLocator(allLPToggleButton_xpath + "/div", "xpath").
                    getAttribute("class").toLowerCase().contains("off")) {
                Utility.getLocator(allLPToggleButton_xpath, "xpath").click();
                Reports.pass("Turn on SHOW All LP toggle button", "Turned on successfully.");
            } else Reports.pass("Turn on SHOW All LP toggle button", "Button is already on.");

        } catch (Exception e) {
            Reports.fail("Show All LP button not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static String fetchAvailableQty(String ItemNumber, String inventoryLocation) {
        return null;
    }

    public static String fetchAllocatedQty(String ItemNumber, String inventoryLocation) {
        return null;
    }

    public static String fetchSuspenseQty(String ItemNumber, String inventoryLocation) {
        return null;
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

    public static String verifyQuantityInInventoryInsight(String fetchItem, String expectedQtyAfterCC) throws Exception {
        List<WebElement> Elements_td;
        String returnItem = null;
        String actualQuantity;
        String inventory_xpath = "//table[@data-dbtable='Metadata_Insight_Inventory_View']//tr//td[contains(@aria-describedby,'" + fetchItem + "')]";
        try {
            Elements_td = Utility.getLocatorList(inventory_xpath, "xpath");
            for (int j = 0; j < Elements_td.size(); j++) {
                actualQuantity = Elements_td.get(j).getText();
                if (actualQuantity.equals(expectedQtyAfterCC)) {
                    returnItem = expectedQtyAfterCC;
                    Reports.pass("Verify "+fetchItem+" quantity under Inventory Insight section.", "Quantity is : "+ returnItem+" which is expected");
                } else {
                    returnItem = actualQuantity;
                    Reports.fail("Verify "+fetchItem+" quantity under Inventory Insight section.", "Actual quantity is " + returnItem+" which is not expected");
                    Assert.fail();
                }
                if (returnItem != null) {
                    break;
                }
            }
        } catch (Exception e) {
            Reports.fail("Inventory record  is not found under inventory insight.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
        return returnItem;
    }

    public static void ZoomLeavelToDecrease(WebDriver driver) throws InterruptedException, AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_MINUS);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_MINUS);
        Thread.sleep(5000);

    }
    public static void ZoomLeavelToIncrease(WebDriver driver) throws InterruptedException, AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_EQUALS);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_EQUALS);
        Thread.sleep(5000);

    }
    public static void clickOnFilterIfOpen() throws Exception {
        String filterIcon_xpath = "//a[@data-toggle='search']";
        try {
            Utility.getLocator(filterIcon_xpath, "xpath").click();
            Reports.pass("Close filter section", "Closed successfully.");
        } catch (Exception e) {
            Reports.fail("Close filter section is not clicked", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void clickOnStatusChange() throws Exception {
        String statusChange_xpath = "//a[text()='Status change']";
        try {
            Utility.getLocator(actionDropDownMenu_xpath, "xpath").click();
            Utility.getLocator(statusChange_xpath, "xpath").click();
            if (Utility.driver.getTitle().equalsIgnoreCase("Inventory Status change")) {
                Reports.pass("Go to Actions menu and click on Status change.", "Clicked successfully and Inventory status change page is displayed.");
            } else Reports.fail("Inventory status change page is not displayed.", "--");

        } catch (Exception e) {
            Reports.fail("Status change is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static String verifyRowDataInInventoryInsight(String header, String value) throws Exception {
        List<WebElement> Elements_td;
        String returnItem = null;
        String actualQuantity;
        String inventory_xpath = "//table[@data-dbtable='Metadata_Insight_Inventory_View']//tr//td[contains(@aria-describedby,'" + header + "')]";
        try {
            Elements_td = Utility.getLocatorList(inventory_xpath, "xpath");
            for (int j = 0; j < Elements_td.size(); j++) {
                actualQuantity = Elements_td.get(j).getText();
                if (actualQuantity.equals(value)) {
                    returnItem = value;
                    Reports.pass("Verify "+header+" under Inventory Insight section.", "Record is : "+ returnItem+" which is expected");
                } else {
                    returnItem = actualQuantity;
                    Reports.fail("Verify "+header+"  under Inventory Insight section.", "Actual Record is " + returnItem+" which is not expected");
                    Assert.fail();
                }
                if (returnItem != null) {
                    break;
                }
            }
        } catch (Exception e) {
            Reports.fail("Inventory record  is not found under inventory insight.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
        return returnItem;
    }

}
