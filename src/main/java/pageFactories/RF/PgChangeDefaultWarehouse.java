package pageFactories.RF;

import org.openqa.selenium.WebDriver;
import utilities.LaunchEnvironment;
import utilities.Utility;

public class PgChangeDefaultWarehouse {
    static WebDriver driver;

    public static void changeIHSWarehouse (String warehouse) throws Exception {
        LaunchEnvironment.LaunchEnv("chrome");
        LaunchEnvironment.launchURl("https://ihsscale-test.global.dish.com/RF/SignonMenuRF.aspx?ACCESS=MANLOGON");
        PgRFSignOn.clickOnContinueForNewSession();
        PgRFSignOn.clickOnChangeDefaultWarehouseLink();
        String status=PgRFSignOn.warehouseSelection(driver,warehouse);
        if(status.equals("false")){
            PgRFSignOn.clickOnSave();
            PgRFSignOn.clickOnRFAlert(driver);
            PgRFSignOn.clickOnHome();
        }
        Utility.driver.close();
    }
    public static void changeONPremWarehouse (String warehouse) throws Exception {
        LaunchEnvironment.LaunchEnv("chrome");
        LaunchEnvironment.launchURl("https://wmsscale-test.global.dish.com/RF/SignonMenuRF.aspx");
        PgRFSignOn.clickOnContinueForNewSession();
        PgRFSignOn.clickOnChangeDefaultWarehouseLink();
        String status=PgRFSignOn.warehouseSelection(driver,warehouse);
        if(status.equals("false")){
            PgRFSignOn.clickOnSave();
            PgRFSignOn.clickOnRFAlert(driver);
            PgRFSignOn.clickOnHome();
        }
        Utility.driver.close();
    }
}
