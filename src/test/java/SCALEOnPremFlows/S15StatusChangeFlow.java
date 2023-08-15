package SCALEOnPremFlows;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageFactories.RF.PgChangeDefaultWarehouse;
import pageFactories.dashboard.PgHome;
import pageFactories.inventory.PgInventoryInsight;
import pageFactories.inventory.PgInventoryStatusChange;
import utilities.GoogleSheetHandler;
import utilities.LaunchEnvironment;
import utilities.Reports;
import utilities.Utility;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class S15StatusChangeFlow {
    //Link of the Google Sheet: https://docs.google.com/spreadsheets/d/1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE/edit#gid=0
    static String sheetID = "1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE";
    static String sheetName = "S15 Inv status change";
    static int rowToStartFrom = 1;
    public static WebDriver driver;

    @DataProvider(name = "Inventory status change")
    public static Object[][] getData() throws IOException, GeneralSecurityException {
        //ExcelSheetHandler.InitializeExcel(ConstantPaths.EXCEL_PATH,sheetName);
        return GoogleSheetHandler.readData(sheetID, sheetName);
    }
    @Test(dataProvider = "Inventory status change")
    public static void invAdjustment(String SN, String status,String ChangedStatus, String appMode,String runMode,String item, String loc,String type,String AVQtyBeforeStatusChange,String AVQtyAfterStatusChange,String Warehouse,String Status)
            throws Exception {
        if (runMode.equalsIgnoreCase("Y")) {
            Reports.startTest(Warehouse + "- changing status to " + ChangedStatus + " and" , "Test Environment - " + appMode);
            PgChangeDefaultWarehouse.changeONPremWarehouse(Warehouse);

            //Browser launch and required ware house selection.
            LaunchEnvironment.LaunchEnv("chrome");
            LaunchEnvironment.launchURl("https://wmsscale-test.global.dish.com/scale/trans/dashboard");
            PgHome.cancelWorkstationSelection();
            if (appMode.equalsIgnoreCase("Desktop")) {
                PgHome.clickOnHamburgerIcon();
                PgHome.gotoInventoryInsightPage();
                PgInventoryInsight.ZoomLeavelToDecrease(driver);
                PgInventoryInsight.ZoomLeavelToDecrease(driver);
                PgInventoryInsight.openFilterSection();
                PgInventoryInsight.enterLocationInFilterPane(loc);
                PgInventoryInsight.enterItemNumberInFilterPane(item);
                PgInventoryInsight.clickOnPlayButton();
                PgInventoryInsight.clickOnFilterIfOpen();
                Reports.info("Verify quantity at UI lavel before status change process start.", "");
                PgInventoryInsight.verifyQuantityInInventoryInsight("_AV", AVQtyBeforeStatusChange);
                PgInventoryInsight.verifyRowDataInInventoryInsight("INVENTORY_STS",status);
                PgInventoryInsight.selectInventory(loc);
                PgInventoryInsight.clickOnStatusChange();

                //change status
                PgInventoryStatusChange.selectAdjustmentType(type);
                PgInventoryStatusChange.selectStatus(ChangedStatus);
                PgInventoryStatusChange.clickOnSave();

                //verify the changed staus under Inventory insight page.
                PgHome.clickOnHamburgerIcon();
                PgHome.gotoInventoryInsightPage();
                PgInventoryInsight.openFilterSection();
                PgInventoryInsight.enterLocationInFilterPane(loc);
                PgInventoryInsight.enterItemNumberInFilterPane(item);
                PgInventoryInsight.clickOnPlayButton();
                PgInventoryInsight.verifyRowDataInInventoryInsight("INVENTORY_STS",ChangedStatus);
                PgInventoryInsight.verifyQuantityInInventoryInsight("_AV", AVQtyAfterStatusChange);

            }
        }
    }
    @AfterMethod
    public static void temp(ITestResult iTestResult) {
        try {
            if (iTestResult.getStatus() == ITestResult.SUCCESS) {
                Reports.attachScreenshot("Screenshot", Utility.getSuccessScreenshot());
                Utility.driver.quit();
                GoogleSheetHandler.setScriptStatus(sheetID, sheetName, rowToStartFrom, GoogleSheetHandler.columnCount - 1, "Passed");
            } else if (iTestResult.getStatus() == ITestResult.FAILURE) {
                Reports.attachfailScreenshot("", "", Utility.getfailScreenshot("Failed"));
                Utility.driver.quit();
                GoogleSheetHandler.setScriptStatus(sheetID, sheetName, rowToStartFrom, GoogleSheetHandler.columnCount - 1, "Failed");
            } else if (iTestResult.getStatus() == ITestResult.SKIP) {
                GoogleSheetHandler.setScriptStatus(sheetID, sheetName, rowToStartFrom, GoogleSheetHandler.columnCount - 1, "Skipped");
            }
        } catch (IOException | GeneralSecurityException e) {
            Reports.endTest();
        }
        rowToStartFrom++;
    }

    @AfterTest
    public void flushReport() {
        Reports.flush();
        try {
            Utility.driver.quit();
            Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
