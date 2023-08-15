package pageFactories.RF;

import utilities.Reports;
import utilities.Utility;

public class PgPickConfirmation {

    public static void clickOKButton() throws Exception {
        String okButton_id = "bOK";

        try {
            Utility.getLocator(okButton_id, "id").click();
            Reports.pass("Click on OK button on Pick Confirmation page.","Clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Click on OK button on Pick Confirmation page.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}
