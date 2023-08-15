package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class LaunchEnvironment {

    /**
     * @author abdul.samad
     */
    public static WebDriver driver;
    public static boolean flag;

    public static boolean LaunchEnv(String browser) {
        flag = false;

        try {
            System.out.println(System.getProperty("user.dir"));
            if (browser.equalsIgnoreCase("chrome")) {
                System.out.println("within chrome");
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
                /*System.setProperty("webdriver.chrome.driver", ConstantPaths.CHROMEDRIVER_PATH);*/
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                DesiredCapabilities dc = new DesiredCapabilities();
                dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                driver = new ChromeDriver(options);
                System.out.println("Chrome Browser Launched");
            } else if (browser.equalsIgnoreCase("IE")) {
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                System.out.println("IE Browser Launched");
            }else if(browser.equalsIgnoreCase("firefox")){
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                System.out.println("FireFox Browser Launched");
            }

            //driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
            flag = true;
            System.out.println("Environment Launched Successfully");
            Reports.pass("Launch Browser", "Chrome Launched Successfully");
        } catch (Exception e) {
            System.out.println("in catch part of chrome browser");
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean launchURl(String URL) {
        System.out.println("in launch url()");
        flag = false;
        try {
            driver.navigate().to(URL);

        } catch (Exception e) {
            System.out.println("Navigated to URL :- " + URL);
            Reports.pass("Navigate to SCALE-IHS URL", "Successfully Navigated to URL:" + URL);
            flag = true;
            System.out.println(e);
        }
        return flag;
    }
}