package pageFactories.RF;

import utilities.Reports;
import utilities.Utility;

public class PgContainerSelection {

    public static void enterLP(String LPN) throws Exception {
        String lpFiled_name = "RFLOGISTICSUNIT";

        try {
            Utility.getLocator(lpFiled_name, "name").sendKeys(LPN);
            Reports.pass("Enter LP","Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Enter LP", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void enterQty(String qty) throws Exception {
        String qtyFiled_name = "CONTQTY";

        try {
            Utility.getLocator(qtyFiled_name, "name").sendKeys(qty);
            Reports.pass("Enter Qty","Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Enter Qty", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void clickOKButton() throws Exception {

        String OKButton_xpath = "//*[@value='OK']";

        try {
            Utility.getLocator(OKButton_xpath, "xpath").click();
            Reports.pass("Click On OK Button", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Click On OK Button", "Not clicked.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
