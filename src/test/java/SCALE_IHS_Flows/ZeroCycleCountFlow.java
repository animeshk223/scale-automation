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
import pageFactories.inventory.PgCycleCountPlanInsight;
import pageFactories.inventory.PgCycleCountRequestInsight;
import pageFactories.inventory.PgInventoryInsight;
import pageFactories.inventory.PgWorkInsight;
import utilities.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

public class ZeroCycleCountFlow{

    //Link of the Google Sheet: https://docs.google.com/spreadsheets/d/1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE/edit#gid=0
    static String sheetID = "1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE";
    static String sheetName = "IHS- Zero Cycle Count";
    static int rowToStartFrom = 1;
    public static WebDriver driver;

    @DataProvider(name = "IHS- Zero Cycle Count")
    public static Object[][] getData() throws IOException, GeneralSecurityException {
        return GoogleSheetHandler.readData(sheetID, sheetName);
    }
    @Test(dataProvider = "IHS- Zero Cycle Count")
    public static void ZeroCycleCount(String SN, String scenario, String appMode, String runMode,String item, String loc,String licensePlate,String AVQtyBeforeCC, String OHQtyBeforeCC, String SUQtyBeforeCC,String QtyInserted, String ExpectedAVQtyAfterCC,String ExpectedOHQtyAfterCC,String ExpectedSUQtyAfterCC,String Warehouse,String Status)
            throws Exception {

        String ReferenceID;
        String WorkUnit;

//Launch app
        if (runMode.equalsIgnoreCase("Y")) {
            Reports.startTest(Warehouse + " Zero Cycle count when input quantity is - " + scenario, "Test Environment - " + appMode);
            PgChangeDefaultWarehouse.changeIHSWarehouse(Warehouse);
            //Browser launch and required ware house selection.
            LaunchEnvironment.LaunchEnv("chrome");
            LaunchEnvironment.launchURl("https://ihsscale-test.global.dish.com/scale/trans/dashboard");
            PgHome.cancelWorkstationSelection();
            PgHome.clickOnHamburgerIcon();

//Verify available, OH and SU quantity at UI lavel before cycle count process
            PgHome.gotoInventoryInsightPage();
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.ZoomLeavelToDecrease(driver);
            PgInventoryInsight.openFilterSection();
            PgInventoryInsight.enterLocationInFilterPane(loc);
            PgInventoryInsight.enterItemNumberInFilterPane(item);
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.clickOnFilterIfOpen();
            Reports.info("Verify quantity at UI lavel before zero cycle count process start.","");
            PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",AVQtyBeforeCC);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_ON_HAND_QTY",OHQtyBeforeCC);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_SUSPENSE_QTY",SUQtyBeforeCC);
            PgInventoryInsight.ZoomLeavelToIncrease(driver);


//Create zero cycle count
            PgHome.clickOnHamburgerIcon();
            PgHome.gotoCycleCountPlanInsightPage();
            PgCycleCountPlanInsight.clickOnQuickPlan();
            PgCycleCountPlanInsight.ItemSelectionCriteria("Plan preferences");
            PgCycleCountPlanInsight.enterDescriptionInPlanPreferences(loc);
            PgCycleCountPlanInsight.onZeroCycleCountToggle();
            PgCycleCountPlanInsight.ItemSelectionCriteria("Location inventory");
            PgCycleCountPlanInsight.enterLocationInLocationInventory(loc);
            PgCycleCountPlanInsight.enterItemInLocationInventory(item);
            PgCycleCountPlanInsight.enterCompanyInLocationInventory("DBS");
            PgCycleCountPlanInsight.clickOnCreate();
            Thread.sleep(6000);
            PgHome.clickOnHamburgerIcon();

//Get reference id/Plan ID from Cycle count request insight
            PgHome.gotoCycleCountRequestInsightPage();
            PgCycleCountRequestInsight.openFilterSection();
            PgCycleCountRequestInsight.enterLocationInFilterPane(loc);
            PgCycleCountRequestInsight.enterItemNumberInFilterPane(item);
            PgCycleCountRequestInsight.clickOnPlayButton();
            PgCycleCountRequestInsight.clickOnOpenWork();
            WorkUnit= PgWorkInsight.getOpenWorkUnitData("Work unit");
            ReferenceID=PgWorkInsight.getOpenWorkUnitData("Reference id");
            PgHome.clickOnHamburgerIcon();
            PgHome.gotoCycleCountPlanInsightPage();
            PgCycleCountPlanInsight.openFilterSection();
            PgCycleCountPlanInsight.enterPlanNumberInFilterPane(ReferenceID);
            PgCycleCountPlanInsight.clickOnPlayButton();
            PgCycleCountPlanInsight.selectPlan(ReferenceID);
            PgCycleCountPlanInsight.clickOnRelease();

//Process RF screen
            Thread.sleep(7000);
            PgHome.clickOnHamburgerIcon();
            PgHome.navigateToRFScreen();
            PgRFSignOn.clickOnContinueForNewSession();
            PgRFSignOn.clickOnWorkLink();
            PgWorkProfileSelection.clickOnCycleCount();
            PgBeginWork.enterLocation(loc);
            PgBeginWork.clickOKButton();
            PgBlindCycleCount.verifyBCC();
            PgBlindCycleCount.enterItemNumber(item);
            if(!licensePlate.equals("NA")){
                String[] LP=licensePlate.split(",");
                String temp="";
                for(int i=0;i<LP.length;i++){
                    temp=temp+LP[i];
                }
                String NewLP=temp.trim();
                licensePlate=NewLP;
                PgBlindCycleCount.enterLicensePlateNumber(licensePlate);
            }
            PgBlindCycleCount.enterQty(QtyInserted);
            PgBlindCycleCount.clickOnConfirmButton();
            PgBlindCycleCount.enterQty(QtyInserted);
            PgBlindCycleCount.clickOnConfirmButton();
            PgBlindCycleCount.clickOnDoneButton();

//Verify available, OH and SU quantity at UI lavel after zero cycle count process
            LaunchEnvironment.launchURl("https://ihsscale-test.global.dish.com/scale/trans/dashboard");
            PgHome.cancelWorkstationSelection();
            PgHome.clickOnHamburgerIcon();
            PgHome.gotoInventoryInsightPage();
            PgInventoryInsight.ZoomLeavelToDecrease(driver);
            PgInventoryInsight.openFilterSection();
            PgInventoryInsight.enterLocationInFilterPane(loc);
            PgInventoryInsight.enterItemNumberInFilterPane(item);
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.clickOnFilterIfOpen();
            Reports.info("Verify quantity at UI lavel after zero cycle count process.","");
            PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",ExpectedAVQtyAfterCC);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_ON_HAND_QTY",ExpectedOHQtyAfterCC);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_SUSPENSE_QTY",ExpectedSUQtyAfterCC);


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
            Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}