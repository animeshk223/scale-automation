package pageFactories.RF;

import utilities.Reports;
import utilities.Utility;

public class PgPutawayConfirmation {

    public static void clickOKButton() throws Exception {
        String okButton_id = "bOK";

        try {
            Utility.getLocator(okButton_id, "id").click();
            Reports.pass("Click on OK button on Put Away Confirmation page.","Clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Click on OK button on Put Away Confirmation page.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
}
