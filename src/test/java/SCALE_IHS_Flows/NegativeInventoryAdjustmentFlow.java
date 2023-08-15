package SCALE_IHS_Flows;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageFactories.RF.*;
import pageFactories.dashboard.PgHome;
import pageFactories.inventory.PgInventoryAdjustment;
import pageFactories.inventory.PgInventoryInsight;
import utilities.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

public class NegativeInventoryAdjustmentFlow {

    //Link of the Google Sheet: https://docs.google.com/spreadsheets/d/1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE/edit#gid=0
    static String sheetID = "1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE";
    static String sheetName = "IHS - Inv Adjustment -ve";
    static int rowToStartFrom = 1;
    public static WebDriver driver;

    @DataProvider(name = "Inventory Adjustment")
    public static Object[][] getData() throws Exception {
        return GoogleSheetHandler.readData(sheetID, sheetName);
    }

    @Test(dataProvider = "Inventory Adjustment")
    public static void invAdjustment(String SN, String scenario, String appMode, String runMode, String type, String item, String loc,
                                     String licensePlate,String qty, String testData,String uDef2, String warehouse, String AVQtyBeforeAdjust,String OHQtyBeforeAdjust, String SUQtyBeforeAdjust,String ExpectedAVQtyAfterAdjust,String ExpectedOHQtyAfterAdjust,String ExpectedSUQtyAfterAdjust, String status)
            throws Exception {
        if (runMode.equalsIgnoreCase("Y")) {
            Reports.startTest(scenario + "-" + type + "-" + warehouse, "Test Environment - " + appMode);
            PgChangeDefaultWarehouse.changeIHSWarehouse(warehouse);
            LaunchEnvironment.LaunchEnv("chrome");
            LaunchEnvironment.launchURl("https://ihsscale-test.global.dish.com/scale/security/login");
            PgHome.clickOnHamburgerIcon();

            //Verify available, OH and SU quantity at UI lavel before Adjustment
            PgHome.gotoInventoryInsightPage();
            PgInventoryInsight.ZoomLeavelToDecrease(driver);
            PgInventoryInsight.ZoomLeavelToDecrease(driver);
            PgInventoryInsight.openFilterSection();
            PgInventoryInsight.enterLocationInFilterPane(loc);
            PgInventoryInsight.enterItemNumberInFilterPane(item);
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.clickOnFilterIfOpen();
            Reports.info("Verify quantity at UI lavel before Adjustment process start.","");
            PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",AVQtyBeforeAdjust);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_ON_HAND_QTY",OHQtyBeforeAdjust);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_SUSPENSE_QTY",SUQtyBeforeAdjust);
            //PgInventoryInsight.ZoomLeavelToIncrease(driver);

            if (appMode.equalsIgnoreCase("Desktop")) {
                PgInventoryInsight.selectInventory(loc);
                PgInventoryInsight.clickOnAdjust();
                PgInventoryAdjustment.selectAdjustmentType(type);
                PgInventoryAdjustment.verifyLocationToBeAdjusted(loc);
                PgInventoryAdjustment.verifyItemToBeAdjusted(item);
                PgInventoryAdjustment.enterQtyToAdjust(qty);

                //Move Order Approval related code starts here..
                if (!scenario.contains("Approval Required")) {
                    PgInventoryAdjustment.clearUDField(1);
                }else if (scenario.contains("Approval Required")) {
                    PgInventoryAdjustment.enterValueInUDField(1,"SCRAP");
                }
                //Move Order Approval related code ends here..

                if(!uDef2.isEmpty()){
                    PgInventoryAdjustment.enterValueInUDField(2,uDef2);
                }

                PgInventoryAdjustment.clickOnAdjustButton();

                 PgInventoryInsight.waitForDisappearanceOfToastMsg();


            } else if (appMode.equalsIgnoreCase("RF")) {
                PgHome.clickOnHamburgerIcon();
                PgHome.navigateToRFScreen();
                PgRFSignOn.clickOnContinueForNewSession();
                PgRFSignOn.clickOnInventoryManagementLink();
                PgInventoryManagement.selectAdjustmentType(type);
                PgInventoryManagement.enterItemNumber(item);
                PgInventoryManagement.enterQty(qty);
                if(!licensePlate.equals("NA")){
                    if(licensePlate.contains(",")){
                        String[] LP=licensePlate.split(",");
                        String temp="";
                        for(int i=0;i<LP.length;i++){
                            temp=temp+LP[i];
                        }
                        String NewLP=temp.trim();
                        licensePlate=NewLP;
                    }
                    PgInventoryManagement.enterLicensePlateNumber(licensePlate);
                }
                PgInventoryManagement.enterLocation(loc);
                PgInventoryManagement.clickOnOKButton();

                //Move Order Approval related code starts here..
                if (!scenario.contains("Approval Required")) {
                    PgUserDefinedData.clearTextInUserDefined(1);
                }else if(scenario.contains("Approval Required")) {
                    PgUserDefinedData.enterTextInUserDefined(1, "SCRAP");
                }
                //Move Order Approval related code ends here..

                if(!uDef2.isEmpty()){
                    PgUserDefinedData.enterTextInUserDefined(2, uDef2);
                }

                PgUserDefinedData.clickOKButton();
                LaunchEnvironment.launchURl("https://ihsscale-test.global.dish.com/scale/security/login");

            }

            //Verify available, OH and SU quantity at UI lavel after Adjustment process
            PgHome.clickOnHamburgerIcon();
            PgHome.gotoInventoryInsightPage();
            PgInventoryInsight.ZoomLeavelToDecrease(driver);
            PgInventoryInsight.openFilterSection();
            PgInventoryInsight.enterLocationInFilterPane(loc);
            PgInventoryInsight.enterItemNumberInFilterPane(item);
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.clickOnFilterIfOpen();
            Reports.info("Verify quantity at UI lavel after Adjustment process.","");
            PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",ExpectedAVQtyAfterAdjust);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_ON_HAND_QTY",ExpectedOHQtyAfterAdjust);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_SUSPENSE_QTY",ExpectedSUQtyAfterAdjust);
        } else throw new SkipException("Run mode is N");
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