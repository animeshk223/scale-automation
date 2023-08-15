package SCALEOnPremFlows;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageFactories.RF.PgChangeDefaultWarehouse;
import pageFactories.dashboard.PgHome;
import pageFactories.inventory.PgInventoryInsight;
import pageFactories.inventory.PgInventoryTransfer;
import utilities.GoogleSheetHandler;
import utilities.LaunchEnvironment;
import utilities.Reports;
import utilities.Utility;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class S07_InventoryTransferToAnotherLocation {
    //Link of the Google Sheet: https://docs.google.com/spreadsheets/d/1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE/edit#gid=0
    static String sheetID = "1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE";
    static String sheetName = "S07- InventoryTransfer";
    static int rowToStartFrom = 1;
    public static WebDriver driver;

    @DataProvider(name = "Inventory Transfer")
    public static Object[][] getData() throws IOException, GeneralSecurityException {
        //ExcelSheetHandler.InitializeExcel(ConstantPaths.EXCEL_PATH,sheetName);
        return GoogleSheetHandler.readData(sheetID, sheetName);//ExcelSheetHandler.readData(sheetName);
    }
    @Test(dataProvider = "Inventory Transfer")
    public static void invTransfer(String SN, String scenario, String appMode, String runMode, String Transfertype, String item,
                                   String fromLoc, String toLoc, String Qty, String warehouse,String AV_FromLocbeforeTransfer,String AV_ToLocbeforeTransfer,String AV_FromLocAfterTransfer,String AV_ToLocAfterTransfer, String status) throws Exception {

        if (runMode.equalsIgnoreCase("Y")) {
            Reports.startTest(scenario + "-" + Transfertype + "-" + warehouse, "Test Environment - " + appMode);

            // Change warehouse as per user requirement.
            PgChangeDefaultWarehouse.changeONPremWarehouse(warehouse);

            LaunchEnvironment.LaunchEnv("chrome");
            LaunchEnvironment.launchURl("https://wmsscale-test.global.dish.com/scale/trans/dashboard");
            PgHome.clickOnHamburgerIcon();
            if (appMode.equalsIgnoreCase("Desktop")) {
                PgHome.gotoInventoryInsightPage();
                PgInventoryInsight.openFilterSection();

                //Verify -to location- inventory before transfer at UI lavel
                PgInventoryInsight.enterItemNumberInFilterPane(item);
                PgInventoryInsight.enterLocationInFilterPane(toLoc);
                PgInventoryInsight.clickOnPlayButton();
                PgInventoryInsight.selectInventory(toLoc);
                PgInventoryInsight.ZoomLeavelToDecrease(driver);
                PgInventoryInsight.ZoomLeavelToDecrease(driver);
                PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",AV_ToLocbeforeTransfer);


                //Verify -From location- inventory before transfer at UI lavel
                PgInventoryInsight.enterItemNumberInFilterPane(item);
                PgInventoryInsight.enterLocationInFilterPane(fromLoc);
                PgInventoryInsight.clickOnPlayButton();
                PgInventoryInsight.selectInventory(fromLoc);
                PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",AV_FromLocbeforeTransfer);


                //Process transfer from one to another location
                PgInventoryInsight.clickOnTransfer();
                PgInventoryTransfer.selectTransferType(Transfertype);
                PgInventoryTransfer.enterQtyToTransfer(Qty);
                PgInventoryTransfer.enterToLocation(toLoc);
                PgInventoryTransfer.clickOnTransferButton();


                //Verify -to location- inventory after transfer at UI lavel
                PgHome.clickOnHamburgerIcon();
                PgHome.gotoInventoryInsightPage();
                PgInventoryInsight.openFilterSection();
                PgInventoryInsight.enterItemNumberInFilterPane(item);
                PgInventoryInsight.enterLocationInFilterPane(toLoc);
                PgInventoryInsight.clickOnPlayButton();
                PgInventoryInsight.selectInventory(toLoc);
                PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",AV_ToLocAfterTransfer);


                //Verify -From location- inventory before transfer at UI lavel
                PgInventoryInsight.enterItemNumberInFilterPane(item);
                PgInventoryInsight.enterLocationInFilterPane(fromLoc);
                PgInventoryInsight.clickOnPlayButton();
                PgInventoryInsight.selectInventory(fromLoc);
                PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",AV_FromLocAfterTransfer);

            }

        }
        else throw new SkipException("Run mode is N");
    }
    @AfterMethod
    public static void temp(ITestResult iTestResult) {
        try {
            if (iTestResult.getStatus() == ITestResult.SUCCESS) {
                //ExcelSheetHandler.setScriptStatus(sheetName,rowToStartFrom,"Passed");
                Reports.attachScreenshot("Screenshot", Utility.getSuccessScreenshot());
                System.out.println("Test Case Passed of Row Number: " + rowToStartFrom);
                Utility.driver.quit();
                GoogleSheetHandler.setScriptStatus(sheetID, sheetName, rowToStartFrom, GoogleSheetHandler.columnCount - 1, "Passed");
            } else if (iTestResult.getStatus() == ITestResult.FAILURE) {
                //ExcelSheetHandler.setScriptStatus(sheetName, rowToStartFrom,"Failed");
                Reports.attachfailScreenshot("", "", Utility.getfailScreenshot("Failed"));
                System.out.println("Test Case Failed of Row Number: " + rowToStartFrom);
                Utility.driver.quit();
                GoogleSheetHandler.setScriptStatus(sheetID, sheetName, rowToStartFrom, GoogleSheetHandler.columnCount - 1, "Failed");
            } else if (iTestResult.getStatus() == ITestResult.SKIP) {
                //ExcelSheetHandler.setScriptStatus(sheetName,rowToStartFrom,"Skipped");
                System.out.println("Test Case Skipped of Row Number: " + rowToStartFrom);
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
            Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
