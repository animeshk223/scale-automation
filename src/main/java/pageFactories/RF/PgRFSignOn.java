package pageFactories.RF;

import com.google.api.client.util.Key;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import utilities.Reports;
import utilities.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.openqa.selenium.By.*;

public class PgRFSignOn {

    public static void clickOnContinueForNewSession() throws Exception {
        String continueBtn_xpath = "//*[@value='Continue']";

        try {
            if (Utility.driver.getTitle().equalsIgnoreCase("Logout")) {
                Utility.getLocator(continueBtn_xpath, "xpath").click();
                Reports.pass("Click on Continue button for new session of RF",
                        "Successfully clicked on Continue button.");
            } else if (Utility.driver.getTitle().equalsIgnoreCase("Select function")) {
                Reports.pass("RF Opened with new session.",
                        "");
            } else Reports.fail("RF page is not displayed.", "");

        } catch (Exception e) {
            Reports.fail("RF page is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clickOnInventoryManagementLink() throws Exception {
        String inventoryManagement_xpath = "//*[text()='Inventory management']";

        try {
            if (Utility.driver.getTitle().equalsIgnoreCase("Select function")) {
                Utility.getLocator(inventoryManagement_xpath, "xpath").click();
                Reports.pass("Click on Inventory management link.",
                        "Successfully clicked on Inventory management link.");
            } else if (!Utility.driver.getTitle().equalsIgnoreCase("Select function")) {
                Reports.fail("Main SignOn page is not displayed.",
                        "");
            }
            if (Utility.driver.getTitle().equalsIgnoreCase("Inventory management")) {
                Reports.pass("Verify if Inventory management page is displayed.", "Verified successfully.");
            } else Reports.fail("Verify if Inventory management page is displayed.", "Verification failed.");

        } catch (Exception e) {
            Reports.fail("RF page is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clickOnWorkLink() throws Exception {
        String work_xpath = "//*[text()='Work']";

        try {
            Utility.getLocator(work_xpath, "xpath").click();
            Reports.pass("Click on Work link.","Successfully clicked on Work link.");

            if (Utility.driver.getTitle().equalsIgnoreCase("Work profile selection")) {
                Reports.pass("Verify if Work profile selection page is displayed.", "Verified successfully.");
            } else Reports.fail("Verify if Work profile selection page is displayed.", "Verification failed.");

        } catch (Exception e) {
            Reports.fail("RF page is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void clickOnOnPremRFWorkLink() throws Exception {
        String work_xpath = "//*[text()='01Work']";

        try {
            Utility.getLocator(work_xpath, "xpath").click();
            Reports.pass("Click on Work link.","Successfully clicked on Work link.");

            if (Utility.driver.getTitle().equalsIgnoreCase("Work profile selection")) {
                Reports.pass("Verify if Work profile selection page is displayed.", "Verified successfully.");
            } else Reports.fail("Verify if Work profile selection page is displayed.", "Verification failed.");

        } catch (Exception e) {
            Reports.fail("RF page is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void clickOnChangeDefaultWarehouseLink() throws Exception {
        String work_xpath = "//*[text()='Change Default Warehouse']";

        try {
            Utility.getLocator(work_xpath, "xpath").click();
            Reports.pass("Click on Change Default Warehouse link.","Successfully clicked on Change Default Warehouse.");

        } catch (Exception e) {
            Reports.fail("Change Default Warehouse link  is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static String warehouseSelection(WebDriver driver,String warehouse) throws Exception {
        String status = "false";
        String DropDownListWarehouse_xpath = "//*[@name='DropDownListWarehouse']";
        try {
            WebElement AA=Utility.getLocator(DropDownListWarehouse_xpath, "xpath");
            List<WebElement> DrpDownList=AA.findElements(By.tagName("option"));
                for(int i=0;i<=DrpDownList.size()-1;i++ )
                {
                       if(DrpDownList.get(i).isSelected() && (DrpDownList.get(i).getText().equalsIgnoreCase(warehouse))){
                           Reports.pass("Warehouse selection.","Already selected Warehouse as ."+warehouse);
                           status="true";
                           break;
                       }

                    if(DrpDownList.get(i).getText().equalsIgnoreCase(warehouse)){
                        DrpDownList.get(i).click();
                        Reports.pass("Warehouse selection.","Successfully selected Warehouse as ."+warehouse);
                        break;
                    }
                }
        } catch (Exception e) {
            Reports.fail("Warehouse drop down is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
        return status;
    }
    public static void clickOnSave() throws Exception {
        String saveBtn_xpath = "//div[@id='buttons']//input[@id='ILSButtonSAVE']";
        try {
                Utility.getLocator(saveBtn_xpath, "xpath").click();
                Thread.sleep(3000);
                Reports.pass("Click on save button of RF","Successfully clicked on save button.");

        } catch (Exception e) {
            Reports.fail("Save button is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void clickOnRFAlert(WebDriver driver) throws Exception {

        try{
            //driver.switchTo().alert();
            Robot rb =new Robot();
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void clickOnHome() throws Exception {
        String home_xpath = "//a[text()='Home']";

        try {
            Utility.getLocator(home_xpath, "xpath").click();
            Reports.pass("Click on home link of RF",
                    "Successfully clicked on home link.");

        } catch (Exception e) {
            Reports.fail("Home link is not displayed.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

}
