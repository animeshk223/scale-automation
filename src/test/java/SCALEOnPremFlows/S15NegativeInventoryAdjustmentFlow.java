package SCALEOnPremFlows;

import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageFactories.RF.PgInventoryManagement;
import pageFactories.RF.PgRFSignOn;
import pageFactories.RF.PgUserDefinedData;
import pageFactories.dashboard.PgHome;
import pageFactories.inventory.PgInventoryAdjustment;
import pageFactories.inventory.PgInventoryInsight;
import utilities.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

public class S15NegativeInventoryAdjustmentFlow {

    //Link of the Google Sheet: https://docs.google.com/spreadsheets/d/1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE/edit#gid=0
    static String sheetID = "1mchP_RehJP0NvjU_tn8ahM4McBNQefG4GgkAKffCDIE";
    static String sheetName = "S15 Inv Adjustment -ve";
    static int rowToStartFrom = 1;

    @DataProvider(name = "Inventory Adjustment")
    public static Object[][] getData() throws IOException, GeneralSecurityException {
        //ExcelSheetHandler.InitializeExcel(ConstantPaths.EXCEL_PATH,sheetName);
        return GoogleSheetHandler.readData(sheetID, sheetName);//ExcelSheetHandler.readData(sheetName);
    }

    @Test(dataProvider = "Inventory Adjustment")
    public static void invAdjustment(String SN, String scenario, String appMode, String runMode, String type, String item, String loc,
                                     String licensePlate,String qty,String uDef2, String warehouse, String status)
            throws Exception {
        if (runMode.equalsIgnoreCase("Y")) {
            Reports.startTest(warehouse + "-" + scenario + "-" + type, "Test Environment - " + appMode);
            LaunchEnvironment.LaunchEnv("chrome");
            LaunchEnvironment.launchURl("https://wmsscale-test.global.dish.com/scale/trans/dashboard");
            PgHome.cancelWorkstationSelection();
            PgHome.selectWarehouse(warehouse);
            PgHome.clickOnHamburgerIcon();

            String onHandQtyQuery = "select * from [dbo].[LOCATION_INVENTORY] where item='" + item + "' and warehouse='" + warehouse + "' and LOCATION='" + loc + "'";
            ArrayList<HashMap<String, String>> resultSet = DatabaseHandler.runQueryInILS(onHandQtyQuery);
            float onHandQtyBeforeAdjustment = Float.parseFloat(resultSet.get(0).get("ON_HAND_QTY"));
            float suspenseQtyBeforeAdjustment = Float.parseFloat(resultSet.get(0).get("SUSPENSE_QTY"));

            if (appMode.equalsIgnoreCase("Desktop")) {
                PgHome.gotoInventoryInsightPage();
                PgInventoryInsight.openFilterSection();
                PgInventoryInsight.enterItemNumberInFilterPane(item);
                PgInventoryInsight.clickOnPlayButton();
                PgInventoryInsight.selectInventory(loc);
                PgInventoryInsight.clickOnAdjust();
                PgInventoryAdjustment.selectAdjustmentType(type);
                PgInventoryAdjustment.verifyLocationToBeAdjusted(loc);
                PgInventoryAdjustment.verifyItemToBeAdjusted(item);
                PgInventoryAdjustment.enterQtyToAdjust(qty);

                //Move Order Approval related code starts here..
                if (!scenario.contains("Approval Required")) {
                    PgInventoryAdjustment.clearUDField(1);
                }
                //Move Order Approval related code ends here..

                PgInventoryAdjustment.clickOnAdjustButton();

            } else if (appMode.equalsIgnoreCase("RF")) {

                //PgHome.gotoRFPage();
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

                if (!scenario.contains("Approval Required")) {
                    PgUserDefinedData.clearTextInUserDefined(1);
                }
                //Move Order Approval related code ends here..

                PgUserDefinedData.clickOKButton();

            }

            resultSet = DatabaseHandler.runQueryInILS(onHandQtyQuery);
            float onHandQtyAfterAdjustment = Float.parseFloat(resultSet.get(0).get("ON_HAND_QTY"));
            float suspenseQtyAfterAdjustment = Float.parseFloat(resultSet.get(0).get("SUSPENSE_QTY"));
            if (scenario.contains("Approval Required")) {
                Reports.info("Suspense Qty before Adjustment.", "Qty:" + suspenseQtyBeforeAdjustment);
                if ((suspenseQtyAfterAdjustment - suspenseQtyBeforeAdjustment) == Integer.parseInt(qty) * -1) {
                    Reports.pass("Adjustment was successful.", "Qty: <b>" + qty + "</b>");
                    Reports.info("Suspense Qty after Adjustment.", "Qty:" + suspenseQtyAfterAdjustment);
                } else {
                    Reports.fail("Adjustment was not successful.", "Qty: <b>" + qty + "</b>");
                    Reports.info("Suspense Qty after Adjustment.", "Qty:" + suspenseQtyAfterAdjustment);
                    throw new Exception("Suspense Qty is not as expected.");
                }
            } else {
                Reports.info("OH Qty before Adjustment.", "Qty:" + onHandQtyBeforeAdjustment);
                if ((onHandQtyAfterAdjustment - onHandQtyBeforeAdjustment) == Integer.parseInt(qty)) {
                    Reports.pass("Adjustment was successful.", "Qty: <b>" + qty + "</b>");
                    Reports.info("OH Qty after Adjustment.", "Qty:" + onHandQtyAfterAdjustment);
                } else {
                    Reports.fail("Adjustment was not successful.", "Qty: <b>" + qty + "</b>");
                    Reports.info("OH Qty after Adjustment.", "Qty:" + onHandQtyAfterAdjustment);
                    throw new Exception("OH Qty is not as expected.");
                }
            }
        } else throw new SkipException("Run mode is N");
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