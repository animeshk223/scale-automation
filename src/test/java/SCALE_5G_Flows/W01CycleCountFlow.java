package SCALE_5G_Flows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageFactories.RF.PgBeginWork;
import pageFactories.RF.PgBlindCycleCount;
import pageFactories.RF.PgRFSignOn;
import pageFactories.RF.PgWorkProfileSelection;
import pageFactories.dashboard.PgHome;
import pageFactories.inventory.PgCycleCountPlanInsight;
import pageFactories.inventory.PgInventoryInsight;
import pageFactories.inventory.PgWorkInsight;
import utilities.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

public class W01CycleCountFlow {
    //Link of the Google Sheet: https://docs.google.com/spreadsheets/d/1YXZb-jfW3p8ITApXNVMaiwEGCWsaF7xTYsLDrbFCNZ4/edit?usp=sharing
    static String sheetID = "1YXZb-jfW3p8ITApXNVMaiwEGCWsaF7xTYsLDrbFCNZ4";
    static String sheetName = "W01- Cycle Count";
    static int rowToStartFrom = 1;
    public static WebDriver driver;


    @DataProvider(name = "W01 Cycle Count")
    public static Object[][] getData() throws IOException, GeneralSecurityException {
        return GoogleSheetHandler.readData(sheetID, sheetName);
    }
    @Test(dataProvider = "W01 Cycle Count")
    public static void CycleCount(String SN, String scenario, String appMode, String runMode,String item, String loc,String licensePlate,String AVQtyBeforeCC, String OHQtyBeforeCC, String SUQtyBeforeCC,String QtyInserted, String ExpectedAVQtyAfterCC,String ExpectedOHQtyAfterCC,String ExpectedSUQtyAfterCC,String Warehouse,String Status)
            throws Exception {
        String ReferenceID;
        String WorkUnit;

        //Launch app
        if (runMode.equalsIgnoreCase("Y")) {
            Reports.startTest(Warehouse + " Cycle count when input quantity is - " + scenario , "Test Environment - " + appMode);

            //Browser launch and required ware house selection.
            LaunchEnvironment.LaunchEnv("chrome");
            LaunchEnvironment.launchURl("https://wirelesswms-test.global.dish.com/scale/trans/dashboard");
            PgHome.cancelWorkstationSelection();
            PgHome.selectWarehouse(Warehouse);
            PgHome.clickOnHamburgerIcon();

            //Verify OH and SU quantity at DB side before cycle count process
            String onHandQtyQuery = "select * from [dbo].[LOCATION_INVENTORY] where item='" + item + "' and warehouse='" + Warehouse + "' and LOCATION='" + loc + "'";
            ArrayList<HashMap<String, String>> resultSet = DatabaseHandler.runQueryInILS(onHandQtyQuery);
            try {
                float onHandQtyBeforeAdjustment = Float.parseFloat(resultSet.get(0).get("ON_HAND_QTY"));
                float suspenseQtyBeforeAdjustment = Float.parseFloat(resultSet.get(0).get("SUSPENSE_QTY"));

                if(Integer.parseInt(OHQtyBeforeCC)==((int)onHandQtyBeforeAdjustment)){
                    Reports.pass("Verify On Hand quantity before Cycle count on data base.", "On Hand Quantity is : "+ OHQtyBeforeCC+" which is expected");
                }else{
                    Reports.fail("Verify On Hand quantity before Cycle count on data base.", "On Hand Quantity is : "+ ((int)onHandQtyBeforeAdjustment)+" which is not expected");
                }

                if(Integer.parseInt(SUQtyBeforeCC)==((int)suspenseQtyBeforeAdjustment)){
                    Reports.pass("Verify Suspended quantity before Cycle count on data base.", "Suspended Quantity is : "+ SUQtyBeforeCC+" which is expected");
                }else{
                    Reports.fail("Verify Suspended quantity before Cycle count on data base.", "Suspended Quantity is : "+ ((int)suspenseQtyBeforeAdjustment)+" which is not expected");
                }

            }catch (NumberFormatException e){
                System.out.println("not a number");
            }

            //Verify available, OH and SU quantity at UI lavel before cycle count process
            PgHome.gotoInventoryInsightPage();
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.ZoomLeavelToDecrease(driver);
            PgInventoryInsight.openFilterSection();
            PgInventoryInsight.enterLocationInFilterPane(loc);
            PgInventoryInsight.enterItemNumberInFilterPane(item);
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.clickOnFilterIfOpen();
            Reports.info("Verify quantity at UI lavel before cycle count process start.","");
            PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",AVQtyBeforeCC);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_ON_HAND_QTY",OHQtyBeforeCC);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_SUSPENSE_QTY",SUQtyBeforeCC);
            PgInventoryInsight.ZoomLeavelToIncrease(driver);

            //Create cycle count
            PgHome.clickOnHamburgerIcon();
            PgHome.gotoCycleCountPlanInsightPage();
            PgCycleCountPlanInsight.clickOnQuickPlan();
            PgCycleCountPlanInsight.ItemSelectionCriteria("Plan preferences");
            PgCycleCountPlanInsight.enterDescriptionInPlanPreferences(loc);
            PgCycleCountPlanInsight.enterMaxCountInPlanPreferences("1");
            PgCycleCountPlanInsight.enterGroupCountInPlanPreferences("1");
            PgCycleCountPlanInsight.ItemSelectionCriteria("Location inventory");
            PgCycleCountPlanInsight.enterLocationInLocationInventory(loc);
            PgCycleCountPlanInsight.enterItemInLocationInventory(item);
            PgCycleCountPlanInsight.enterCompanyInLocationInventory("DISH");
            PgCycleCountPlanInsight.clickOnCreate();
            PgCycleCountPlanInsight.verifyConfirmationAndClickOnYes();
            PgHome.clickOnHamburgerIcon();

            //Get work unit and reference id
            PgHome.gotoInventoryInsightPage();
            PgInventoryInsight.openFilterSection();
            PgInventoryInsight.enterLocationInFilterPane(loc);
            PgInventoryInsight.enterItemNumberInFilterPane(item);
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.clickOnOpenWork();
            WorkUnit= PgWorkInsight.getOpenWorkUnitData("Work unit");
            ReferenceID=PgWorkInsight.getOpenWorkUnitData("Reference id");
            PgHome.clickOnHamburgerIcon();

            //Process RF screen
            PgHome.gotoRFPage();
            PgRFSignOn.clickOnContinueForNewSession();
            PgRFSignOn.clickOnWorkLink();
            PgWorkProfileSelection.clickOnAllWork();
            PgBeginWork.enterWorkUnit(WorkUnit);
            PgBeginWork.clickOKButton();
            PgBlindCycleCount.verifyBCC();
            PgBlindCycleCount.enterLocation(WorkUnit);
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
            if(scenario.equalsIgnoreCase("Equal to System Qty")){
                PgBlindCycleCount.enterQty(QtyInserted);
                PgBlindCycleCount.clickOnConfirmButton();
                PgBlindCycleCount.clickOnDoneButton();
            }else {
                PgBlindCycleCount.enterQty(QtyInserted);
                PgBlindCycleCount.clickOnConfirmButton();
                PgBlindCycleCount.enterLocation(WorkUnit);
                PgBlindCycleCount.enterQty(QtyInserted);
                PgBlindCycleCount.clickOnConfirmButton();
                PgBlindCycleCount.clickOnDoneButton();
            }
            //Verify available, OH and SU quantity at UI lavel after cycle count process
            LaunchEnvironment.launchURl("https://wmsscale-test.global.dish.com/scale/trans/dashboard");
            PgHome.cancelWorkstationSelection();
            PgHome.clickOnHamburgerIcon();
            PgHome.gotoInventoryInsightPage();
            PgInventoryInsight.ZoomLeavelToDecrease(driver);
            PgInventoryInsight.openFilterSection();
            PgInventoryInsight.enterLocationInFilterPane(loc);
            PgInventoryInsight.enterItemNumberInFilterPane(item);
            PgInventoryInsight.clickOnPlayButton();
            PgInventoryInsight.clickOnFilterIfOpen();
            Reports.info("Verify quantity at UI lavel after cycle count process.","");
            PgInventoryInsight.verifyQuantityInInventoryInsight("_AV",ExpectedAVQtyAfterCC);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_ON_HAND_QTY",ExpectedOHQtyAfterCC);
            PgInventoryInsight.verifyQuantityInInventoryInsight("_SUSPENSE_QTY",ExpectedSUQtyAfterCC);

            //Verify OH and SU quantity at DB side after cycle count process
            resultSet = DatabaseHandler.runQueryInILS(onHandQtyQuery);
            try {
                float onHandQtyAfterAdjustment = Float.parseFloat(resultSet.get(0).get("ON_HAND_QTY"));
                float suspenseQtyAfterAdjustment = Float.parseFloat(resultSet.get(0).get("SUSPENSE_QTY"));
                if(Integer.parseInt(ExpectedOHQtyAfterCC)==((int)onHandQtyAfterAdjustment)){
                    Reports.pass("Verify On Hand quantity after Cycle count on data base.", "On Hand Quantity is : "+ ExpectedOHQtyAfterCC+" which is expected");
                }else{
                    Reports.fail("Verify On Hand quantity after Cycle count on data base.", "On Hand Quantity is : "+ ((int)onHandQtyAfterAdjustment)+" which is not expected");
                }
                if(Integer.parseInt(ExpectedSUQtyAfterCC)==((int)suspenseQtyAfterAdjustment)){
                    Reports.pass("Verify Suspended quantity after Cycle count on data base.", "Suspended Quantity is : "+ ExpectedSUQtyAfterCC+" which is expected");
                }else{
                    Reports.fail("Verify Suspended quantity after Cycle count on data base.", "Suspended Quantity is : "+ ((int)suspenseQtyAfterAdjustment)+" which is not expected");
                }

            }catch (NumberFormatException e){
                System.out.println("not a number");
            }


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
