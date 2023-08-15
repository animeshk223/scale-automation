package pageFactories.dashboard;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import utilities.Reports;
import utilities.Utility;

import java.sql.Driver;
import java.util.ArrayList;

public class PgHome {
    WebDriver driver;
    public static void clickOnHamburgerIcon() throws Exception {
        String id = "menutoggle";

        try {
            Utility.getLocator(id, "id").click();
            Reports.pass("Click on Hamburger Icon.", "Hamburger Icon clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Hamburger Icon not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void gotoPlannedShipmentInsightPage() throws Exception {
        String orderPlanningHeader_xpath = "//a[text()='Order planning']/..";

        String plannedShipmentInsight_lnktxt = "Planned shipment insight";

        try {
            if (Utility.getLocator(orderPlanningHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(orderPlanningHeader_xpath, "xpath").click();
            }
            Utility.getLocator(plannedShipmentInsight_lnktxt, "lnktxt").click();
            Reports.pass("Expand Order Planning and click on Planned Shipment Insight link.",
                    "Successfully clicked on Planned Shipment Insight link.");
        } catch (Exception e) {
            Reports.fail("Planned Shipment Insight link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void gotoWaveInsightPage() throws Exception {
        String orderPlanningHeader_xpath = "//a[text()='Order planning']/..";

        String plannedShipmentInsight_lnktxt = "Wave insight";

        try {
            if (Utility.getLocator(orderPlanningHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(orderPlanningHeader_xpath, "xpath").click();
            }
            Utility.getLocator(plannedShipmentInsight_lnktxt, "lnktxt").click();
            Reports.pass("Expand Order Planning and click on Wave Insight link.",
                    "Successfully clicked on Wave Insight link.");
        } catch (Exception e) {
            Reports.fail("Wave Insight link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void gotoShippingLoadInsightPage() throws Exception {
        String orderPlanningHeader_xpath = "//a[text()='Shipping']/..";

        String plannedShipmentInsight_lnktxt = "Shipping load insight";

        try {
            if (Utility.getLocator(orderPlanningHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(orderPlanningHeader_xpath, "xpath").click();
            }
            Utility.getLocator(plannedShipmentInsight_lnktxt, "lnktxt").click();
            Reports.pass("Expand Shipping and click on Shipping Load Insight link.",
                    "Successfully clicked on Shipping Load Insight link.");
        } catch (Exception e) {
            Reports.fail("Shipping Load Insight link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }

    }


    public static void gotoShipmentInsightPage() throws Exception {
        String orderPlanningHeader_xpath = "//a[text()='Shipping']/..";

        String plannedShipmentInsight_lnktxt = "Shipment insight";

        try {
            if (Utility.getLocator(orderPlanningHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(orderPlanningHeader_xpath, "xpath").click();
            }
            Utility.getLocator(plannedShipmentInsight_lnktxt, "lnktxt").click();
            Reports.pass("Expand Shipping and click on Shipment Insight link.",
                    "Successfully clicked on Shipment Load Insight link.");
        } catch (Exception e) {
            Reports.fail("Shipment Insight link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }

    }

    public static void gotoInventoryInsightPage() throws Exception {
        String orderPlanningHeader_xpath = "//a[text()='Inventory']/..";

        String plannedShipmentInsight_lnktxt = "Inventory insight";

        try {
            if (Utility.getLocator(orderPlanningHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(orderPlanningHeader_xpath, "xpath").click();
            }
            Utility.getLocator(plannedShipmentInsight_lnktxt, "lnktxt").click();
            Reports.pass("Expand Inventory and click on Inventory insight link.",
                    "Successfully clicked on Inventory insight link.");
        } catch (Exception e) {
            Reports.fail("Inventory insight link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }

    }


    public static void gotoRFPage() throws Exception {
        String crossApplicationHeader_xpath = "//a[text()='Cross application']/..";

        String rF_lnktxt = "RF";

        try {
            if (Utility.getLocator(crossApplicationHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(crossApplicationHeader_xpath, "xpath").click();
            }
            Utility.getLocator(rF_lnktxt, "lnktxt").sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
            Reports.pass("Expand Cross Application and click on RF link.",
                    "Successfully clicked on RF link.");

        } catch (Exception e) {
            Reports.fail("RF link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void cancelWorkstationSelection() {
        try {
            Utility.getLocator("workStationSelectionModalNoneButton", "id").click();
        } catch (Exception e) {
            System.out.println("Workstation option is not displayed.");
        }
    }

    public static void selectWarehouse(String warehouse) {
        try {
            Utility.getLocator("currentWhsVal", "id").click();
            Utility.getLocator(warehouse, "lnktxt").click();
        } catch (Exception e) {
            System.out.println("Workstation option is not displayed.");
        }
    }


    public static void gotoCycleCountPlanInsightPage() throws Exception {
        String orderPlanningHeader_xpath = "//a[text()='Inventory']/..";

        String cycleCountPlanInsight_lnktxt = "Cycle count plan insight";

        try {
            if (Utility.getLocator(orderPlanningHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(orderPlanningHeader_xpath, "xpath").click();
            }
            Utility.getLocator(cycleCountPlanInsight_lnktxt, "lnktxt").click();
            Reports.pass("Expand Inventory and click on Cycle count plan insight link.",
                    "Successfully clicked on Planned Shipment Insight link.");
        } catch (Exception e) {
            Reports.fail("Cycle count plan insight link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void closeHamMenu() throws Exception {
        String closeButton_id = "systemmenuclose";
        try {
            Utility.getLocator(closeButton_id, "id").click();
            Reports.pass("click on close button of Ham-Menu pane.",
                    "Successfully clicked on close button.");
        } catch (Exception e) {
            Reports.fail("close button not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void navigateToRFScreen() throws Exception {
        String crossApplicationHeader_xpath = "//a[text()='Cross application']/..";

        String rF_lnktxt = "RF";

        try {
            if (Utility.getLocator(crossApplicationHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(crossApplicationHeader_xpath, "xpath").click();
            }
            Utility.getLocator(rF_lnktxt, "lnktxt").click();
            Reports.pass("Expand Cross Application and click on RF link.",
                    "Successfully clicked on RF link.");

        } catch (Exception e) {
            Reports.fail("RF link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void gotoCycleCountRequestInsightPage() throws Exception {
        String orderPlanningHeader_xpath = "//a[text()='Inventory']/..";

        String cycleCountPlanInsight_lnktxt = "Cycle count request insight";

        try {
            if (Utility.getLocator(orderPlanningHeader_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(orderPlanningHeader_xpath, "xpath").click();
            }
            Utility.getLocator(cycleCountPlanInsight_lnktxt, "lnktxt").click();
            Reports.pass("Expand Inventory and click on Cycle count Request insight link.",
                    "Successfully clicked on Cycle count Request Insight link.");
        } catch (Exception e) {
            Reports.fail("Cycle count Request Insight link not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


}

