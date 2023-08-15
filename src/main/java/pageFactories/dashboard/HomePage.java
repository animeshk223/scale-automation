package pageFactories.dashboard;

import utilities.Reports;
import utilities.Utility;

public class HomePage {

    public static void clickOnHamburgerIcon() {
        String id = "menutoggle";

        try {
            Utility.getLocator(id, "id").click();
            Reports.pass("Click on Hamburger Icon.", "Hamburger Icon clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Hamburger Icon not clicked.", e.toString());
            e.printStackTrace();
        }
    }

    public static void gotoPlannedShipmentInsightPage() {
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
        }
    }


    public static void gotoWaveInsightPage() {
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
        }
    }


    public static void gotoShippingLoadInsightPage() {
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
        }

    }


    public static void gotoShipmentInsightPage() {
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
        }

    }

}